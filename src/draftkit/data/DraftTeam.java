package draftkit.data;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class represents a team of players.
 *
 * @author H2
 */
public class DraftTeam extends Team {

    private ObservableList<Hitter> hitters;
    private ObservableList<Pitcher> pitchers;
    private ObservableList<Player> taxi;
    private ObservableList<Player> lineup;
    private int r;
    private int hr;
    private int rbi;
    private int sb;
    private double ba;
    private int w;
    private int k;
    private int sv;
    private double era;
    private double whip;

    public DraftTeam() {
        setCash(260);
        setPlayersNeeded(31);
        hitters = FXCollections.observableArrayList(new ArrayList<Hitter>());
        pitchers = FXCollections.observableArrayList(new ArrayList<Pitcher>());
        taxi = FXCollections.observableArrayList(new ArrayList<Player>());
        lineup = FXCollections.observableArrayList(new ArrayList<Player>());
    }

    public boolean addPlayer(Player p) {
        boolean added = false;
        if (p instanceof Hitter) {
            if (!isHittersFull()) {
                getHitters().add((Hitter) p);
                added = true;
            } else if (!isTaxiFull()) {
                getTaxi().add(p);
                added = true;
            }
        } else {
            if (!isPitchersFull()) {
                getPitchers().add((Pitcher) p);
                added = true;
            } else if (!isTaxiFull()) {
                getTaxi().add(p);
                added = true;
            }
        }
        if (added) {
            lineup.add(p);
            setPlayersNeeded(getPlayersNeeded() - 1);
            updateTeamStats();
        }
        return added;
    }
    
    public void removePlayer(Player p) {
        if (hitters.contains((Hitter) p)) {
            hitters.remove((Hitter) p);
        }
        else if (pitchers.contains((Pitcher) p)) {
            pitchers.remove((Pitcher) p);
        }
        else if (taxi.contains(p)) {
            taxi.remove(p);
        }
        if (lineup.contains(p)) {
            lineup.remove(p);
        }
    }

    //HELPER METHODS
    private boolean isHittersFull() {
        return getHitters().size() > 14;
    }

    private boolean isPitchersFull() {
        return getPitchers().size() > 9;
    }

    private boolean isTaxiFull() {
        return getTaxi().size() > 8;
    }

    /*private boolean isValidPosition(String s){
        
     }*/
    /**
     * @return the hitters
     */
    public ObservableList<Hitter> getHitters() {
        return hitters;
    }

    /**
     * @param hitters the hitters to set
     */
    public void setHitters(ObservableList<Hitter> hitters) {
        this.hitters = hitters;
    }

    /**
     * @return the pitchers
     */
    public ObservableList<Pitcher> getPitchers() {
        return pitchers;
    }

    /**
     * @param pitchers the pitchers to set
     */
    public void setPitchers(ObservableList<Pitcher> pitchers) {
        this.pitchers = pitchers;
    }

    /**
     * @return the taxi
     */
    public ObservableList<Player> getTaxi() {
        return taxi;
    }

    /**
     * @param taxi the taxi to set
     */
    public void setTaxi(ObservableList<Player> taxi) {
        this.taxi = taxi;
    }

    /**
     * @return the r
     */
    public int getR() {
        return r;
    }

    public void setR() {
        r = 0;
        for (Hitter h : hitters) {
            r += h.getR_w();
        }
    }

    /**
     * @return the hr
     */
    public int getHr() {
        return hr;
    }

    public void setHr() {
        hr = 0;
        for (Hitter h : hitters) {
            hr += h.getHr_sv();
        }
    }

    public int getRbi() {
        return rbi;
    }

    public void setRbi() {
        rbi = 0;
        for (Hitter h : hitters) {
            rbi += h.getRbi_k();
        }
    }

    /**
     * @return the sb
     */
    public int getSb() {
        return sb;
    }

    public void setSb() {
        sb = 0;
        for (Hitter h : hitters) {
            sb += h.getSb_era();
        }
    }

    /**
     * @return the ba
     */
    public double getBa() {
        return ba;
    }

    public void setBa() {
        int hits = 0;
        int ab = 0;
        for (Hitter h : hitters) {
            hits += h.getH();
            ab += h.getAb();
        }
        ba = (hits * 1.00) / ab;
    }

    /**
     * @return the w
     */
    public int getW() {
        return w;
    }

    public void setW() {
        w = 0;
        for (Pitcher p : pitchers) {
            w += p.getR_w();
        }
    }

    /**
     * @return the k
     */
    public int getK() {
        return k;
    }

    public void setK() {
        k = 0;
        for (Pitcher p : pitchers) {
            k += p.getRbi_k();
        }
    }

    /**
     * @return the sv
     */
    public int getSv() {
        return sv;
    }

    public void setSv() {
        sv = 0;
        for (Pitcher p : pitchers) {
            sv += p.getHr_sv();
        }
    }

    /**
     * @return the era
     */
    public double getEra() {
        return era;
    }

    public void setEra() {
        int er = 0;
        double ip = 0;
        for (Pitcher p : pitchers) {
            er += p.getEr();
            ip += p.getIp();
        }
        era = (er * 1.00) / ip;
    }

    /**
     * @return the whip
     */
    public double getWhip() {
        return whip;
    }

    public void setWhip() {
        int walks = 0;
        int h = 0;
        double ip = 0;
        for (Pitcher p : pitchers) {
            walks += p.getW();
            h += p.getH();
            ip += p.getIp();
        }
        whip = ((h + walks) * 1.00) / ip;
    }

    private void updateTeamStats() {
        setBa();
        setEra();
        setHr();
        setK();
        setR();
        setRbi();
        setSb();
        setSv();
        setW();
        setWhip();
    }

    /**
     * @return the lineup
     */
    public ObservableList<Player> getLineup() {
        return lineup;
    }

    /**
     * @param lineup the lineup to set
     */
    public void setLineup(ObservableList<Player> lineup) {
        this.lineup = lineup;
    }
    
    public ObservableList<Player> getPlayers() {
        return getLineup();
    }
    
    public void setPlayers(ObservableList<Player> lineup) {
        setLineup(lineup);
    }
}
