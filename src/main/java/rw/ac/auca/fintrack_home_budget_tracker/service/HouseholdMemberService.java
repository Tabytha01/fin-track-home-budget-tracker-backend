package rw.ac.auca.fintrack_home_budget_tracker.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.fintrack_home_budget_tracker.entity.HouseholdMember;
import rw.ac.auca.fintrack_home_budget_tracker.repository.HouseholdMemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HouseholdMemberService {

    private final HouseholdMemberRepository householdMemberRepository;

    public HouseholdMemberService(HouseholdMemberRepository householdMemberRepository) {
        this.householdMemberRepository = householdMemberRepository;
    }

    public String saveHouseholdMember(HouseholdMember householdMember) {
        householdMemberRepository.save(householdMember);
        return "Household member saved successfully";
    }

    public List<HouseholdMember> getAllHouseholdMembers() {
        return householdMemberRepository.findAll();
    }

    public Optional<HouseholdMember> getHouseholdMemberById(UUID id) {
        return householdMemberRepository.findById(id);
    }

    public String updateHouseholdMember(UUID id, HouseholdMember householdMember) {
        Optional<HouseholdMember> existing = householdMemberRepository.findById(id);
        if (existing.isEmpty()) {
            return "The household member with that id does not exist";
        }
        householdMember.setId(id);
        householdMemberRepository.save(householdMember);
        return "Household member updated successfully";
    }

    public String deleteHouseholdMember(UUID id) {
        Optional<HouseholdMember> existing = householdMemberRepository.findById(id);
        if (existing.isEmpty()) {
            return "The household member with that id does not exist";
        }
        householdMemberRepository.deleteById(id);
        return "Household member deleted successfully";
    }
}
