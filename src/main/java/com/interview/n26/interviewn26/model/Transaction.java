package com.interview.n26.interviewn26.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    private double amount;
    private long timestamp;
}
