package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {
    @Test
    void invalidDay() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            Date date = new Date(32,12,2023);
        });

        String expected = "Invalid date";
        String message = exception.getMessage();

        assertTrue(message.contains(expected));
    }

    @Test
    void invalidMonth() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            Date date = new Date(31,13,2023);
        });

        String expected = "Invalid date";
        String message = exception.getMessage();

        assertTrue(message.contains(expected));
    }

    @Test
    void getDay() throws Exception {
        Date date = new Date(30,12,2023);
        assertEquals(date.getDay(), 30);
    }

    @Test
    void setDay() throws Exception {
        Date date = new Date(30,12,2023);
        date.setDay(15);
        assertEquals(date.getDay(), 15);
    }

    @Test
    void getMonth() throws Exception {
        Date date = new Date(30,12,2023);
        assertEquals(date.getMonth(), 12);
    }

    @Test
    void setMonth() throws Exception {
        Date date = new Date(30,12,2023);
        date.setMonth(11);
        assertEquals(date.getMonth(), 11);
    }

    @Test
    void getYear() throws Exception {
        Date date = new Date(30,12,2023);
        assertEquals(date.getYear(), 2023);
    }

    @Test
    void setYear() throws Exception {
        Date date = new Date(30,12,2023);
        date.setYear(2022);
        assertEquals(date.getYear(), 2022);
    }

    @Test
    void testEquals() throws Exception {
        Date date1 = new Date(30,12,2023);
        Date date2 = new Date(30,12,2023);
        assertTrue(date1.equals(date2));
    }

    @Test
    void testHashCode() throws Exception {
        Date date1 = new Date(30,12,2023);
        Date date2 = new Date(30,12,2023);
        assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    void toStringDMY() throws Exception {
        Date date = new Date(30,12,2023);
        assertEquals(date.toStringDMY(), "30.12.2023");
    }

    @Test
    void toStringMDY() throws Exception {
        Date date = new Date(30,12,2023);
        assertEquals(date.toStringMDY(), "12.30.2023");
    }
}