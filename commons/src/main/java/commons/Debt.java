package commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import commons.primaryKeys.DebtKey;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
@IdClass(DebtKey.class)
public class Debt {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "debt_id")
    private long id;

    @Id
    @Column(name = "event_id")
    private long eventId;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    @JsonIgnoreProperties({"participants", "expenses"})
    private Event event;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "debtor_event_id", referencedColumnName = "event_id"),
        @JoinColumn(name = "debtor_id", referencedColumnName = "participant_id")})
    @JsonIgnoreProperties({"expensesPaidBy", "expensesToPay", "event_id", "event", "participantKey"})
    private Participant debtor;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "creditor_event_id", referencedColumnName = "event_id"),
        @JoinColumn(name = "creditor_id", referencedColumnName = "participant_id")})
    @JsonIgnoreProperties({"expensesPaidBy", "expensesToPay", "event_id", "event", "participantKey"})
    //@JsonIgnore
    private Participant creditor;

    @Column(name = "amount")
    private double amount;
    @Transient
    private DebtKey debtKey;

    @SuppressWarnings("unused")
    protected Debt() {
        // for object mapper
    }

    public Debt(Event event, Participant debtor, Participant creditor, double amount) {
        this.eventId = event.getId();
        this.event = event;
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
        this.debtKey = new DebtKey(event.getId(), id);
    }

    @JsonIgnore
    public DebtKey getDebtKey() {
        return new DebtKey(eventId, id);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public long getEventId() {
        return eventId;
    }

    public long getId() {
        return id;
    }

    public Participant getDebtor() {
        return debtor;
    }

    public void setDebtor(Participant debtor) {
        this.debtor = debtor;
    }

    public Participant getCreditor() {
        return creditor;
    }

    public void setCreditor(Participant creditor) {
        this.creditor = creditor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void addAmount(double amount) { this.amount += amount; }

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

    public void setId(long id) {
        this.id = id;
    }
}
