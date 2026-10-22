package rw.ac.auca.fintrack_home_budget_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.fintrack_home_budget_tracker.entity.SpendingCategory;
import java.util.UUID;

public interface SpendingCategoryRepository extends JpaRepository<SpendingCategory, UUID> {
}
