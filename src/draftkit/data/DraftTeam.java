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
    private int c;
    private int ci;
    private int oneB;
    private int twoB;
    private int threeB;
    private int mi;
    private int ss;
    private int u;
    private int of;

    public DraftTeam() {
        setCash(260);
        setPlayersNeeded(31);
        hitters = FXCollections.observableArrayList(new ArrayList<Hitter>());
        pitchers = FXCollections.observableArrayList(new ArrayList<Pitcher>());
        taxi = FXCollections.observableArrayList(new ArrayList<Player>());
        lineup = FXCollections.observableArrayList(new ArrayList<Player>());
        c = 2;
        ci = 1;
        oneB = 1;
        twoB = 1;
        threeB = 1;
        mi = 1;
        ss = 1;
        u = 1;
        of = 5;
    }

    public boolean isPositionFull(String s) {
        if (s.equals("C")) {
            return getC() == 0;
        } else if (s.equals("CI")) {
            return getCi() == 0;
        } else if (s.equals("1B")) {
            return getOneB() == 0;
        } else if (s.equals("2B")) {
            return getTwoB() == 0;
        } else if (s.equals("3B")) {
            return getThreeB() == 0;
        } else if (s.equals("MI")) {
            return getMi() == 0;
        } else if (s.equals("SS")) {
            return getSs() == 0;
        } else if (s.equals("U")) {
            return getU() == 0;
        } else if (s.equals("OF")) {
            return getOf() == 0;
        }
        return true;
    }

    public void changePositionNum(String s, int i) {
        switch (s) {
            case "C":
                setC(getC() + i);
                break;
            case "CI":
                setCi(getCi() + i);
                break;
            case "1B":
                setOneB(getOneB() + i);
                break;
            case "2B":
                setTwoB(getTwoB() + i);
                break;
            case "3B":
                setThreeB(getThreeB() + i);
                break;
            case "MI":
                setMi(getMi() + i);
                break;
            case "SS":
                setSs(getSs() + i);
                break;
            case "U":
                setU(getU() + i);
                break;
            case "OF":
                setOf(getOf() + i);
                break;
        }
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
                changePositionNum(p.getPosition(), 1);
            } else if (!isTaxiFull()) {
                getTaxi().add(p);
                added = true;
            }
        }
        if (added) {
            getLineup().add(p);
            changePositionNum(p.getPosition(), -1);
            setPlayersNeeded(getPlayersNeeded() - 1);
            updateTeamStats();
        }
        return added;
    }

    public void removePlayer(Player p) {
        if (getHitters().contains((Hitter) p)) {
            getHitters().remove((Hitter) p);
        } else if (getPitchers().contains((Pitcher) p)) {
            getPitchers().remove((Pitcher) p);
        } else if (getTaxi().contains(p)) {
            getTaxi().remove(p);
        }
        if (getLineup().contains(p)) {
            getLineup().remove(p);
            changePositionNum(p.getPosition(), 1);
        }
    }

    //HELPER METHODS
    private boolean isHittersFull() {
        return getHitters().size() >= 14;
    }

    private boolean isPitchersFull() {
        return getPitchers().size() >= 9;
    }

    private boolean isTaxiFull() {
        return getTaxi().size() >= 8;
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
        setR(0);
        for (Hitter h : getHitters()) {
            setR(getR() + h.getR_w());
        }
    }

    /**
     * @return the hr
     */
    public int getHr() {
        return hr;
    }

    public void setHr() {
        setHr(0);
        for (Hitter h : getHitters()) {
            setHr(getHr() + h.getHr_sv());
        }
    }

    public int getRbi() {
        return rbi;
    }

    public void setRbi() {
        setRbi(0);
        for (Hitter h : getHitters()) {
            setRbi(getRbi() + h.getRbi_k());
        }
    }

    /**
     * @return the sb
     */
    public int getSb() {
        return sb;
    }

    public void setSb() {
        setSb(0);
        for (Hitter h : getHitters()) {
            setSb((int) (getSb() + h.getSb_era()));
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
        for (Hitter h : getHitters()) {
            hits += h.getH();
            ab += h.getAb();
        }
        if (ab != 0) {
            setBa((hits * 1.00) / ab);
        }
    }

    /**
     * @return the w
     */
    public int getW() {
        return w;
    }

    public void setW() {
        setW(0);
        for (Pitcher p : getPitchers()) {
            setW(getW() + p.getR_w());
        }
    }

    /**
     * @return the k
     */
    public int getK() {
        return k;
    }

    public void setK() {
        setK(0);
        for (Pitcher p : getPitchers()) {
            setK(getK() + p.getRbi_k());
        }
    }

    /**
     * @return the sv
     */
    public int getSv() {
        return sv;
    }

    public void setSv() {
        setSv(0);
        for (Pitcher p : getPitchers()) {
            setSv(getSv() + p.getHr_sv());
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
        for (Pitcher p : getPitchers()) {
            er += p.getEr();
            ip += p.getIp();
        }
        if (ip != 0) {
            setEra((er * 1.00) / ip);
        }
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
        for (Pitcher p : getPitchers()) {
            walks += p.getW();
            h += p.getH();
            ip += p.getIp();
        }
        if (ip != 0) {
            setWhip(((h + walks) * 1.00) / ip);
        }
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

    /**
     * @param r the r to set
     */
    public void setR(int r) {
        this.r = r;
    }

    /**
     * @param hr the hr to set
     */
    public void setHr(int hr) {
        this.hr = hr;
    }

    /**
     * @param rbi the rbi to set
     */
    public void setRbi(int rbi) {
        this.rbi = rbi;
    }

    /**
     * @param sb the sb to set
     */
    public void setSb(int sb) {
        this.sb = sb;
    }

    /**
     * @param ba the ba to set
     */
    public void setBa(double ba) {
        this.ba = ba;
    }

    /**
     * @param w the w to set
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * @param k the k to set
     */
    public void setK(int k) {
        this.k = k;
    }

    /**
     * @param sv the sv to set
     */
    public void setSv(int sv) {
        this.sv = sv;
    }

    /**
     * @param era the era to set
     */
    public void setEra(double era) {
        this.era = era;
    }

    /**
     * @param whip the whip to set
     */
    public void setWhip(double whip) {
        this.whip = whip;
    }

    /**
     * @return the c
     */
    public int getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(int c) {
        this.c = c;
    }

    /**
     * @return the ci
     */
    public int getCi() {
        return ci;
    }

    /**
     * @param ci the ci to set
     */
    public void setCi(int ci) {
        this.ci = ci;
    }

    /**
     * @return the oneB
     */
    public int getOneB() {
        return oneB;
    }

    /**
     * @param oneB the oneB to set
     */
    public void setOneB(int oneB) {
        this.oneB = oneB;
    }

    /**
     * @return the twoB
     */
    public int getTwoB() {
        return twoB;
    }

    /**
     * @param twoB the twoB to set
     */
    public void setTwoB(int twoB) {
        this.twoB = twoB;
    }

    /**
     * @return the threeB
     */
    public int getThreeB() {
        return threeB;
    }

    /**
     * @param threeB the threeB to set
     */
    public void setThreeB(int threeB) {
        this.threeB = threeB;
    }

    /**
     * @return the mi
     */
    public int getMi() {
        return mi;
    }

    /**
     * @param mi the mi to set
     */
    public void setMi(int mi) {
        this.mi = mi;
    }

    /**
     * @return the ss
     */
    public int getSs() {
        return ss;
    }

    /**
     * @param ss the ss to set
     */
    public void setSs(int ss) {
        this.ss = ss;
    }

    /**
     * @return the u
     */
    public int getU() {
        return u;
    }

    /**
     * @param u the u to set
     */
    public void setU(int u) {
        this.u = u;
    }

    /**
     * @return the of
     */
    public int getOf() {
        return of;
    }

    /**
     * @param of the of to set
     */
    public void setOf(int of) {
        this.of = of;
    }
    
    public int getHittersNeeded() {
        return 14 - hitters.size();
    }
    
    public int getPitchersNeeded() {
        return 9 - pitchers.size();
    }
}
