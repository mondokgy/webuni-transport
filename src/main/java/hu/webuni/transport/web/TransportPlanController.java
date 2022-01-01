package hu.webuni.transport.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.transport.dto.DelayDto;
import hu.webuni.transport.exception.InvalidTransportPlanAndMilestonePairException;
import hu.webuni.transport.exception.MilestoneNotFoundException;
import hu.webuni.transport.exception.TransportPlanNotFoundException;
import hu.webuni.transport.service.TransportPlanService;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {
	
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Autowired
	private TransportPlanService tpService;
	
	@PostAuthorize("hasAuthority('TransportManager')")
	@PostMapping("/{id}/delay")
	public void registringDelay(@PathVariable long id, @Valid @RequestBody DelayDto delayDto) {
		
		log.debug("TransportPlan restapi controller, /api/transportPlans, post, registringDelay start");		
		log.debug("Request body:"+delayDto.toString());		
		
		try {
			tpService.addDelay(delayDto,id);
		}catch (MilestoneNotFoundException e) {
			log.debug("MilestoneNotFoundException, message: "+e.getMessage());		
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}catch (TransportPlanNotFoundException e) {
			log.debug("TransportPlanNotFoundException, message: "+e.getMessage());		
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}catch(InvalidTransportPlanAndMilestonePairException e) {
			log.debug("InvalidTransportPlanAndMilestonePairException, message: "+e.getMessage());	
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
		
		log.debug("TransportPlan restapi controller, /api/transportPlans, post, registringDelay end");
		
	}
}
