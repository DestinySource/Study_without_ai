package com.analytics.tool.service;

import com.analytics.tool.model.Visit;
import com.analytics.tool.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.file.Paths;
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

    public String getVisitCountry() {
        String url = "https://api.ipdata.co/country_code?api-key=2b1cc91904281119589b2d02a698106cf58f59c4e018b1a57bfe9ed6";
        URI uri = URI.create(url);

        try (java.util.Scanner s = new java.util.Scanner(uri.toURL().openStream())) {
            String country = s.hasNext() ? s.next().trim() : "unknown";
            return "My current country is " + country;
        } catch (java.io.IOException e) {
            return "My current country is unknown";
        }
    }
}