package com.interview.n26.interviewn26.services;

import com.interview.n26.interviewn26.model.StatisticDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final DetailedStatisticsService detailedStatisticsService;

    @Autowired
    public StatisticsService(DetailedStatisticsService detailedStatisticsService) {
        this.detailedStatisticsService = detailedStatisticsService;
    }

    public StatisticDetails getStatistics() {
        return detailedStatisticsService.getStatisticDetails();
    }

}
