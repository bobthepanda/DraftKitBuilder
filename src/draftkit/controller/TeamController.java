package draftkit.controller;

import static draftkit.DraftKit_PropertyType.REMOVE_ITEM_MESSAGE;
import draftkit.data.Draft;
import draftkit.data.DraftDataManager;
import draftkit.data.Team;
import draftkit.gui.GUI;
import draftkit.gui.MessageDialog;
import draftkit.gui.TeamDialog;
import draftkit.gui.YesNoCancelDialog;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Henry Chin
 */
public class TeamController {

    TeamDialog td;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;

    public TeamController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        td = new TeamDialog(initPrimaryStage, draft, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

    // THESE ARE FOR LECTURES
    public void handleAddTeamRequest(GUI gui) {
        DraftDataManager cdm = gui.getDataManager();
        Draft draft = cdm.getDraft();
        td.showAddTeamDialog();

        // DID THE USER CONFIRM?
        if (td.wasCompleteSelected()) {
            // GET THE LECTURE
            Team t = td.getTeam();

            // AND ADD IT AS A ROW TO THE TABLE
            draft.addTeam(t);
            
            // AND ALLOW SAVING
            gui.getFileController().markAsEdited(gui);
        } else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }

    public void handleEditTeamRequest(GUI gui, Team teamToEdit) {
        DraftDataManager cdm = gui.getDataManager();
        Draft draft = cdm.getDraft();
        td.showEditTeamDialog(teamToEdit);

        // DID THE USER CONFIRM?
        if (td.wasCompleteSelected()) {
            // UPDATE THE LECTURE
            Team t = td.getTeam();
            teamToEdit.setName(t.getName());
            teamToEdit.setOwner(t.getOwner());
            draft.getTeams().set(draft.getTeams().indexOf(teamToEdit), teamToEdit);
            
            // AND ALLOW SAVING
            gui.getFileController().markAsEdited(gui);
        } else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }

    public void handleRemoveTeamRequest(GUI gui, Team teamToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));

        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();
        
        // AND ALLOW SAVING
        gui.getFileController().markAsEdited(gui);

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) {
            gui.getDataManager().getDraft().removeTeam(teamToRemove);
        }
    }
}
