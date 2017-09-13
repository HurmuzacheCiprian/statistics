package com.interview.n26.interviewn26.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDetails {
    private double sum;
    private double average;
    private double max;
    private double min;
    private double count;
}
