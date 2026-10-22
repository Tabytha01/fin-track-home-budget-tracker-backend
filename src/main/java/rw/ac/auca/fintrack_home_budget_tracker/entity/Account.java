package rw.ac.auca.fintrack_home_budget_tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String accountName;
    private String holderName;
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToMany
    @JoinTable(name = "account_spending_category")
    private List<SpendingCategory> spendingCategories;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public Bank getBank() { return bank; }
    public void setBank(Bank bank) { this.bank = bank; }

    public List<SpendingCategory> getSpendingCategories() { return spendingCategories; }
    public void setSpendingCategories(List<SpendingCategory> spendingCategories) { this.spendingCategories = spendingCategories; }
}
