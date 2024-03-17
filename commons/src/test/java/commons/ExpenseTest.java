/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class ExpenseTest {

    Expense e;
    Participant participant;
    Event event;
    LocalDate date;
    @BeforeEach
    public void setUp(){
        event = new Event("party");
        date = LocalDate.of(2024, 12, 12);
        participant = new Participant("John", event);
        e = new Expense(event, date, participant, List.of(participant), "parking", 12.5f);
    }

    @Test
    public void checkConstructor() {
        assertEquals(participant, e.getPayer());
        assertEquals("parking", e.getTitle());
        assertEquals(12.5f, e.getAmount());
    }
    @Test
    public void equalsHashCode() {
        var a = e;
        assertEquals(a, e);
        assertEquals(a.hashCode(), e.hashCode());
    }
    @Test
    public void notEqualsHashCode() {
        var a = new Expense();
        var b = new Expense(event, date, participant, List.of(participant), "park", 1.5f);
        assertNotEquals(a, b);
        assertNotEquals(a, e);
        assertNotEquals(e, b);
        assertNotEquals(a.hashCode(), b.hashCode());
        assertNotEquals(e.hashCode(), b.hashCode());
        assertNotEquals(a.hashCode(), e.hashCode());
    }
    // TODO THIS IS A BUG, NEEDS TO BE FIXED
    @Test
    public void getIdTest() {
        var a = new Expense(event, date, participant, List.of(participant), "parking", 12.5f);
        var b = new Expense(new Event("BBQ"), date, participant, List.of(participant), "parking", 12.5f);

        // same properties, should still be a different id
//        assertNotEquals(e.getId(), a.getId());
        // different properties, should be different id
//        assertNotEquals(e.getId(), b.getId());
    }

    @Test
    public void getPayerTest() {
        assertEquals(participant, e.getPayer());
    }

    @Test
    public void setPayerTest() {
        var part = new Participant("name1", event);
        e.setPayer(part);
        assertEquals(part, e.getPayer());
        e.setPayer(participant);
    }

    @Test
    public void getDebtorsTest() {
        assertEquals(participant, e.getDebtors().get(0));
    }

    @Test
    public void setDebtorsTest() {
        var participant1 = new Participant(event, "name1", "email1", "iban1", "bic1");
        var participant2 = new Participant(event, "name1", "email1", "iban1", "bic1");
        e.setDebtors(List.of(participant1, participant2));
        assertEquals(2, e.getDebtors().size());
        assertEquals(participant1, e.getDebtors().get(0));
        assertEquals(participant2, e.getDebtors().get(1));
    }

    @Test
    public void setTitleTest() {
        e.setTitle("title2");
        assertEquals("title2", e.getTitle());
        e.setTitle("parking");
    }

    @Test
    public void getTitleTest() {
        assertEquals("parking", e.getTitle());
    }

    @Test
    public void getAmountTest() {
        assertEquals(12.5f, e.getAmount());
    }

    @Test
    public void setAmountTest() {
        e.setAmount(15.5f);
        assertEquals(15.5f, e.getAmount());
    }

    @Test
    public void getDateTest() {
        assertEquals(date, e.getLocalDate());
    }

    @Test
    public void setDateTest() {
        var newDate = LocalDate.of(2024, 12, 13);
        e.setLocalDate(newDate);
        assertEquals(newDate, e.getLocalDate());
    }

    @Test
    public void getEventTest() {
        assertEquals(event, e.getEvent());
    }

    @Test
    public void getEventIdTest() {
        assertEquals(event.getId(), e.getEventId());
    }
}
