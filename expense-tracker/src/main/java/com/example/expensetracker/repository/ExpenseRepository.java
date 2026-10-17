package com.example.expensetracker.repository;

import com.example.expensetracker.dto.CategorySummary;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByCategoryOrderByExpenseDateDesc(ExpenseCategory category);

    List<Expense> findByExpenseDateBetweenOrderByExpenseDateDesc(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.expenseDate BETWEEN :start AND :end")
    BigDecimal getTotalSpentBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT new com.example.expensetracker.dto.CategorySummary(e.category, SUM(e.amount)) " +
           "FROM Expense e WHERE e.expenseDate BETWEEN :start AND :end GROUP BY e.category")
    List<CategorySummary> getCategoryBreakdownBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
