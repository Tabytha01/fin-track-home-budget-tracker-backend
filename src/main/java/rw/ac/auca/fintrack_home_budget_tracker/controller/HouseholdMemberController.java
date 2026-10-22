package rw.ac.auca.fintrack_home_budget_tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.ac.auca.fintrack_home_budget_tracker.entity.HouseholdMember;
import rw.ac.auca.fintrack_home_budget_tracker.service.HouseholdMemberService;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/household-members")
public class HouseholdMemberController {

    private final HouseholdMemberService householdMemberService;

    public HouseholdMemberController(HouseholdMemberService householdMemberService) {
        this.householdMemberService = householdMemberService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody HouseholdMember householdMember) {
        String message = householdMemberService.saveHouseholdMember(householdMember);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(householdMemberService.getAllHouseholdMembers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<HouseholdMember> result = householdMemberService.getHouseholdMemberById(id);
        if (result.isEmpty()) {
            return new ResponseEntity<>("The household member with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody HouseholdMember householdMember) {
        String message = householdMemberService.updateHouseholdMember(id, householdMember);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = householdMemberService.deleteHouseholdMember(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
