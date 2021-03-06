package draftkit.controller;

import static draftkit.DraftKit_PropertyType.REMOVE_ITEM_MESSAGE;
import draftkit.data.Draft;
import draftkit.data.DraftDataManager;
import draftkit.data.Player;
import draftkit.gui.GUI;
import draftkit.gui.MessageDialog;
import draftkit.gui.PlayerAddDialog;
import draftkit.gui.PlayerEditDialog;
import draftkit.gui.YesNoCancelDialog;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author McKillaGorilla
 */
public class PlayerController {

    PlayerAddDialog pad;
    PlayerEditDialog ped;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;

    public PlayerController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        pad = new PlayerAddDialog(initPrimaryStage, draft, initMessageDialog);
        ped = new PlayerEditDialog(initPrimaryStage, draft, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

    // THESE ARE FOR PLAYERS
    public void handleAddPlayerRequest(GUI gui) {
        DraftDataManager cdm = gui.getDataManager();
        Draft draft = cdm.getDraft();
        pad.showAddPlayerDialog();

        // DID THE USER CONFIRM?
        if (pad.wasCompleteSelected()) {
            // GET THE PLAYER
            Player p = pad.getPlayer();

            // AND ADD IT AS A ROW TO THE TABLE
            draft.addPlayer(p);

            // AND ALLOW SAVING
            gui.getFileController().markAsEdited(gui);
        } else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }

    public void handleEditPlayerRequest(GUI gui, Player playerToEdit) {
        DraftDataManager cdm = gui.getDataManager();
        Draft draft = cdm.getDraft();
        String s = playerToEdit.getTeam();
        String oldContract = playerToEdit.getContract();
        playerToEdit.setOldPosition(playerToEdit.getPosition());
        ped.showEditPlayerDialog(playerToEdit);

        // DID THE USER CONFIRM?
        if (ped.wasCompleteSelected()) {
            // UPDATE THE PLAYER
            playerToEdit = ped.getPlayer();

            // MOVE PLAYER FILES IN THE MODEL
            if (s == null && playerToEdit.getTeam() == null) {
            } else if (s == null) {
                draft.removePlayer(playerToEdit);
                draft.getTeam(playerToEdit.getTeam()).addPlayer(playerToEdit);
                if (playerToEdit.getContract().equals("S2") || (playerToEdit.getContract().equals("X") && draft.getTeam(playerToEdit.getTeam()).getTaxi().contains(playerToEdit))) {
                    draft.getDraftPicks().add(playerToEdit);
                    playerToEdit.setIndex(draft.getDraftPicks().size());
                }
                draft.setTeamPoints();
            } else if (playerToEdit.getTeam() == null) {
                draft.getTeam(s).removePlayer(playerToEdit);
                if (draft.getDraftPicks().contains(playerToEdit)) {
                    draft.getDraftPicks().remove(playerToEdit);
                    for (int i = 0; i < draft.getDraftPicks().size(); i++) {
                        draft.getDraftPicks().get(i).setIndex(i + 1);
                    }
                }
                draft.addPlayer(playerToEdit);
            } else if (s.equals(playerToEdit.getTeam())) {
                if (oldContract.equals("S2") &&
                        (playerToEdit.getContract().equals("S1") ||
                        (playerToEdit.getContract().equals("X") && draft.getTeam(playerToEdit.getTeam()).getLineup().contains(playerToEdit)))) {
                    draft.getDraftPicks().remove(playerToEdit);
                    for (int i = 0; i < draft.getDraftPicks().size(); i++) {
                        draft.getDraftPicks().get(i).setIndex(i + 1);
                    }
                }
                else if (!oldContract.equals("S2") && playerToEdit.getContract().equals("S2")) {
                    playerToEdit.setIndex(draft.getDraftPicks().size() + 1);
                    draft.getDraftPicks().add(playerToEdit);
                }
            } else {
                draft.getTeam(s).removePlayer(playerToEdit);
                draft.getTeam(playerToEdit.getTeam()).addPlayer(playerToEdit);
                if (oldContract.equals("S2") &&
                        (playerToEdit.getContract().equals("S1") ||
                        (playerToEdit.getContract().equals("X") && draft.getTeam(playerToEdit.getTeam()).getLineup().contains(playerToEdit)))) {
                    draft.getDraftPicks().remove(playerToEdit);
                    for (int i = 0; i < draft.getDraftPicks().size(); i++) {
                        draft.getDraftPicks().get(i).setIndex(i + 1);
                    }
                }
                else if (!oldContract.equals("S2") && playerToEdit.getContract().equals("S2")) {
                    draft.getDraftPicks().add(playerToEdit);
                    playerToEdit.setIndex(draft.getDraftPicks().size());
                }
            }

            // AND ALLOW SAVING
            gui.updatePlayerTable();
            gui.updateLineupTable();
            gui.updateTaxiTable();
            gui.updateSummaryTable();
            draft.setTeamPoints();
            gui.getFileController().markAsEdited(gui);
        } else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }

    public void handleRemovePlayerRequest(GUI gui, Player playerToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));

        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) {
            gui.getDataManager().getDraft().removePlayer(playerToRemove);

            // AND ALLOW SAVING
            gui.getFileController().markAsEdited(gui);
        }
    }
}
