package org.vaadin.stefan.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import elemental.json.Json;
import elemental.json.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.stefan.fullcalendar.*;
import org.vaadin.stefan.fullcalendar.dataprovider.EagerInMemoryEntryProvider;
import org.vaadin.stefan.fullcalendar.dataprovider.EntryProvider;

import java.util.Collection;

/**
 * A basic class for simple calendar views, e.g. for demo or testing purposes. Takes care of
 * creating a toolbar, a description element and embedding the created calendar into the view.
 * Also registers a dates rendered listener to update the toolbar.
 */
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractCalendarView extends VerticalLayout {
    //    private final CalendarViewToolbar toolbar;
    private final FullCalendar calendar;

    // TODO add scheduler support

    public AbstractCalendarView() {
        calendar = createCalendar(createDefaultInitialOptions());

        calendar.addEntryClickedListener(this::onEntryClick);
        calendar.addEntryDroppedListener(this::onEntryDropped);
        calendar.addEntryResizedListener(this::onEntryResized);
        calendar.addDayNumberClickedListener(this::onDayNumberClicked);
        calendar.addBrowserTimezoneObtainedListener(this::onBrowserTimezoneObtained);
        calendar.addMoreLinkClickedListener(this::onMoreLinkClicked);
        calendar.addTimeslotClickedListener(this::onTimeslotClicked);
        calendar.addTimeslotsSelectedListener(this::onTimeslotsSelected);
        calendar.addViewSkeletonRenderedListener(this::onViewSkeletonRendered);
        calendar.addDatesRenderedListener(this::onDatesRendered);
        calendar.addWeekNumberClickedListener(this::onWeekNumberClicked);

//        toolbar = createToolbar(CalendarViewToolbar.builder()
//                .calendar(calendar)
//                .settingsAvailable(true)
//                .viewChangeable(true)
//                .dateChangeable(true)
//                .editable(true)
//                .allowAddingRandomItemsInitially(true)
//                .onSamplesCreated(this::onEntriesCreated)
//                .onSamplesRemoved(this::onEntriesRemoved));

        calendar.setHeightByParent();

//        if (toolbar != null) {
//            calendar.addDatesRenderedListener(event -> {
//                toolbar.updateInterval(event.getIntervalStart());
//            });
//        }


        VerticalLayout titleAndDescription = new VerticalLayout();
        titleAndDescription.setSpacing(false);
        titleAndDescription.setPadding(false);

        Component titleElement = createTitleElement();
        if (titleElement != null) {
            titleAndDescription.add(titleElement);
        }

        Component descriptionElement = createDescriptionElement();
        if (descriptionElement != null) {
            titleAndDescription.add(descriptionElement);
            titleAndDescription.setHorizontalComponentAlignment(Alignment.STRETCH, descriptionElement);
        }

        if (titleElement != null || descriptionElement != null) {
            add(titleAndDescription);
            setHorizontalComponentAlignment(Alignment.STRETCH, titleAndDescription);
        }

//        if (toolbar != null) {
//            add(toolbar);
//            setHorizontalComponentAlignment(Alignment.CENTER, toolbar);
//        }

        Checkbox toggleDarkMode = new Checkbox("Toggle Dark mode");
        toggleDarkMode.addValueChangeListener(event -> {
            ThemeList themeList = calendar.getElement().getThemeList();
            if (event.getValue()) {
                themeList.add("dark");
            } else {
                themeList.remove("dark");
            }
        });

        add(toggleDarkMode, calendar);

        setFlexGrow(1, calendar);
        setHorizontalComponentAlignment(Alignment.STRETCH, calendar);

        setSizeFull();
    }

    /**
     * Creates the plain full calendar instance with all initial options. The given default initial options are created by
     * {@link #createDefaultInitialOptions()} beforehand.
     * <p></p>
     * The calender is automatically embedded afterwards and connected with the toolbar (if one is created, which
     * is the default). Also all event listeners will be initialized with a default callback method.
     *
     * @param defaultInitialOptions default initial options
     * @return calendar instance
     */
    protected abstract FullCalendar createCalendar(JsonObject defaultInitialOptions);

    /**
     * Creates a default set of initial options.
     *
     * @return initial options
     */
    protected JsonObject createDefaultInitialOptions() {
        JsonObject initialOptions = Json.createObject();
        JsonObject eventTimeFormat = Json.createObject();
//{ hour: 'numeric', minute: '2-digit', timeZoneName: 'short' }
        eventTimeFormat.put("hour", "2-digit");
        eventTimeFormat.put("minute", "2-digit");
        eventTimeFormat.put("timeZoneName", "short");
        eventTimeFormat.put("meridiem", false);
        eventTimeFormat.put("hour12", false);
        initialOptions.put("eventTimeFormat", eventTimeFormat);
        return initialOptions;
    }

    /**
     * Called by the calendar's entry click listener. Noop by default.
     *
     * @param event event
     * @see FullCalendar#addEntryClickedListener(ComponentEventListener)
     */
    protected void onEntryClick(EntryClickedEvent event) {
    }

    /**
     * Called by the calendar's entry drop listener (i. e. an entry has been dragged around / moved by the user).
     * Applies the changes to the entry and calls {@link #onEntryChanged(Entry)} by default.
     *
     * @param event event
     * @see FullCalendar#addEntryDroppedListener(ComponentEventListener)
     */
    protected void onEntryDropped(EntryDroppedEvent event) {
        event.applyChangesOnEntry();
        onEntryChanged(event.getEntry());
    }

    /**
     * Called by the calendar's entry resize listener.
     * Applies the changes to the entry and calls {@link #onEntryChanged(Entry)} by default.
     *
     * @param event event
     * @see FullCalendar#addEntryResizedListener(ComponentEventListener)
     */
    protected void onEntryResized(EntryResizedEvent event) {
        event.applyChangesOnEntry();
        onEntryChanged(event.getEntry());
    }

    /**
     * Called by the calendar's week number click listener. Noop by default.
     *
     * @param event event
     * @see FullCalendar#addWeekNumberClickedListener(ComponentEventListener)
     */
    protected void onWeekNumberClicked(WeekNumberClickedEvent event) {

    }

    /**
     * Called by the calendar's dates rendered listener. Noop by default.
     * Please note, that there is a separate dates rendered listener taking
     * care of updating the toolbar.
     *
     * @param event event
     * @see FullCalendar#addDatesRenderedListener(ComponentEventListener)
     */
    protected void onDatesRendered(DatesRenderedEvent event) {

    }

    /**
     * Called by the calendar's view skeleton rendered listener. Noop by default.
     *
     * @param event event
     * @see FullCalendar#addViewSkeletonRenderedListener(ComponentEventListener)
     */
    protected void onViewSkeletonRendered(ViewSkeletonRenderedEvent event) {

    }

    /**
     * Called by the calendar's timeslot selected listener. Noop by default.
     *
     * @param event event
     * @see FullCalendar#addTimeslotsSelectedListener(ComponentEventListener)
     */
    protected void onTimeslotsSelected(TimeslotsSelectedEvent event) {

    }

    /**
     * Called by the calendar's timeslot clicked listener. Noop by default.
     *
     * @param event event
     * @see FullCalendar#addTimeslotClickedListener(ComponentEventListener)
     */
    protected void onTimeslotClicked(TimeslotClickedEvent event) {

    }

    /**
     * Called by the calendar's "more" link clicked listener. Noop by default.
     *
     * @param event event
     * @see FullCalendar#addMoreLinkClickedListener(ComponentEventListener)
     */
    protected void onMoreLinkClicked(MoreLinkClickedEvent event) {
    }

    /**
     * Called by the calendar's browser timezone obtained listener. Noop by default.
     * Please note, that the full calendar builder registers also a listener, when the
     * {@link FullCalendarBuilder#withAutoBrowserTimezone()} option is used.
     *
     * @param event event
     * @see FullCalendar#addBrowserTimezoneObtainedListener(ComponentEventListener)
     */
    protected void onBrowserTimezoneObtained(BrowserTimezoneObtainedEvent event) {

    }

    /**
     * Called by the calendar's day number click listener. Noop by default.
     *
     * @param event event
     * @see FullCalendar#addDayNumberClickedListener(ComponentEventListener)
     */
    protected void onDayNumberClicked(DayNumberClickedEvent event) {

    }

    protected Component createDescriptionElement() {
        String description = createDescription();
        if (description == null) {
            return null;
        }
        Span descriptionElement = new Span(description);
        descriptionElement.getStyle() // TODO move to css at some point
                .set("font-size", "0.8rem")
                .set("color", "#666");

        return descriptionElement;
    }

    protected String createDescription() {
        return null;
    }


    protected Component createTitleElement() {
        String title = createTitle();
        if (title == null) {
            return null;
        }
        Span titleElement = new Span(title);
        titleElement.getStyle() // TODO move to css at some point
                .set("font-size", "1.1rem")
                .set("font-weight", "600")
                .set("color", "#666");

        return titleElement;
    }

    protected String createTitle() {
//        MenuItem item = getClass().getAnnotation(MenuItem.class);
//        return item != null ? item.label() : String.join(" ", StringUtils.splitByCharacterTypeCamelCase(getClass().getSimpleName()));
        return String.join(" ", StringUtils.splitByCharacterTypeCamelCase(getClass().getSimpleName()));
    }

//    /**
//     * Inits the toolbar. Calendar and the "onSample" callbacks are already set. Change view and date
//     * parameters are also enabled by default. Either update the given variable or create a new one, if
//     * necessary. Return null for no toolbar at all.
//     *
//     * @param toolbarBuilder toolbar builder
//     * @return modified or new instance
//     */
//    protected CalendarViewToolbarBuilder initToolbarBuilder(CalendarViewToolbarBuilder toolbarBuilder) {
//        return toolbarBuilder;
//    }
//
//    /**
//     * Creates the toolbar. The parameter might be null depending on a custom implementation of
//     * {@link #initToolbarBuilder(CalendarViewToolbarBuilder)}. Return null if no toolbar shall
//     * be available.
//     *
//     * @param toolbarBuilder builder or null
//     * @return toolbar or null
//     */
//    protected CalendarViewToolbar createToolbar(CalendarViewToolbarBuilder toolbarBuilder) {
//        return toolbarBuilder != null ? toolbarBuilder.build() : null;
//    }

    /**
     * Called by the toolbar, when one of the "Create sample entries" button has been pressed to simulate the
     * creation of new data. Might be called by any other source, too.
     * <p></p>
     * Intended to update the used backend. By default it will check, if the used entry provider is eager in memory
     * and in that case automatically update the entry provider (to prevent unnecessary code duplication when
     * the default entry provider is used).
     *
     * @param entries entries to add
     */
    protected void onEntriesCreated(Collection<Entry> entries) {
        // The eager in memory provider provider provides API to modify its internal cache and takes care of pushing
        // the data to the client - no refresh call is needed (or even recommended here)
        if (getCalendar().isEagerInMemoryEntryProvider()) {
            ((EagerInMemoryEntryProvider<Entry>) getCalendar().getEntryProvider()).addEntries(entries);
        }
    }

    /**
     * Called by the toolbar, when the "Remove entries" button has been pressed to simulate the removal of entries.
     * Might be called by any other source, too.
     * <p></p>
     * Intended to update the used backend. By default it will check, if the used entry provider is eager in memory
     * and in that case automatically update the entry provider (to prevent unnecessary code duplication when
     * the default entry provider is used).
     *
     * @param entries entries to remove
     */
    protected void onEntriesRemoved(Collection<Entry> entries) {
        // The eager in memory provider provider provides API to modify its internal cache and takes care of pushing
        // the data to the client - no refresh call is needed (or even recommended here)
        if (getCalendar().isEagerInMemoryEntryProvider()) {
            ((EagerInMemoryEntryProvider<Entry>) getCalendar().getEntryProvider()).removeEntries(entries);
        }
    }

    /**
     * Called, when one of the sample entries have been modified, e. g. by an event.
     * Might be called by any other source, too.
     * <p></p>
     * Intended to update the used backend. By default it will check, if the used entry provider is eager in memory
     * and in that case automatically update the entry provider (to prevent unnecessary code duplication when
     * the default entry provider is used).
     *
     * @param entry entry that has changed
     */
    protected void onEntryChanged(Entry entry) {
        // The eager in memory provider provider provides API to modify its internal cache and takes care of pushing
        // the data to the client - no refresh call is needed (or even recommended here)
        if (getCalendar().isEagerInMemoryEntryProvider()) {
            ((EagerInMemoryEntryProvider<Entry>) getCalendar().getEntryProvider()).updateEntry(entry);
        }
    }

    /**
     * Returns the entry provider set to the calendar. Will be available after {@link #createCalendar(JsonObject)}
     * has been called.
     *
     * @return entry provider or null
     */
    protected EntryProvider<Entry> getEntryProvider() {
        return getCalendar().getEntryProvider();
    }


}
