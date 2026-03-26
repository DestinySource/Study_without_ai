package com.analytics.tool.service;

import com.analytics.tool.model.Visit;
import com.analytics.tool.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Value("${apiKey}")
    private String apiKey;

    public Visit logNewVisit(String referrer, String source) {
        Visit visit = new Visit();
        visit.setReferrer(referrer);
        visit.setSource(source != null ? source : "direct");
        visit.setTimestamp(LocalDateTime.now());

        return visitRepository.save(visit);
    }

    public String getVisitCountry() {

        String url = "https://api.ipdata.co/country_code?api-key=" + apiKey;
        URI uri = URI.create(url);

        try (java.util.Scanner s = new java.util.Scanner(uri.toURL().openStream())) {
            return s.hasNext() ? s.next().trim() : "unknown";
        } catch (java.io.IOException e) {
            return "My current country is unknown";
        }
    }

    public String getVisitFlag() {
        String url = "https://api.ipdata.co/flag?api-key=" + apiKey;
        URI uri = URI.create(url);

        try (java.util.Scanner s = new java.util.Scanner(uri.toURL().openStream())) {
            return s.hasNext() ? s.next().trim() : "unknown";
        } catch (java.io.IOException e) {
            return "My current flag is unknown";
        }
    }
}