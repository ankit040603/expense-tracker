package com.example.expensetracker.dto;

import com.example.expensetracker.model.ExpenseCategory;
import java.math.BigDecimal;

public record CategorySummary(ExpenseCategory category, BigDecimal totalAmount) {}
