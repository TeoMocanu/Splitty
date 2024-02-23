package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public long payerId;
    public String title;
    public float amount;
    //public List<Participant> splitBetween;

    public Expense(){

    }

    public Expense(long payerId, String title, float amount){
        this.payerId = payerId;
        this.title = title;
        this.amount = amount;
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
        return id == expense.id && Float.compare(expense.amount, amount) == 0 && Objects.equals(payerId, expense.payerId) && Objects.equals(title, expense.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payerId, title, amount);
    }


}
