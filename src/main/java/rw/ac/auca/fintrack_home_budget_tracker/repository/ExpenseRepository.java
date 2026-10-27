package rw.ac.auca.fintrack_home_budget_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Account;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Expense;
import rw.ac.auca.fintrack_home_budget_tracker.entity.ExpenseStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    // B2 - DERIVED
    List<Expense> findByStatusOrderByExpenseDateAsc(ExpenseStatus status);

    // B3 - DERIVED
    List<Expense> findByExpenseDateBetweenOrderByExpenseDateAsc(LocalDate start, LocalDate end);

    // D1 - DERIVED: check if account already has a non-VOIDED expense on that date
    boolean existsByAccountAndExpenseDateAndStatusNot(Account account, LocalDate expenseDate, ExpenseStatus status);

    // F1 - JPQL: count per status
    @Query("SELECT e.status, COUNT(e) FROM Expense e GROUP BY e.status")
    List<Object[]> countByStatus();

    // F2 - JPQL: bulk cancel
    @Modifying
    @Query("UPDATE Expense e SET e.status = rw.ac.auca.fintrack_home_budget_tracker.entity.ExpenseStatus.VOIDED " +
           "WHERE e.account.id = :accountId AND e.expenseDate = :date AND e.status <> rw.ac.auca.fintrack_home_budget_tracker.entity.ExpenseStatus.PAID")
    int cancelByAccountAndDate(@Param("accountId") UUID accountId, @Param("date") LocalDate date);
}
