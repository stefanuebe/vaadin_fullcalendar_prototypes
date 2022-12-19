///*
// * Copyright 2020, Stefan Uebe
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
// * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
// * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
// * permit persons to whom the Software is furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in all copies or substantial portions
// * of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
// * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
// * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
// */
//package org.vaadin.stefan.demo;
//
//import com.vaadin.flow.router.Route;
//import elemental.json.JsonObject;
//import org.vaadin.stefan.fullcalendar.*;
//
//import java.time.LocalDate;
//import java.util.HashMap;
//
//@Route(value = "")
////@CssImport("./styles.css")
////@CssImport("./styles-scheduler.css")
//public class DemoView extends AbstractCalendarView {
//
//    @Override
//    protected FullCalendar createCalendar(JsonObject defaultInitialOptions) {
//        FullCalendar calendar = FullCalendarBuilder.create()
//                .withAutoBrowserTimezone()
//                .withInitialOptions(defaultInitialOptions)
//                .withEntryLimit(3)
//                .build();
//
//        createTestEntries(calendar);
//
//        return calendar;
//    }
//
//    private void createTestEntries(FullCalendar calendar) {
//        LocalDate now = LocalDate.now();
//
//        HashMap<String, Object> extendedProps = new HashMap<String, Object>();
//        HashMap<String, Object> cursors = new HashMap<String, Object>();
//        cursors.put("enabled", "pointer");
//        cursors.put("disabled", "not-allowed");
//        extendedProps.put("cursors", cursors);
//
//        EntryManager.createTimedBackgroundEntry(calendar, now.withDayOfMonth(3).atTime(10, 0), 120, null);
//        EntryManager.createTimedEntry(calendar, "Meeting 9", now.withDayOfMonth(7).atTime(11, 30), 120, "mediumseagreen");
//        EntryManager.createTimedEntry(calendar, "Meeting 10", now.withDayOfMonth(15).atTime(9, 0), 120, "mediumseagreen");
//        EntryManager.createTimedEntry(calendar, "Meeting 11", now.withDayOfMonth(18).atTime(10, 0), 120, "mediumseagreen");
//        EntryManager.createTimedEntry(calendar, "Meeting 12", now.withDayOfMonth(17).atTime(11, 30), 120, "mediumseagreen");
//        EntryManager.createTimedEntry(calendar, "Meeting 13", now.withDayOfMonth(24).atTime(9, 0), 120, "mediumseagreen");
//
//        EntryManager.createTimedEntry(calendar, "Grocery Store", now.withDayOfMonth(7).atTime(17, 30), 45, "violet");
//        EntryManager.createTimedEntry(calendar, "Dentist", now.withDayOfMonth(20).atTime(11, 30), 60, "violet");
//        EntryManager.createTimedEntry(calendar, "Cinema", now.withDayOfMonth(10).atTime(20, 30), 140, "dodgerblue");
//        EntryManager.createDayEntry(calendar, "Short trip", now.withDayOfMonth(17), 2, "dodgerblue");
//        EntryManager.createDayEntry(calendar, "John's Birthday", now.withDayOfMonth(23), 1, "gray");
//        EntryManager.createDayEntry(calendar, "This special holiday", now.withDayOfMonth(4), 1, "gray");
//
//        EntryManager.createDayEntry(calendar, "Multi 1", now.withDayOfMonth(12), 2, "tomato");
//        EntryManager.createDayEntry(calendar, "Multi 2", now.withDayOfMonth(12), 2, "tomato");
//        EntryManager.createDayEntry(calendar, "Multi 3", now.withDayOfMonth(12), 2, "tomato");
//        EntryManager.createDayEntry(calendar, "Multi 4", now.withDayOfMonth(12), 2, "tomato");
//        EntryManager.createDayEntry(calendar, "Multi 5", now.withDayOfMonth(12), 2, "tomato");
//        EntryManager.createDayEntry(calendar, "Multi 6", now.withDayOfMonth(12), 2, "tomato");
//        EntryManager.createDayEntry(calendar, "Multi 7", now.withDayOfMonth(12), 2, "tomato");
//        EntryManager.createDayEntry(calendar, "Multi 8", now.withDayOfMonth(12), 2, "tomato");
//        EntryManager.createDayEntry(calendar, "Multi 9", now.withDayOfMonth(12), 2, "tomato");
//        EntryManager.createDayEntry(calendar, "Multi 10", now.withDayOfMonth(12), 2, "tomato");
//
//
//        EntryManager.createDayBackgroundEntry(calendar, now.withDayOfMonth(4), 6, "#B9FFC3");
//        EntryManager.createDayBackgroundEntry(calendar, now.withDayOfMonth(19), 2, "#CEE3FF");
//        EntryManager.createTimedBackgroundEntry(calendar, now.withDayOfMonth(20).atTime(11, 0), 150, "#ff0000");
//
//        EntryManager.createRecurringEvents(calendar);
//    }
//
//    @Override
//    protected String createDescription() {
//        return "Welcome to the FullCalendar demo playground. In this instance you see a basic set of different calendar entry types to play around with. " +
//                "You may also create new ones or delete them. Have fun :)";
//    }
//
//    @Override
//    protected void onTimeslotsSelected(TimeslotsSelectedEvent event) {
//        super.onTimeslotsSelected(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//
//    @Override
//    protected void onDayNumberClicked(DayNumberClickedEvent event) {
//        super.onDayNumberClicked(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//
//    @Override
//    protected void onWeekNumberClicked(WeekNumberClickedEvent event) {
//        super.onWeekNumberClicked(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//
//    @Override
//    protected void onViewSkeletonRendered(ViewSkeletonRenderedEvent event) {
//        super.onViewSkeletonRendered(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//
//    @Override
//    protected void onEntryResized(EntryResizedEvent event) {
//        super.onEntryResized(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//
//    @Override
//    protected void onEntryDropped(EntryDroppedEvent event) {
//        super.onEntryDropped(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//
//    @Override
//    protected void onEntryClick(EntryClickedEvent event) {
//        super.onEntryClick(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//
//    @Override
//    protected void onBrowserTimezoneObtained(BrowserTimezoneObtainedEvent event) {
//        super.onBrowserTimezoneObtained(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//
//    @Override
//    protected void onDatesRendered(DatesRenderedEvent event) {
//        super.onDatesRendered(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//
//    @Override
//    protected void onMoreLinkClicked(MoreLinkClickedEvent event) {
//        super.onMoreLinkClicked(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//
//    @Override
//    protected void onTimeslotClicked(TimeslotClickedEvent event) {
//        super.onTimeslotClicked(event);
//        System.out.println(event.getClass().getSimpleName() + ": " + event);
//    }
//}