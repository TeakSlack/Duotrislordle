package teak.events;

public interface IEventHandler<T> {
    public void handleEvent(T data);
}
