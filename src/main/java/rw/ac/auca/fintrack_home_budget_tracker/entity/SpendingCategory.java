package rw.ac.auca.fintrack_home_budget_tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "spending_category")
public class SpendingCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "spendingCategories")
    private List<Account> accounts;
    public UUID getId() { return id; }
    public void setId(UUID id)
    { this.id = id; }
    public String getName() { return name; }
    public void setName(String name)
    { this.name = name; }
    public List<Account> getAccounts() { return accounts; }
    public void setAccounts(List<Account> accounts)
    { this.accounts = accounts; }
}
