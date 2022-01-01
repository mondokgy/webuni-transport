package hu.webuni.transport.dto;

import javax.validation.constraints.NotNull;

public class DelayDto {
	@NotNull(message = "milestoneId cannot be null")
	private Long milestoneId;
	@NotNull(message = "delayMin cannot be null")
	private Integer delayMin;
	
	public DelayDto() {

	}
	
	public DelayDto(Long milestoneId, Integer delayMin) {
		super();
		this.milestoneId = milestoneId;
		this.delayMin = delayMin;
	}
	
	public Long getMilestoneId() {
		return milestoneId;
	}
	public void setMilestoneId(Long milestoneId) {
		this.milestoneId = milestoneId;
	}
	public Integer getDelayMin() {
		return delayMin;
	}
	public void setDelayMin(Integer delayMin) {
		this.delayMin = delayMin;
	}
	
	@Override
	public String toString() {
		return "DelayDto [milestoneId=" + milestoneId + ", delayMin=" + delayMin + "]";
	}
		
}
