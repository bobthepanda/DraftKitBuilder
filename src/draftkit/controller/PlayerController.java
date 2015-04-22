package draftkit.controller;

import static draftkit.DraftKit_PropertyType.REMOVE_ITEM_MESSAGE;
import draftkit.data.Draft;
import draftkit.data.DraftDataManager;
import draftkit.data.Player;
import draftkit.gui.GUI;
import draftkit.gui.MessageDialog;
import draftkit.gui.PlayerDialog;
import draftkit.gui.YesNoCancelDialog;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author McKillaGorilla
 */
public class PlayerController {
    PlayerDialog pd;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public PlayerController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        pd = new PlayerDialog(initPrimaryStage, draft, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

    // THESE ARE FOR SCHEDULE ITEMS
    
    public void handleAddPlayerRequest(GUI gui) {
        DraftDataManager cdm = gui.getDataManager();
        Draft draft = cdm.getDraft();
        pd.showAddPlayerDialog();
        
        // DID THE USER CONFIRM?
        if (pd.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Player p = pd.getPlayer();
            
            // AND ADD IT AS A ROW TO THE TABLE
            draft.addPlayer(p);
            
            // AND ALLOW SAVING
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
    
    /*public void handleEditPlayerRequest(GUI gui, Player itemToEdit) {
        DraftDataManager cdm = gui.getDataManager();
        Draft draft = cdm.getDraft();
        pd.showEditPlayerDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (pd.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Player p = pd.getPlayer();
            itemToEdit.setDescription(p.getDescription());
            itemToEdit.setDate(p.getDate());
            itemToEdit.setLink(p.getLink());
            
            // AND ALLOW SAVING
            gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }*/
    
    public void handleRemovePlayerRequest(GUI gui, Player itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getDraft().removePlayer(itemToRemove);
            
            // AND ALLOW SAVING
            gui.getFileController().markAsEdited(gui);
        }
    }
}