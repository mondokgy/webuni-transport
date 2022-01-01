package hu.webuni.transport.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.transport.model.Address;
import hu.webuni.transport.model.Milestone;
import hu.webuni.transport.model.Section;
import hu.webuni.transport.model.TransportPlan;
import hu.webuni.transport.repository.AddressRepository;
import hu.webuni.transport.repository.MilestoneRepository;
import hu.webuni.transport.repository.SectionRepository;
import hu.webuni.transport.repository.TransportPlanRepository;

@Service
public class InitDbService {
	
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	MilestoneRepository milestoneRepository ;
	
	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	TransportPlanRepository tpRepository;
	
	
	public void clearDb(){		
		addressRepository.deleteAll();	
		milestoneRepository.deleteAll();
		sectionRepository.deleteAll();
		tpRepository.deleteAll();
	}
	
	public void insertTestData(){
		Address address1 = addressRepository.save(new Address("HU", "Budapest", "1194", "Kossuth u.", "1", Double.valueOf(40), Double.valueOf(36)));
		Address address2 = addressRepository.save(new Address("HU", "Győr", "8765", "Raktár köz", "5", Double.valueOf(40), Double.valueOf(36)));
		Address address3 = addressRepository.save(new Address("HU", "Budapest", "1205", "Petőfi u.", "97", Double.valueOf(40), Double.valueOf(36)));
		Address address4 = addressRepository.save(new Address("HU", "Pécs", "7654", "Fő u.", "1", Double.valueOf(40), Double.valueOf(36)));
		Address address5 = addressRepository.save(new Address("AU", "Bécs", "3423-1", "Address s.", "16", Double.valueOf(40), Double.valueOf(36)));
		Address address6 = addressRepository.save(new Address("AU", "Bécs", "3531-1", "Address s.", "35", Double.valueOf(40), Double.valueOf(36)));
		Address address7 = addressRepository.save(new Address("AU", "Linz", "7532-7", "Address s.", "644", Double.valueOf(40), Double.valueOf(36)));
		Address address8 = addressRepository.save(new Address("AU", "Linz", "6356-5", "Address s.", "2", Double.valueOf(40), Double.valueOf(36)));
		Address address9 = addressRepository.save(new Address("DE", "München", "353545", "Address s.", "63", Double.valueOf(40), Double.valueOf(36)));
		Address address10 = addressRepository.save(new Address("DE", "Berlin", "765445", "Address s.", "134", Double.valueOf(40), Double.valueOf(36)));
	
		Milestone milestone1 = milestoneRepository.save(new Milestone(address1,LocalDateTime.now()));
		Milestone milestone2 = milestoneRepository.save(new Milestone(address2,LocalDateTime.now()));
		Milestone milestone3 = milestoneRepository.save(new Milestone(address3,LocalDateTime.now()));
		Milestone milestone4 = milestoneRepository.save(new Milestone(address4,LocalDateTime.now()));
		Milestone milestone5 = milestoneRepository.save(new Milestone(address5,LocalDateTime.now()));
		Milestone milestone6 = milestoneRepository.save(new Milestone(address6,LocalDateTime.now()));
		Milestone milestone7 = milestoneRepository.save(new Milestone(address7,LocalDateTime.now()));
		Milestone milestone8 = milestoneRepository.save(new Milestone(address8,LocalDateTime.now()));
		Milestone milestone9 = milestoneRepository.save(new Milestone(address9,LocalDateTime.now()));
		Milestone milestone10 = milestoneRepository.save(new Milestone(address10,LocalDateTime.now()));
	
		List<Section> sectionList1 = new ArrayList<Section>();	
		TransportPlan tp1 = tpRepository.save(new TransportPlan(2353455.0,sectionList1));
		
		Section section = sectionRepository.save(new Section(milestone1,milestone2,0,tp1));
		sectionList1.add(section);
		Section section1 = sectionRepository.save(new Section(milestone3,milestone4,1,tp1));
		sectionList1.add(section1);
		Section section2 = sectionRepository.save(new Section(milestone5,milestone6,2,tp1));
		sectionList1.add(section2);
		Section section3 = sectionRepository.save(new Section(milestone7,milestone8,3,tp1));
		sectionList1.add(section3);
		Section section4 = sectionRepository.save(new Section(milestone9,milestone10,4,tp1));
		sectionList1.add(section4);
		
		tp1.setSection(sectionList1);
		tpRepository.save(tp1);
		
		List<Section> sectionList2 = new ArrayList<Section>();
		TransportPlan tp2 = tpRepository.save(new TransportPlan(999000.0,sectionList2));
		
		Section section5 = sectionRepository.save(new Section(milestone2,milestone3,0,tp2));
		sectionList1.add(section5);
		Section section6 = sectionRepository.save(new Section(milestone4,milestone6,1,tp2));
		sectionList1.add(section6);
		Section section7 = sectionRepository.save(new Section(milestone7,milestone8,2,tp2));
		sectionList1.add(section7);
		Section section8 = sectionRepository.save(new Section(milestone9,milestone1,3,tp2));
		sectionList1.add(section8);
		
		tp2.setSection(sectionList2);
		tpRepository.save(tp2);
	}
}
