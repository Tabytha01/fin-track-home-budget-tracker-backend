package rw.ac.auca.fintrack_home_budget_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Bank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
    @Query("SELECT b FROM Bank b WHERE NOT EXISTS (SELECT a FROM Account a WHERE a.bank = b) ORDER BY b.bankCode ASC")
    List<Bank> findFreeBanks();
    Optional<Bank> findByBankCode(int bankCode);
}
