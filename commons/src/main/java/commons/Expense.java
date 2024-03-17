package commons;

import commons.primaryKeys.ExpenseKey;
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
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "date")
    private LocalDate localDate;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "payer_event_id", referencedColumnName = "event_id"),
        @JoinColumn(name = "payer_id", referencedColumnName = "participant_id")})
    private Participant payer;

    @ManyToMany
    @JoinTable(name = "expense_splitter",
            joinColumns = {
                @JoinColumn(name = "expense_id", referencedColumnName = "expense_id"),
                @JoinColumn(name = "expense_event_id", referencedColumnName = "event_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "splitter_event_id", referencedColumnName = "event_id"),
                @JoinColumn(name = "splitter_id", referencedColumnName = "participant_id")})
    List<Participant> splitters;

    @Column(name = "title")
    private String title;
    @Column(name = "amount")
    private float amount;

    @SuppressWarnings("unused")
    public Expense() {
        // for object mappers
    }

    public Expense(Event event, LocalDate localDate, Participant payer, List<Participant> owings, String title,
                   float amount) {
        this.expenseKey = new ExpenseKey(event.getId());
        this.event = event;
        this.localDate = localDate;
        this.payer = payer;
        this.splitters = owings;
        this.title = title;
        this.amount = amount;
    }

    public ExpenseKey getExpenseKey() {
        return expenseKey;
    }

    public long getId() {
        return expenseKey.getId();
    }

    public Event getEvent() {
        return event;
    }

    public long getEventId() {
        return expenseKey.getEventId();
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

    public List<Participant> getSplitters() {
        return splitters;
    }

    public void setSplitters(List<Participant> debtors) {
        this.splitters = debtors;
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
