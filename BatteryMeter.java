/**
 * {@inheritDoc}
 *
 * This class works exactly as a {@link Meter} object, with the
 * additional feature to use a {@code Battery} object to cover
 * the consumption or to store the utility generated.
 *
 */
public class BatteryMeter extends Meter{
    private Battery battery;

    /**
     * {@inheritDoc}
     *
     * @param utilityName the utility name.
     * @param unitCost    the cost of the utility.
     * @param battery     the battery used by {@code BatteryMeter}.
     */
    BatteryMeter(String utilityName, double unitCost, Battery battery) {
        super(utilityName, unitCost);
        this.battery = battery;
    }

    /**
     *  This method is capable of recording and reporting the amount of power
     *  drawn from the battery and the total amount drawn from the mains and
     *  total cost of the consumption.
     *
     *  In addition to that, it stores or takes units to/from the {@code Battery}
     *  by checking whether the consumption is more than the production or vice versa.
     *
     * @return
     */
    @Override
    public double report(){
        float unitsToStore = 0;
        float unitsToTake  = 0;
        
        // case when production has exceeded the consumption
        if(this.getMeterReading() < 0){
            unitsToStore = battery.storeUnits(this.getMeterReading());
        }
        // case when consumption exceeds production
        else{
            unitsToTake =  battery.takeUnits(this.getMeterReading());
        }

        /**
         *  Update current value of {@code meterReading}
         */
        this.consumeUnits(-unitsToTake);
        this.consumeUnits(unitsToStore);

        double cost;
        float unitsDrawnFromMains;

        if(this.getMeterReading() < 0 ) {
            unitsDrawnFromMains = 0.0f;
            cost = 0.0f;
            this.setMeterReading(0.0f);
        }
        else{
            unitsDrawnFromMains = this.getMeterReading();
            cost = this.getUnitCost() * this.getMeterReading();
        }

        String format = "| %-12s | %-12s | %-12s | %-27s | %-29s | %-18s |%n";

        System.out.format(       "+--------------+--------------+--------------+-----------------------------+-------------------------------+--------------------+%n");
        System.out.format(       "| Utility name |    Meter     |   Cost(GPB)  | Units drawn from the mains  |  Units drawn from the battery |Units in the battery|%n");
        System.out.format(       "+--------------+--------------+--------------+-----------------------------+-------------------------------+--------------------+%n");
        System.out.format(format, getUtilityName(), getMeterReading(), String.format("%.2f", cost), unitsDrawnFromMains, unitsToTake, battery.getBatteryUnits() +
                                                                                                                                    "/" + battery.getBatteryCapacity() );
        System.out.format(       "+--------------+--------------+--------------+-----------------------------+-------------------------------+--------------------+%n");

        this.setMeterReading(0);
        return cost;
    }
}
