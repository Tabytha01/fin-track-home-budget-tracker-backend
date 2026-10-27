package rw.ac.auca.fintrack_home_budget_tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate expenseDate;
    @Enumerated(EnumType.STRING)
    private ExpenseStatus status;
    private String description;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private HouseholdMember member;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    public UUID getId() { return id; }
    public void setId(UUID id)
    { this.id = id; }
    public LocalDate getExpenseDate() { return expenseDate; }
    public void setExpenseDate(LocalDate expenseDate)
    { this.expenseDate = expenseDate; }
    public ExpenseStatus getStatus() { return status; }
    public void setStatus(ExpenseStatus status)
    { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description)
    { this.description = description; }
    public double getAmount() { return amount; }
    public void setAmount(double amount)
    { this.amount = amount; }
    public HouseholdMember getMember() { return member; }
    public void setMember(HouseholdMember member)
    { this.member = member; }
    public Account getAccount() { return account; }
    public void setAccount(Account account)
    { this.account = account; }
}
