package rw.ac.auca.fintrack_home_budget_tracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String storeName;
    private String note;
    private int itemCount;
    private LocalDate recordedDate;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public int getItemCount() { return itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }
    public LocalDate getRecordedDate() { return recordedDate; }
    public void setRecordedDate(LocalDate recordedDate) { this.recordedDate = recordedDate; }
    public Expense getExpense() { return expense; }
    public void setExpense(Expense expense) { this.expense = expense; }
}
