package com.cheatbreaker.client.event;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventBus {
    private final ConcurrentHashMap<Class<? extends Event>, CopyOnWriteArrayList<Consumer>> registeredEvents = new ConcurrentHashMap<>();

    public <T extends Event> boolean addEvent(Class<T> clazz, Consumer<T> consumer) {
        return this.registeredEvents.computeIfAbsent(clazz, p0 -> new CopyOnWriteArrayList<>()).add(consumer);
    }

    public <T extends Event> boolean removeEvent(final Class<T> clazz, final Consumer<T> consumer) {
        final CopyOnWriteArrayList<Consumer> list = this.registeredEvents.get(clazz);
        return list != null && list.remove(consumer);
    }

    public void handleEvent(Event event) {
        try {
            for (Serializable s = event.getClass(); s != null && s != Event.class; s = ((Class<Event>) s).getSuperclass()) {
                final CopyOnWriteArrayList<Consumer> list = this.registeredEvents.get(s);
                if (list != null) {
                    list.forEach(consumer -> consumer.accept(event));
                }
            }
        } catch (Exception ex) {
            System.out.println("EventBus [" + event.getClass() + "]");
            ex.printStackTrace();
        }
    }
    
    public static class Event {
    }

    public static class EventData extends Event {
        @Getter @Setter private boolean canceled = false;
    }


}
