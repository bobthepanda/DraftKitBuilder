package draftkit.data;

import java.util.ArrayList;
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
                if (h.getPositions().contains("2B") || h.getPositions().contains("SS")) {
                    hittersWithPosition.add(h);
                }
            }
        } else if (s.equals("CI")) {
            for (Hitter h : hitters) {
                if (h.getPositions().contains("1B") || h.getPositions().contains("3B")) {
                    hittersWithPosition.add(h);
                }
            }
        } else if (s.equals("U")) {
            for (Hitter h : hitters) {
                hittersWithPosition.add(h);
            }
        } else {
            for (Hitter h : hitters) {
                if (h.getPositions().contains(s)) {
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
        } else {
            pitchers.remove((Pitcher) p);
        }
        players.remove(p);
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
}
