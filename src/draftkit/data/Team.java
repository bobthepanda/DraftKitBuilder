/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draftkit.data;

import javafx.collections.ObservableList;

/**
 *
 * @author H2
 */
public abstract class Team {

    private String name;
    private String owner;
    private int cash;
    private int playersNeeded;
    private int points;
    private int cashPP;

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
        setCashPP();
    }

    /**
     * @return the playersNeeded
     */
    public int getPlayersNeeded() {
        return playersNeeded;
    }
    
    public abstract int getHittersNeeded();
    public abstract int getPitchersNeeded();

    /**
     * @param playersNeeded the playersNeeded to set
     */
    public void setPlayersNeeded(int playersNeeded) {
        this.playersNeeded = playersNeeded;
        setCashPP();
    }

    /**
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public abstract boolean addPlayer(Player p);
    public abstract void removePlayer(Player p);
    public abstract ObservableList<Player> getPlayers();
    public abstract void setPlayers(ObservableList<Player> players);
    public abstract ObservableList<Player> getTaxi();
    public abstract void setTaxi(ObservableList<Player> players);
    public abstract boolean isPositionFull(String s);
    public abstract boolean isTeamFull();
    public abstract boolean isTaxiFull();

    /**
     * @return the cashPP
     */
    public int getCashPP() {
        return cashPP;
    }

    /**
     * @param cashPP the cashPP to set
     */
    public void setCashPP() {
        try {
            cashPP = cash / playersNeeded;
        }
        catch (Exception e) {
            cashPP = -1;
        }
    }
}
