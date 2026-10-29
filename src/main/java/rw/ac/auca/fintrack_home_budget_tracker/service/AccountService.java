package rw.ac.auca.fintrack_home_budget_tracker.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Account;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Bank;
import rw.ac.auca.fintrack_home_budget_tracker.entity.SpendingCategory;
import rw.ac.auca.fintrack_home_budget_tracker.repository.AccountRepository;
import rw.ac.auca.fintrack_home_budget_tracker.repository.BankRepository;
import rw.ac.auca.fintrack_home_budget_tracker.repository.SpendingCategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final SpendingCategoryRepository spendingCategoryRepository;
    private final BankRepository bankRepository;

    public AccountService(AccountRepository accountRepository, SpendingCategoryRepository spendingCategoryRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.spendingCategoryRepository = spendingCategoryRepository;
        this.bankRepository = bankRepository;
    }

    public String saveAccount(Account account) {
        accountRepository.save(account);
        return "Account saved successfully";
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(UUID id) {
        return accountRepository.findById(id);
    }

    public String updateAccount(UUID id, Account account) {
        Optional<Account> existing = accountRepository.findById(id);
        if (existing.isEmpty()) {
            return "The account with that id does not exist";
        }
        account.setId(id);
        accountRepository.save(account);
        return "Account updated successfully";
    }

    public String deleteAccount(UUID id) {
        Optional<Account> existing = accountRepository.findById(id);
        if (existing.isEmpty()) {
            return "The account with that id does not exist";
        }
        accountRepository.deleteById(id);
        return "Account deleted successfully";
    }

    // C1: Retrieve accounts that hold a specific spending category, matched case-insensitively
    public List<Account> getAccountsBySpendingCategory(String name) {
        return accountRepository.findBySpendingCategoryName(name);
    }

    // D3: Add a spending category to an account without removing existing ones
    public String addSpendingCategory(UUID accountId, UUID spendingCategoryId) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return "The account with that id does not exist";
        }
        Optional<SpendingCategory> categoryOpt = spendingCategoryRepository.findById(spendingCategoryId);
        if (categoryOpt.isEmpty()) {
            return "That spending category does not exist";
        }
        Account account = accountOpt.get();
        SpendingCategory category = categoryOpt.get();
        if (account.getSpendingCategories().contains(category)) {
            return "The account already has that spending category";
        }
        account.getSpendingCategories().add(category);
        accountRepository.save(account);
        return "Spending category added to the account";
    }

    // D4: Move an account to a different bank, ensuring the target bank is not already occupied
    public String moveToBank(UUID accountId, int bankCode) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return "The account with that id does not exist";
        }
        Optional<Bank> bankOpt = bankRepository.findByBankCode(bankCode);
        if (bankOpt.isEmpty()) {
            return "The bank with that bankCode does not exist";
        }
        Bank bank = bankOpt.get();
        Account account = accountOpt.get();
        if (accountRepository.existsByBank(bank) && !bank.equals(account.getBank())) {
            return "That bank is taken by another account";
        }
        account.setBank(bank);
        accountRepository.save(account);
        return "Bank changed successfully";
    }
}
