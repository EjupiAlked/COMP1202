import java.util.Random;
/**
 * This class represents a type of {@link Appliance}
 * object which the {@code House} can have.
 *
 * A {@code RandomFixed} object is switched on at random
 * hours during the day, each has a probability of being on
 * during any time the {@link #timePasses()}, method is called.
 * This appliance consumes a fixed amount of resources when
 * it is switched on like a {@link CyclicFixed} does.

 */
public class RandomFixed extends Appliance{
    private float unitsFixed;
    private int   probability;

    /**
     * Creates a new {@code RandomFixed} appliance.
     *
     * @param name        the name of the appliance.
     * @param unitsFixed  the units consumed each hour.
     * @param probability the probability of being on.
     */
    RandomFixed(String name, float unitsFixed, int probability) {
        super(name);
        this.unitsFixed  = unitsFixed;
        this.probability = probability;
    }

    /**
     * {@inheritDoc}
     *
     * Consumes the same number of {@code unitsFixed} depending
     * on the random probability generated ({@code randomProbability}).
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

        int randomProbability = new Random().nextInt(probability);

        if(randomProbability == 0){
            tellMeterToConsumeUnits(unitsFixed);
        }
    }
}
