package rw.ac.auca.fintrack_home_budget_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.auca.fintrack_home_budget_tracker.entity.SpendingCategory;
import java.util.List;
import java.util.UUID;

public interface SpendingCategoryRepository extends JpaRepository<SpendingCategory, UUID> {

    // C3 - JPQL
    @Query("SELECT s FROM SpendingCategory s WHERE s.accounts IS EMPTY")
    List<SpendingCategory> findUnused();
}
