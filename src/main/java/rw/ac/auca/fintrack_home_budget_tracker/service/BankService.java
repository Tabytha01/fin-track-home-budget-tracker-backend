package rw.ac.auca.fintrack_home_budget_tracker.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Bank;
import rw.ac.auca.fintrack_home_budget_tracker.repository.BankRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public String saveBank(Bank bank) {
        bankRepository.save(bank);
        return "Bank saved successfully";
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public Optional<Bank> getBankById(UUID id) {
        return bankRepository.findById(id);
    }

    public String updateBank(UUID id, Bank bank) {
        Optional<Bank> existing = bankRepository.findById(id);
        if (existing.isEmpty()) {
            return "The bank with that id does not exist";
        }
        bank.setId(id);
        bankRepository.save(bank);
        return "Bank updated successfully";
    }

    public String deleteBank(UUID id) {
        Optional<Bank> existing = bankRepository.findById(id);
        if (existing.isEmpty()) {
            return "The bank with that id does not exist";
        }
        bankRepository.deleteById(id);
        return "Bank deleted successfully";
    }

    // C2
    public List<Bank> getFreeBanks() {
        return bankRepository.findFreeBanks();
    }
}
