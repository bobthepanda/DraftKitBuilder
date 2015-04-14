/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draftkit.data;

/**
 *
 * @author H2
 */
public abstract class Team {

    private String name;
    private int cash;
    private int playersNeeded;
    private double points;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cash
     */
    public int getCash() {
        return cash;
    }

    /**
     * @param cash the cash to set
     */
    public void setCash(int cash) {
        this.cash = cash;
    }

    /**
     * @return the playersNeeded
     */
    public int getPlayersNeeded() {
        return playersNeeded;
    }

    /**
     * @param playersNeeded the playersNeeded to set
     */
    public void setPlayersNeeded(int playersNeeded) {
        this.playersNeeded = playersNeeded;
    }

    /**
     * @return the points
     */
    public double getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(double points) {
        this.points = points;
    }

}
