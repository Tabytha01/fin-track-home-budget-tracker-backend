package rw.ac.auca.fintrack_home_budget_tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int bankCode;

    private String name;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public int getBankCode() { return bankCode; }
    public void setBankCode(int bankCode) { this.bankCode = bankCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
