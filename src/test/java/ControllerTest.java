import com.charter.assessment.controller.Controller;
import com.charter.assessment.model.MonthlyReward;
import com.charter.assessment.model.Transaction;
import com.charter.assessment.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    @Mock
    private RewardsService rewardsService;

    @InjectMocks
    private Controller controller;

    @Test
    void testGetRewards() {
        List<MonthlyReward> mockRewards = List.of();
        when(rewardsService.getQuarterlyRewards(2024, 1, "1")).thenReturn(mockRewards);

        List<MonthlyReward> result = controller.getRewards(2024, 1, "1");

        assertEquals(mockRewards, result);
        verify(rewardsService).getQuarterlyRewards(2024, 1, "1");
    }

    @Test
    void testGetTransactions() {
        List<Transaction> mockTransactions = List.of();
        when(rewardsService.getTransactions(2024, 1, "1")).thenReturn(mockTransactions);

        List<Transaction> result = controller.getTransactions(2024, 1, "1");

        assertEquals(mockTransactions, result);
        verify(rewardsService).getTransactions(2024, 1, "1");
    }
}