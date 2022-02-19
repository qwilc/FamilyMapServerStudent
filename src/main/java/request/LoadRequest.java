package request;

import model.Event;
import model.Person;
import model.User;

/**
 * The LoadRequest class stores the data for the request body of a load request
 */
public class LoadRequest {
    /**
     * The users to load
     */
    private User[] users;
    /**
     * The people to load
     */
    private Person[] people;
    /**
     * The events to load
     */
    private Event[] events;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPeople() {
        return people;
    }

    public void setPeople(Person[] people) {
        this.people = people;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
