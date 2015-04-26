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
        ped.showEditPlayerDialog(playerToEdit);

        // DID THE USER CONFIRM?
        if (ped.wasCompleteSelected()) {
            // UPDATE THE PLAYER
            Player p = ped.getPlayer();

            // MOVE PLAYER FILES IN THE MODEL
            if (s == null && p.getTeam() == null) {
            } else if (s == null) {
                draft.removePlayer(playerToEdit);
                draft.getTeam(p.getTeam()).addPlayer(playerToEdit);
            } else if (p.getTeam() == null) {
                draft.getTeam(s).removePlayer(playerToEdit);
                draft.addPlayer(playerToEdit);
            } else if (s.equals(p.getTeam())) {
            } else {
                draft.getTeam(s).removePlayer(playerToEdit);
                draft.getTeam(p.getTeam()).addPlayer(playerToEdit);
            }

            // SET VALUES FOR PLAYER
            playerToEdit.setTeam(p.getTeam());
            if (p.getTeam() != null) {
                playerToEdit.setPosition(p.getPosition());
                playerToEdit.setContract(p.getContract());
                playerToEdit.setSalary(p.getSalary());
                draft.getTeam(playerToEdit.getTeam()).setCash(draft.getTeam(playerToEdit.getTeam()).getCash() - playerToEdit.getSalary());
            } else {
                playerToEdit.setPosition(null);
                playerToEdit.setContract(null);
                playerToEdit.setSalary(0);
            }

            // AND ALLOW SAVING
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
