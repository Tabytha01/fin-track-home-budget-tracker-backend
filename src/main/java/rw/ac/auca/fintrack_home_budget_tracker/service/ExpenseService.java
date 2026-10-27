package rw.ac.auca.fintrack_home_budget_tracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Account;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Expense;
import rw.ac.auca.fintrack_home_budget_tracker.entity.ExpenseStatus;
import rw.ac.auca.fintrack_home_budget_tracker.entity.HouseholdMember;
import rw.ac.auca.fintrack_home_budget_tracker.repository.AccountRepository;
import rw.ac.auca.fintrack_home_budget_tracker.repository.ExpenseRepository;
import rw.ac.auca.fintrack_home_budget_tracker.repository.HouseholdMemberRepository;
import rw.ac.auca.fintrack_home_budget_tracker.repository.ReceiptRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final AccountRepository accountRepository;
    private final ReceiptRepository receiptRepository;

    public ExpenseService(ExpenseRepository expenseRepository, HouseholdMemberRepository householdMemberRepository, AccountRepository accountRepository, ReceiptRepository receiptRepository) {
        this.expenseRepository = expenseRepository;
        this.householdMemberRepository = householdMemberRepository;
        this.accountRepository = accountRepository;
        this.receiptRepository = receiptRepository;
    }

    // D1: safe saving
    public String saveExpense(Expense expense, UUID memberId, UUID accountId) {
        if (expense.getExpenseDate().isBefore(LocalDate.now())) {
            return "Expense date cannot be in the past";
        }
        Optional<HouseholdMember> getMember = householdMemberRepository.findById(memberId);
        Optional<Account> getAccount = accountRepository.findById(accountId);
        if (getMember.isEmpty()) {
            return "The household member with that id does not exist";
        }
        if (getAccount.isEmpty()) {
            return "The account with that id does not exist";
        }
        if (expenseRepository.existsByAccountAndExpenseDateAndStatusNot(getAccount.get(), expense.getExpenseDate(), ExpenseStatus.VOIDED)) {
            return "Account already has an expense on that date";
        }
        expense.setMember(getMember.get());
        expense.setAccount(getAccount.get());
        expense.setStatus(ExpenseStatus.PLANNED);
        expenseRepository.save(expense);
        return "Expense saved successfully";
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> getExpenseById(UUID id) {
        return expenseRepository.findById(id);
    }

    // D2: PUT update must NOT change status
    public String updateExpense(UUID id, Expense expense) {
        Optional<Expense> existing = expenseRepository.findById(id);
        if (existing.isEmpty()) {
            return "The expense with that id does not exist";
        }
        expense.setId(id);
        expense.setStatus(existing.get().getStatus());
        expense.setMember(existing.get().getMember());
        expense.setAccount(existing.get().getAccount());
        expenseRepository.save(expense);
        return "Expense updated successfully";
    }

    // D2: status flow
    public String updateExpenseStatus(UUID id, ExpenseStatus newStatus) {
        Optional<Expense> existing = expenseRepository.findById(id);
        if (existing.isEmpty()) {
            return "The expense with that id does not exist";
        }
        ExpenseStatus current = existing.get().getStatus();
        boolean allowed = (current == ExpenseStatus.PLANNED && (newStatus == ExpenseStatus.PENDING || newStatus == ExpenseStatus.VOIDED))
                || (current == ExpenseStatus.PENDING && (newStatus == ExpenseStatus.PAID || newStatus == ExpenseStatus.VOIDED));
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        existing.get().setStatus(newStatus);
        expenseRepository.save(existing.get());
        return "Expense status updated successfully";
    }

    public String deleteExpense(UUID id) {
        Optional<Expense> existing = expenseRepository.findById(id);
        if (existing.isEmpty()) {
            return "The expense with that id does not exist";
        }
        if (receiptRepository.existsByExpense(existing.get())) {
            return "That expense has receipts, delete them first";
        }
        expenseRepository.deleteById(id);
        return "Expense deleted successfully";
    }

    // B2
    public List<Expense> getExpensesByStatus(ExpenseStatus status) {
        return expenseRepository.findByStatusOrderByExpenseDateAsc(status);
    }

    // B3
    public List<Expense> getExpensesBetween(LocalDate start, LocalDate end) {
        return expenseRepository.findByExpenseDateBetweenOrderByExpenseDateAsc(start, end);
    }

    // F1
    public List<Object[]> countByStatus() {
        return expenseRepository.countByStatus();
    }

    // F2
    @Transactional
    public String cancelByAccountAndDate(UUID accountId, LocalDate date) {
        int count = expenseRepository.cancelByAccountAndDate(accountId, date);
        return count + " expenses cancelled";
    }
}
