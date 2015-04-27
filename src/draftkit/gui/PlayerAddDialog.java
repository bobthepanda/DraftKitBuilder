package draftkit.gui;

import draftkit.DraftKit_PropertyType;
import draftkit.data.Draft;
import draftkit.data.Hitter;
import draftkit.data.Pitcher;
import draftkit.data.Player;
import static draftkit.gui.GUI.CLASS_GENERAL;
import static draftkit.gui.GUI.CLASS_HEADING_LABEL;
import static draftkit.gui.GUI.CLASS_PROMPT_LABEL;
import static draftkit.gui.GUI.PRIMARY_STYLE_SHEET;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author McKillaGorilla
 */
public class PlayerAddDialog extends Stage {

    // THIS IS THE OBJECT DATA BEHIND THIS UI
    Player player;

    // GUI CONTROLS FOR OUR DIALOG
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label proTeamLabel;
    ComboBox proTeamComboBox;
    CheckBox c;
    CheckBox oneB;
    CheckBox twoB;
    CheckBox threeB;
    CheckBox SS;
    CheckBox OF;
    CheckBox P;
    HBox first;
    HBox second;
    ArrayList<String> positions;
    String position;
    Button completeButton;
    Button cancelButton;

    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    String teams = "ATL,AZ,CHC,CIN,COL,LAD,MIA,MIL,NYM,PHI,PIT,SD,SF,STL,WAS";
    ArrayList<String> teamsList = new ArrayList<String>(Arrays.asList(teams.split(",")));

    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String FIRST_NAME_PROMPT = "First Name: ";
    public static final String LAST_NAME_PROMPT = "Last Name:";
    public static final String PRO_TEAM_PROMPT = "Pro Team:";
    public static final String PLAYER_HEADING = "Player Details";
    public static final String ADD_PLAYER_TITLE = "Add New Player";
    public static final String EDIT_PLAYER_TITLE = "Edit Player";

    MessageDialog messageDialog;

    /**
     * Initializes this dialog so that it can be used for either adding new
     * schedule items or editing existing ones.
     *
     * @param primaryStage The owner of this modal dialog.
     */
    public PlayerAddDialog(Stage primaryStage, Draft draft, MessageDialog messageDialog) {
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        this.messageDialog = messageDialog;
        positions = new ArrayList<String>();
    }

