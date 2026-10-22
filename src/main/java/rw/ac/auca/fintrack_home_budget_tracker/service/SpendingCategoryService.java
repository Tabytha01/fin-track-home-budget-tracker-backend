package rw.ac.auca.fintrack_home_budget_tracker.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.fintrack_home_budget_tracker.entity.SpendingCategory;
import rw.ac.auca.fintrack_home_budget_tracker.repository.SpendingCategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpendingCategoryService {

    private final SpendingCategoryRepository spendingCategoryRepository;

    public SpendingCategoryService(SpendingCategoryRepository spendingCategoryRepository) {
        this.spendingCategoryRepository = spendingCategoryRepository;
    }

    public String saveSpendingCategory(SpendingCategory spendingCategory) {
        spendingCategoryRepository.save(spendingCategory);
        return "Spending category saved successfully";
    }

    public List<SpendingCategory> getAllSpendingCategories() {
        return spendingCategoryRepository.findAll();
    }

    public Optional<SpendingCategory> getSpendingCategoryById(UUID id) {
        return spendingCategoryRepository.findById(id);
    }

    public String updateSpendingCategory(UUID id, SpendingCategory spendingCategory) {
        Optional<SpendingCategory> existing = spendingCategoryRepository.findById(id);
        if (existing.isEmpty()) {
            return "The spending category with that id does not exist";
        }
        spendingCategory.setId(id);
        spendingCategoryRepository.save(spendingCategory);
        return "Spending category updated successfully";
    }

    public String deleteSpendingCategory(UUID id) {
        Optional<SpendingCategory> existing = spendingCategoryRepository.findById(id);
        if (existing.isEmpty()) {
            return "The spending category with that id does not exist";
        }
        spendingCategoryRepository.deleteById(id);
        return "Spending category deleted successfully";
    }
}
