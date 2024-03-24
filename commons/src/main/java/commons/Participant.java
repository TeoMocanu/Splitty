package commons;

import commons.primaryKeys.ParticipantKey;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
@IdClass(ParticipantKey.class)
public class Participant {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "participant_id")
    private long id;

    @Id
    @Column(name = "event_id")
    private long eventId;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "payer")
    private List<Expense> expensesPaidBy;

    @ManyToMany(mappedBy = "splitters")
    private List<Expense> expensesToPay;

    private String name;
    private String email;
    private String iban;
    private String bic;

    public Participant(){ }

    public Participant(Event event, String name, String email, String iban, String bic){
        this.event = event;
        this.eventId = event.getId();
        this.expensesPaidBy = new ArrayList<>();
        this.expensesToPay = new ArrayList<>();
        this.name = name;
        this.email = email;
        this.iban = iban;
        this.bic = bic;
    }

    public Participant(String name, Event event){
        this.expensesPaidBy = new ArrayList<>();
        this.expensesToPay = new ArrayList<>();
        this.event = event;
        this.eventId = event.getId();
        this.name = name;
    }

    public ParticipantKey getParticipantKey() {
        return new ParticipantKey(eventId, id);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public long getEventId() {
        return eventId;
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
    //@JsonIgnore

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

}
