package treeGenerator;

import dao.DataAccessException;
import dao.EventDAO;
import dao.PersonDAO;
import model.Event;
import model.Person;

import java.sql.Connection;
import java.util.logging.Logger;

public class TreeGenerator {
    private final Connection conn;
    private final String associatedUsername;
    private final Logger logger = Logger.getLogger("TreeGenerator");

    public TreeGenerator(Connection conn, String associatedUsername) {
        this.conn = conn;
        this.associatedUsername = associatedUsername;
    }

    public void generateAncestors(Person person, int numGenerations) {
        int childBirthYear = getBirthYear(person);

        Person mother = generatePerson("f", childBirthYear);
        Person father = generatePerson("m", childBirthYear);
        generateMarriageEvent(mother, father);

        person.setMotherID(mother.getPersonID());
        person.setFatherID(father.getPersonID());

        addPersonToDatabase(person);

        if(numGenerations > 1) {
            numGenerations--;
            generateAncestors(mother, numGenerations);
            generateAncestors(father, numGenerations);
        }
        else {
            addPersonToDatabase(mother);
            addPersonToDatabase(father);
        }
    }

    private void addPersonToDatabase(Person person) {
        try {
            PersonDAO dao = new PersonDAO(conn);
            dao.insert(person);
        }
        catch (DataAccessException ex) {
            logger.severe("Error inserting randomly generated person into database");
            ex.printStackTrace();
        }
    }

    public Person generatePerson(String gender) {
        Person person = new Person(associatedUsername, null, null, gender, null, null, null);
        setRandomName(person, gender);

        generateUserBirthEvent(person.getPersonID());

        return person;
    }

    public Person generatePerson(String gender, int childBirthYear) {
        Person person = new Person(associatedUsername, null, null, gender, null, null, null);
        setRandomName(person, gender);

        generateBirthEvent(person.getPersonID(), childBirthYear, gender);
        generateDeathEvent(person.getPersonID(), childBirthYear);

        return person;
    }

    private void setRandomName(Person person, String gender) {
        person.setFirstName(selectFirstName(gender));
        person.setLastName(selectLastName());
    }

    private void generateMarriageEvent(Person mother, Person father) {
    }

    private void generateUserBirthEvent(String personID) {
        Event birth = new Event(associatedUsername, personID, 0, 0, null, null, "birth", 2000);
        setRandomLocation(birth);
    }

    private void generateBirthEvent(String personID, int childBirthYear, String gender) {

    }

    private void generateDeathEvent(String personID, int childBirthYear) {

    }

    private void addEventToDatabase(Event event) {
        try {
            EventDAO dao = new EventDAO(conn);
            dao.insert(event);
        }
        catch (DataAccessException ex) {
            logger.severe("Error inserting randomly generated event into database");
            ex.printStackTrace();
        }
    }

    private void setRandomLocation(Event event) {
        //Location location = *randomly select from the location list*
        //event.setCountry(location.getCountry());
    }

    //private Location selectLocation() //TODO: Implement this and the Location object

    private String selectFirstName(String gender) {
        return null;
    }

    private String selectLastName() {
        return null;
    }

    private int getBirthYear(Person person) {
        try {
            EventDAO dao = new EventDAO(conn);
            Event[] queryResult = dao.queryByPersonAndType(person.getPersonID(), "birth");
            assert (queryResult.length == 1);

            Event birth = queryResult[0];
            return birth.getYear();
        }
        catch (DataAccessException ex) {
            logger.severe("Error getting birth year");
            ex.printStackTrace();
        }
        return -1;
    }
}
