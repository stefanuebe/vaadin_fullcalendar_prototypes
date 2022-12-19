package org.vaadin.stefan.demo;

import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.HashMap;

@Deprecated
public class EntryManager {
    public static void createRecurringEvents(FullCalendar calendar) {
        LocalDate now = LocalDate.now();

        Entry recurring = new Entry();
        recurring.setTitle(now.getYear() + "'s sunday event");
        recurring.setColor("lightgray");
        recurring.setRecurringDaysOfWeek(Collections.singleton(DayOfWeek.SUNDAY));

        recurring.setRecurringStartDate(now.with(TemporalAdjusters.firstDayOfYear()));
        recurring.setRecurringEndDate(now.with(TemporalAdjusters.lastDayOfYear()));
        recurring.setRecurringStartTime(LocalTime.of(14, 0));
        recurring.setRecurringEndTime(LocalTime.of(17, 0));

        if (calendar != null && calendar.getEntryProvider().isInMemory()) {
            calendar.addEntry(recurring);
        }
    }

    public static void createDayEntry(FullCalendar calendar, String title, LocalDate start, int days, String color) {
        Entry entry = new Entry();
        setValues(calendar, entry, title, start.atStartOfDay(), days, ChronoUnit.DAYS, color);

        if (calendar != null && calendar.getEntryProvider().isInMemory()) {
            calendar.addEntry(entry);
        }
    }

    public static void createDayBackgroundEntry(FullCalendar calendar, LocalDate start, int days, String color) {
        Entry entry = new Entry();
        setValues(calendar, entry, start.atStartOfDay(), days, ChronoUnit.DAYS, color);

        entry.setRenderingMode(Entry.RenderingMode.BACKGROUND);

        if (calendar != null && calendar.getEntryProvider().isInMemory()) {
            calendar.addEntry(entry);
        }
    }

    public static void createTimedBackgroundEntry(FullCalendar calendar, LocalDateTime start, int minutes, String color) {
        Entry entry = new Entry();
        setValues(calendar, entry, start, minutes, ChronoUnit.MINUTES, color);
        entry.setRenderingMode(Entry.RenderingMode.BACKGROUND);

        if (calendar != null && calendar.getEntryProvider().isInMemory()) {
            calendar.addEntry(entry);
        }
    }

    public static Entry createTimedEntry(FullCalendar calendar, String title, LocalDateTime start, int minutes, String color) {
        Entry entry = new Entry();
        setValues(calendar, entry, title, start, minutes, ChronoUnit.MINUTES, color);
        if (calendar != null && calendar.getEntryProvider().isInMemory()) {
            calendar.addEntry(entry);
        }
        return entry;
    }

    public static void createTimedEntry(FullCalendar calendar, String title, LocalDateTime start, int minutes, String color, HashMap<String, Object> extendedProps) {
        Entry entry = new Entry();
        setValues(calendar, entry, title, start, minutes, ChronoUnit.MINUTES, color, extendedProps);
        entry.setEditable(false);
        if (calendar != null && calendar.getEntryProvider().isInMemory()) {
            calendar.addEntry(entry);
        }
    }

    public static void setValues(FullCalendar calendar, Entry entry, String title, LocalDateTime start, int amountToAdd, ChronoUnit unit, String color) {
        entry.setTitle(title);
        entry.setStart(start);
        entry.setEnd(entry.getStart().plus(amountToAdd, unit));
        entry.setAllDay(unit == ChronoUnit.DAYS);
        entry.setColor(color);
        entry.setCustomProperty("description", "Description of " + title);
    }

    static void setValues(FullCalendar calendar, Entry entry, String title, LocalDateTime start, int amountToAdd, ChronoUnit unit, String color, HashMap<String, Object> extendedProps) {
        entry.setTitle(title);
        entry.setStart(start);
        entry.setEnd(entry.getStart().plus(amountToAdd, unit));
        entry.setAllDay(unit == ChronoUnit.DAYS);
        entry.setColor(color);
        entry.setCustomProperties(extendedProps);
    }

    static void setValues(FullCalendar calendar, Entry entry, LocalDateTime start, int amountToAdd, ChronoUnit unit, String color) {
        entry.setTitle("");
        entry.setStart(start);
        entry.setEnd(entry.getStart().plus(amountToAdd, unit));
        entry.setAllDay(unit == ChronoUnit.DAYS);
        entry.setColor(color);
    }
}
