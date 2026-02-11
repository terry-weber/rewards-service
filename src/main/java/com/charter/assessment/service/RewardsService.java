package com.charter.assessment.service;

import com.charter.assessment.dao.TransactionDao;
import com.charter.assessment.model.MonthlyReward;
import com.charter.assessment.model.Transaction;
import com.charter.assessment.util.DateRangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;

@Service
public class RewardsService {
    @Autowired
    private TransactionDao transactionDao;

    public List<MonthlyReward> getQuarterlyRewards(Integer year, Integer quarter, String customerId) {

        DateRangeUtil.DateRange range = DateRangeUtil.getDateRangeForQuarter(year, quarter);
        TreeMap<YearMonth, List<Transaction>> transactions = transactionDao.getTransactionsByCustomerAndDateRangeGroupedByMonth(customerId, range.startDate(), range.endDate());
        return calculateRewards(transactions);
    }

    public List<Transaction> getTransactions(Integer year, Integer quarter, String customerId) {

        DateRangeUtil.DateRange range = DateRangeUtil.getDateRangeForQuarter(year, quarter);
        List<Transaction> transactions = transactionDao.getTransactionsByCustomerAndDateRange(customerId, range.startDate(), range.endDate());
        return transactions;
    }

    private List<MonthlyReward> calculateRewards(TreeMap<YearMonth, List<Transaction>> groupedTransactions) {

        List<MonthlyReward> rewardsResponse = new ArrayList<>();
        BinaryOperator<Integer> rewardsAccumulator = (accumulator, currentElement) -> {
            Integer transactionReward = 0;
            if(currentElement.intValue() > 50 && currentElement.intValue() <= 100) {
                transactionReward = (currentElement - 50);
            } else if(currentElement > 100) {
                transactionReward = 50 + (currentElement - 100) * 2;
            }

            return accumulator + transactionReward;
        };

        for (Map.Entry<YearMonth, List<Transaction>> entry : groupedTransactions.entrySet()) {
            MonthlyReward monthlyReward = new MonthlyReward();
            monthlyReward.setMonth(entry.getKey().getMonth().toString());

            Integer sum = entry.getValue().stream()
                    .map(Transaction::transactionAmount)
                    .map(BigDecimal::intValue)
                    .reduce(0, rewardsAccumulator);
            monthlyReward.setReward(sum);
            rewardsResponse.add(monthlyReward);
        }
        return rewardsResponse;
    }
}
