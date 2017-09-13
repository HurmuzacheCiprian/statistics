package com.interview.n26.interviewn26.resources;

import com.interview.n26.interviewn26.model.StatisticDetails;
import com.interview.n26.interviewn26.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class Statistics {

    private final StatisticsService statisticsService;

    @Autowired
    public Statistics(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<StatisticDetails> getStatistics() {
        return new ResponseEntity<>(statisticsService.getStatistics(), HttpStatus.OK);
    }

}
