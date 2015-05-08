package draftkit.controller;

import draftkit.data.Draft;
import draftkit.data.DraftDataManager;
import draftkit.data.Player;
import draftkit.data.Team;
import draftkit.gui.GUI;
import java.util.concurrent.locks.ReentrantLock;
import javafx.concurrent.Task;

/**
 *
 * @author H2
 */
public class DraftController {

    boolean go;

    public DraftController() {
        go = true;
    }

    public void handleAutoAddRequest(GUI gui) {
        DraftDataManager dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();

        boolean added = draft.isTeamsFull();
        if (!added) {
            // GO THROUGH CURRENTLY NON-FULL TEAMS
            for (int i = 0; i < draft.getTeams().size() && !added; i++) {
                Team t = draft.getTeams().get(i);
                if (!t.isTeamFull()) {
                    for (int j = 0; j < draft.getPlayers().size() && !added; j++) {
                        // FIND AVAILABLE PLAYERS
                        Player p = draft.getPlayers().get(j);
                        // TEST ALL POSITIONS AND USE THE FIRST AVAILABLE ONE
                        for (int k = 0; k < p.getPositions().size() && !added; k++) {
                            String position = p.getPositions().get(k);
                            if (!t.isPositionFull(position)) {
                                p.setOldPosition(p.getPosition());
                                p.setTeam(t.getName());
                                p.setIndex(draft.getDraftPicks().size() + 1);
                                p.setPosition(position);
                                p.setContract("S2");
                                p.setSalary(1);
                                draft.removePlayer(p);
                                t.addPlayer(p);
                                draft.getDraftPicks().add(p);
                                added = true;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < draft.getTeams().size() && !added; i++) {
                Team t = draft.getTeams().get(i);
                if (!t.isTaxiFull()) {
                    if (!t.getPlayers().isEmpty()) {
                        Player p = draft.getPlayers().get(0);
                        p.setTeam(t.getName());
                        p.setIndex(draft.getDraftPicks().size() + 1);
                        p.setContract("X");
                        p.setSalary(0);
                        draft.removePlayer(p);
                        t.addPlayer(p);
                        draft.getDraftPicks().add(p);
                    }
                    added = true;
                }
            }
        }
        if (added) {
            gui.setDraftPickTable();
            gui.sortLineupTable();
            gui.updatePlayerTable();
        }
    }

    public void handleStartRequest(GUI gui) {
        go = true;
        DraftDataManager dataManager = gui.getDataManager();
        Draft draft = dataManager.getDraft();
        ReentrantLock progressLock = new ReentrantLock();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                while (!draft.isTeamsFull() & go) {
                    handleAutoAddRequest(gui);
                    try {
                        Thread.sleep(250);
                    } catch (Exception e) {
                    }
                }
                }
                finally {
                    progressLock.unlock();
                }
                return null;
            }
        };
        
        Thread thread = new Thread(task);
        thread.start();
    }
    
    public void handlePauseRequest(GUI gui) {
        go = false;
    }
}
        
        
