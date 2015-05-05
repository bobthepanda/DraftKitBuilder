package draftkit.gui;

import draftkit.DraftKit_PropertyType;
import static draftkit.DraftKit_StartupConstants.PATH_FLAGS;
import static draftkit.DraftKit_StartupConstants.PATH_PLAYERS;
import draftkit.data.Draft;
import draftkit.data.Player;
import draftkit.data.Team;
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
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author McKillaGorilla
 */
public class PlayerEditDialog extends Stage {

    // THIS IS THE OBJECT DATA BEHIND THIS UI
    Player player;

    // GUI CONTROLS FOR OUR DIALOG
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    ImageView faceImage;
    VBox infoPane;
    ImageView countryImage;
    Label nameLabel;
    Label positionsLabel;
    Label fantasyTeamLabel;
    ComboBox fantasyTeamComboBox;
    Label positionLabel;
    ComboBox positionComboBox;
    Label contractLabel;
    ComboBox contractComboBox;
    Label salaryLabel;
    TextField salaryTextField;
    Button completeButton;
    Button cancelButton;

    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    String positions = "C,1B,2B,3B,SS,OF,P";
    ArrayList<String> positionsList = new ArrayList<String>(Arrays.asList(positions.split(",")));
    String contracts = "S2,S1,X";
    ArrayList<String> contractsList = new ArrayList<String>(Arrays.asList(contracts.split(",")));
    ArrayList<String> teamNames;
    Draft draft;

    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String FANTASY_TEAM_PROMPT = "Fantasy Team: ";
    public static final String POSITION_PROMPT = "Position:";
    public static final String CONTRACT_PROMPT = "Contract:";
    public static final String PLAYER_HEADING = "Player Details";
    public static final String ADD_PLAYER_TITLE = "Edit New Player";
    public static final String EDIT_PLAYER_TITLE = "Edit Player";
    public static final String SALARY_PROMPT = "Salary ($): ";

    MessageDialog messageDialog;

    /**
     * Initializes this dialog so that it can be used for either adding new
     * schedule items or editing existing ones.
     *
     * @param primaryStage The owner of this modal dialog.
     */
    public PlayerEditDialog(Stage primaryStage, Draft draft, MessageDialog messageDialog) {
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        this.messageDialog = messageDialog;
        teamNames = new ArrayList<String>();
        this.draft = draft;
    }

