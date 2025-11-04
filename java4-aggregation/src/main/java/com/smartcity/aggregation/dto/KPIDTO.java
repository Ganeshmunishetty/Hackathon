package com.smartcity.aggregation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KPIDTO {
    private Integer totalIncidents;
    private Integer openIncidents;
    private Integer inProgressIncidents;
    private Integer resolvedIncidents;
    private Double trafficFlowPercentage;
    private Integer airQualityIndex;
    private Double energyUsageGW;
    private Integer activeCameras;
    private Integer onlineSensors;
    private Integer availableVehicles;
}

