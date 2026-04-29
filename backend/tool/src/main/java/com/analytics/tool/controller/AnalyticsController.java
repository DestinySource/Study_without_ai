package com.analytics.tool.controller;

import java.util.List;
import java.util.Map;

import com.analytics.tool.DTO.DeviceInfoDTO;
import com.analytics.tool.DTO.SiteStatsDTO;
import com.analytics.tool.DTO.VisitRequestDTO;
import com.analytics.tool.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/log-visit")
    public ResponseEntity<Void> logVisit(@RequestBody VisitRequestDTO dto) {
        analyticsService.saveVisit(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/analytics")
    public ResponseEntity<SiteStatsDTO> getAnalytics() {
        return ResponseEntity.ok(analyticsService.getSiteStats());
    }

    @GetMapping("/analytics/pages")
    public ResponseEntity<List<Map<String, Object>>> getVisitsPerPage() {
        return ResponseEntity.ok(analyticsService.getVisitsPerPage());
    }

    @GetMapping("/analytics/sources")
    public ResponseEntity<List<Map<String, Object>>> getVisitsPerSource() {
        return ResponseEntity.ok(analyticsService.getVisitsPerSource());
    }

    @GetMapping("/analytics/browsers")
    public ResponseEntity<List<Map<String, Object>>> getBrowserStats() {
        return ResponseEntity.ok(analyticsService.getBrowserStats());
    }

    @PostMapping("/log-device")
    public ResponseEntity<Void> logDevice(@RequestBody DeviceInfoDTO dto) {
        analyticsService.saveDevice(dto);
        return ResponseEntity.ok().build();
    }
}