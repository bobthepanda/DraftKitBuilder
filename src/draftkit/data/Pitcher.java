package draftkit.data;

import java.text.DecimalFormat;

/**
 * Stores the unique characteristics for Pitchers and performs necessary
 * calculations.
 *
 * @author H2
 */
public class Pitcher extends Player {

    private double ip;
    private int er;
    private int w;
    private int h;

    /**
     * @return the ip
     */
    public double getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(double ip) {
        this.ip = ip;
    }

    /**
     * @return the er
     */
    public int getEr() {
        return er;
    }

    /**
     * @param er the er to set
     */
    public void setEr(int er) {
        this.er = er;
    }

    /**
     * @return the w
     */
    public int getW() {
        return w;
    }

    /**
     * @param w the w to set
     */
    public void setW(int w) {
        this.w = w;
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

    public void setSb_era() {
        if (ip != 0) {
            try {
                super.setSb_era(Double.parseDouble(new DecimalFormat("#.##").format(er * 9.00 / ip)));
            } catch (Exception e) {
                super.setSb_era(0);
            }
        } else {
            super.setSb_era(0);
        }
    }

    public void setBa_whip() {
        if (ip != 0) {
            try {
                super.setBa_whip(Double.parseDouble(new DecimalFormat("#.##").format((w + h) / ip)));
            } catch (Exception e) {
                super.setSb_era(0);
            }
        } else {
            super.setSb_era(0);
        }
    }
}
