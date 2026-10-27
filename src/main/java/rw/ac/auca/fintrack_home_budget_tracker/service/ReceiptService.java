package rw.ac.auca.fintrack_home_budget_tracker.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Expense;
import rw.ac.auca.fintrack_home_budget_tracker.entity.ExpenseStatus;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Receipt;
import rw.ac.auca.fintrack_home_budget_tracker.repository.ExpenseRepository;
import rw.ac.auca.fintrack_home_budget_tracker.repository.ReceiptRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ExpenseRepository expenseRepository;

    public ReceiptService(ReceiptRepository receiptRepository, ExpenseRepository expenseRepository) {
        this.receiptRepository = receiptRepository;
        this.expenseRepository = expenseRepository;
    }

    // E1
    public String saveReceipt(Receipt receipt, UUID expenseId) {
        Optional<Expense> expenseOpt = expenseRepository.findById(expenseId);
        if (expenseOpt.isEmpty()) {
            return "The expense with that id does not exist";
        }
        if (expenseOpt.get().getStatus() != ExpenseStatus.PAID) {
            return "Receipts are only for PAID expenses";
        }
        receipt.setRecordedDate(LocalDate.now());
        receipt.setExpense(expenseOpt.get());
        receiptRepository.save(receipt);
        return "Receipt saved successfully";
    }

    // E2
    public List<Receipt> getByMemberCode(String memberCode) {
        return receiptRepository.findByMemberCode(memberCode);
    }

    // E3
    public String deleteReceipt(UUID id) {
        Optional<Receipt> existing = receiptRepository.findById(id);
        if (existing.isEmpty()) {
            return "The receipt with that id does not exist";
        }
        receiptRepository.deleteById(id);
        return "Receipt deleted successfully";
    }

    public boolean hasReceipts(Expense expense) {
        return receiptRepository.existsByExpense(expense);
    }
}
