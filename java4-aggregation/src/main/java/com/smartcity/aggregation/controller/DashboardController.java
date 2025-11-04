package com.smartcity.aggregation.controller;

import com.smartcity.aggregation.dto.DashboardDTO;
import com.smartcity.aggregation.dto.KPIDTO;
import com.smartcity.aggregation.dto.TimeWindowedMetricDTO;
import com.smartcity.aggregation.service.AnalyticsService;
import com.smartcity.aggregation.service.DashboardService;
import com.smartcity.aggregation.service.KPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    
    private final DashboardService dashboardService;
    private final KPIService kpiService;
    private final AnalyticsService analyticsService;
    
    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboard() {
        DashboardDTO dashboard = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/kpis")
    public ResponseEntity<KPIDTO> getKPIs() {
        KPIDTO kpis = kpiService.getKPIs();
        return ResponseEntity.ok(kpis);
    }
    
    @GetMapping("/kpis/time-window")
    public ResponseEntity<KPIDTO> getKPIsForTimeWindow(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        KPIDTO kpis = kpiService.getKPIsForTimeWindow(startTime, endTime);
        return ResponseEntity.ok(kpis);
    }
    
    @GetMapping("/metrics/{metricName}")
    public ResponseEntity<TimeWindowedMetricDTO> getTimeWindowedMetric(
            @PathVariable String metricName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "hour") String windowSize) {
        TimeWindowedMetricDTO metric = analyticsService.getTimeWindowedMetric(
                metricName, startTime, endTime, windowSize);
        return ResponseEntity.ok(metric);
    }
    
    @GetMapping("/analytics")
    public ResponseEntity<java.util.Map<String, Object>> getAnalyticsData() {
        return ResponseEntity.ok(analyticsService.getAnalyticsData());
    }
}

