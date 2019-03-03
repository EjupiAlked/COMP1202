/**
 *  This class represents an object that manages the consumption and
 *  production of a particular utility. Utilities are measures in units.
 *
 */
public class Meter{

     private String utility;
     private double unitCost;
     private float  meterReading;

    /**
     *  Instantiate a new meter
     *
     * @param utility the utility name
     * @param unitCost the cost of one unit
     */
    Meter(String utility, double unitCost) {
        this.utility      =  utility;
        this.unitCost     =  unitCost;
        this.meterReading =  0;
    }

    /**
     * Returns the cost of the utility per unit.
     * @return the utility cost.
     */
    public double getUnitCost() {
        return unitCost;
    }

    /**
     * Returns the number of units consumed by different
     * appliances attached to the same {@code Meter} object.
     *
     * @return {@code meterReading}
     */
    public float getMeterReading() {
        return meterReading;
    }

    /**
     * Sets the {@code meterReading} to a specific value passed.
     */
    public void setMeterReading(float meterReading) {
        this.meterReading = meterReading;
    }

    /**
     * Returns the utility name of this {@code Meter} object.
     *
     * @return {@code utility} name.
     */
    public String getUtilityName() {
        return utility;
    }

    /**
     * This method is used to consumed units
     * in the {@code Meter} updating {@code meterReading} property.
     *
     * @param units the {@code units} to consume
     */
    public void consumeUnits(double units){
        this.meterReading += units;
    }

    /**
     * Prints out the total cost of hour consumption
     * and the current {@code meterReading}.
     *
     * @return the cost of the {@code Meter} consumption
     */
    public double report(){
        String leftAlignFormat = "| %-12s | %-12s | %-12s | %n";

        double cost = unitCost * this.meterReading;

        if(this.meterReading < 0){
            System.out.format(leftAlignFormat, utility, 0.0f, 0.0f);
        }else{
            System.out.format(leftAlignFormat, utility, meterReading,  String.format("%.2f", cost));
        }
		
        this.meterReading = 0;
        return cost;
    }
}
