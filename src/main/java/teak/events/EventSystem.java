package teak.events;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class EventSystem {
    private Map<Class<?>, List<IEventHandler<?>>> subscribers;

    public EventSystem()
    {
        subscribers = new HashMap<>();
    }

    public <T> void subscribe(Class<?> eventName, IEventHandler<T> handler) // subscribe handler to event of certain name
    {
        List<IEventHandler<?>> handlers = subscribers.getOrDefault(eventName, new ArrayList<>());
        handlers.add(handler);
        subscribers.put(eventName, handlers);
    }

    @SuppressWarnings("unchecked")
    public <T> void publish(Class<?> eventName, T data) // publish event of name
    {
        List<IEventHandler<?>> handlers = subscribers.getOrDefault(eventName, new ArrayList<>());
        for(IEventHandler<?> handler : handlers)
        {
            ((IEventHandler<T>) handler).handleEvent(data);
        }
    }
}
