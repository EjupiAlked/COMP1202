import java.util.ArrayList;

/**
 * This class defines a simulation of a real house in which
 * can contain different {@link Appliance} objects stored in
 * an {@link ArrayList<Appliance>}
 * <p>
 * Two {@link Meter} objects are attached to it (one for each utility
 * Electric and Water), keeping track the consumption of the
 * {@code House} object.
 */
public class House {

    private Meter waterMeter;
    private Meter electricMeter;
    private ArrayList<Appliance> appliancesHouse;


    public static void main(String[] args) {

        BatteryMeter electricMeter = new BatteryMeter("Electricity", 0.013f, new Battery(500));
        Meter waterMeter = new Meter("Water", 0.002f);
        House house = new House(electricMeter, waterMeter);

        int hours;
        String CONFIGURATION_FILE = args[0];

        try {
            hours = Integer.parseInt(args[1]);
        }catch (Exception e){
            hours = 168;
        }
	    
        FileParser configFile = new FileParser(house, CONFIGURATION_FILE);
        configFile.start();
	house.activate(hours);
    }
	

    /**
     * Creates a new {@code House} object with two default {@code Meter}
     * objects attached to it and an {@link ArrayList<Appliance>}
     * of all appliances in the house. (In this case an empty list).
     */
    House() {
        this.waterMeter = new Meter("Water", 0.002f);
        this.electricMeter = new Meter("Electricity", 0.013f);
        this.appliancesHouse = new ArrayList<Appliance>();
    }



    /**
     * Creates a new {@code House} object with a water {@code Meter}
     * object an a {@code BatteryMeter} object.
     * <p>
     * This instance of House is able to store or take utility from
     * a {@link Battery} attached to the {@code BatteryMeter}
     *
     * @param batteryMeter the {@link BatteryMeter} object.
     * @param waterMeter   the {@link Meter} object.
     */
    House(Meter batteryMeter, Meter waterMeter) {
        this.waterMeter = waterMeter;
        this.electricMeter = batteryMeter;
        this.appliancesHouse = new ArrayList<>();
    }


    /**
     * Adds a water {@link Appliance} object to the
     * {@code House} setting the appropriate
     * {@link Meter} to it.
     *
     * @param a the appliance to add to the house.
     */
    public void addWaterAppliance(Appliance a){
        appliancesHouse.add(a);
        a.setMeter(waterMeter);

    }

    /**
     * Adds a water {@link Appliance} object to the
     * {@code House} setting the appropriate
     * {@link Meter} to it.
     *
     * @param a the appliance to add the house
     */
    public void addElectricAppliance(Appliance a) {
        appliancesHouse.add(a);
        a.setMeter(electricMeter);
    }

    /**
     * Removes an {@link Appliance} object from
     * the {@code House}.
     *
     * @param a the appliance to remove.
     */
    public void removeAppliance(Appliance a) {
        appliancesHouse.remove(a);
    }

    /**
     * Returns the current number of appliances in the house
     *
     * @return the number of appliances
     */
    public int numAppliances() {
        return appliancesHouse.size();
    }

    /**
     * Simulates one hour of consumption of every appliances,
     * by calling {@link Appliance#timePasses()} on each {@link Appliance}
     * contained in {@code appliancesHouse].
     *
     * Calculates the total cost of every appliances in one hour consumption.
     *
     * @return the total cost
     */
    public double activate() {
        double totalCost = 0;
        for (Appliance appliance : this.appliancesHouse) {
            appliance.timePasses();
        }
        totalCost += getTotalCost();
        return totalCost;
    }

    /**
     * Simulates n hours of consumption of every appliances,
     * by calling "n" times {@link Appliance#timePasses()} on each {@link Appliance}
     * contained in {@code appliancesHouse].
     *
     * @param     the hours to simulate the consumption
     * @return    the total cost of the entire simulation.
     */
    public double activate(int hours) {
        double totalCost = 0;
        int currentHour  = 0;
        int currentDay   = 1;

        for (int hour = 0; hour < hours; hour++) {
            if (currentHour != 24) {
                currentHour++;
            } else {
                currentHour = 1;
                currentDay++;
            }
            try {
                System.out.println("Day: " + currentDay);
                System.out.println("Time: " + currentHour + ":00");
                Thread.sleep(50);
                totalCost += activate();
                System.out.println();
            } catch (InterruptedException e) {
            }
        }

        if (totalCost < 0) {
            System.out.println(String.format("The total cost is: " + "%.2f", 0.0) + "GPB");
        } else {
            System.out.println(String.format("The total cost is: " + "%.2f", totalCost) + "GPB");
        }
        return totalCost;
    }


    /**
     * Returns the total cost calculated by adding values return by each
     * {@link Meter#report()}.
     *
     * @return the total cost
     */
    public double getTotalCost() {
        double cost = 0;

        System.out.format("+--------------+--------------+--------------+%n");
        System.out.format("|   Utility    |    Meter     |    Cost(GPB) |%n");
        System.out.format("+--------------+--------------+--------------+%n");

        // Prints out the correct format of the table
        if (electricMeter instanceof BatteryMeter) {
            cost = (waterMeter.report());
            System.out.format("+--------------+--------------+--------------+%n");
            cost += electricMeter.report();
        } else {
            cost = (waterMeter.report()) + (electricMeter.report());
            System.out.format("+--------------+--------------+--------------+%n");
        }
        return cost;
    }
}



