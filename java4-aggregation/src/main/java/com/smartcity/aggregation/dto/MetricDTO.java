package com.smartcity.aggregation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricDTO {
    private String label;
    private String value;
    private Double change;
    private String status; // good, warning, critical
}

