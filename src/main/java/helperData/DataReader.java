package helperData;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.logging.Logger;

public class DataReader {
    private static Names femaleNames;
    private static Names maleNames;
    private static Names surnames;
    private static Locations locations;
    private static final Logger logger = Logger.getLogger("DataReader");

    static {
        Gson gson = new Gson();
        locations = gson.fromJson(readFile("json/locations.json"), Locations.class);
        femaleNames = gson.fromJson(readFile("json/fnames.json"), Names.class);
        maleNames = gson.fromJson(readFile("json/mnames.json"), Names.class);
        surnames = gson.fromJson(readFile("json/snames.json"), Names.class);
    }

    private static String readFile(String pathName) {
        File file = new File(pathName);

        try(FileReader fileReader = new FileReader(file);) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        logger.info(DataReader.locations.getData()[0].getCountry());
        logger.info(DataReader.femaleNames.getData()[0]);
        logger.info(DataReader.maleNames.getData()[0]);
        logger.info(DataReader.surnames.getData()[0]);
    }

    public static Names getFemaleNames() {
        return femaleNames;
    }

    public static void setFemaleNames(Names femaleNames) {
        DataReader.femaleNames = femaleNames;
    }

    public static Names getMaleNames() {
        return maleNames;
    }

    public static void setMaleNames(Names maleNames) {
        DataReader.maleNames = maleNames;
    }

    public static Names getSurnames() {
        return surnames;
    }

    public static void setSurnames(Names surnames) {
        DataReader.surnames = surnames;
    }

    public static Locations getLocations() {
        return locations;
    }

    public static void setLocations(Locations locations) {
        DataReader.locations = locations;
    }
}
