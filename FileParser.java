import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for reading different types of {@link Appliance} objects
 * from a specific TXT file format. The constant attributes of this
 * {@code FileParser} class define the format of the TXT file needed
 * to individuate all properties of each {@link Appliance} object.
 * <p>
 * The format (stored in {@code paragraphFormat}) of the TXT file
 * should therefore be strictly correct, there will be thrown exceptions
 * otherwise.
 * <p>
 * If the TXT file add some new {@link Appliance} properties or properties of
 * file itself, the format could be easily changed by just reassigning different
 * values to the constant attributes of {@code FileParser} class.
 * <p>
 * However, in order to reuse {@code FileParser} class for reading
 * an extended/different TXT file, the property "subclass" which defines
 * ({@code CyclicFixed}, {@code CyclicVaries}, {@code RandomFixed},
 * {@code RandomVaries,} must be also included in the extended TXT file.
 *
 * @author Alked Ejupi <email>ae7g18@soton.ac.uk</email>
 */

public class FileParser {
    private House  house;
    private String CONFIGURATION_FILE;

    private int SUBCLASS_PROPERTY_POSITION =  2;
    private int DISTANCE_FIRST_PROPERTY    = -1;
    private int DISTANCE_LAST_PROPERTY     =  6;
    private int DISTANCE_NEXT_SUBCLASS     =  9;
    private String[] properties            = new String[SUBCLASS_PROPERTY_POSITION + DISTANCE_LAST_PROPERTY+1];
    private String[] paragraphFormat       = new String[]{"",
            "name:",
            "subclass:",
            "meter:",
            "Min units consumed:",
            "Max units consumed:",
            "Fixed units consumed",
            "Probability switched on:",
            "Cycle length:"
    };

    /***
     *  Instantiate a {@code FileParse} object, specifying the house
     *  which will be populated with {@link Appliance} objects read
     *  from the {@code CONFIGURATION_FILE} given.
     *
     * @param house a {@link House} object.
     * @param CONFIGURATION_FILE a TXT
     */
    public FileParser(House house, String CONFIGURATION_FILE){
        this.house                      = house;
        this.CONFIGURATION_FILE         = CONFIGURATION_FILE;
    }

    /**
     * This method will start parsing the TXT file converted into a
     * {@code List<String>} object by using {@link #convertFileToList(String)};
     *
     * It is only used to individuate all "Subclass" fields, which refers
     * to the number of {@link Appliance} objects to create. When it finds them,
     * it will use {@link #parseList(List, String[])} to return the first property
     * of the paragraph just identified.
     *
     * All properties extracted will be stored in {@code properties}, and they
     * will be used to instantiate an {@link Appliance} with the method:
     *
     * {@link #instantiateApplianceFromFile(String, String, String,
     *                                      String, String, String,
     *                                      String, String)}
     *
     */
    public void start() {
        List<String> listLines = null;
        try {
            listLines = convertFileToList(CONFIGURATION_FILE);
        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(1);
        }
	    
        // Stops when cannot find any other subclass property.
        while (SUBCLASS_PROPERTY_POSITION < listLines.size()){
            try {
                properties = parseList(listLines, paragraphFormat);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            try {
                instantiateApplianceFromFile(
                        properties[1],
                        properties[2],
                        properties[3],
                        properties[4],
                        properties[5],
                        properties[6],
                        properties[7],
                        properties[8]);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
            // The "subclass" property line is updated to the next one
            SUBCLASS_PROPERTY_POSITION += DISTANCE_NEXT_SUBCLASS; // Next subclass of the file
        }
    }

    /**
     * Creates a {@code List<String>} instance by reading
     * and adding each line of {@code file}.
     *
     * @param fileName a {@code String} object which is the pathname of the TXT file to parse.
     * @return the {@code file} converted into a {@code List<String>}.
     * @throws IOException if the {@code file} is not found
     */
    private List<String> convertFileToList(String fileName) throws IOException {

        FileReader file = new FileReader(fileName);
        BufferedReader in = new BufferedReader(file);
        List<String> listLines = new ArrayList<>();

        // Makes first line of the file with index 1.
        listLines.add("");

        String line;
        while ((line = in.readLine()) != null) {
            listLines.add(line);
        }
        return listLines;
    }

    /**
     *  Parses all properties of the paragraph with the current
     *  {@code SUBCLASS_PROPERTY_POSITION}.
     *
     *  The{@code fileLine} to parse is checked with
     *  the appropriate {@code fileFormat}.
     *
     * @param listLines       the txt file converted into a {@code List<String>}
     * @param paragraphFormat the original paragraph format to be checked against
     *                        the {@code listLines}
     *
     * @return                a {@code String[]} object containing all extracted properties
     * @throws Exception      if the format of the paragraph is not correct, specifying in
     *                        which line the error is.
     */
    private String[] parseList(List<String> listLines, String []paragraphFormat) throws Exception {

        String EXCEPTION_FORMAT_MESSAGE = "The format of your file" + " '" + CONFIGURATION_FILE + " is not correct.";
        String propertyExtracted;

        // First line of the paragraph
        int currentLineParsed = 1; //Starts always with 1

        // Iterates through all properties position using distances
        for (int d = DISTANCE_FIRST_PROPERTY; d <= DISTANCE_LAST_PROPERTY; d++) {
			
                String lineFormat = paragraphFormat[currentLineParsed];
                String lineFile = listLines.get(SUBCLASS_PROPERTY_POSITION + d);

                if (lineFile.contains(lineFormat)) {
                    propertyExtracted = lineFile.substring(lineFile.lastIndexOf(": ") + 2);
                    properties[currentLineParsed] = propertyExtracted;
                }else{
                    throw new Exception(
                            EXCEPTION_FORMAT_MESSAGE +
                            "\nLine: " + (SUBCLASS_PROPERTY_POSITION + d) +
                            " \nError:" + " ' " + lineFile + " '"
                    );
                }
                currentLineParsed++;
            }
            return properties;
    }

    /**
     * Instantiates the correct subclass of {@link Appliance}
     * by checking {@param subclass}.
     *
     * @param name          the 1st property of the 1st line of the paragraph.
     * @param subclass      the 2st property of the 2st line of the paragraph.
     * @param meter         the 3rd property of the 3rd line of the paragraph.
     * @param min           the 4th property of the 4th line of the paragraph.
     * @param max           the 5th property of the 5th line of the paragraph.
     * @param units         the 6th property of the 6th line of the paragraph.
     * @param probability   the 7th property of the 7th line of the paragraph.
     * @param cycle         the 8th property of the 8th line of the paragraph.
     *
     * @throws Exception if {@param subclass} is not a valid subclass of {@link Appliance}.
     */
    private void instantiateApplianceFromFile(String name, String subclass, String meter,
                                              String min, String max, String units, String probability,
                                              String cycle) throws Exception {
            switch (subclass) {
                case "CyclicFixed":
                    addUnknownAppliance(new CyclicFixed(name, Float.parseFloat(units), Integer.parseInt(cycle.replaceAll("/24", ""))), meter);
                    break;
                case "CyclicVaries":
                    addUnknownAppliance(new CyclicVaries(name, Integer.parseInt(cycle.replaceAll("/24", "")), Float.parseFloat(min), Float.parseFloat(max)), meter);
                    break;
                case "RandomFixed":
                    addUnknownAppliance(new RandomFixed(name, Float.parseFloat(units), Integer.parseInt(probability.replaceAll("1 in ", ""))), meter);
                    break;
                case "RandomVaries":
                    addUnknownAppliance(new RandomVaries(name, Integer.parseInt(probability.replaceAll("1 in ", "")), Float.parseFloat(min), Float.parseFloat(max)), meter);
                    break;
                default:
                    throw new Exception("'" + subclass + "'" + " is not a valid type of appliance. Error in appliance: " + name);
            }
    }

    /**
     * Instantiates the {@link Appliance} object to the appropriate
     * {@link Meter} type, using methods from {@link House} class.
     *
     * @param appliance the appliance to add.
     * @param meter     the meter.
     * @throws Exception if {@param meter} is not a valid meter.
     */
    private void addUnknownAppliance(Appliance appliance, String meter) throws Exception {
        switch (meter) {
            case "water":
                house.addWaterAppliance(appliance);
                break;
            case "electric":
                house.addElectricAppliance(appliance);
                break;
            default:
                throw new Exception(meter + " is not a valid type of meter. Error in appliance: " + appliance.getName());
        }
    }
}
