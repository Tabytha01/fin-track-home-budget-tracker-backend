package rw.ac.auca.fintrack_home_budget_tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Receipt;
import rw.ac.auca.fintrack_home_budget_tracker.service.ReceiptService;
import java.util.UUID;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    // E1
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Receipt receipt, @RequestParam UUID expenseId) {
        String message = receiptService.saveReceipt(receipt, expenseId);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.contains("only for PAID")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // E2
    @GetMapping("/household-member/{memberCode}")
    public ResponseEntity<?> getByMemberCode(@PathVariable String memberCode) {
        return new ResponseEntity<>(receiptService.getByMemberCode(memberCode), HttpStatus.OK);
    }

    // E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = receiptService.deleteReceipt(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
