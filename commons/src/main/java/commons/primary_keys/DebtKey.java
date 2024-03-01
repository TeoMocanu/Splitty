package commons.primary_keys;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class DebtKey implements Serializable{
    @Id
    @Column(name = "eventId")
    private long eventId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "debtId")
    private long debtId;

    public DebtKey(long eventId, long debtId) {
        this.eventId = eventId;
        this.debtId = debtId;
    }

    @SuppressWarnings("unused")
    protected DebtKey() {
        // for object mappers
    }


}
