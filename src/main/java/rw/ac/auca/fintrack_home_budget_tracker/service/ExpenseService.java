package rw.ac.auca.fintrack_home_budget_tracker.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Expense;
import rw.ac.auca.fintrack_home_budget_tracker.entity.ExpenseStatus;
import rw.ac.auca.fintrack_home_budget_tracker.entity.HouseholdMember;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Account;
import rw.ac.auca.fintrack_home_budget_tracker.repository.ExpenseRepository;
import rw.ac.auca.fintrack_home_budget_tracker.repository.HouseholdMemberRepository;
import rw.ac.auca.fintrack_home_budget_tracker.repository.AccountRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final AccountRepository accountRepository;

    public ExpenseService(ExpenseRepository expenseRepository, HouseholdMemberRepository householdMemberRepository, AccountRepository accountRepository) {
        this.expenseRepository = expenseRepository;
        this.householdMemberRepository = householdMemberRepository;
        this.accountRepository = accountRepository;
    }

    public String saveExpense(Expense expense, UUID memberId, UUID accountId) {
        Optional<HouseholdMember> getMember = householdMemberRepository.findById(memberId);
        Optional<Account> getAccount = accountRepository.findById(accountId);

        if (getMember.isEmpty()) {
            return "The household member with that id does not exist";
        }
        if (getAccount.isEmpty()) {
            return "The account with that id does not exist";
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

    public String updateExpense(UUID id, Expense expense) {
        Optional<Expense> existing = expenseRepository.findById(id);
        if (existing.isEmpty()) {
            return "The expense with that id does not exist";
        }
        expense.setId(id);
        expense.setStatus(existing.get().getStatus());
        expenseRepository.save(expense);
        return "Expense updated successfully";
    }

    public String deleteExpense(UUID id) {
        Optional<Expense> existing = expenseRepository.findById(id);
        if (existing.isEmpty()) {
            return "The expense with that id does not exist";
        }
        expenseRepository.deleteById(id);
        return "Expense deleted successfully";
    }
}
