package service;

import result.PersonResult;

/**
 * The PersonService class performs the functionality for person requests with a specified ID
 */
public class PersonService {
    /**
     * Finds and returns data for the person specified by the person ID if that person is associated with the user indicated by the authentication token
     *
     * @param authtoken the authentication token
     * @param personID the person ID
     * @return a PersonResult object with the outcome of the person request
     */
    public PersonResult person(String authtoken, String username, String personID) {
        return null;
    }
}
