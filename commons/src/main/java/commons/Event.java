package commons;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private long id;
    @Column(name = "event_name")
    private String title;
    @ElementCollection
    private List<Long> participants;
    @ElementCollection
    private List<Long> expenses;

    public Event(String title){
        this.title = title;
        participants = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    protected Event() {
        // for object mappers
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant.getId());
    }

    public List<Long> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Long> expenses) {
        this.expenses = expenses;
    }

    public void addExpenses(Expense expense) {
        this.expenses.add(expense.getId());
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
