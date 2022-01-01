package hu.webuni.transport.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.transport.config.TransportConfigProperties;
import hu.webuni.transport.config.TransportConfigProperties.Delay;
import hu.webuni.transport.dto.DelayDto;
import hu.webuni.transport.exception.InvalidTransportPlanAndMilestonePairException;
import hu.webuni.transport.exception.MilestoneNotFoundException;
import hu.webuni.transport.exception.TransportPlanNotFoundException;
import hu.webuni.transport.model.Milestone;
import hu.webuni.transport.model.Section;
import hu.webuni.transport.model.TransportPlan;
import hu.webuni.transport.repository.MilestoneRepository;
import hu.webuni.transport.repository.SectionRepository;
import hu.webuni.transport.repository.TransportPlanRepository;

@Service
public class TransportPlanService {
private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	private MilestoneRepository milestoneRepository;
	
	@Autowired
	private SectionRepository sectionRepository;
	
	@Autowired
	private TransportPlanRepository tpRepository;
	
	@Autowired
	private TransportConfigProperties config;
	
	@Transactional
	public void addDelay(DelayDto delayDto, Long transportPlanId) {
		log.debug("called TransportPlanService.addDelay()");
		
		Long milestoneId = delayDto.getMilestoneId();
		Integer delayMin = delayDto.getDelayMin();
		
		TransportPlan tp;
		
		try {
			milestoneRepository.findById(milestoneId).orElseThrow(()->new NoSuchElementException());
		}catch(NoSuchElementException e){
			throw new MilestoneNotFoundException("Milestone (id=" +milestoneId.toString() + ") not found!");
		}
		try {
			tp = tpRepository.findById(transportPlanId).orElseThrow(()->new NoSuchElementException());
		}catch(NoSuchElementException e){
			throw new TransportPlanNotFoundException("TransportPlan (id=" + transportPlanId.toString() + ")not found!");
		}
		
		List<Section> sectionList = tp.getSection();
		
		Integer sectionNumber = null;
		boolean isStart = false;
		boolean isEnd = false;
		
		Milestone startMilestone = null;
		Milestone endMilestone = null;
		
		for(Section section : sectionList) {
			startMilestone = section.getFromMilestone();
			endMilestone = section.getToMilestone();
			
			if(startMilestone.getMilestoneId().equals(milestoneId)) {
				sectionNumber = section.getNumber();
				isStart = true;
			}
			if(endMilestone.getMilestoneId().equals(milestoneId)) {
				sectionNumber = section.getNumber();
				isEnd = true;
			}
			
			if(sectionNumber!=null) {
			  break;
			}
		}
		
		if (sectionNumber == null) {
			throw new InvalidTransportPlanAndMilestonePairException(" The TransportPlan (id=" + transportPlanId.toString() + ") has not got Milestone (id="+milestoneId.toString()+")!");
		}
		
		Long delayNanos = TimeUnit.NANOSECONDS.convert(delayMin, TimeUnit.MINUTES);

		addDelay(milestoneId,delayNanos);
		
		if(isStart && endMilestone !=null) {
			addDelay(endMilestone.getMilestoneId(),delayNanos);
		}
		
		if(isEnd) {			
			Section sectionNext = sectionRepository.findByNumberAndTp(sectionNumber+1,tp);
			
			if(sectionNext!=null) {				
				addDelay(sectionNext.getFromMilestone().getMilestoneId(),delayNanos);
			}			
		}
		
		
		List<Delay> delayConfigList = config.getDelay();
		int percent = 0;
		int actDelay = 0;
		for(Delay delayConfig : delayConfigList) {
			if (delayMin<delayConfig.getMin() && (delayConfig.getMin()<actDelay || actDelay==0)) {
				actDelay = delayConfig.getMin();
				percent = delayConfig.getPercent();
			}
		}
		
		Double proceeds = tp.getProceeds();
		Double descPercent = (1-(percent/100.0));
		Double newProceeds = proceeds * descPercent;
		tp.setProceeds(newProceeds);
		tpRepository.save(tp);
		
		log.debug("finished TransportPlanService.addDelay()");
	}
	
	@Transactional
	private void addDelay(Long milestoneId, Long delayNanos) {
		Milestone milestone= milestoneRepository.findById(milestoneId).get();
		LocalDateTime origDateTime = milestone.getPlannedTime();
		LocalDateTime newDatetime = origDateTime.plusNanos(delayNanos);
		milestone.setPlannedTime(newDatetime);
		milestoneRepository.save(milestone);
	}
}
