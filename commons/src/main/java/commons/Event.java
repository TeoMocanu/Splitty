package commons;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    public long id;
    private String title;
    @OneToMany(mappedBy = "event")
    @Column(name = "PARTICIPANTS")
    @ElementCollection(targetClass = List.class)
    private List<Participant> participants;
    @OneToMany(mappedBy = "event")
    @Column(name = "EXPENSES")
    @ElementCollection(targetClass = List.class)
    private List<Expense> expenses;

    public Event(String title){
        this.title = title;
        this.participants = new ArrayList<Participant>(0);
        this.expenses = new ArrayList<Expense>(0);
    }

//    public Event(long id, String title, List<Long> participants, List<Long> expenses) {
//        this.title = title;
//        this.participants = new ArrayList<>();
//        this.expenses = new ArrayList<>();
//    }


    @SuppressWarnings("unused")
    protected Event() {
        // for object mappers
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
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
        // uncomment later
        //return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
        return this.title + " Ex: " + this.getExpenses().size() + " Pa: " + this.getParticipants().size();
    }

}
