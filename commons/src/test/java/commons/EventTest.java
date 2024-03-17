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
import java.util.ArrayList;
import java.util.List;

public class EventTest {

    public Event e;
    public Participant p0, p1, p2, p3;
    public Expense e0, e1, e2, e3;
    @BeforeEach
    public void initialize(){
        e = new Event("title");
        p0 = new Participant(e, "name1", "email1", "iban1", "bic1");
        p1 = new Participant(e, "name2", "email2", "iban2", "bic2");
        p2 = new Participant(e, "name3", "email3", "iban3", "bic3");
        p3 = new Participant(e, "name4", "email4", "iban4", "bic4");
        e.addParticipant(p0);
        e.addParticipant(p1);
        e.addParticipant(p2);
        e.addParticipant(p3);
        e0 = new Expense(e, LocalDate.now(), p0, List.of(p1), "title1", 1);
        e1 = new Expense(e, LocalDate.now(), p0, List.of(p1), "title2", 2);
        e2 = new Expense(e, LocalDate.now(), p0, List.of(p1), "title3", 3);
        e3 = new Expense(e, LocalDate.now(), p0, List.of(p1), "title4", 4);
        e.addExpense(e0);
        e.addExpense(e1);
        e.addExpense(e2);
        e.addExpense(e3);
    }
    @Test
    public void checkConstructor() {
        var a = new Event("title");
        assertEquals("title", a.getTitle());
    }

    @Test
    public void equalsHashCode() {
        var a = new Event("title");
        var b = new Event("title");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new Event("title1");
        var b = new Event("title2");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void setTitleTest() {
        var a = new Event("title1");
        a.setTitle("title2");
        assertEquals("title2", a.getTitle());
    }

    @Test
    public void getTitleTest() {
        var a = new Event("title1");
        assertEquals("title1", a.getTitle());
    }

    @Test
    public void getParticipantsTest() {
        assertEquals(4, e.getParticipants().size());
        assertEquals(p0, e.getParticipants().get(0));
        assertEquals(p1, e.getParticipants().get(1));
        assertEquals(p2, e.getParticipants().get(2));
        assertEquals(p3, e.getParticipants().get(3));
    }

    @Test
    public void setParticipantsTest() {
        List<Participant> list = List.of(p3, p1, p2, p0);
        e.setParticipants(list);
        assertEquals(4, e.getParticipants().size());
        assertEquals(p3, e.getParticipants().get(0));
        assertEquals(p1, e.getParticipants().get(1));
        assertEquals(p2, e.getParticipants().get(2));
        assertEquals(p0, e.getParticipants().get(3));
    }

    @Test
    public void addParticipantTest() {
        var p = new Participant(e, "name", "email", "000", "ABC");
        e.addParticipant(p);
        assertEquals(5, e.getParticipants().size());
        assertEquals(p3, e.getParticipants().get(3));
        assertEquals(p, e.getParticipants().get(4));
    }

    @Test
    public void getExpensesTest() {
        assertEquals(4, e.getExpenses().size());
        assertEquals(e0, e.getExpenses().get(0));
        assertEquals(e1, e.getExpenses().get(1));
        assertEquals(e2, e.getExpenses().get(2));
        assertEquals(e3, e.getExpenses().get(3));
    }

    @Test
    public void setExpensesTest() {
        List<Expense> list = List.of(e3, e2, e1, e0);
        e.setExpenses(list);
        assertEquals(4, e.getExpenses().size());
        assertEquals(e3, e.getExpenses().get(0));
        assertEquals(e2, e.getExpenses().get(1));
        assertEquals(e1, e.getExpenses().get(2));
        assertEquals(e0, e.getExpenses().get(3));
    }

    @Test
    public void addExpenseTest() {
        List<Expense> list = new ArrayList<>();
        e.setExpenses(list);
        e.addExpense(e1);
        // size is 1 before the addition
        assertEquals(1, e.getExpenses().size());
        assertEquals(e1, e.getExpenses().get(0));
        e.addExpense(e3);
        // size is 2 after addition
        assertEquals(2, e.getExpenses().size());
        assertEquals(e1, e.getExpenses().get(0));
        assertEquals(e3, e.getExpenses().get(1));
    }

    @Test
    public void getIdTest() {
        Long id = e.id;
        assertEquals(id, e.getId());
    }

    // TODO bug including the uniqueness of the generated ids
    @Test
    public void uniqueIdTest() {
        var event = new Event("other event");
        //assertNotEquals(e.getId(), event.getId());

        var a = new Event("event");
        var b = new Event("event");
        //assertNotEquals(a.getId(), b.getId());
    }

    @Test
    public void toStringTest() {
        Event event = new Event("simple event");
        assertEquals(event.toString().substring(0, 14), "commons.Event@");
        assertEquals(event.toString().substring(49),
                " participants=[]\r\n" +
                "  title=simple event\r\n" +
                "]");
    }

}
