package teak.events;

public interface IEventHandler<T> {
    // An event handler interface allows interoperability in the event system manager and allows the amount and complexity of code to be significantly reduced.
    public void handleEvent(T data);
}
