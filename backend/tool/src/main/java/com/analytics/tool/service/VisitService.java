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

    public Visit logNewVisit(String referrer) {
        // We maken hier het object aan en zetten de tijd op 'nu'
        Visit visit = new Visit(referrer, LocalDateTime.now());
        return visitRepository.save(visit);
    }
}