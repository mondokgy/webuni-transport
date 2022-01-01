package hu.webuni.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.transport.model.TransportPlan;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long>{
	@EntityGraph(attributePaths = {"section"})
	List<TransportPlan> findAll();
}
