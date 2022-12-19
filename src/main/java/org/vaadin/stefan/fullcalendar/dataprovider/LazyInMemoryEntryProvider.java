package org.vaadin.stefan.fullcalendar.dataprovider;

import org.vaadin.stefan.fullcalendar.Entry;

/**
 * Lazy loading {@link InMemoryEntryProvider}. At this point it uses the {@link AbstractInMemoryEntryProvider} implementation
 * as it is, but as a dedicated class for easier distinction between eager and lazy loading.
 * @author Stefan Uebe
 */
public class LazyInMemoryEntryProvider<T extends Entry> extends AbstractInMemoryEntryProvider<T> {

    public LazyInMemoryEntryProvider() {
    }

    public LazyInMemoryEntryProvider(Iterable<T> entries) {
        super(entries);
    }
}
