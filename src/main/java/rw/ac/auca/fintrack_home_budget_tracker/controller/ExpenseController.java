package rw.ac.auca.fintrack_home_budget_tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Expense;
import rw.ac.auca.fintrack_home_budget_tracker.entity.ExpenseStatus;
import rw.ac.auca.fintrack_home_budget_tracker.service.ExpenseService;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService)
    {
        this.expenseService = expenseService;
    }
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Expense expense, @RequestParam UUID memberId, @RequestParam UUID accountId) {
        String message = expenseService.saveExpense(expense, memberId, accountId);
        if (message.equals("Expense date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (message.equals("Account already has an expense on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);}
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Expense> result = expenseService.getExpenseById(id);
        if (result.isEmpty()) {
            return new ResponseEntity<>("The expense with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Expense expense) {
        String message = expenseService.updateExpense(id, expense);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = expenseService.deleteExpense(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.contains("has receipts")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("/by-status")
    public ResponseEntity<?> getByStatus(@RequestParam ExpenseStatus status) {
        return new ResponseEntity<>(expenseService.getExpensesByStatus(status), HttpStatus.OK);
    }
    @GetMapping("/between")
    public ResponseEntity<?> getBetween(@RequestParam String start, @RequestParam String end) {
        return new ResponseEntity<>(expenseService.getExpensesBetween(LocalDate.parse(start), LocalDate.parse(end)), HttpStatus.OK);
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam ExpenseStatus status) {
        String message = expenseService.updateExpenseStatus(id, status);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.startsWith("Cannot change")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("/stats/by-status")
    public ResponseEntity<?> statsByStatus() {
        return new ResponseEntity<>(expenseService.countByStatus(), HttpStatus.OK);
    }
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID accountId, @RequestParam String date) {
        String message = expenseService.cancelByAccountAndDate(accountId, LocalDate.parse(date));
        return new ResponseEntity<>(message, HttpStatus.OK);}
}
