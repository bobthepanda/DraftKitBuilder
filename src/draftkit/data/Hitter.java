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

    public void setBa_whip() {
        if (ab != 0) {
            try {
                super.setBa_whip(Double.parseDouble(new DecimalFormat("#.##").format((h * 1.000) / ab)));
            } catch (Exception e) {
                super.setBa_whip(0);
            }
        } else {
            super.setBa_whip(0);
        }
    }
}
