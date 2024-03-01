package commons;

import commons.primary_keys.ExpenseKey;
import commons.primary_keys.ParticipantKey;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
public class Expense {

    @EmbeddedId
    private ExpenseKey expenseKey;

    @ManyToOne
    @MapsId("event_id")
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "Date")
    private LocalDate localDate;

    @ManyToOne
    private Participant payer;

    @ManyToMany
    List<Participant> debtors;

    @Column(name = "title")
    private String title;
    @Column(name = "amount")
    public float amount;

    //public List<Participant> splitBetween;

    public Expense(){

    }

    public Expense(long payerId, String title, float amount){
        this.payerId = payerId;
        this.title = title;
        this.amount = amount;
    }

    public Expense(long payerId, String title, float amount, String type){
        this.payerId = payerId;
        this.title = title;
        this.amount = amount;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPayer() {
        return payerId;
    }

    public void setPayer(Participant payer) {
        this.payerId = payerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense expense)) return false;
        return id == expense.id && Float.compare(expense.amount, amount) == 0
                && Objects.equals(payerId, expense.payerId) && Objects.equals(title, expense.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payerId, title, amount);
    }


}
