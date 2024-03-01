package commons;

import commons.primary_keys.DebtKey;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Debt {

    @EmbeddedId
    private DebtKey debtKey;

    @ManyToOne
    @MapsId("event_id")
    @JoinColumn(name = "event_id")
    public Event event;

    @Column(name = "debtor")
    private String debtor;

    @Column(name = "creditor")
    private String creditor;

    @Column(name = "amount")
    private double amount;

    @SuppressWarnings("unused")
    protected Debt() {
        // for object mapper
    }

    public Debt(long eventId, String debtor, String creditor, double amount) {
        this.debtKey = new DebtKey(eventId);
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
    }

    public long getId() {
        return debtKey.getId();
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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