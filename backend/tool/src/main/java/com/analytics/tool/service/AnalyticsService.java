package com.analytics.tool.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.analytics.tool.DTO.SiteStatsDTO;
import com.analytics.tool.DTO.VisitRequestDTO;
import com.analytics.tool.model.PageVisit;
import com.analytics.tool.repository.PageVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnalyticsService {

    @Autowired
    private PageVisitRepository pageVisitRepository;

    // Bezoek opslaan — conversie van DTO naar Model gebeurt hier
    public void saveVisit(VisitRequestDTO dto) {
        PageVisit visit = new PageVisit();
        visit.setPage(dto.page());
        visit.setReferrer(dto.referrer());
        visit.setSource(dto.source());
        visit.setTimeOnPage(dto.timeOnPage());
        visit.setIsBounce(dto.isBounce());
        visit.setVisitedAt(LocalDateTime.now());
        pageVisitRepository.save(visit);
    }

    // Site statistieken berekenen
    public SiteStatsDTO getSiteStats() {
        long totalVisits  = pageVisitRepository.count();
        long totalBounces = pageVisitRepository.countByIsBounceTrue();
        double bounceRate = totalVisits > 0
                ? Math.round((double) totalBounces / totalVisits * 10000.0) / 100.0
                : 0.0;

        return new SiteStatsDTO(totalVisits, totalBounces, bounceRate);
    }

    public List<Map<String, Object>> getVisitsPerPage() {
        return pageVisitRepository.getVisitsPerPage()
                .stream()
                .map(row -> Map.of("label", row[0], "count", row[1]))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getVisitsPerSource() {
        return pageVisitRepository.getVisitsPerSource()
                .stream()
                .map(row -> Map.of("label", row[0], "count", row[1]))
                .collect(Collectors.toList());
    }
}