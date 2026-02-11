import com.charter.assessment.util.DateRangeUtil;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeUtilTest {

    @Test
    void testGetDateRangeForQuarter_Q1() {
        DateRangeUtil.DateRange range = DateRangeUtil.getDateRangeForQuarter(2024, 1);
        
        assertEquals(LocalDate.of(2024, 1, 1), range.startDate());
        assertEquals(LocalDate.of(2024, 3, 31), range.endDate());
    }

    @Test
    void testGetDateRangeForQuarter_Q4() {
        DateRangeUtil.DateRange range = DateRangeUtil.getDateRangeForQuarter(2024, 4);
        
        assertEquals(LocalDate.of(2024, 10, 1), range.startDate());
        assertEquals(LocalDate.of(2024, 12, 31), range.endDate());
    }

    @Test
    void testGetDateRangeForQuarter_InvalidQuarter() {
        assertThrows(IllegalArgumentException.class, 
            () -> DateRangeUtil.getDateRangeForQuarter(2024, 0));
        
        assertThrows(IllegalArgumentException.class, 
            () -> DateRangeUtil.getDateRangeForQuarter(2024, 5));
    }

    @Test
    void testDateRangeContains() {
        DateRangeUtil.DateRange range = DateRangeUtil.getDateRangeForQuarter(2024, 1);
        
        assertTrue(range.contains(LocalDate.of(2024, 2, 15)));
        assertFalse(range.contains(LocalDate.of(2024, 4, 1)));
    }

    @Test
    void testDateRangeGetDays() {
        DateRangeUtil.DateRange range = DateRangeUtil.getDateRangeForQuarter(2024, 1);
        
        assertEquals(91, range.getDays());
    }
}
