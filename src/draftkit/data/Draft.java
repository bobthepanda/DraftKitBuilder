package draftkit.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class represents a draft to be edited and then used to generate a site.
 * 
 * @author Henry Chin 109265023
 */
public class Draft {
    private ObservableList<Player> players;
    private ObservableList<Hitter> hitters;
    private ObservableList<Pitcher> pitchers;
    private ObservableList<Team> teams;
    
    public Draft(ObservableList<Hitter> givenHitters, ObservableList<Pitcher> givenPitchers) {
        hitters = givenHitters;
        pitchers = givenPitchers;
        for (Hitter h: hitters) {
            players.add(h);
        }
        for (Pitcher p: pitchers) {
            players.add(p);
        }
        teams = FXCollections.observableArrayList();
    }

    /**
     * @return the players
     */
    public ObservableList<Player> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(ObservableList<Player> players) {
        this.players = players;
    }

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
            hitters.remove((Hitter)p);
        }
        else {
            pitchers.remove((Pitcher) p);
        }
        players.remove(p);
    }
    
    public ObservableList<Team> getTeams() {
        return teams;
    }
    
    public void setTeams(ObservableList<Team> teams) {
        this.teams = teams;
    }
}
