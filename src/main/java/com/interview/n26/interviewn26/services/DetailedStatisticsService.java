package com.interview.n26.interviewn26.services;

import com.interview.n26.interviewn26.model.StatisticDetails;
import com.interview.n26.interviewn26.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
public class DetailedStatisticsService {

    private ZonedDateTime start;
    private StatisticDetails statisticDetails;
    private static final int MAX_LIMIT = 60;
    private double sum = 0;

    public DetailedStatisticsService() {
        start = ZonedDateTime.now(ZoneOffset.UTC);
        statisticDetails = new StatisticDetails();
    }

    public boolean processTransaction(Transaction transaction) {
        synchronized (this) {
            ZonedDateTime timestampTime = Instant.ofEpochMilli(transaction.getTimestamp()).atZone(ZoneOffset.UTC);

            boolean resp = isInTimeWindow(timestampTime);

            if (resp) {
                computeStatistics(transaction);
            } else {
                resetStatistics();
            }

            return resp;
        }
    }

    public boolean isInTimeWindow(ZonedDateTime date) {
        synchronized (this) {
            boolean resp = date.compareTo(start) >= 0 && date.compareTo(start.plusSeconds(MAX_LIMIT)) <= 0;

            moveToNextTimeFrame();
            return resp;
        }
    }

    public synchronized StatisticDetails getStatisticDetails() {
        synchronized (this) {
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
            if (isInTimeWindow(now)) {
                return this.statisticDetails;
            } else {
                resetStatistics();
            }
            return this.statisticDetails;
        }
    }

    private void moveToNextTimeFrame() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        if (start.plusSeconds(MAX_LIMIT).compareTo(now) < 0) {
            start = now;
        }
    }

    private void resetStatistics() {
        statisticDetails = new StatisticDetails();
        sum = 0;
    }

    private void computeStatistics(Transaction transaction) {
        double count = statisticDetails.getCount() + 1;
        double max = transaction.getAmount() > statisticDetails.getMax() ? transaction.getAmount() : statisticDetails.getMax();
        double min = statisticDetails.getMin() == 0 ? transaction.getAmount() : (transaction.getAmount() < statisticDetails.getMin() ? transaction.getAmount() : statisticDetails.getMin());
        sum += transaction.getAmount();
        statisticDetails = new StatisticDetails(sum, sum / count, max, min, count);
    }
}
