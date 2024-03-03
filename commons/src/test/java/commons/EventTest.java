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

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventTest {
    @Test
    public void checkConstructor() {
        var e = new Event("title");
        assertEquals("title", e.getTitle());
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
        var a = new Event("title1");
        var p0 = new Participant(a, "name1", "email1", "iban1", "bic1");
        var p1 = new Participant(a, "name2", "email2", "iban2", "bic2");
        var p2 = new Participant(a, "name3", "email3", "iban3", "bic3");
        var p3 = new Participant(a, "name4", "email4", "iban4", "bic4");
        a.addParticipant(p0);
        a.addParticipant(p1);
        a.addParticipant(p2);
        a.addParticipant(p3);
        assertEquals(4, a.getParticipants().size());
        assertEquals(p0, a.getParticipants().get(0));
        assertEquals(p1, a.getParticipants().get(1));
        assertEquals(p2, a.getParticipants().get(2));
        assertEquals(p3, a.getParticipants().get(3));
    }

    @Test
    public void setParticipantsTest() {
        var a = new Event("title1");
        var p0 = new Participant(a, "name1", "email1", "iban1", "bic1");
        var p1 = new Participant(a, "name2", "email2", "iban2", "bic2");
        var p2 = new Participant(a, "name3", "email3", "iban3", "bic3");
        var p3 = new Participant(a, "name4", "email4", "iban4", "bic4");
        List<Participant> list = new ArrayList<>();
        list.add(p0);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        a.setParticipants(list);
        assertEquals(4, a.getParticipants().size());
        assertEquals(p0, a.getParticipants().get(0));
        assertEquals(p1, a.getParticipants().get(1));
        assertEquals(p2, a.getParticipants().get(2));
        assertEquals(p3, a.getParticipants().get(3));
    }

    @Test
    public void getSetExpensesTest() {
        var a = new Event("title1");
        var p0 = new Participant(a, "name0", "email0", "iban0", "bic0");
        var p1 = new Participant(a, "name1", "email1", "iban1", "bic1");
        var e0 = new Expense(a, LocalDate.now(), p0, List.of(p1), "title1", 1);
        var e1 = new Expense(a, LocalDate.now(), p0, List.of(p1), "title2", 2);
        var e2 = new Expense(a, LocalDate.now(), p0, List.of(p1), "title3", 3);
        var e3 = new Expense(a, LocalDate.now(), p0, List.of(p1), "title4", 4);
        List<Expense> list = new ArrayList<>();
        list.add(e0);
        list.add(e1);
        list.add(e2);
        list.add(e3);
        a.setExpenses(list);
        assertEquals(4, a.getExpenses().size());
        assertEquals(e0, a.getExpenses().get(0));
        assertEquals(e1, a.getExpenses().get(1));
        assertEquals(e2, a.getExpenses().get(2));
        assertEquals(e3, a.getExpenses().get(3));
    }

    @Test
    public void addExpenseTest() {
        var a = new Event("title1");
        var p0 = new Participant(a, "name0", "email0", "iban0", "bic0");
        var p1 = new Participant(a, "name1", "email1", "iban1", "bic1");
        var e0 = new Expense(a, LocalDate.now(), p0, List.of(p1), "title1", 1);
        var e1 = new Expense(a, LocalDate.now(), p0, List.of(p1), "title2", 2);
        var e2 = new Expense(a, LocalDate.now(), p0, List.of(p1), "title3", 3);
        var e3 = new Expense(a, LocalDate.now(), p0, List.of(p1), "title4", 4);
        List<Expense> list = new ArrayList<>();
        list.add(e0);
        list.add(e1);
        list.add(e2);
        a.setExpenses(list);
        a.addExpenses(e3);
        assertEquals(4, a.getExpenses().size());
        assertEquals(e0, a.getExpenses().get(0));
        assertEquals(e1, a.getExpenses().get(1));
        assertEquals(e2, a.getExpenses().get(2));
        assertEquals(e3, a.getExpenses().get(3));
    }
}
