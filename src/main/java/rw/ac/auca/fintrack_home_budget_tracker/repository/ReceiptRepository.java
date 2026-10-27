package rw.ac.auca.fintrack_home_budget_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Expense;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Receipt;
import java.util.List;
import java.util.UUID;

public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {

    // E2 - JPQL: receipts by member code, newest first
    @Query("SELECT r FROM Receipt r WHERE r.expense.member.memberCode = :memberCode ORDER BY r.recordedDate DESC")
    List<Receipt> findByMemberCode(@Param("memberCode") String memberCode);

    // E3 - DERIVED
    boolean existsByExpense(Expense expense);
}
