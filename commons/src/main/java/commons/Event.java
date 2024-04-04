package commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    public long id;
    private String title;
    private List<String> types;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"event", "expensesToPay", "expensesPaidBy"})
    private List<Participant> participants;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<Expense> expenses;

    public Event(String title) {
        this.title = title;
        this.participants = new ArrayList<Participant>(0);
        this.expenses = new ArrayList<Expense>(0);
        this.types = new ArrayList<String>(0);
    }


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

    public List<String> getTypes() {
        return this.types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void addType(String s) {
        this.types.add(s);
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
        // uncomment later
        //return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
        return this.title + " Ex: " + this.getExpenses().size() + " Pa: " + this.getParticipants().size();
    }


    public String toJSONString() throws IOException {
        // only include title, participants, expenses, and types
//        return "{title: " + this.title + ", participants: " + this.participants + ", expenses: " + this.expenses + ", types: " + this.types + "}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String ret = mapper.writeValueAsString(this);
        return ret;
    }
    public void setId(long id) {
        this.id=id;
    }
}
