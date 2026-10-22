package rw.ac.auca.fintrack_home_budget_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.fintrack_home_budget_tracker.entity.HouseholdMember;
import java.util.UUID;

public interface HouseholdMemberRepository extends JpaRepository<HouseholdMember, UUID> {
}
