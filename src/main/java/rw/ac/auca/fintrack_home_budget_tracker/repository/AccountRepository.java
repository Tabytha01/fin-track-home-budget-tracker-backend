package rw.ac.auca.fintrack_home_budget_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.fintrack_home_budget_tracker.entity.Account;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
