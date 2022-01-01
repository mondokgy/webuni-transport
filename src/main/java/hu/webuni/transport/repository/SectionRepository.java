package hu.webuni.transport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.transport.model.Section;
import hu.webuni.transport.model.TransportPlan;

public interface SectionRepository extends JpaRepository<Section, Long>{

	public Section findByNumberAndTp(Integer number, TransportPlan tp);
}
