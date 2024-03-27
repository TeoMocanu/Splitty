package commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import commons.primaryKeys.ExpenseKey;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@IdClass(ExpenseKey.class)
public class Expense {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "expense_id")
    private long id;

    @Id
    @Column(name = "event_id")
    private long eventId;

    @Transient
    private ExpenseKey expenseKey;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    @JsonIgnoreProperties({"participants", "expenses"})
    private Event event;

    @Column(name = "date")
    private LocalDate localDate;

    @ManyToOne
    @JsonIgnoreProperties({"event", "expensesPaidBy", "expensesToPay"})
    @JoinColumns({
        @JoinColumn(name = "payer_event_id", referencedColumnName = "event_id"),
        @JoinColumn(name = "payer_id", referencedColumnName = "participant_id")})
    private Participant payer;

    @ManyToMany
    @JsonIgnoreProperties({"event", "expensesPaidBy", "expensesToPay"})
    @JoinTable(name = "expense_splitter",
            joinColumns = {
                @JoinColumn(name = "expense_id", referencedColumnName = "expense_id"),
                @JoinColumn(name = "expense_event_id", referencedColumnName = "event_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "splitter_event_id", referencedColumnName = "event_id"),
                @JoinColumn(name = "splitter_id", referencedColumnName = "participant_id")})
    List<Participant> splitters;

    @ManyToMany
    List<Participant> debtors;

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
        this.eventId = event.getId();
        this.event = event;
        this.localDate = localDate;
        this.payer = payer;
        this.splitters = owings;
        this.title = title;
        this.amount = amount;
        this.
    }

    public ExpenseKey getExpenseKey() {
        return new ExpenseKey(eventId, id);
    }

    public long getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public long getEventId() {
        return eventId;
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

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
//    }

    @Override
    public String toString() {
        return title;
    }
}
