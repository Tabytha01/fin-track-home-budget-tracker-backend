package rw.ac.auca.fintrack_home_budget_tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Account;
import rw.ac.auca.fintrack_home_budget_tracker.service.AccountService;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Account account) {
        String message = accountService.saveAccount(account);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Account> result = accountService.getAccountById(id);
        if (result.isEmpty()) {
            return new ResponseEntity<>("The account with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Account account) {
        String message = accountService.updateAccount(id, account);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = accountService.deleteAccount(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // C1
    @GetMapping("/by-spending-category")
    public ResponseEntity<?> getBySpendingCategory(@RequestParam String name) {
        return new ResponseEntity<>(accountService.getAccountsBySpendingCategory(name), HttpStatus.OK);
    }

    // D3
    @PutMapping("/{accountId}/spending-categories/{spendingCategoryId}")
    public ResponseEntity<String> addSpendingCategory(@PathVariable UUID accountId, @PathVariable UUID spendingCategoryId) {
        String message = accountService.addSpendingCategory(accountId, spendingCategoryId);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.contains("already has")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // D4
    @PatchMapping("/{accountId}/bank")
    public ResponseEntity<String> moveToBank(@PathVariable UUID accountId, @RequestParam int bankCode) {
        String message = accountService.moveToBank(accountId, bankCode);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.contains("taken by another")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
