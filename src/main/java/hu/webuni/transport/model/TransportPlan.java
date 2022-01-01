package hu.webuni.transport.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="TP_SEQUENCE_GENERATOR", sequenceName="TP_SEQUENCE", initialValue=1, allocationSize=10)
public class TransportPlan {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TP_SEQUENCE_GENERATOR")
	private Long transportPlanId;
	
	private Double proceeds;
	
	@OneToMany(mappedBy="tp")  
	private List<Section> section;
	
	public TransportPlan() {
		
	}
	
	public TransportPlan(Long transportPlanId, Double proceeds, List<Section> section) {
		super();
		this.transportPlanId = transportPlanId;
		this.proceeds = proceeds;
		this.section = section;
	}
	
	public TransportPlan(Double proceeds, List<Section> section) {
		super();
		this.proceeds = proceeds;
		this.section = section;
	}

	public Long getTransportPlanId() {
		return transportPlanId;
	}

	public void setTransportPlanId(Long transportPlanId) {
		this.transportPlanId = transportPlanId;
	}

	public Double getProceeds() {
		return proceeds;
	}

	public void setProceeds(Double proceeds) {
		this.proceeds = proceeds;
	}

	public List<Section> getSection() {
		return section;
	}

	public void setSection(List<Section> section) {
		this.section = section;
	}

	@Override
	public String toString() {
		return "TransportPlan [transportPlanId=" + transportPlanId + ", proceeds=" + proceeds + ", section=" + section
				+ "]";
	}
	
}
