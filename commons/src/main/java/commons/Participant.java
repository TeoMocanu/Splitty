package commons;

import commons.primary_keys.ParticipantKey;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Participant {
    @EmbeddedId
    private ParticipantKey participantKey;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "payer")
    private List<Expense> expensesPaidBy;

    @ManyToMany(mappedBy = "debtors")
    private List<Expense> expensesToPay;

    private String name;
    private String email;
    private String iban;
    private String bic;

    public Participant(){ }

    public Participant(Event event, String name, String email, String iban, String bic){
        this.participantKey = new ParticipantKey(event.getId());
        this.event = event;
        this.expensesPaidBy = new ArrayList<>();
        this.expensesToPay = new ArrayList<>();
        this.name = name;
        this.email = email;
        this.iban = iban;
        this.bic = bic;
    }

    public Participant(String name, Event event){
        this.participantKey = new ParticipantKey(event.getId());
        this.expensesPaidBy = new ArrayList<>();
        this.expensesToPay = new ArrayList<>();
        this.event = event;
        this.name = name;
    }

    public ParticipantKey getParticipantKey() {
        return participantKey;
    }

    public void setParticipantKey(ParticipantKey participantKey) {
        this.participantKey = participantKey;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public long getId() {
        return participantKey.getId();
    }

    public long getEventId() {
        return participantKey.getEventId();
    }

    public Event getEvent() {
        return event;
    }

    public List<Expense> getExpensesPaidBy() {
        return expensesPaidBy;
    }

    public void setExpensesPaidBy(List<Expense> expensesPaidBy) {
        this.expensesPaidBy = expensesPaidBy;
    }

    public List<Expense> getExpensesToPay() {
        return expensesToPay;
    }

    public void setExpensesToPay(List<Expense> expensesToPay) {
        this.expensesToPay = expensesToPay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
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
