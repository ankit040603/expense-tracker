package com.example.expensetracker.service;

import com.example.expensetracker.dto.CategorySummary;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.ExpenseCategory;
import com.example.expensetracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository repository;

    public List<Expense> getExpenses(ExpenseCategory category, LocalDate startDate, LocalDate endDate) {
        if (category != null) {
            return repository.findByCategoryOrderByExpenseDateDesc(category);
        }
        if (startDate != null && endDate != null) {
            return repository.findByExpenseDateBetweenOrderByExpenseDateDesc(startDate, endDate);
        }
        return repository.findAll();
    }

    public Expense createExpense(Expense expense) {
        return repository.save(expense);
    }

    public Expense updateExpense(Long id, Expense updated) {
        Expense existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));
        existing.setDescription(updated.getDescription());
        existing.setAmount(updated.getAmount());
        existing.setCategory(updated.getCategory());
        existing.setExpenseDate(updated.getExpenseDate());
        return repository.save(existing);
    }

    public void deleteExpense(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found");
        }
        repository.deleteById(id);
    }

    public Map<String, Object> getSpendingSummary(LocalDate startDate, LocalDate endDate) {
        LocalDate start = (startDate != null) ? startDate : LocalDate.now().withDayOfMonth(1);
        LocalDate end = (endDate != null) ? endDate : LocalDate.now();

        BigDecimal total = repository.getTotalSpentBetween(start, end);
        List<CategorySummary> breakdown = repository.getCategoryBreakdownBetween(start, end);

        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("startDate", start);
        summary.put("endDate", end);
        summary.put("totalSpent", total);
        summary.put("categoryBreakdown", breakdown);
        return summary;
    }
}
