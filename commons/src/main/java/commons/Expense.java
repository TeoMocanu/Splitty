package commons;

import commons.primary_keys.ExpenseKey;
import commons.primary_keys.ParticipantKey;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

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
    private float amount;

    //public List<Participant> splitBetween;

    public Expense(){

    }

    public Expense(Event event, LocalDate localDate, Participant payer, List<Participant> debtors, String title, float amount){
        this.expenseKey = new ExpenseKey(event.getId());
        this.event = event;
        this.localDate = localDate;
        this.payer = payer;
        this.debtors = debtors;
        this.title = title;
        this.amount = amount;
    }

    public ExpenseKey getExpenseKey() {
        return expenseKey;
    }

    public void setExpenseKey(ExpenseKey expenseKey) {
        this.expenseKey = expenseKey;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Participant getPayer() {
        return payer;
    }

    public void setPayer(Participant payer) {
        this.payer = payer;
    }

    public List<Participant> getDebtors() {
        return debtors;
    }

    public void setDebtors(List<Participant> debtors) {
        this.debtors = debtors;
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
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
