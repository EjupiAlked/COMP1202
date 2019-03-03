import java.util.Random;
/**
 * This class represents a type of {@link Appliance}
 * object which the {@code House} can have.
 *
 * A {@code RandomVaries} object is switched on at
 * random times during the days (as per {@link CyclicVaries}),
 * and use a variable amount of resources in each hour that they
 * are switched on,(a random number within a min-max range).
 */

public class RandomVaries extends Appliance {

    private int    probability;
    private float  minUnits;
    private float  maxUnits;
    private Random random;

    /**
     * Creates a new {@code RandomVaries} appliance.
     *
     * @param name          the name of the appliance.
     * @param probability   the probability of being on.
     * @param minUnits      the min units that cab be consumed each hour.
     * @param maxUnits      the max units that cab be consumed each hour.
     */
    RandomVaries(String name, int probability, float minUnits, float maxUnits) {
        super(name);
        this.probability = probability;
        this.minUnits    = minUnits;
        this.maxUnits    = maxUnits;
        this.random      = new Random();
    }

    /**
     * {@inheritDoc}
     *
     * Consumes the random numbers of {@code randomUnits} depending
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

        float randomUnits       = generateRandomUnits(minUnits, maxUnits);
        int   randomProbability = random.nextInt(probability);

        if(randomProbability == 0){
            tellMeterToConsumeUnits(randomUnits);
        }
    }

}
