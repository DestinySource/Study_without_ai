package com.analytics.tool.controller;

import com.analytics.tool.model.Visit;
import com.analytics.tool.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @PostMapping("/log-visit")
    public Visit logVisit(@RequestBody Map<String, String> payload) {
        String referrer = payload.get("referrer");
        String source = payload.get("source");
        return visitService.logNewVisit(referrer, source);
    }
}