/**
 * This class represents a type of {@link Appliance}
 * object which the {@code House} can have.
 *
 * A {@code CyclicVaries} object is switched on for a
 * set cycle, i.e. a fixed number of hours each day
 * (as per {@link CyclicFixed}, but use a variable amount
 * of resources in each hour that they are switched on
 * (a random number within a min-max range).
 *
 *
 */
public class CyclicVaries extends Appliance {

    private int    cycleLength;
    private float  minUnits;
    private float  maxUnits;
    private int    currentTime;

    /**
     * Creates a new {@code CyclicFixed} appliance.
     *
     * The {@code cycleLength} property accepts only values
     * between 1 and 24, it will be assigned no value otherwise.
     *
     *
     * @param name        the name of the appliance.
     * @param cycleLength the number of hours in which the appliance is on.
     * @param minUnits    the min units that cab be consumed each hour.
     * @param maxUnits    the max units that cab be consumed each hour.
     */
    CyclicVaries(String name, int cycleLength, float minUnits, float maxUnits) {
        super(name);

        if(cycleLength >= 1 && cycleLength <= 24){
            this.cycleLength = cycleLength;
        }

        this.minUnits    = minUnits;
        this.maxUnits    = maxUnits;
        this.currentTime = 0;
    }

    /**
     * {@inheritDoc}
     *
     * Sets {@code currentTime} to 1 when reaches 24 hours,
     * increments the value by one 1 hour otherwise.
     *
     * Consumes {@ccode randomUnits} only for the specified
     * {@code cycleLength}.
     *
     */
    @Override
    public void timePasses() {
        try {
            checkMeterSet();
        } catch (Exception e) {
            System.out.print(e.getMessage());
            System.exit(1);
        }
        float randomUnits = generateRandomUnits(minUnits, maxUnits);

        if(currentTime != 24){
            currentTime++;
        }else{
            currentTime = 1;
        }

        if(currentTime <= cycleLength) {
            tellMeterToConsumeUnits(randomUnits);
        }
    }
}
