package com.analytics.tool.service;

import com.analytics.tool.model.Visit;
import com.analytics.tool.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    public Visit logNewVisit(String referrer, String source) {
        Visit visit = new Visit();
        visit.setReferrer(referrer);
        visit.setSource(source != null ? source : "direct");
        visit.setTimestamp(LocalDateTime.now());

        return visitRepository.save(visit);
    }
}