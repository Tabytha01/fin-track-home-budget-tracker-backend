package rw.ac.auca.fintrack_home_budget_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.fintrack_home_budget_tracker.entity.HouseholdMember;
import java.util.List;
import java.util.UUID;

public interface HouseholdMemberRepository extends JpaRepository<HouseholdMember, UUID> {

    // B1 - DERIVED
    List<HouseholdMember> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    // C4 - JPQL
    @Query("SELECT DISTINCT e.member FROM Expense e WHERE e.account.id = :accountId")
    List<HouseholdMember> findMembersByAccountId(@Param("accountId") UUID accountId);
}