    public void makeEditPlayerDialog(MessageDialog messageDialog) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        teamNames.clear();
        for (Team t : draft.getTeams()) {
            teamNames.add(t.getName());
        }

        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);

        // NOW THE IMAGES
        String facePath = "file:" + PATH_PLAYERS + player.getLastName() + player.getFirstName() + ".jpg";
        Image face;
        try {
            face = new Image(facePath);
        } catch (IllegalArgumentException e) {
            face = new Image("file:" + PATH_PLAYERS + "AAA_PhotoMissing.jpg");
        }
        faceImage = new ImageView(face);

        infoPane = new VBox();
        String countryPath = "file:" + PATH_FLAGS + player.getNationOfBirth() + ".png";
        Image country;
        try {
            country = new Image(countryPath);
        } catch (IllegalArgumentException e) {
            country = new Image("file:" + PATH_FLAGS + "USA.png");
        }
        countryImage = new ImageView(country);
        nameLabel = new Label(player.getFirstName() + " " + player.getLastName());
        positionsLabel = new Label(player.getPositions_String());
        infoPane.getChildren().add(countryImage);
        infoPane.getChildren().add(nameLabel);
        infoPane.getChildren().add(positionsLabel);

        // NOW THE FIRST NAME
        fantasyTeamLabel = new Label(FANTASY_TEAM_PROMPT);
        fantasyTeamLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        fantasyTeamComboBox = new ComboBox();
        fantasyTeamComboBox.setItems(FXCollections.observableArrayList(teamNames));
        if (player.getTeam() != null) {
            fantasyTeamComboBox.getItems().remove(player.getTeam());
            fantasyTeamComboBox.getItems().add("Free Agent");
        }
        fantasyTeamComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.toString().equals("Free Agent")) {
                player.setTeam(null);
                positionComboBox.setValue(null);
                player.setPosition(null);
                contractComboBox.setValue(null);
                player.setContract(null);
                salaryTextField.setText(null);
                player.setSalary(0);
            } else if (draft.getTeam(newValue.toString()).isTeamFull()) {
                if (player.getTeam() != null) {
                    if (player.getTeam().equals(newValue.toString())) {
                    } else {
                        player.setTeam(newValue.toString());
                        positionComboBox.setValue(null);
                        contractComboBox.setValue("X");
                        salaryTextField.setText("1");
                    } 
                }else {
                    player.setTeam(newValue.toString());
                positionComboBox.setValue(null);
                contractComboBox.setValue("X");
                salaryTextField.setText("1");
            }
            }
            else {
                player.setTeam(newValue.toString());
                positionComboBox.setItems(FXCollections.observableArrayList(player.getPositions()));
                for (String s : player.getPositions()) {
                    if (draft.getTeam(player.getTeam()).isPositionFull(s)) {
                        positionComboBox.getItems().remove(s);
                    }
                }
                contractComboBox.setItems(FXCollections.observableArrayList(contractsList));
            }
    }
    );

        // AND THE POSITION
        positionLabel  = new Label(POSITION_PROMPT);

    positionLabel.getStyleClass ()
    .add(CLASS_PROMPT_LABEL);
        positionComboBox  = new ComboBox();

    positionComboBox.valueProperty () 
        .addListener((observable, oldValue, newValue) -> {
            if (player.getTeam() == null) {
            player.setPosition(null);
            positionComboBox.setValue(null);
        } else {
            player.setPosition(newValue.toString());
        }
    }
    );

        // AND THE CONTRACT
        contractLabel  = new Label(CONTRACT_PROMPT);

    contractLabel.getStyleClass ()
    .add(CLASS_PROMPT_LABEL);
        contractComboBox  = new ComboBox();

    contractComboBox.valueProperty () 
        .addListener((observable, oldValue, newValue) -> {
            if (player.getTeam() == null) {
            player.setContract(null);
            contractComboBox.setValue(null);
        } else {
            player.setContract(newValue.toString());
        }
    }
    );

        // AND THE SALARY
        salaryLabel  = new Label(SALARY_PROMPT);

    salaryLabel.getStyleClass ()
    .add(CLASS_PROMPT_LABEL);
        salaryTextField  = new TextField();

    salaryTextField.textProperty () 
        .addListener((observable, oldValue, newValue) -> {
            if (player.getTeam() == null) {
            player.setSalary(0);
            salaryTextField.setText(null);
        } else {
            try {
                player.setSalary(Integer.parseInt(newValue.toString()));
            } catch (Exception e) {
                messageDialog.show(props.getProperty(DraftKit_PropertyType.ILLEGAL_SALARY_MESSAGE));
                player.setSalary(0);
                salaryTextField.setText(null);
            }
        }
    }
    );

        // AND FINALLY, THE BUTTONS
        completeButton  = new Button(COMPLETE);
    cancelButton  = new Button(CANCEL);

    // REGISTER EVENT HANDLERS FOR OUR BUTTONS
    EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
        if (((Button) ae.getSource()).getText().equals(COMPLETE)
                && (!fantasyTeamComboBox.getValue().equals("Free Agent")
                && ((!draft.getTeam(player.getTeam()).isTeamFull() && (positionComboBox.getValue() == null))
                || contractComboBox.getValue() == null
                || salaryTextField.getText() == null))) {
            messageDialog.show(props.getProperty(DraftKit_PropertyType.INSUFFICIENT_INFO_MESSAGE));
        } else {
            Button sourceButton = (Button) ae.getSource();
            PlayerEditDialog.this.selection = sourceButton.getText();
            PlayerEditDialog.this.hide();
        }
    };

    completeButton.setOnAction (completeCancelHandler);

    cancelButton.setOnAction (completeCancelHandler);

    // NOW LET'S ARRANGE THEM ALL AT ONCE
    gridPane.add (headingLabel, 

    0, 0, 2, 1);
    gridPane.add (faceImage, 

    0, 1, 1, 1);
    gridPane.add (infoPane, 

    1, 1, 1, 1);
    gridPane.add (fantasyTeamLabel, 

    0, 2, 1, 1);
    gridPane.add (fantasyTeamComboBox, 

    1, 2, 1, 1);
    gridPane.add (positionLabel, 

    0, 3, 1, 1);
    gridPane.add (positionComboBox, 

    1, 3, 1, 1);
    gridPane.add (contractLabel, 

    0, 4, 1, 1);
    gridPane.add (contractComboBox, 

    1, 4, 1, 1);
    gridPane.add (salaryLabel, 

    0, 5, 1, 1);
    gridPane.add (salaryTextField, 

    1, 5, 1, 1);
    gridPane.add (completeButton, 

    0, 6, 1, 1);
    gridPane.add (cancelButton, 
    1, 6, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene  = new Scene(gridPane);

    dialogScene.getStylesheets ()
    .add(PRIMARY_STYLE_SHEET);
         

    this.setScene(dialogScene);

    loadGUIData();
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
    public Player showEditPlayerDialog(Player playerToEdit) {
        player = playerToEdit;

        // SET THE DIALOG TITLE
        setTitle(ADD_PLAYER_TITLE);

        // SHOW THE ADD DIALOG
        makeEditPlayerDialog(messageDialog);

        // AND OPEN IT UP
        this.showAndWait();

        return player;
    }

    public void loadGUIData() {
        // LOAD THE UI STUFF
        if (player.getTeam() != null) {
            positionComboBox.setItems(FXCollections.observableArrayList(player.getPositions()));
            contractComboBox.setItems(FXCollections.observableArrayList(contractsList));
            for (String s : player.getPositions()) {
                if (draft.getTeam(player.getTeam()).isPositionFull(s)) {
                    positionComboBox.getItems().remove(s);
                }
            }
            if (player.getPosition() != null) {
                    positionComboBox.getItems().add(player.getPosition());
            }
            fantasyTeamComboBox.setValue(player.getTeam());
            positionComboBox.setValue(player.getPosition());
            contractComboBox.setValue(player.getContract());
            salaryTextField.setText(player.getSalary() + "");
        }
    }

    public boolean wasCompleteSelected() {
        try {
            return selection.equals(COMPLETE);
        }
        catch (Exception e) {
            return false;
        }
    }
}
