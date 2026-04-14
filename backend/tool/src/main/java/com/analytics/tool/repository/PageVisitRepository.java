package com.analytics.tool.repository;

import java.util.List;

import com.analytics.tool.model.PageVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PageVisitRepository extends JpaRepository<PageVisit, Long> {
	long countByIsBounceTrue();

	@Query("SELECT p.page, COUNT(p) FROM PageVisit p GROUP BY p.page ORDER BY COUNT(p) DESC")
	List<Object[]> getVisitsPerPage();

	@Query("SELECT p.source, COUNT(p) FROM PageVisit p GROUP BY p.source ORDER BY COUNT(p) DESC")
	List<Object[]> getVisitsPerSource();
}