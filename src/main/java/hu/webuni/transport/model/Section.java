package hu.webuni.transport.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="SECTION_SEQUENCE_GENERATOR", sequenceName="SECTION_SEQUENCE", initialValue=1, allocationSize=10)

public class Section {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SECTION_SEQUENCE_GENERATOR")
	private Long sectionId;
	
	@OneToOne()
    @JoinColumn(name = "fromMilestoneId", referencedColumnName = "milestoneId")
	private Milestone fromMilestone;
	@OneToOne()
    @JoinColumn(name = "toMilestoneId", referencedColumnName = "milestoneId")
	private Milestone toMilestone;
	private Integer number;
	@ManyToOne
	@JoinColumn(name ="transportPlanId")
	private TransportPlan tp;
	
	public Section() {
		
	}
	
	public Section(Long sectionId, Milestone fromMilestone, Milestone toMilestone, Integer number) {
		super();
		this.sectionId = sectionId;
		this.fromMilestone = fromMilestone;
		this.toMilestone = toMilestone;
		this.number = number;
	}
	
	public Section(Milestone fromMilestone, Milestone toMilestone, Integer number, TransportPlan tp) {
		super();
		this.fromMilestone = fromMilestone;
		this.toMilestone = toMilestone;
		this.number = number;
		this.tp = tp;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Milestone getFromMilestone() {
		return fromMilestone;
	}

	public void setFromMilestone(Milestone fromMilestone) {
		this.fromMilestone = fromMilestone;
	}

	public Milestone getToMilestone() {
		return toMilestone;
	}

	public void setToMilestone(Milestone toMilestone) {
		this.toMilestone = toMilestone;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Section [sectionId=" + sectionId + ", fromMilestone=" + fromMilestone + ", toMilestone=" + toMilestone
				+ ", number=" + number + "]";
	}
	
}