    public void makeAddPlayerDialog(MessageDialog messageDialog) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);

        // NOW THE FIRST NAME
        firstNameLabel = new Label(FIRST_NAME_PROMPT);
        firstNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        firstNameTextField = new TextField();
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            player.setFirstName(newValue);
        });

        // AND THE LAST NAME
        lastNameLabel = new Label(LAST_NAME_PROMPT);
        lastNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        lastNameTextField = new TextField();
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            player.setLastName(newValue);
        });

        // AND THE PRO TEAM
        proTeamLabel = new Label(PRO_TEAM_PROMPT);
        proTeamLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        proTeamComboBox = new ComboBox();
        proTeamComboBox.setItems(FXCollections.observableArrayList(teamsList));
        proTeamComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            player.setProTeam(newValue.toString());
        });

        // AND THE CHECKBOXES
        c = new CheckBox("C");
        c.getStyleClass().add(CLASS_GENERAL);
        c.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (positions.contains("P")) {
                    messageDialog.show(props.getProperty(DraftKit_PropertyType.ILLEGAL_POSITION_MESSAGE));
                    c.setSelected(false);
                } else {
                    positions.add(c.getText());
                    updatePositionString();
                }
            } else {
                positions.remove(c.getText());
                updatePositionString();
            }
        });
        oneB = new CheckBox("1B");
        oneB.getStyleClass().add(CLASS_GENERAL);
        oneB.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (positions.contains("P")) {
                    messageDialog.show(props.getProperty(DraftKit_PropertyType.ILLEGAL_POSITION_MESSAGE));
                    oneB.setSelected(false);
                } else {
                    positions.add(oneB.getText());
                    updatePositionString();
                }
            } else {
                positions.remove(oneB.getText());
                updatePositionString();
            }
        });
        twoB = new CheckBox("2B");
        twoB.getStyleClass().add(CLASS_GENERAL);
        twoB.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (positions.contains("P")) {
                    messageDialog.show(props.getProperty(DraftKit_PropertyType.ILLEGAL_POSITION_MESSAGE));
                    twoB.setSelected(false);
                } else {
                    positions.add(twoB.getText());
                    updatePositionString();
                }
            } else {
                positions.remove(twoB.getText());
                updatePositionString();
            }
        });
        threeB = new CheckBox("3B");
        threeB.getStyleClass().add(CLASS_GENERAL);
        threeB.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (positions.contains("P")) {
                    messageDialog.show(props.getProperty(DraftKit_PropertyType.ILLEGAL_POSITION_MESSAGE));
                    threeB.setSelected(false);
                } else {
                    positions.add(threeB.getText());
                    updatePositionString();
                }
            } else {
                positions.remove(threeB.getText());
                updatePositionString();
            }
        });
        SS = new CheckBox("SS");
        SS.getStyleClass().add(CLASS_GENERAL);
        SS.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (positions.contains("P")) {
                    messageDialog.show(props.getProperty(DraftKit_PropertyType.ILLEGAL_POSITION_MESSAGE));
                    SS.setSelected(false);
                } else {
                    positions.add(SS.getText());
                    updatePositionString();
                }
            } else {
                positions.remove(SS.getText());
                updatePositionString();
            }
        });
        OF = new CheckBox("OF");
        OF.getStyleClass().add(CLASS_GENERAL);
        OF.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (positions.contains("P")) {
                    messageDialog.show(props.getProperty(DraftKit_PropertyType.ILLEGAL_POSITION_MESSAGE));
                    OF.setSelected(false);
                } else {
                    positions.add(OF.getText());
                    updatePositionString();
                }
            } else {
                positions.remove(OF.getText());
                updatePositionString();
            }
        });
        P = new CheckBox("P");
        P.getStyleClass().add(CLASS_GENERAL);
        P.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (positions.isEmpty()) {
                    player = new Pitcher();
                    updatePlayer();
                    positions.add(P.getText());
                } else {
                    messageDialog.show(props.getProperty(DraftKit_PropertyType.ILLEGAL_POSITION_MESSAGE));
                    P.setSelected(false);
                }
            } else {
                player = new Hitter();
                updatePlayer();
                positions.remove(P.getText());
                updatePositionString();
            }
        });

        first = new HBox();
        first.getChildren().add(c);
        first.getChildren().add(oneB);
        second = new HBox();
        second.getChildren().add(twoB);
        second.getChildren().add(threeB);
        second.getChildren().add(SS);
        second.getChildren().add(OF);
        second.getChildren().add(P);

        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);

        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            if (((Button)ae.getSource()).getText().equals(COMPLETE) && 
                    (firstNameTextField.getText() == null || lastNameTextField.getText() == null)) {
                messageDialog.show(props.getProperty(DraftKit_PropertyType.ILLEGAL_NAME_MESSAGE));
            }
            else {
                Button sourceButton = (Button) ae.getSource();
                PlayerAddDialog.this.selection = sourceButton.getText();
                PlayerAddDialog.this.hide();
            }
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(firstNameLabel, 0, 1, 1, 1);
        gridPane.add(firstNameTextField, 1, 1, 1, 1);
        gridPane.add(lastNameLabel, 0, 2, 1, 1);
        gridPane.add(lastNameTextField, 1, 2, 1, 1);
        gridPane.add(proTeamLabel, 0, 3, 1, 1);
        gridPane.add(proTeamComboBox, 1, 3, 1, 1);
        gridPane.add(first, 0, 4, 1, 1);
        gridPane.add(second, 1, 4, 1, 1);
        gridPane.add(completeButton, 0, 5, 1, 1);
        gridPane.add(cancelButton, 1, 5, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
    }

    /**
     * Accessor method for getting the selection the user made.
     *
     * @return Either YES, NO, or CANCEL, depending on which button the user
     * selected when this dialog was presented.
     */

    public String getSelection() {
        return selection;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * This method loads a custom message into the label and then pops open the
     * dialog.
     *
     * @param message Message to appear inside the dialog.
     */
    public Player showAddPlayerDialog() {
        player = new Hitter();

        // SET THE DIALOG TITLE
        setTitle(ADD_PLAYER_TITLE);

        // SHOW THE ADD DIALOG
        makeAddPlayerDialog(messageDialog);

        // AND OPEN IT UP
        this.showAndWait();

        return player;
    }

    public void loadGUIData() {
        // LOAD THE UI STUFF
        firstNameTextField.setText(player.getFirstName());
        lastNameTextField.setText(player.getFirstName());
    }

    public boolean wasCompleteSelected() {
        try {
            return selection.equals(COMPLETE);
        }
        catch (Exception e) {
            return false;
        }
    }

    private void updatePositionString() {
        int i;
        position = "";
        for (i = 0; i < positions.size() - 1; i++) {
            position += positions.get(i) + "_";
        }
        if (!positions.isEmpty()) {
        position += positions.get(i);
        }
        player.setPositions_String(position);
    }
    
    private void updatePlayer() {
        player.setFirstName(firstNameTextField.getText());
        player.setLastName(lastNameTextField.getText());
        if (proTeamComboBox.getSelectionModel().getSelectedItem() != null) {
            player.setProTeam(proTeamComboBox.getSelectionModel().getSelectedItem().toString());
        }
    }
}
