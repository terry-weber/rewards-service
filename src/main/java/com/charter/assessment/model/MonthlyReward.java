package com.charter.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class MonthlyReward {
    @Setter
    @Getter
    @JsonProperty
    String month;
    @Setter
    @Getter
    @JsonProperty
    Integer reward;
}
