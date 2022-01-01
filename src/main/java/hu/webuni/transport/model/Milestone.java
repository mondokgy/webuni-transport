package hu.webuni.transport.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="MILESTONE_SEQUENCE_GENERATOR", sequenceName="MILESTONE_SEQUENCE", initialValue=1, allocationSize=10)
public class Milestone {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MILESTONE_SEQUENCE_GENERATOR")
	private Long milestoneId;
	
	@ManyToOne
	@JoinColumn(name ="addressId")
	private Address address;
	private LocalDateTime plannedTime;
	
	public Milestone() {
		
	}
	
	public Milestone(Long milestoneId, Address address, LocalDateTime plannedTime) {
		super();
		this.milestoneId = milestoneId;
		this.address = address;
		this.plannedTime = plannedTime;
	}
	
	public Milestone(Address address, LocalDateTime plannedTime) {
		super();
		this.address = address;
		this.plannedTime = plannedTime;
	}

	public Long getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(Long milestoneId) {
		this.milestoneId = milestoneId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public LocalDateTime getPlannedTime() {
		return plannedTime;
	}

	public void setPlannedTime(LocalDateTime plannedTime) {
		this.plannedTime = plannedTime;
	}

	@Override
	public String toString() {
		return "Milestone [milestoneId=" + milestoneId + ", address=" + address + ", plannedTime=" + plannedTime + "]";
	}
	
}


