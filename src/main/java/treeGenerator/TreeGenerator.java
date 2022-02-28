package treeGenerator;

import dao.DataAccessException;
import dao.EventDAO;
import dao.PersonDAO;
import helperData.DataReader;
import helperData.Location;
import model.Event;
import model.Person;

import java.sql.Connection;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

public class TreeGenerator {
    private final Connection conn;
    private final String associatedUsername;
    private final Logger logger = Logger.getLogger("TreeGenerator");
    private Random random = new Random();

    public TreeGenerator(Connection conn, String associatedUsername) {
        this.conn = conn;
        this.associatedUsername = associatedUsername;
    }

    public void generateAncestors(Person person, int numGenerations) {
        int childBirthYear = getBirthYear(person.getPersonID());

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
        generateDeathEvent(person.getPersonID());

        return person;
    }

    private void setRandomName(Person person, String gender) {
        person.setFirstName(selectFirstName(gender));
        person.setLastName(selectLastName());
    }

    private void generateMarriageEvent(Person mother, Person father) {
        int marriageYear = getBirthYear(mother.getPersonID());
        Event marriage = new Event(associatedUsername, null, 0, 0, null, null, "marriage", marriageYear);
        setRandomLocation(marriage);

        marriage.setPersonID(mother.getPersonID());
        addEventToDatabase(marriage);

        marriage.setPersonID(father.getPersonID()); //TODO: This works, right? I don't have to create a copy?
        addEventToDatabase(marriage);
    }

    private void generateUserBirthEvent(String personID) {
        Event birth = new Event(associatedUsername, personID, 0, 0, null, null, "birth", 2000);
        setRandomLocation(birth);
        addEventToDatabase(birth);
    }

    private void generateBirthEvent(String personID, int childBirthYear, String gender) {
        int birthYear = childBirthYear - 30;
        Event birth = new Event(associatedUsername, personID, 0, 0, null, null, "birth", birthYear);
        setRandomLocation(birth);
        addEventToDatabase(birth);
    }

    private void generateDeathEvent(String personID) {
        int deathYear = getBirthYear(personID) + 70;
        Event death = new Event(associatedUsername, personID, 0, 0, null, null, "death", deathYear);
        setRandomLocation(death);
        addEventToDatabase(death);
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
        Location location = selectLocation();
        event.setCountry(location.getCountry());
        event.setCity(location.getCity());
        event.setLatitude(location.getLatitude());
        event.setLongitude(location.getLongitude());
    }

    private Location selectLocation() {
        Location[] possibleLocations = null;
        possibleLocations = DataReader.getLocations().getData();

        assert possibleLocations != null;
        int index = random.nextInt(possibleLocations.length);
        return possibleLocations[index];
    }

    private String selectFirstName(String gender) {
        String[] possibleNames = null;

        if(Objects.equals(gender, "f")) {
            possibleNames = DataReader.getFemaleNames().getData();
        }
        else if(Objects.equals(gender, "m")) {
            possibleNames = DataReader.getMaleNames().getData();
        }

        assert possibleNames != null;
        int index = random.nextInt(possibleNames.length);
        return possibleNames[index];
    }

    private String selectLastName() {
        String[] possibleNames = null;
        possibleNames = DataReader.getSurnames().getData();

        assert possibleNames != null;
        int index = random.nextInt(possibleNames.length);
        return possibleNames[index];
    }

    private int getBirthYear(String personID) {
        try {
            EventDAO dao = new EventDAO(conn);
            Event[] queryResult = dao.queryByPersonAndType(personID, "birth");
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
