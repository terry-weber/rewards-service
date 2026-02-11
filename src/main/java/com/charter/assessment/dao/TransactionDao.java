package com.charter.assessment.dao;

import com.charter.assessment.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
public class TransactionDao {
    
    private List<Transaction> transactions;
    private final ObjectMapper objectMapper;
    
    public TransactionDao() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.transactions = new ArrayList<>();
    }
    
    @PostConstruct
    public void loadTransactions() {
        try {
            var resource = new ClassPathResource("customer_transactions.json");
            try (var inputStream = resource.getInputStream()) {
                var wrapper = objectMapper.readValue(inputStream, TransactionWrapper.class);
                this.transactions = wrapper.transactions();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load transactions from JSON file", e);
        }
    }
    
    public List<Transaction> getTransactionsByCustomerAndDateRange(
            String customerId, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.customerId().equals(customerId))
                .filter(t -> !t.date().isBefore(startDate) && !t.date().isAfter(endDate))
                .toList();
    }

    public TreeMap<YearMonth, List<Transaction>> getTransactionsByCustomerAndDateRangeGroupedByMonth(
            String customerId, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(t -> t.customerId().equals(customerId))
                .filter(t -> !t.date().isBefore(startDate) && !t.date().isAfter(endDate))
                .collect(Collectors.groupingBy(
                        t -> YearMonth.from(t.date()),
                        TreeMap::new,
                        Collectors.toList()
                ));
    }
    
    // Record to map the JSON structure with "transactions" wrapper
    private record TransactionWrapper(List<Transaction> transactions) {}
}
