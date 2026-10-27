package rw.ac.auca.fintrack_home_budget_tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.fintrack_home_budget_tracker.entity.SpendingCategory;
import rw.ac.auca.fintrack_home_budget_tracker.service.SpendingCategoryService;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/spending-categories")
public class SpendingCategoryController {

    private final SpendingCategoryService spendingCategoryService;

    public SpendingCategoryController(SpendingCategoryService spendingCategoryService) {
        this.spendingCategoryService = spendingCategoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody SpendingCategory spendingCategory) {
        String message = spendingCategoryService.saveSpendingCategory(spendingCategory);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(spendingCategoryService.getAllSpendingCategories(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<SpendingCategory> result = spendingCategoryService.getSpendingCategoryById(id);
        if (result.isEmpty()) {
            return new ResponseEntity<>("The spending category with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody SpendingCategory spendingCategory) {
        String message = spendingCategoryService.updateSpendingCategory(id, spendingCategory);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = spendingCategoryService.deleteSpendingCategory(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // C3
    @GetMapping("/unused")
    public ResponseEntity<?> getUnused() {
        return new ResponseEntity<>(spendingCategoryService.getUnused(), HttpStatus.OK);
    }
}
