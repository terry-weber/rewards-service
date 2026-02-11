import com.charter.assessment.dao.TransactionDao;

import com.charter.assessment.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDaoTest {

    private TransactionDao dao;

    @BeforeEach
    void setUp() {
        dao = new TransactionDao();
        
        // Mock transactions instead of loading from file
        List<Transaction> mockTransactions = List.of(
                new Transaction(LocalDate.of(2024, 1, 15), new BigDecimal("120.00"), "1"),
                new Transaction(LocalDate.of(2024, 2, 20), new BigDecimal("75.00"), "1"),
                new Transaction(LocalDate.of(2024, 3, 10), new BigDecimal("150.00"), "2")
        );
        
        ReflectionTestUtils.setField(dao, "transactions", mockTransactions);
    }

    @Test
    void testGetTransactionsByCustomerAndDateRange() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 28);

        List<Transaction> result = dao.getTransactionsByCustomerAndDateRange("1", startDate, endDate);

        assertEquals(2, result.size());
        assertEquals("1", result.get(0).customerId());
        assertEquals("1", result.get(1).customerId());
    }

    @Test
    void testGetTransactionsByCustomerAndDateRangeGroupedByMonth() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 28);

        TreeMap<YearMonth, List<Transaction>> result = 
                dao.getTransactionsByCustomerAndDateRangeGroupedByMonth("1", startDate, endDate);

        assertEquals(2, result.size());
        assertTrue(result.containsKey(YearMonth.of(2024, 1)));
        assertTrue(result.containsKey(YearMonth.of(2024, 2)));
        assertEquals(1, result.get(YearMonth.of(2024, 1)).size());
        assertEquals(1, result.get(YearMonth.of(2024, 2)).size());
    }
}
