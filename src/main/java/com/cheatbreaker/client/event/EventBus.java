package com.cheatbreaker.client.event;

import com.cheatbreaker.client.CheatBreaker;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * CheatBreaker's Event Manager.
 */
public class EventBus {

    public EventBus()
    {
        CheatBreaker.getInstance().logger.info(CheatBreaker.getInstance().loggerPrefix + "Created EventBus");
    }

    /**
     * Currently registered events.
     */
    private final ConcurrentHashMap<Class<? extends Event>, CopyOnWriteArrayList<Consumer<Event>>> registeredEvents = new ConcurrentHashMap<>();

    /**
     * Registers an event.
     */
    public <T extends Event> boolean addEvent(Class<T> clazz, Consumer<T> consumer) {
        return this.registeredEvents.computeIfAbsent(clazz, p0 -> new CopyOnWriteArrayList<>()).add((Consumer<Event>) consumer);
    }

    /**
     * Unregisters an event.
     */
    public <T extends Event> void removeEvent(final Class<T> clazz, final Consumer<T> consumer) {
        final CopyOnWriteArrayList<Consumer<Event>> list = this.registeredEvents.get(clazz);
        if (list != null) list.remove(consumer);
    }

    /**
     * Handles the event.
     */
    public void handleEvent(Event event) {
        try {
            for (Serializable s = event.getClass(); s != null && s != Event.class; s = ((Class<Event>) s).getSuperclass()) {
                final CopyOnWriteArrayList<Consumer<Event>> list = this.registeredEvents.get(s);
                if (list != null) {
                    list.forEach(consumer -> consumer.accept(event));
                }
            }
        } catch (Exception ex) {
            System.err.println("EventBus [" + event.getClass() + "]");
            ex.printStackTrace();
        }
    }

    /**
     * The event class, defines what an event is (NOTHING LOL).
     */
    public static class Event {
    }

    /**
     * Defines the data for an event.
     */
    public static class EventData extends Event {
        private boolean canceled = false;

        public boolean isCanceled() {
            return this.canceled;
        }

        public void setCanceled(boolean canceled) {
            this.canceled = canceled;
        }
    }

}
