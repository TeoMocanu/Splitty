///*
// * Copyright 2021 Delft University of Technology
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package commons;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//import org.junit.jupiter.api.Test;
//
//public class ExpenseTest {
//    @Test
//    public void checkConstructor() {
//        var e = new Expense(1, "title", 1);
//        assertEquals(1, e.payerId);
//        assertEquals("title", e.title);
//        assertEquals(1, e.amount);
//    }
//    @Test
//    public void equalsHashCode() {
//        var a = new Expense(1, "title", 1);
//        var b = new Expense(1, "title", 1);
//        assertEquals(a, b);
//        assertEquals(a.hashCode(), b.hashCode());
//    }
//    @Test
//    public void notEqualsHashCode() {
//        var a = new Expense(1, "title1", 1);
//        var b = new Expense(2, "title2", 2);
//        assertNotEquals(a, b);
//        assertNotEquals(a.hashCode(), b.hashCode());
//    }
//    @Test
//    public void setIdTest() {
//        var a = new Expense(0, "title1", 1);
//        a.setId(1);
//        assertEquals(1, a.getId());
//    }
//
//    @Test
//    public void setPayerTest() {
//        var a = new Expense(0, "title1", 1);
//        var part = new Participant("name1", "email1", "iban1");
//        a.setPayer(part);
//        assertEquals(part.getId(), a.getPayer());
//    }
//
//    @Test
//    public void setTitleTest() {
//        var a = new Expense(0, "title1", 1);
//        a.setTitle("title2");
//        assertEquals("title2", a.getTitle());
//    }
//
//    @Test
//    public void getTitleTest() {
//        var a = new Expense(0, "title1", 1);
//        assertEquals("title1", a.getTitle());
//    }
//
//    @Test
//    public void getAmountTest() {
//        var a = new Expense(0, "title1", 1);
//        assertEquals(1, a.getAmount());
//    }
//}
