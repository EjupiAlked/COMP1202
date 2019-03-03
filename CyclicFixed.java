/**
 * This class represents a type of {@link Appliance}
 * object which the {@code House} can have.
 *
 * A {@code CyclicFixed} object is switched on for a set cycle,
 * i.e. number of hours each day ({@code cycleLength} and uses
 * a fixed amount of resource each hour ({@code unitsFixed}).
 *
 */
public class CyclicFixed extends Appliance {

    private float unitsFixed;
    private int   cycleLength;
    private int   currentTime;

    /**
     * Creates a new {@code CyclicFixed} appliance.
     *
     * The {@code cycleLength} property accepts only values
     * between 1 and 24, it will be assigned no value otherwise.
     *
     *
     * @param name        the name of the appliance.
     * @param unitsFixed  the units consumed each hour.
     * @param cycleLength the number of hours in which the appliance is on.
     */
    CyclicFixed(String name, float unitsFixed, int cycleLength) {
        super(name);
        this.unitsFixed = unitsFixed;

        if(cycleLength >= 1 && cycleLength <= 24){
            this.cycleLength = cycleLength;
        }

        currentTime = 0;
    }

    /**
     * {@inheritDoc}
     *
     * Sets {@code currentTime} to 1 when reaches 24 hours,
     * increments the value by one 1 hour otherwise.
     * @code cycleLength}.
     *
     * Consumes the same number of {@code unitsFixed}.
     */
    @Override
    public void timePasses() {

        try {
            checkMeterSet();
        } catch (Exception e) {
            System.out.print(e.getMessage());
            System.exit(1);
        }

        if(currentTime != 24){
            currentTime++;
        }else{
            currentTime = 1;
        }

        if(currentTime <= cycleLength) {
            tellMeterToConsumeUnits(unitsFixed);
        }
    }
}
