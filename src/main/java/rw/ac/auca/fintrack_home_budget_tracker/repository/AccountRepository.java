package rw.ac.auca.fintrack_home_budget_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Account;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Bank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    // C1 - JPQL
    @Query("SELECT a FROM Account a JOIN a.spendingCategories c WHERE LOWER(c.name) = LOWER(:name)")
    List<Account> findBySpendingCategoryName(@Param("name") String name);

    // D4 - DERIVED
    Optional<Account> findByBank(Bank bank);

    boolean existsByBank(Bank bank);
}
