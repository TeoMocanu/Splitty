package commons;

import java.util.Objects;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) throws Exception {
        if(day > 31 || day < 0)
            throw new Exception("Invalid date");
        if(month < 0 || month > 12)
            throw new Exception("Invalid date");
        if(year < 0)
            throw new Exception("Invalid date");
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Date date)) return false;
        return day == date.day && month == date.month && year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    public String toStringDMY() {
        return day + "." + month + "." + year;
    }

    public String toStringMDY() {
        return month + "." + day + "." + year;
    }
}
