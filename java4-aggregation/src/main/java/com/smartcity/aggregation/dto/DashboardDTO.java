package com.smartcity.aggregation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private List<MetricDTO> metrics;
    private List<IncidentSummaryDTO> recentIncidents;
    private Map<String, Object> analyticsData;
    private KPIDTO kpis;
}

