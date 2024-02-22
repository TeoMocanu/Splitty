package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Objects;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public Participant payer;
    public String title;
    public float amount;
    //public List<Participant> splitBetween;

    public Expense(){

    }

    public Expense(Participant payer, String title, float amount){
        this.payer = payer;
        this.title = title;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Participant getPayer() {
        return payer;
    }

    public void setPayer(Participant payer) {
        this.payer = payer;
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
        return id == expense.id && Float.compare(expense.amount, amount) == 0 && Objects.equals(payer, expense.payer) && Objects.equals(title, expense.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payer, title, amount);
    }


}
