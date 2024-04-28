package teak.events;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

// An events system allows for a loosely coupled system. It enables better organization and far greater flexibility.
public class EventSystem {
    private Map<Class<?>, List<IEventHandler<?>>> subscribers; // A key\value system that allows many different events to subscribe to one publisher.

    public EventSystem()
    {
        subscribers = new HashMap<>(); // Map x = new HashMap<>(); allows for the Map to be reassigned to a different type of map later.
    }

    public <T> void subscribe(Class<?> eventName, IEventHandler<T> handler) // subscribe handler to event of certain class
    {
        List<IEventHandler<?>> handlers = subscribers.getOrDefault(eventName, new ArrayList<>()); // getOrDefault prevents errors from calling methods on null classes.
        handlers.add(handler);
        subscribers.put(eventName, handlers);
    }

    @SuppressWarnings("unchecked") // slightly hacky workaround, allows casting without a warning being thrown
    public <T> void publish(Class<?> eventName, T data) // publish event of class
    {
        List<IEventHandler<?>> handlers = subscribers.getOrDefault(eventName, new ArrayList<>());
        for(IEventHandler<?> handler : handlers)
        {
            ((IEventHandler<T>) handler).handleEvent(data);
        }
    }
}
