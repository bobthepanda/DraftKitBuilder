package draftkit.data;

import java.text.DecimalFormat;

/**
 * Stores the unique characteristics for Hitters and performs necessary
 * calculations.
 *
 * @author H2
 */
public class Hitter extends Player {

    private int ab;
    private int h;

    /**
     * @return the ab
     */
    public int getAb() {
        return ab;
    }

    /**
     * @param ab the ab to set
     */
    public void setAb(int ab) {
        this.ab = ab;
    }

    /**
     * @return the h
     */
    public int getH() {
        return h;
    }

    /**
     * @param h the h to set
     */
    public void setH(int h) {
        this.h = h;
    }

    public void setSb_era(double d) {
        super.setSb_era(d);
        setSb_era_String();
    }
    
    public void setSb_era_String() {
        super.setSb_era_String((int)super.getSb_era() + "");
    }
    
    public void setBa_whip() {
        if (ab != 0) {
            try {
                super.setBa_whip((h * 1.000) / ab);
            } catch (Exception e) {
                super.setBa_whip(0);
            }
        } else {
            super.setBa_whip(0);
        }
        setBa_whip_String();
    }
    
    public void setBa_whip_String() {
        super.setBa_whip_String(String.format("%.3f", getBa_whip()));
    }
}
