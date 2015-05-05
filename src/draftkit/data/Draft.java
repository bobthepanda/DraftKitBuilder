package draftkit.data;

import java.util.ArrayList;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class represents a draft to be edited and then used to generate a site.
 *
 * @author Henry Chin 109265023
 */
public class Draft {

    private ArrayList<Player> players;
    private ArrayList<Hitter> hitters;
    private ArrayList<Pitcher> pitchers;
    private ArrayList<Team> teams;

    public Draft(ArrayList<Hitter> givenHitters, ArrayList<Pitcher> givenPitchers) {
        hitters = givenHitters;
        pitchers = givenPitchers;
        players = new ArrayList<Player>();
        for (Hitter h : hitters) {
            players.add(h);
        }
        for (Pitcher p : pitchers) {
            players.add(p);
        }
        teams = new ArrayList<Team>();
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getHittersPosition(String s) {
        ArrayList<Player> hittersWithPosition = new ArrayList<Player>();
        if (s.equals("MI")) {
            for (Hitter h : hitters) {
                if (h.getPositions() == null) {
                } else if (h.getPositions().contains("2B") || h.getPositions().contains("SS")) {
                    hittersWithPosition.add(h);
                }
            }
        } else if (s.equals("CI")) {
            for (Hitter h : hitters) {
                if (h.getPositions() == null) {
                } else if (h.getPositions().contains("1B") || h.getPositions().contains("3B")) {
                    hittersWithPosition.add(h);
                }
            }
        } else if (s.equals("U")) {
            for (Hitter h : hitters) {
                hittersWithPosition.add(h);
            }
        } else {
            for (Hitter h : hitters) {
                if (h.getPositions() == null) {
                } else if (h.getPositions().contains(s)) {
                    hittersWithPosition.add(h);
                }
            }
        }
        return hittersWithPosition;
    }

    /**
     * @return the hitters
     */
    public ArrayList<Hitter> getHitters() {
        return hitters;
    }

    /**
     * @param hitters the hitters to set
     */
    public void setHitters(ArrayList<Hitter> hitters) {
        this.hitters = hitters;
        setHitterEstValues();
    }

    /**
     * @return the pitchers
     */
    public ArrayList<Pitcher> getPitchers() {
        return pitchers;
    }

    public ArrayList<Player> getPitcherPlayers() {
        ArrayList<Player> pitcherPlayers = new ArrayList<Player>();
        for (Pitcher p : pitchers) {
            pitcherPlayers.add(p);
        }
        return pitcherPlayers;
    }

    /**
     * @param pitchers the pitchers to set
     */
    public void setPitchers(ArrayList<Pitcher> pitchers) {
        this.pitchers = pitchers;
        setPitcherEstValues();
    }

    public void addPitcher(Pitcher p) {
        pitchers.add(p);
        players.add(p);
    }

    public void addHitter(Hitter h) {
        hitters.add(h);
        players.add(h);
    }

    public void removePlayer(Player p) {
        if (p instanceof Hitter) {
            hitters.remove((Hitter) p);
        } else if (p instanceof Pitcher) {
            pitchers.remove((Pitcher) p);
        }
        players.remove(p);
        setHitterEstValues();
        setPitcherEstValues();
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
        setHitterEstValues();
        setPitcherEstValues();
        setTeamPoints();
    }

    public void addTeam(Team t) {
        teams.add(t);
        setHitterEstValues();
        setPitcherEstValues();
    }

    public void removeTeam(Team t) {
        teams.remove(t);
        setHitterEstValues();
        setPitcherEstValues();
    }

    public Team getTeam(String s) {
        for (Team t : teams) {
            if (t.getName().equals(s)) {
                return t;
            }
        }
        return null;
    }

    public void addPlayer(Player p) {
        if (p instanceof Hitter) {
            addHitter((Hitter) p);
        } else if (p instanceof Pitcher) {
            addPitcher((Pitcher) p);
        }
        setHitterEstValues();
        setPitcherEstValues();
    }

    public int getCashLeft() {
        int cash = 0;
        for (Team t : teams) {
            cash += t.getCash();
        }
        return cash;
    }

    public void setHitterEstValues() {
        if (teams.size() > 0 && pitchers.size() > 0) {
            ObservableList<Hitter> tempHitters = FXCollections.observableArrayList(hitters);
            for (Hitter h : tempHitters) {
                h.setRank(0);
            }
            FXCollections.sort(tempHitters, (Hitter a, Hitter b) -> {
                if (a.getR_w() < b.getR_w()) {
                    return -1;
                } else if (a.getR_w() > b.getR_w()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            for (int i = 0; i < tempHitters.size(); i++) {
                tempHitters.get(i).setRank(tempHitters.get(i).getRank() + tempHitters.size() - i);
            }
            FXCollections.sort(tempHitters, (Hitter a, Hitter b) -> {
                if (a.getHr_sv() < b.getHr_sv()) {
                    return -1;
                } else if (a.getHr_sv() > b.getHr_sv()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            for (int i = 0; i < tempHitters.size(); i++) {
                tempHitters.get(i).setRank(tempHitters.get(i).getRank() + tempHitters.size() - i);
            }
            FXCollections.sort(tempHitters, (Hitter a, Hitter b) -> {
                if (a.getRbi_k() < b.getRbi_k()) {
                    return -1;
                } else if (a.getRbi_k() > b.getRbi_k()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            for (int i = 0; i < tempHitters.size(); i++) {
                tempHitters.get(i).setRank(tempHitters.get(i).getRank() + tempHitters.size() - i);
            }
            FXCollections.sort(tempHitters, (Hitter a, Hitter b) -> {
                if (a.getSb_era() < b.getSb_era()) {
                    return -1;
                } else if (a.getSb_era() > b.getSb_era()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            for (int i = 0; i < tempHitters.size(); i++) {
                tempHitters.get(i).setRank(tempHitters.get(i).getRank() + tempHitters.size() - i);
            }
            FXCollections.sort(tempHitters, (Hitter a, Hitter b) -> {
                if (a.getBa_whip() < b.getBa_whip()) {
                    return -1;
                } else if (a.getBa_whip() > b.getBa_whip()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            for (int i = 0; i < tempHitters.size(); i++) {
                tempHitters.get(i).setRank(tempHitters.get(i).getRank() + tempHitters.size() - i);
                tempHitters.get(i).setRank(tempHitters.get(i).getRank() / 5);
            }

            int hittersNeeded = 0;
            for (Team t : teams) {
                hittersNeeded += t.getHittersNeeded();
            }

            int medianSalary = getCashLeft() / (2 * hittersNeeded);
            for (Hitter h : tempHitters) {
                h.setEstimatedValue(medianSalary * hittersNeeded * 2 / h.getRank());
            }
        } else {
            for (Hitter h : hitters) {
                h.setEstimatedValue(0);
            }
        }
    }

    public void setPitcherEstValues() {
        if (teams.size() > 0 && pitchers.size() > 0) {
            ObservableList<Pitcher> tempPitchers = FXCollections.observableArrayList(pitchers);
            for (Pitcher p : tempPitchers) {
                p.setRank(0);
            }
            FXCollections.sort(tempPitchers, (Pitcher a, Pitcher b) -> {
                if (a.getR_w() < b.getR_w()) {
                    return -1;
                } else if (a.getR_w() > b.getR_w()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            for (int i = 0; i < tempPitchers.size(); i++) {
                tempPitchers.get(i).setRank(tempPitchers.get(i).getRank() + tempPitchers.size() - i);
            }
            FXCollections.sort(tempPitchers, (Pitcher a, Pitcher b) -> {
                if (a.getHr_sv() < b.getHr_sv()) {
                    return -1;
                } else if (a.getHr_sv() > b.getHr_sv()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            for (int i = 0; i < tempPitchers.size(); i++) {
                tempPitchers.get(i).setRank(tempPitchers.get(i).getRank() + tempPitchers.size() - i);
            }
            FXCollections.sort(tempPitchers, (Pitcher a, Pitcher b) -> {
                if (a.getRbi_k() < b.getRbi_k()) {
                    return -1;
                } else if (a.getRbi_k() > b.getRbi_k()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            for (int i = 0; i < tempPitchers.size(); i++) {
                tempPitchers.get(i).setRank(tempPitchers.get(i).getRank() + tempPitchers.size() - i);
            }
            FXCollections.sort(tempPitchers, (Pitcher a, Pitcher b) -> {
                if (a.getSb_era() < b.getSb_era()) {
                    return -1;
                } else if (a.getSb_era() > b.getSb_era()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            for (int i = 0; i < tempPitchers.size(); i++) {
                tempPitchers.get(i).setRank(tempPitchers.get(i).getRank() + tempPitchers.size() - i);
            }
            FXCollections.sort(tempPitchers, (Pitcher a, Pitcher b) -> {
                if (a.getBa_whip() < b.getBa_whip()) {
                    return -1;
                } else if (a.getBa_whip() > b.getBa_whip()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            for (int i = 0; i < tempPitchers.size(); i++) {
                tempPitchers.get(i).setRank(tempPitchers.get(i).getRank() + tempPitchers.size() - i);
                tempPitchers.get(i).setRank(tempPitchers.get(i).getRank() / 5);
            }

            int pitchersNeeded = 0;
            for (Team t : teams) {
                pitchersNeeded += t.getPitchersNeeded();
            }

            int medianSalary = getCashLeft() / (2 * pitchersNeeded);
            for (Pitcher h : tempPitchers) {
                h.setEstimatedValue(medianSalary * pitchersNeeded * 2 / h.getRank());
            }
        } else {
            for (Pitcher p : pitchers) {
                p.setEstimatedValue(0);
            }
        }
    }

    public ObservableList<Player> getProTeam(String s) {
        ObservableList<Player> proTeam = FXCollections.observableArrayList(new ArrayList());
        for (Player p : players) {
            if (p.getProTeam().equals(s)) {
                proTeam.add(p);
            }
        }
        for (Team t : teams) {
            for (Player p : t.getPlayers()) {
                if (p.getProTeam().equals(s)) {
                    proTeam.add(p);
                }
            }
            for (Player p : t.getTaxi()) {
                if (p.getProTeam().equals(s)) {
                    proTeam.add(p);
                }
            }
        }
        return proTeam;
    }

    public void setTeamPoints() {
        ObservableList<Team> tempTeams = FXCollections.observableArrayList(teams);
        for (Team t : tempTeams) {
            t.setPoints(0);
        }
        FXCollections.sort(tempTeams, (Team a, Team b) -> {
            if (((DraftTeam) a).getR() < ((DraftTeam) b).getR()) {
                return -1;
            } else if (((DraftTeam) a).getR() > ((DraftTeam) b).getR()) {
                return 1;
            } else {
                return 0;
            }
        });
        for (int i = 0; i < tempTeams.size(); i++) {
            tempTeams.get(i).setPoints(tempTeams.get(i).getPoints() + tempTeams.size() - i);
        }
        FXCollections.sort(tempTeams, (Team a, Team b) -> {
            if (((DraftTeam) a).getHr() < ((DraftTeam) b).getHr()) {
                return -1;
            } else if (((DraftTeam) a).getHr() > ((DraftTeam) b).getHr()) {
                return 1;
            } else {
                return 0;
            }
        });
        for (int i = 0; i < tempTeams.size(); i++) {
            tempTeams.get(i).setPoints(tempTeams.get(i).getPoints() + tempTeams.size() - i);
        }
        FXCollections.sort(tempTeams, (Team a, Team b) -> {
            if (((DraftTeam) a).getRbi() < ((DraftTeam) b).getRbi()) {
                return -1;
            } else if (((DraftTeam) a).getRbi() > ((DraftTeam) b).getRbi()) {
                return 1;
            } else {
                return 0;
            }
        });
        for (int i = 0; i < tempTeams.size(); i++) {
            tempTeams.get(i).setPoints(tempTeams.get(i).getPoints() + tempTeams.size() - i);
        }
        FXCollections.sort(tempTeams, (Team a, Team b) -> {
            if (((DraftTeam) a).getSb() < ((DraftTeam) b).getSb()) {
                return -1;
            } else if (((DraftTeam) a).getSb() > ((DraftTeam) b).getSb()) {
                return 1;
            } else {
                return 0;
            }
        });
        for (int i = 0; i < tempTeams.size(); i++) {
            tempTeams.get(i).setPoints(tempTeams.get(i).getPoints() + tempTeams.size() - i);
        }
        FXCollections.sort(tempTeams, (Team a, Team b) -> {
            if (((DraftTeam) a).getBa() < ((DraftTeam) b).getBa()) {
                return -1;
            } else if (((DraftTeam) a).getBa() > ((DraftTeam) b).getBa()) {
                return 1;
            } else {
                return 0;
            }
        });
        for (int i = 0; i < tempTeams.size(); i++) {
            tempTeams.get(i).setPoints(tempTeams.get(i).getPoints() + tempTeams.size() - i);
        }
        FXCollections.sort(tempTeams, (Team a, Team b) -> {
            if (((DraftTeam) a).getW() < ((DraftTeam) b).getW()) {
                return -1;
            } else if (((DraftTeam) a).getW() > ((DraftTeam) b).getW()) {
                return 1;
            } else {
                return 0;
            }
        });
        for (int i = 0; i < tempTeams.size(); i++) {
            tempTeams.get(i).setPoints(tempTeams.get(i).getPoints() + tempTeams.size() - i);
        }
        FXCollections.sort(tempTeams, (Team a, Team b) -> {
            if (((DraftTeam) a).getSv() < ((DraftTeam) b).getSv()) {
                return -1;
            } else if (((DraftTeam) a).getSv() > ((DraftTeam) b).getSv()) {
                return 1;
            } else {
                return 0;
            }
        });
        for (int i = 0; i < tempTeams.size(); i++) {
            tempTeams.get(i).setPoints(tempTeams.get(i).getPoints() + tempTeams.size() - i);
        }
        FXCollections.sort(tempTeams, (Team a, Team b) -> {
            if (((DraftTeam) a).getK() < ((DraftTeam) b).getK()) {
                return -1;
            } else if (((DraftTeam) a).getK() > ((DraftTeam) b).getK()) {
                return 1;
            } else {
                return 0;
            }
        });
        for (int i = 0; i < tempTeams.size(); i++) {
            tempTeams.get(i).setPoints(tempTeams.get(i).getPoints() + tempTeams.size() - i);
        }
        FXCollections.sort(tempTeams, (Team a, Team b) -> {
            if (((DraftTeam) a).getEra() < ((DraftTeam) b).getEra()) {
                return -1;
            } else if (((DraftTeam) a).getEra() > ((DraftTeam) b).getEra()) {
                return 1;
            } else {
                return 0;
            }
        });
        for (int i = 0; i < tempTeams.size(); i++) {
            tempTeams.get(i).setPoints(tempTeams.get(i).getPoints() + tempTeams.size() - i);
        }
        FXCollections.sort(tempTeams, (Team a, Team b) -> {
            if (((DraftTeam) a).getWhip() < ((DraftTeam) b).getWhip()) {
                return -1;
            } else if (((DraftTeam) a).getWhip() > ((DraftTeam) b).getWhip()) {
                return 1;
            } else {
                return 0;
            }
        });
        for (int i = 0; i < tempTeams.size(); i++) {
            tempTeams.get(i).setPoints(tempTeams.get(i).getPoints() + tempTeams.size() - i);
        }
    }
}
