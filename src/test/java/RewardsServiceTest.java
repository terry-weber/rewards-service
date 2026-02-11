import com.charter.assessment.service.RewardsService;

import com.charter.assessment.dao.TransactionDao;
import com.charter.assessment.model.MonthlyReward;
import com.charter.assessment.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RewardsServiceTest {

    @Mock
    private TransactionDao transactionDao;

    @InjectMocks
    private RewardsService rewardsService;

    @Test
    void testGetQuarterlyRewards() {
        TreeMap<YearMonth, List<Transaction>> mockGroupedTransactions = new TreeMap<>();
        mockGroupedTransactions.put(
                YearMonth.of(2024, 1),
                List.of(new Transaction(LocalDate.of(2024, 1, 15), new BigDecimal("120"), "1"))
        );

        when(transactionDao.getTransactionsByCustomerAndDateRangeGroupedByMonth(
                eq("1"), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(mockGroupedTransactions);

        List<MonthlyReward> result = rewardsService.getQuarterlyRewards(2024, 1, "1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("JANUARY", result.get(0).getMonth());
        assertEquals(90, result.get(0).getReward()); // (120-100)*2 + 50 = 90
    }

    @Test
    void testGetTransactions() {
        List<Transaction> mockTransactions = List.of(
                new Transaction(LocalDate.of(2024, 1, 15), new BigDecimal("120"), "1")
        );

        when(transactionDao.getTransactionsByCustomerAndDateRange(
                eq("1"), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(mockTransactions);

        List<Transaction> result = rewardsService.getTransactions(2024, 1, "1");

        assertEquals(1, result.size());
        verify(transactionDao).getTransactionsByCustomerAndDateRange(
                eq("1"), any(LocalDate.class), any(LocalDate.class));
    }
}
