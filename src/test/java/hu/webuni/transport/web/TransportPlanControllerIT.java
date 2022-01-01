package hu.webuni.transport.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.transport.dto.DelayDto;
import hu.webuni.transport.dto.LoginDto;
import hu.webuni.transport.model.Address;
import hu.webuni.transport.model.Milestone;
import hu.webuni.transport.model.Section;
import hu.webuni.transport.model.TransportPlan;
import hu.webuni.transport.repository.AddressRepository;
import hu.webuni.transport.repository.MilestoneRepository;
import hu.webuni.transport.repository.SectionRepository;
import hu.webuni.transport.repository.TransportPlanRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransportPlanControllerIT {

	private static final String BASE_URI = "/api/transportPlans";
	
	@Autowired
	WebTestClient webTestClient;
	
	@Autowired
	TransportPlanRepository tpRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	MilestoneRepository milestoneRepository;
	
	@Autowired
	SectionRepository sectionRepository;
	
	String user1 = "userAddress";
	String user2 = "userTransport";
	String pass = "pass";
	
	private String jwt;
	
	//User security check
	@Test
	void testForbiddenUser() throws Exception{
		
		jwt = login(user1);
		
		List<TransportPlan> tpList = tpRepository.findAll();
		
		TransportPlan tp = tpList.get(0);
		
		List<Section> sectionList = tp.getSection();
		
		Section section = sectionList.get(0);

		DelayDto delay = new DelayDto(section.getFromMilestone().getMilestoneId(),45);
		registrinDelay(delay,tp.getTransportPlanId())
		.expectStatus().isForbidden();
	}
	
	//Milestone exitst check
	@Test
	void testThatValidMilestone() throws Exception{
		
		jwt = login(user2);
		
		List<TransportPlan> tpList = tpRepository.findAll();
		
		TransportPlan tp = tpList.get(0);
		
		List<Section> sectionList = tp.getSection();
		
		Long maxMilestoneId = 0L;
		
		for(Section s : sectionList) {
			
			if(maxMilestoneId<s.getFromMilestone().getMilestoneId()) {
				maxMilestoneId = s.getFromMilestone().getMilestoneId();
			}
			
			if(maxMilestoneId<s.getToMilestone().getMilestoneId()) {
				maxMilestoneId = s.getToMilestone().getMilestoneId();
			}
			
		}
		
		DelayDto delay = new DelayDto(maxMilestoneId+10,45);
		registrinDelay(delay,tp.getTransportPlanId())
		.expectStatus().isNotFound();
	}
	
	//TransportPlan exists check
	@Test
	void testThatValidTransportPlan() throws Exception{
		
		jwt = login(user2);
		
		List<TransportPlan> tpList = tpRepository.findAll();
		
		Long maxTpId = 0L;
		
		for(TransportPlan tp : tpList) {
			
			if(maxTpId<tp.getTransportPlanId()) {
				maxTpId = tp.getTransportPlanId();
			}
		}
		
		TransportPlan tp = tpList.get(0);
		
		List<Section> sectionList = tp.getSection();
	
		DelayDto delay = new DelayDto(sectionList.get(0).getToMilestone().getMilestoneId(),45);
		registrinDelay(delay,maxTpId+10)
		.expectStatus().isNotFound();
	}
	
	//TransportPlan and Milestone relation check
	@Test
	void testThatValidMileStoneAnsTransportPlan() throws Exception{
		
		jwt = login(user2);
		
		List<TransportPlan> tpList = tpRepository.findAll();
		
		TransportPlan tp = tpList.get(0);
		
		Address address = addressRepository.save(new Address("HU", "Budapest", "1194", "Kossuth u.", "1", Double.valueOf(40), Double.valueOf(36)));
		Milestone milestone = milestoneRepository.save(new Milestone(address,LocalDateTime.now()));
		
		DelayDto delay = new DelayDto(milestone.getMilestoneId(),45);
		registrinDelay(delay,tp.getTransportPlanId())
		.expectStatus().isBadRequest();
	}
	
	//success call check (proceeds, milestone planned time change check)
	@Test
	void testThatSuccesCall() throws Exception{
		
		jwt = login(user2);
		
		Integer delaymin = 45;
		
		List<TransportPlan> tpList = tpRepository.findAll();
		
		TransportPlan tp = tpList.get(0);
		
		List<Section> sectionList = tp.getSection();
		
		Section section = sectionRepository.findBySectionId(sectionList.get(0).getSectionId());
		
		Milestone milestone = section.getFromMilestone();
		
		LocalDateTime origFromTime = section.getFromMilestone().getPlannedTime();
		LocalDateTime origToTime = section.getToMilestone().getPlannedTime();
		
		Double origProceeds = tp.getProceeds();
		
		DelayDto delay = new DelayDto(milestone.getMilestoneId(),delaymin);
		registrinDelay(delay,tp.getTransportPlanId())
		.expectStatus().isOk();
		
		Double newProceeds = tpRepository.findById(tp.getTransportPlanId()).get().getProceeds();
		
		assertThat(origProceeds*0.8)
		.isEqualTo(newProceeds);
		
		Section newSection = sectionRepository.findBySectionId(sectionList.get(0).getSectionId());
		LocalDateTime newFromTime = newSection.getFromMilestone().getPlannedTime();
		LocalDateTime newToTime = newSection.getToMilestone().getPlannedTime();
		
		assertThat(origFromTime.plusMinutes(delaymin))
		.isEqualTo(newFromTime);
		
		assertThat(origToTime.plusMinutes(delaymin))
		.isEqualTo(newToTime);
	}
	
	private String login(String user){ 
		LoginDto body = new LoginDto(user,pass);

		return jwt = webTestClient.post()
				.uri("/api/login")
				.bodyValue(body)
				.exchange()
				.expectBody(String.class)
				.returnResult()
				.getResponseBody();
	}
	
	private ResponseSpec registrinDelay(DelayDto delay, Long tpId) {
		String path = BASE_URI + "/" + tpId.toString() + "/delay";
		return webTestClient
				.post()
				.uri(path)
				.headers(headers -> headers.setBearerAuth(jwt))
				.bodyValue(delay)
				.exchange();
	}
}
