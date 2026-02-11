package com.charter.assessment.controller;

import com.charter.assessment.model.MonthlyReward;
import com.charter.assessment.model.Transaction;
import com.charter.assessment.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

  @Autowired
  RewardsService rewardsService;

  @GetMapping("/rewards/{customerId}")
  public List<MonthlyReward> getRewards(
          @RequestParam Integer year,
          @RequestParam Integer quarter,
          @PathVariable String customerId) {
    return rewardsService.getQuarterlyRewards(year, quarter, customerId);

  }

  @GetMapping("/transactions/{customerId}")
  public List<Transaction> getTransactions(
          @RequestParam Integer year,
          @RequestParam Integer quarter,
          @PathVariable String customerId) {
    return rewardsService.getTransactions(year, quarter, customerId);

  }
}
