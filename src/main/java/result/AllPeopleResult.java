package result;

import model.Person;

/**
 * The AllPeopleResult class stores the data for the result body for person requests without a personID
 */
public class AllPeopleResult extends Result {
    /**
     * an array of all the people associated with a given user
     */
    private Person[] people;

    /**
     * Creates an AllPeopleResult object
     *
     * @param message The message to return in case of an error
     * @param success indicates whether the request was successful
     * @param people the people
     */
    public AllPeopleResult(String message, boolean success, Person[] people) {
        super(message, success);
        this.people = people;
    }

    public Person[] getPeople() {
        return people;
    }

    public void setPeople(Person[] people) {
        this.people = people;
    }
}
