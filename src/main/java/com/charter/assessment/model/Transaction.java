package com.charter.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(
    LocalDate date,

    @JsonProperty("transaction_amount")
    BigDecimal transactionAmount,

    @JsonProperty("customer_id")
    String customerId
) {}
