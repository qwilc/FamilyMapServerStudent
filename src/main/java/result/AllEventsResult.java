package result;

import model.Event;

/**
 * The AllEventsResult class stores the data for the result body for event requests without an eventID
 */
public class AllEventsResult extends Result {
    /**
     * an array of all the events associated with a given user
     */
    private Event[] events;

    /**
     * Creates an AllEventsResult object
     *
     * @param message The message to return in case of an error
     * @param success indicates whether the request was successful
     * @param events the events
     */
    public AllEventsResult(String message, boolean success, Event[] events) {
        super(message, success);
        this.events = events;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
