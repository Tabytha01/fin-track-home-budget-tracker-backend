package rw.ac.auca.fintrack_home_budget_tracker.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Account;
import rw.ac.auca.fintrack_home_budget_tracker.repository.AccountRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
}
