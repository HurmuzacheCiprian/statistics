package com.interview.n26.interviewn26.services;

import com.interview.n26.interviewn26.model.StatisticDetails;
import com.interview.n26.interviewn26.model.Transaction;
import org.junit.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DetailedStatisticsServiceTest {

    private final DetailedStatisticsService underTest = new DetailedStatisticsService();

    @Test
    public void shouldReturnTrueIfTimestampIsNow() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        long timestamp = now.toInstant().toEpochMilli();

        assertTrue(underTest.processTransaction(new Transaction(0, timestamp)));
    }

    @Test
    public void shouldReturnTrueIfTimestampIsInThePast() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(1);
        long timestamp = now.toInstant().toEpochMilli();

        assertFalse(underTest.processTransaction(new Transaction(0, timestamp)));
    }

    @Test
    public void shouldReturnTrueIfTimestampIsNowPlus30Seconds() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(30);
        long timestamp = now.toInstant().toEpochMilli();

        assertTrue(underTest.processTransaction(new Transaction(0, timestamp)));
    }

    @Test
    public void shouldReturnFalseIfTimestampIsOutOfTheTimeWindow() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(61);
        long timestamp = now.toInstant().toEpochMilli();

        assertFalse(underTest.processTransaction(new Transaction(0, timestamp)));
    }

    @Test
    public void shouldReturnTheCorrectStatisticDetails() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(30);
        long timestamp = now.toInstant().toEpochMilli();

        underTest.processTransaction(new Transaction(22, timestamp));
        underTest.processTransaction(new Transaction(12.9, timestamp));
        underTest.processTransaction(new Transaction(12, timestamp));

        double expectedSum = 46.9;
        double expectedAverage = 15.633333333333333;
        double expectedMax = 22;
        double expectedMin = 12;
        double expectedCount = 3;

        StatisticDetails response = underTest.getStatisticDetails();

        assertTrue(response.getSum() == expectedSum);
        assertTrue(response.getCount() == expectedCount);
        assertTrue(response.getMax() == expectedMax);
        assertTrue(response.getAverage() == expectedAverage);
        assertTrue(response.getMin() == expectedMin);
    }

    @Test
    public void shouldReturnDefaultStatisticDetailsIfTimestampIsOutOfTimeWindow() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(61);
        long timestamp = now.toInstant().toEpochMilli();

        underTest.processTransaction(new Transaction(22, timestamp));
        underTest.processTransaction(new Transaction(12.9, timestamp));
        underTest.processTransaction(new Transaction(12, timestamp));

        StatisticDetails response = underTest.getStatisticDetails();

        assertTrue(response.getSum() == 0.0);
        assertTrue(response.getCount() == 0.0);
        assertTrue(response.getMax() == 0.0);
        assertTrue(response.getAverage() == 0.0);
        assertTrue(response.getMin() == 0.0);
    }
}