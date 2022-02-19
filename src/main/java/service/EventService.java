package service;

import result.EventResult;

/**
 * The EventService class performs the functionality for event requests with a specified ID
 */
public class EventService {
    /**
     * Finds and returns data for the event with the given event ID if that event exists and is associated with the user indicated by the authentication token
     *
     * @param authtoken the authentication token
     * @param eventID the event identification
     * @return an EventResult with the outcome of the request
     */
    public EventResult event(String authtoken, String eventID) {
        return null;
    }
}
