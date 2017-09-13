package com.interview.n26.interviewn26.services;

import com.interview.n26.interviewn26.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

    private final DetailedStatisticsService timeWindow;

    @Autowired
    public TransactionsService(DetailedStatisticsService timeWindow) {
        this.timeWindow = timeWindow;
    }

    public int processTransaction(Transaction transaction) {
        return timeWindow.processTransaction(transaction) ? 201 : 204;
    }

}
