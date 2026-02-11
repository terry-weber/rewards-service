package com.charter.assessment.util;

import java.time.LocalDate;

public class DateRangeUtil {

    /**
     * Get the start and end dates for a given year and quarter.
     *
     * @param year The year (e.g., 2024, 2025)
     * @param quarter The quarter (1-4)
     * @return DateRange record containing start and end dates
     * @throws IllegalArgumentException if quarter is not between 1 and 4
     */
    public static DateRange getDateRangeForQuarter(int year, int quarter) {
        if (quarter < 1 || quarter > 4) {
            throw new IllegalArgumentException("Quarter must be between 1 and 4, got: " + quarter);
        }

        int startMonth = (quarter - 1) * 3 + 1;
        int endMonth = startMonth + 2;

        var startDate = LocalDate.of(year, startMonth, 1);
        var endDate = LocalDate.of(year, endMonth, 1).plusMonths(1).minusDays(1);

        return new DateRange(startDate, endDate);
    }

    /**
     * Record to hold a date range with start and end dates.
     */
    public record DateRange(LocalDate startDate, LocalDate endDate) {

        /**
         * Check if a date falls within this range (inclusive).
         */
        public boolean contains(LocalDate date) {
            return !date.isBefore(startDate) && !date.isAfter(endDate);
        }

        /**
         * Get the number of days in this range (inclusive).
         */
        public long getDays() {
            return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        }

        @Override
        public String toString() {
            return String.format("%s to %s", startDate, endDate);
        }
    }
}