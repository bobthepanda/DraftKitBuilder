package draftkit.gui;

import com.sun.javafx.scene.control.Logging;
import static draftkit.DraftKit_StartupConstants.*;
import draftkit.DraftKit_PropertyType;
//import draftkit.controller.DraftEditController;
import draftkit.data.DraftDataManager;
import draftkit.data.DraftDataView;
import draftkit.controller.FileController;
/*
 import draftkit.controller.TeamEditController;
 */
import draftkit.controller.PlayerController;
import draftkit.controller.TeamController;
import draftkit.data.Player;
import draftkit.data.Draft;
import draftkit.data.PositionComparator;
import draftkit.data.Team;
import draftkit.file.DraftFileManager;
//import draftkit.file.DraftSiteExporter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import properties_manager.PropertiesManager;

/**
 * This class provides the Graphical User Interface for this application,
 * managing all the UI components for editing a Draft and exporting it to a
 * site.
 *
 * @author Richard McKenna
 */
public class GUI implements DraftDataView {

    // THESE CONSTANTS ARE FOR TYING THE PRESENTATION STYLE OF
    // THIS GUI'S COMPONENTS TO A STYLE SHEET THAT IT USES
    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "draftkit_style.css";
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_SUBJECT_PANE = "subject_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    static final String CLASS_GENERAL = "general";
    static final String CLASS_REGULAR_PANE = "regular_pane";
    static final String EMPTY_TEXT = "";
    static final int LARGE_TEXT_FIELD_LENGTH = 20;
    static final int SMALL_TEXT_FIELD_LENGTH = 5;

    // THIS MANAGES ALL OF THE APPLICATION'S DATA
    DraftDataManager dataManager;

    // THIS MANAGES DRAFT FILE I/O
    DraftFileManager draftFileManager;

    // THIS MANAGES EXPORTING OUR SITE PAGES
    //DraftSiteExporter siteExporter;
    // THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    FileController fileController;
    PlayerController playerController;
    TeamController teamController;

    // THIS IS THE APPLICATION WINDOW
    Stage primaryStage;

    // THIS IS THE STAGE'S SCENE GRAPH
    Scene primaryScene;

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION GUI
    BorderPane draftkitPane;

    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPane;
    Button newDraftButton;
    Button loadDraftButton;
    Button saveDraftButton;
    Button exportSiteButton;
    Button exitButton;

    // WE'LL ORGANIZE OUR WORKSPACE COMPONENTS USING A BORDER PANE
    BorderPane workspacePane;
    boolean workspaceActivated;

    // WE'LL PUT THE WORKSPACE INSIDE A SCROLL PANE
    ScrollPane workspaceScrollPane;

    // VBOXES FOR VARIOUS SCREENS
    VBox teamScreen;
    Label teamsLabel;
    VBox playerScreen;
    Label playersLabel;
    VBox standingsScreen;
    Label standingsLabel;
    VBox summaryScreen;
    Label summaryLabel;
    VBox MLBScreen;
    Label MLBLabel;

    //PLAYER SCREEN CONTROLS
    HBox playerButtonBox;
    Button addPlayerButton;
    Button removePlayerButton;
    Label searchLabel;
    TextField searchField;
    ObservableList<Player> displayedPlayers;

    //RADIO BUTTON CONTROLS
    HBox radioBox;
    ToggleGroup select;
    RadioButton all;
    RadioButton c;
    RadioButton firstB;
    RadioButton cI;
    RadioButton secondB;
    RadioButton thirdB;
    RadioButton MI;
    RadioButton SS;
    RadioButton OF;
    RadioButton U;
    RadioButton P;

    //PLAYER TABLE
    TableView<Player> playerTable;
    TableColumn player_first;
    TableColumn player_last;
    TableColumn player_proTeam;
    TableColumn player_positions;
    TableColumn player_year;
    TableColumn player_nation;
    TableColumn player_r_w;
    TableColumn player_hr_sv;
    TableColumn player_rbi_k;
    TableColumn player_sb_era;
    TableColumn player_ba_whip;
    TableColumn player_est_value;
    TableColumn player_notes;

    //LINEUP TABLE
    TableView<Player> lineupTable;
    TableColumn lineup_position;
    TableColumn lineup_first;
    TableColumn lineup_last;
    TableColumn lineup_proTeam;
    TableColumn lineup_positions;
    TableColumn lineup_r_w;
    TableColumn lineup_hr_sv;
    TableColumn lineup_rbi_k;
    TableColumn lineup_sb_era;
    TableColumn lineup_ba_whip;
    TableColumn lineup_est_value;
    TableColumn lineup_contract;
    TableColumn lineup_salary;

    //TAXI TABLE
    TableView<Player> taxiTable;
    TableColumn taxi_position;
    TableColumn taxi_first;
    TableColumn taxi_last;
    TableColumn taxi_proTeam;
    TableColumn taxi_positions;
    TableColumn taxi_r_w;
    TableColumn taxi_hr_sv;
    TableColumn taxi_rbi_k;
    TableColumn taxi_sb_era;
    TableColumn taxi_ba_whip;
    TableColumn taxi_est_value;
    TableColumn taxi_contract;
    TableColumn taxi_salary;

    //TEAM SCREEN CONTROLS
    HBox saveInfo;
    Label saveLabel;
    TextField saveField;
    HBox teamButtonBox;
    Button addTeamButton;
    Button removeTeamButton;
    Button editTeamButton;
    Label selectLabel;
    ComboBox teamComboBox;
    VBox lineupBox;
    Label lineupLabel;
    VBox taxiBox;
    Label taxiLabel;

    //SUMMARY SCREEN CONTROLS
    TableView<Player> summaryTable;
    TableColumn summaryPickNum;
    TableColumn summaryFirstName;
    TableColumn summaryLastName;
    TableColumn summaryTeam;
    TableColumn summaryContract;
    TableColumn summarySalary;

    //STANDINGS SCREEN CONTROLS
    TableView<Team> fantasyTeamTable;
    TableColumn teamName;
    TableColumn playersNeeded;
    TableColumn cashLeft;
    TableColumn cashPP;
    TableColumn teamR;
    TableColumn teamHR;
    TableColumn teamRBI;
    TableColumn teamSB;
    TableColumn teamBA;
    TableColumn teamW;
    TableColumn teamSV;
    TableColumn teamK;
    TableColumn teamERA;
    TableColumn teamWHIP;
    TableColumn teamTotalPts;

    //MLB SCREEN CONTROLS
    HBox selectTeamBox;
    Label selectTeamLabel;
    ComboBox proTeamComboBox;
    TableView<Player> proTeamTable;
    TableColumn proTeam_firstName;
    TableColumn proTeam_lastName;
    TableColumn proTeam_QP;
    String teams = "ATL,AZ,CHC,CIN,COL,LAD,MIA,MIL,NYM,PHI,PIT,SD,SF,STL,WAS";
    ArrayList<String> teamsList = new ArrayList<String>(Arrays.asList(teams.split(",")));

    //LABELS FOR RADIO BUTTONS
    static final String RADIO_ALL = "All";
    static final String RADIO_C = "C";
    static final String RADIO_1B = "1B";
    static final String RADIO_CI = "CI";
    static final String RADIO_3B = "3B";
    static final String RADIO_2B = "2B";
    static final String RADIO_MI = "MI";
    static final String RADIO_SS = "SS";
    static final String RADIO_OF = "OF";
    static final String RADIO_U = "U";
    static final String RADIO_P = "P";

    // AND TABLE COLUMNS
    static final String COL_FIRST = "First";
    static final String COL_LAST = "Last";
    static final String COL_PRO_TEAM = "Pro Team";
    static final String COL_POSITIONS = "Positions";
    static final String COL_POSITION = "Position";
    static final String COL_YEAR = "Year of Birth";
    static final String COL_NATION = "Nation of Birth";
    static final String COL_R_W = "R/W";
    static final String COL_HR_SV = "HR/SV";
    static final String COL_RBI_K = "RBI/K";
    static final String COL_SB_ERA = "SBI/ERA";
    static final String COL_BA_WHIP = "BA/WHIP";
    static final String COL_VALUE = "Estimated Value";
    static final String COL_NOTES = "Notes";
    static final String COL_R = "R";
    static final String COL_HR = "HR";
    static final String COL_RBI = "RBI";
    static final String COL_SB = "SB";
    static final String COL_BA = "BA";
    static final String COL_W = "W";
    static final String COL_SV = "SV";
    static final String COL_K = "K";
    static final String COL_ERA = "ERA";
    static final String COL_WHIP = "WHIP";
    static final String COL_CONTRACT = "Contract";
    static final String COL_SALARY = "Salary ($)";
    static final String COL_TEAM_NAME = "Team Name";
    static final String COL_PLAYERS_NEEDED = "Players Needed";
    static final String COL_CASH_LEFT = "$ Left";
    static final String COL_CASH_PP = "$ PP";
    static final String COL_TOTAL_PTS = "Total Points";
    static final String COL_PICK_NUM = "Pick #";

    // HERE ARE OUR DIALOGS
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;

    // SWITCH SCREEN TOOLBAR
    HBox switchToolbarPane;
    Button playersButton;
    Button teamsButton;
    Button standingsButton;
    Button summaryButton;
    Button MLBTeamsButton;

    /**
     * Constructor for making this GUI, note that it does not initialize the UI
     * controls. To do that, call initGUI.
     *
     * @param initPrimaryStage Window inside which the GUI will be displayed.
     */
    public GUI(Stage initPrimaryStage) {
        primaryStage = initPrimaryStage;
    }

    /**
     * Accessor method for the data manager.
     *
     * @return The DraftDataManager used by this UI.
     */
    public DraftDataManager getDataManager() {
        return dataManager;
    }

    /**
     * Accessor method for the file controller.
     *
     * @return The FileController used by this UI.
     */
    public FileController getFileController() {
        return fileController;
    }

    /**
     * Accessor method for the draft file manager.
     *
     * @return The DraftFileManager used by this UI.
     */
    public DraftFileManager getDraftFileManager() {
        return draftFileManager;
    }

    /**
     * Accessor method for the site exporter.
     *
     * @return The DraftSiteExporter used by this UI.
     */
    /*
     public DraftSiteExporter getSiteExporter() {
     return siteExporter;
     }
     */
    /**
     * Accessor method for the window (i.e. stage).
     *
     * @return The window (i.e. Stage) used by this UI.
     */
    public Stage getWindow() {
        return primaryStage;
    }

    public MessageDialog getMessageDialog() {
        return messageDialog;
    }

    public YesNoCancelDialog getYesNoCancelDialog() {
        return yesNoCancelDialog;
    }

    /**
     * Mutator method for the data manager.
     *
     * @param initDataManager The DraftDataManager to be used by this UI.
     */
    public void setDataManager(DraftDataManager initDataManager) {
        dataManager = initDataManager;
    }

    /**
     * Mutator method for the draft file manager.
     *
     * @param initDraftFileManager The DraftFileManager to be used by this UI.
     */
    public void setDraftFileManager(DraftFileManager initDraftFileManager) {
        draftFileManager = initDraftFileManager;
    }

    /**
     * Mutator method for the site exporter.
     *
     * @param initSiteExporter The DraftSiteExporter to be used by this UI.
     */
    /*
     public void setSiteExporter(DraftSiteExporter initSiteExporter) {
     siteExporter = initSiteExporter;
     }
     */
    /**
     * This method fully initializes the user interface for use.
     *
     * @param windowTitle The text to appear in the UI window's title bar.
     * @param subjects The list of subjects to choose from.
     * @throws IOException Thrown if any initialization files fail to load.
     */
    public void initGUI(String windowTitle) throws IOException {
        // INIT THE DIALOGS
        initDialogs();

        // INIT THE TOOLBARS
        initFileToolbar();

        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
        initWorkspace();

        // NOW SETUP THE EVENT HANDLERS
        initEventHandlers();

        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow(windowTitle);
    }

    /**
     * When called this function puts the workspace into the window, revealing
     * the controls for editing a Draft.
     */
    public void activateWorkspace() {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            draftkitPane.setCenter(workspaceScrollPane);
            workspaceActivated = true;
        }
    }

    /**
     * This function takes all of the data out of the draftToReload argument and
     * loads its values into the user interface controls.
     *
     * @param draftToReload The Draft whose data we'll load into the GUI.
     */
    @Override
    public void reloadDraft(Draft draftToReload, String s) {
        // FIRST ACTIVATE THE WORKSPACE IF NECESSARY
        if (!workspaceActivated) {
            activateWorkspace();
        }

        updateTeamComboBox();
        if (playerTable.getItems() != null) {
            playerTable.setItems(FXCollections.observableArrayList(draftToReload.getPlayers()));
        }
        if (lineupTable.getItems() != null) {
            lineupTable.setItems(null);
        }
        if (taxiTable.getItems() != null) {
            taxiTable.setItems(null);
        }
        if (proTeamTable.getItems() != null) {
            proTeamTable.setItems(null);
        }
        setSaveName(s);
    }

    /**
     * This method is used to activate/deactivate toolbar buttons when they can
     * and cannot be used so as to provide foolproof design.
     *
     * @param saved Describes whether the loaded Draft has been saved or not.
     */
    public void updateToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT DRAFT
        // HAS BEEN SAVED OR NOT
        saveDraftButton.setDisable(saved);

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST DRAFT BEGINS
        loadDraftButton.setDisable(false);
        exportSiteButton.setDisable(false);

        // NOTE THAT THE NEW, LOAD, AND EXIT BUTTONS
        // ARE NEVER DISABLED SO WE NEVER HAVE TO TOUCH THEM
    }

    /**
     * This function loads all the values currently in the user interface into
     * the draft argument.
     *
     * @param draft The draft to be updated using the data from the UI controls.
     */
    public void updateDraftInfo(Draft draft) {
    }

    /**
     * *************************************************************************
     */
    /* BELOW ARE ALL THE PRIVATE HELPER METHODS WE USE FOR INITIALIZING OUR GUI */
    /**
     * *************************************************************************
     */
    private void initDialogs() {
        messageDialog = new MessageDialog(primaryStage, CLOSE_BUTTON_LABEL);
        yesNoCancelDialog = new YesNoCancelDialog(primaryStage);
    }

    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initFileToolbar() {
        fileToolbarPane = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        newDraftButton = initChildButton(fileToolbarPane, DraftKit_PropertyType.NEW_DRAFT_ICON, DraftKit_PropertyType.NEW_DRAFT_TOOLTIP, false);
        loadDraftButton = initChildButton(fileToolbarPane, DraftKit_PropertyType.LOAD_DRAFT_ICON, DraftKit_PropertyType.LOAD_DRAFT_TOOLTIP, false);
        saveDraftButton = initChildButton(fileToolbarPane, DraftKit_PropertyType.SAVE_DRAFT_ICON, DraftKit_PropertyType.SAVE_DRAFT_TOOLTIP, true);
        exportSiteButton = initChildButton(fileToolbarPane, DraftKit_PropertyType.EXPORT_PAGE_ICON, DraftKit_PropertyType.EXPORT_PAGE_TOOLTIP, true);
        exitButton = initChildButton(fileToolbarPane, DraftKit_PropertyType.EXIT_ICON, DraftKit_PropertyType.EXIT_TOOLTIP, false);
    }

    /**
     * This function initializes all the buttons in the toolbar at the bottom of
     * the application window. These are related to file management.
     */
    private void initSwitchToolbar() {
        switchToolbarPane = new HBox();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        teamsButton = initChildButton(switchToolbarPane, DraftKit_PropertyType.TEAMS_ICON, DraftKit_PropertyType.TEAMS_TOOLTIP, false);
        playersButton = initChildButton(switchToolbarPane, DraftKit_PropertyType.PLAYERS_ICON, DraftKit_PropertyType.PLAYERS_TOOLTIP, false);
        standingsButton = initChildButton(switchToolbarPane, DraftKit_PropertyType.STANDINGS_ICON, DraftKit_PropertyType.STANDINGS_TOOLTIP, false);
        summaryButton = initChildButton(switchToolbarPane, DraftKit_PropertyType.SUMMARY_ICON, DraftKit_PropertyType.SUMMARY_TOOLTIP, false);
        MLBTeamsButton = initChildButton(switchToolbarPane, DraftKit_PropertyType.MLB_ICON, DraftKit_PropertyType.MLB_TOOLTIP, false);
    }

    // CREATE THE TEAM SCREEN
    private void initTeamScreen() {
        teamScreen = new VBox();
        teamScreen.setMaxHeight(Double.MAX_VALUE);
        teamScreen.getStyleClass().add(CLASS_REGULAR_PANE);
        initChildLabel(teamScreen, DraftKit_PropertyType.FANTASY_TEAMS_LABEL, CLASS_HEADING_LABEL);

        //SET UP DRAFT SAVING BUTTONS
        saveInfo = new HBox();
        saveLabel = initChildLabel(saveInfo, DraftKit_PropertyType.DRAFT_NAME_LABEL, CLASS_PROMPT_LABEL);
        saveField = new TextField();
        saveField.setEditable(true);
        saveInfo.getChildren().add(saveField);
        saveInfo.getStyleClass().add(CLASS_GENERAL);
        teamScreen.getChildren().add(saveInfo);

        //SET UP EDITING BUTTONS
        teamButtonBox = new HBox();
        addTeamButton = initChildButton(teamButtonBox, DraftKit_PropertyType.ADD_ICON, DraftKit_PropertyType.ADD_TEAM_TOOLTIP, false);
        removeTeamButton = initChildButton(teamButtonBox, DraftKit_PropertyType.MINUS_ICON, DraftKit_PropertyType.REMOVE_TEAM_TOOLTIP, false);
        editTeamButton = initChildButton(teamButtonBox, DraftKit_PropertyType.EDIT_ICON, DraftKit_PropertyType.EDIT_TEAM_TOOLTIP, false);
        selectLabel = initChildLabel(teamButtonBox, DraftKit_PropertyType.SELECT_FANTASY_TEAM_LABEL, CLASS_PROMPT_LABEL);
        teamComboBox = new ComboBox();
        updateTeamComboBox();
        teamButtonBox.getChildren().add(teamComboBox);
        teamButtonBox.getStyleClass().add(CLASS_GENERAL);
        teamScreen.getChildren().add(teamButtonBox);

        //SET UP LINEUP 
        lineupBox = new VBox();
        lineupLabel = initChildLabel(lineupBox, DraftKit_PropertyType.STARTING_LINEUP_LABEL, CLASS_HEADING_LABEL);
        lineupTable = new TableView<Player>();

        lineupTable.setMaxHeight(Double.MAX_VALUE);
        lineup_position = new TableColumn(COL_POSITION);
        lineup_position.setCellValueFactory(new PropertyValueFactory<String, String>("position"));
        lineup_position.setComparator(new PositionComparator());
        sortLineupTable();
        lineup_first = new TableColumn(COL_FIRST);
        lineup_first.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        lineup_last = new TableColumn(COL_LAST);
        lineup_last.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        lineup_proTeam = new TableColumn(COL_PRO_TEAM);
        lineup_proTeam.setCellValueFactory(new PropertyValueFactory<String, String>("proTeam"));
        lineup_positions = new TableColumn(COL_POSITIONS);
        lineup_positions.setCellValueFactory(new PropertyValueFactory<String, String>("positions_String"));
        lineup_r_w = new TableColumn(COL_R_W);
        lineup_r_w.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("r_w"));
        lineup_hr_sv = new TableColumn(COL_HR_SV);
        lineup_hr_sv.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("hr_sv"));
        lineup_rbi_k = new TableColumn(COL_RBI_K);
        lineup_rbi_k.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("rbi_k"));
        lineup_sb_era = new TableColumn(COL_SB_ERA);
        lineup_sb_era.setCellValueFactory(new PropertyValueFactory<Double, String>("sb_era"));
        lineup_ba_whip = new TableColumn(COL_BA_WHIP);
        lineup_ba_whip.setCellValueFactory(new PropertyValueFactory<Double, String>("ba_whip"));
        lineup_est_value = new TableColumn(COL_VALUE);
        lineup_est_value.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("estimatedValue"));
        lineup_contract = new TableColumn(COL_CONTRACT);
        lineup_contract.setCellValueFactory(new PropertyValueFactory<String, String>("contract"));
        lineup_salary = new TableColumn(COL_SALARY);
        lineup_salary.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("salary"));
        lineupTable.getColumns().add(lineup_position);
        lineupTable.getColumns().add(lineup_first);
        lineupTable.getColumns().add(lineup_last);
        lineupTable.getColumns().add(lineup_proTeam);
        lineupTable.getColumns().add(lineup_positions);
        lineupTable.getColumns().add(lineup_r_w);
        lineupTable.getColumns().add(lineup_hr_sv);
        lineupTable.getColumns().add(lineup_rbi_k);
        lineupTable.getColumns().add(lineup_sb_era);
        lineupTable.getColumns().add(lineup_ba_whip);
        lineupTable.getColumns().add(lineup_est_value);
        lineupTable.getColumns().add(lineup_contract);
        lineupTable.getColumns().add(lineup_salary);
        for (TableColumn t : lineupTable.getColumns()) {
            t.setSortable(false);
        }
        lineupBox.getChildren().add(lineupTable);
        lineupBox.getStyleClass().add(CLASS_GENERAL);
        teamScreen.getChildren().add(lineupBox);

        //SET UP TAXI
        taxiBox = new VBox();
        taxiLabel = initChildLabel(taxiBox, DraftKit_PropertyType.TAXI_SQUAD_LABEL, CLASS_HEADING_LABEL);
        taxiTable = new TableView<Player>();
        taxiTable.setMaxHeight(Double.MAX_VALUE);
        taxi_position = new TableColumn(COL_POSITION);
        taxi_position.setCellValueFactory(new PropertyValueFactory<String, String>("position"));
        taxi_position.setComparator(new PositionComparator());
        taxi_position.setSortType(SortType.ASCENDING);
        taxi_first = new TableColumn(COL_FIRST);
        taxi_first.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        taxi_last = new TableColumn(COL_LAST);
        taxi_last.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        taxi_proTeam = new TableColumn(COL_PRO_TEAM);
        taxi_proTeam.setCellValueFactory(new PropertyValueFactory<String, String>("proTeam"));
        taxi_positions = new TableColumn(COL_POSITIONS);
        taxi_positions.setCellValueFactory(new PropertyValueFactory<String, String>("positions_String"));
        taxi_r_w = new TableColumn(COL_R_W);
        taxi_r_w.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("r_w"));
        taxi_hr_sv = new TableColumn(COL_HR_SV);
        taxi_hr_sv.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("hr_sv"));
        taxi_rbi_k = new TableColumn(COL_RBI_K);
        taxi_rbi_k.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("rbi_k"));
        taxi_sb_era = new TableColumn(COL_SB_ERA);
        taxi_sb_era.setCellValueFactory(new PropertyValueFactory<Double, String>("sb_era"));
        taxi_ba_whip = new TableColumn(COL_BA_WHIP);
        taxi_ba_whip.setCellValueFactory(new PropertyValueFactory<Double, String>("ba_whip"));
        taxi_est_value = new TableColumn(COL_VALUE);
        taxi_est_value.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("estimatedValue"));
        taxi_contract = new TableColumn(COL_CONTRACT);
        taxi_contract.setCellValueFactory(new PropertyValueFactory<String, String>("contract"));
        taxi_salary = new TableColumn(COL_SALARY);
        taxi_salary.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("salary"));
        taxiTable.getColumns().add(taxi_position);
        taxiTable.getColumns().add(taxi_first);
        taxiTable.getColumns().add(taxi_last);
        taxiTable.getColumns().add(taxi_proTeam);
        taxiTable.getColumns().add(taxi_positions);
        taxiTable.getColumns().add(taxi_r_w);
        taxiTable.getColumns().add(taxi_hr_sv);
        taxiTable.getColumns().add(taxi_rbi_k);
        taxiTable.getColumns().add(taxi_sb_era);
        taxiTable.getColumns().add(taxi_ba_whip);
        taxiTable.getColumns().add(taxi_est_value);
        taxiTable.getColumns().add(taxi_contract);
        taxiTable.getColumns().add(taxi_salary);
        taxiBox.getChildren().add(taxiTable);
        taxiBox.getStyleClass().add(CLASS_GENERAL);
        teamScreen.getChildren().add(taxiBox);
    }

    private void updateTeamComboBox() {
        teamComboBox.getItems().clear();
        for (Team t : dataManager.getDraft().getTeams()) {
            teamComboBox.getItems().add(t.getName());
        }
    }

    // CREATE THE PLAYER SCREEN
    private void initPlayerScreen() {
        playerScreen = new VBox();
        playerScreen.setMaxHeight(Double.MAX_VALUE);
        playerScreen.getStyleClass().add(CLASS_REGULAR_PANE);
        initChildLabel(playerScreen, DraftKit_PropertyType.AVAILABLE_PLAYERS_LABEL, CLASS_HEADING_LABEL);

        //SET UP NEXT LAYER OF BUTTONS
        playerButtonBox = new HBox();
        addPlayerButton = initChildButton(playerButtonBox, DraftKit_PropertyType.ADD_ICON, DraftKit_PropertyType.ADD_PLAYER_TOOLTIP, false);
        removePlayerButton = initChildButton(playerButtonBox, DraftKit_PropertyType.MINUS_ICON, DraftKit_PropertyType.REMOVE_PLAYER_TOOLTIP, false);
        searchLabel = initChildLabel(playerButtonBox, DraftKit_PropertyType.SEARCH_LABEL, CLASS_HEADING_LABEL);
        searchField = new TextField();
        searchField.setEditable(true);
        playerButtonBox.getChildren().add(searchField);
        playerScreen.getChildren().add(playerButtonBox);
        playerButtonBox.getStyleClass().add(CLASS_GENERAL);

        //SET UP TOOLBAR
        radioBox = new HBox();
        select = new ToggleGroup();
        all = new RadioButton();
        all.setToggleGroup(select);
        all.setText(RADIO_ALL);
        all.setSelected(true);
        radioBox.getChildren().add(all);
        c = addRadioButton(radioBox, select, c, RADIO_C);
        cI = addRadioButton(radioBox, select, cI, RADIO_CI);
        firstB = addRadioButton(radioBox, select, firstB, RADIO_1B);
        secondB = addRadioButton(radioBox, select, secondB, RADIO_2B);
        thirdB = addRadioButton(radioBox, select, thirdB, RADIO_3B);
        MI = addRadioButton(radioBox, select, MI, RADIO_MI);
        SS = addRadioButton(radioBox, select, SS, RADIO_SS);
        OF = addRadioButton(radioBox, select, OF, RADIO_OF);
        U = addRadioButton(radioBox, select, U, RADIO_U);
        P = addRadioButton(radioBox, select, P, RADIO_P);
        radioBox.getStyleClass().add(CLASS_GENERAL);
        playerScreen.getChildren().add(radioBox);

        //SETS UP TABLE
        playerTable = new TableView<Player>();
        playerTable.setMaxHeight(Double.MAX_VALUE);
        player_first = new TableColumn(COL_FIRST);
        player_first.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        player_last = new TableColumn(COL_LAST);
        player_last.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        player_proTeam = new TableColumn(COL_PRO_TEAM);
        player_proTeam.setCellValueFactory(new PropertyValueFactory<String, String>("proTeam"));
        player_positions = new TableColumn(COL_POSITIONS);
        player_positions.setCellValueFactory(new PropertyValueFactory<String, String>("positions_String"));
        player_year = new TableColumn(COL_YEAR);
        player_year.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("yearOfBirth"));
        player_nation = new TableColumn(COL_NATION);
        player_nation.setCellValueFactory(new PropertyValueFactory<String, String>("nationOfBirth"));
        player_r_w = new TableColumn(COL_R_W);
        player_r_w.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("r_w"));
        player_hr_sv = new TableColumn(COL_HR_SV);
        player_hr_sv.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("hr_sv"));
        player_rbi_k = new TableColumn(COL_RBI_K);
        player_rbi_k.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("rbi_k"));
        player_sb_era = new TableColumn(COL_SB_ERA);
        player_sb_era.setCellValueFactory(new PropertyValueFactory<Double, String>("sb_era"));
        player_ba_whip = new TableColumn(COL_BA_WHIP);
        player_ba_whip.setCellValueFactory(new PropertyValueFactory<Double, String>("ba_whip"));
        player_est_value = new TableColumn(COL_VALUE);
        player_est_value.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("estimatedValue"));
        player_notes = new TableColumn(COL_NOTES);
        player_notes.setEditable(true);
        player_notes.setCellValueFactory(new PropertyValueFactory<String, String>("notes"));
        player_notes.setCellFactory(TextFieldTableCell.forTableColumn());
        playerTable.getColumns().add(player_first);
        playerTable.getColumns().add(player_last);
        playerTable.getColumns().add(player_proTeam);
        playerTable.getColumns().add(player_positions);
        playerTable.getColumns().add(player_year);
        playerTable.getColumns().add(player_nation);
        playerTable.getColumns().add(player_r_w);
        playerTable.getColumns().add(player_hr_sv);
        playerTable.getColumns().add(player_rbi_k);
        playerTable.getColumns().add(player_sb_era);
        playerTable.getColumns().add(player_ba_whip);
        playerTable.getColumns().add(player_est_value);
        playerTable.getColumns().add(player_notes);

        //SETS UP FILTERING
        playerTable = setPlayerTable(dataManager.getDraft().getPlayers());
        playerTable.setEditable(true);
        playerScreen.getChildren().add(playerTable);
    }

    // METHODS FOR CHANGING TABLE HEADERS
    private void setPlayerTableColumnsAll() {
        player_r_w.setText(COL_R_W);
        player_hr_sv.setText(COL_HR_SV);
        player_rbi_k.setText(COL_RBI_K);
        player_sb_era.setText(COL_SB_ERA);
        player_ba_whip.setText(COL_BA_WHIP);
    }

    private void setPlayerTableColumnsHitters() {
        player_r_w.setText(COL_R);
        player_hr_sv.setText(COL_HR);
        player_rbi_k.setText(COL_RBI);
        player_sb_era.setText(COL_SB);
        player_ba_whip.setText(COL_BA);
    }

    private void setPlayerTableColumnsPitchers() {
        player_r_w.setText(COL_W);
        player_hr_sv.setText(COL_SV);
        player_rbi_k.setText(COL_K);
        player_sb_era.setText(COL_ERA);
        player_ba_whip.setText(COL_WHIP);
    }

    private TableView<Player> setPlayerTable(ArrayList<Player> players) {
        displayedPlayers = FXCollections.observableArrayList(players);
        FilteredList<Player> filteredData = new FilteredList<Player>(displayedPlayers, p -> true);
        searchField.textProperty().addListener((observable, oldVlaue, newValue) -> {
            filteredData.setPredicate(player -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if ((player.getFirstName() + " " + player.getLastName()).toLowerCase().contains(lowerCaseFilter)
                        || (player.getLastName() + " " + player.getFirstName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        SortedList<Player> sortedData = new SortedList<Player>(filteredData);
        sortedData.comparatorProperty().bind(playerTable.comparatorProperty());
        playerTable.setItems(sortedData);
        return playerTable;
    }

    // CREATE STANDINGS SCREEN
    private void initStandingsScreen() {
        standingsScreen = new VBox();
        standingsScreen.setMaxHeight(Double.MAX_VALUE);
        standingsScreen.getStyleClass().add(CLASS_REGULAR_PANE);
        initChildLabel(standingsScreen, DraftKit_PropertyType.FANTASY_STANDINGS_LABEL, CLASS_HEADING_LABEL);

        fantasyTeamTable = new TableView<Team>();
        fantasyTeamTable.setMaxHeight(Double.MAX_VALUE);
        teamName = new TableColumn(COL_TEAM_NAME);
        teamName.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        playersNeeded = new TableColumn(COL_PLAYERS_NEEDED);
        playersNeeded.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("playersNeeded"));
        cashLeft = new TableColumn(COL_CASH_LEFT);
        cashLeft.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("cash"));
        cashPP = new TableColumn(COL_CASH_PP);
        cashPP.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("cashPP"));
        teamR = new TableColumn(COL_R);
        teamR.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("r"));
        teamHR = new TableColumn(COL_HR);
        teamHR.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("hr"));
        teamRBI = new TableColumn(COL_RBI);
        teamRBI.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("rbi"));
        teamSB = new TableColumn(COL_SB);
        teamSB.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("sb"));
        teamBA = new TableColumn(COL_BA);
        teamBA.setCellValueFactory(new PropertyValueFactory<Double, Double>("ba"));
        teamW = new TableColumn(COL_W);
        teamW.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("w"));
        teamSV = new TableColumn(COL_SV);
        teamSV.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("sv"));
        teamK = new TableColumn(COL_K);
        teamK.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("k"));
        teamERA = new TableColumn(COL_ERA);
        teamERA.setCellValueFactory(new PropertyValueFactory<Double, Double>("era"));
        teamWHIP = new TableColumn(COL_WHIP);
        teamWHIP.setCellValueFactory(new PropertyValueFactory<Double, Double>("whip"));
        teamTotalPts = new TableColumn(COL_TOTAL_PTS);
        teamTotalPts.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("points"));

        fantasyTeamTable.getColumns().addAll(teamName, playersNeeded, cashLeft, cashPP, teamR, teamHR, teamRBI, teamSB, teamBA, teamW, teamSV, teamK, teamERA, teamWHIP, teamTotalPts);
        fantasyTeamTable.setItems(FXCollections.observableArrayList(dataManager.getDraft().getTeams()));
        standingsScreen.getChildren().add(fantasyTeamTable);
    }

    // CREATE SUMMARY SCREEN
    private void initSummaryScreen() {
        summaryScreen = new VBox();
        summaryScreen.setMaxHeight(Double.MAX_VALUE);
        summaryScreen.getStyleClass().add(CLASS_REGULAR_PANE);
        initChildLabel(summaryScreen, DraftKit_PropertyType.DRAFT_SUMMARY_LABEL, CLASS_HEADING_LABEL);

        summaryTable = new TableView<Player>();
        summaryPickNum = new TableColumn(COL_PICK_NUM);
        summaryPickNum.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("index"));
        summaryFirstName = new TableColumn(COL_FIRST);
        summaryFirstName.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        summaryLastName = new TableColumn(COL_LAST);
        summaryLastName.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        summaryTeam = new TableColumn(COL_TEAM_NAME);
        summaryTeam.setCellValueFactory(new PropertyValueFactory<String, String>("team"));
        summaryContract = new TableColumn(COL_CONTRACT);
        summaryContract.setCellValueFactory(new PropertyValueFactory<String, String>("contract"));
        summarySalary = new TableColumn(COL_SALARY);
        summarySalary.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("salary"));

        summaryTable.getColumns().addAll(summaryPickNum, summaryFirstName, summaryLastName, summaryTeam, summaryContract, summarySalary);
        summaryTable.setItems(FXCollections.observableArrayList(dataManager.getDraft().getDraftPicks()));
        summaryScreen.getChildren().add(summaryTable);
    }

    // CREATE MLB SCREEN
    private void initMLBScreen() {
        MLBScreen = new VBox();
        MLBScreen.setMaxHeight(Double.MAX_VALUE);
        MLBScreen.getStyleClass().add(CLASS_REGULAR_PANE);
        initChildLabel(MLBScreen, DraftKit_PropertyType.MLB_TEAMS_LABEL, CLASS_HEADING_LABEL);

        selectTeamBox = new HBox();
        selectTeamLabel = initChildLabel(selectTeamBox, DraftKit_PropertyType.PRO_TEAM_LABEL, CLASS_HEADING_LABEL);
        proTeamComboBox = new ComboBox(FXCollections.observableArrayList(teamsList));
        selectTeamBox.getChildren().add(proTeamComboBox);

        proTeamTable = new TableView<Player>();
        proTeam_firstName = new TableColumn(COL_FIRST);
        proTeam_firstName.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        proTeam_lastName = new TableColumn(COL_LAST);
        proTeam_lastName.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        proTeam_QP = new TableColumn(COL_POSITIONS);
        proTeam_QP.setCellValueFactory(new PropertyValueFactory<String, String>("positions_String"));

        proTeamTable.getColumns().add(proTeam_firstName);
        proTeamTable.getColumns().add(proTeam_lastName);
        proTeamTable.getColumns().add(proTeam_QP);

        MLBScreen.getChildren().add(selectTeamBox);
        MLBScreen.getChildren().add(proTeamTable);
    }

// ADDS RADIO BUTTONS TO A TOOLBAR
    private RadioButton addRadioButton(Pane toolbar, ToggleGroup toggle, RadioButton button, String buttonText) {
        button = new RadioButton();
        button.setToggleGroup(toggle);
        button.setText(buttonText);
        toolbar.getChildren().add(button);
        return button;
    }

// CREATES AND SETS UP ALL THE CONTROLS TO GO IN THE APP WORKSPACE
    private void initWorkspace() throws IOException {
        // THE WORKSPACE HAS A FEW REGIONS, THIS 
        // IS FOR BASIC DRAFT EDITING CONTROLS
        initTeamScreen();
        initPlayerScreen();
        initStandingsScreen();
        initSummaryScreen();
        initMLBScreen();

        // THIS IS FOR SELECTING PAGE LINKS TO INCLUDE
        initSwitchToolbar();

        // THE TOP WORKSPACE HOLDS BOTH THE BASIC DRAFT INFO
        // CONTROLS AS WELL AS THE PAGE SELECTION CONTROLS
        // THIS IS FOR MANAGING SCHEDULE EDITING
        // THIS HOLDS ALL OUR WORKSPACE COMPONENTS, SO NOW WE MUST
        // ADD THE COMPONENTS WE'VE JUST INITIALIZED
        workspacePane = new BorderPane();
        workspacePane.setCenter(teamScreen);
        workspacePane.setBottom(switchToolbarPane);
        workspacePane.getStyleClass().add(CLASS_BORDERED_PANE);

        // AND NOW PUT IT IN THE WORKSPACE
        workspaceScrollPane = new ScrollPane();
        workspaceScrollPane.setContent(workspacePane);
        workspaceScrollPane.setFitToWidth(true);
        workspaceScrollPane.setFitToHeight(true);

        // NOTE THAT WE HAVE NOT PUT THE WORKSPACE INTO THE WINDOW,
        // THAT WILL BE DONE WHEN THE USER EITHER CREATES A NEW
        // DRAFT OR LOADS AN EXISTING ONE FOR EDITING
        workspaceActivated = false;
    }

    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Draft IS CREATED OR LOADED
    private void initWindow(String windowTitle) {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(windowTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A DRAFT
        draftkitPane = new BorderPane();
        draftkitPane.setTop(fileToolbarPane);
        primaryScene = new Scene(draftkitPane);

        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    private void updatePlayerTable() {
        String selected = ((RadioButton) select.getSelectedToggle()).getText();
        if (selected.equals(RADIO_ALL)) {
            playerTable = setPlayerTable(dataManager.getDraft().getPlayers());
        } else if (selected.equals("P")) {
            playerTable = setPlayerTable(dataManager.getDraft().getPitcherPlayers());
        } else {
            playerTable = setPlayerTable(dataManager.getDraft().getHittersPosition(selected));
        }
    }

    // INIT ALL THE EVENT HANDLERS
    private void initEventHandlers() throws IOException {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // FIRST THE FILE CONTROLS
        fileController = new FileController(messageDialog, yesNoCancelDialog, draftFileManager//, siteExporter
        );
        playerController = new PlayerController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        teamController = new TeamController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);

        // FILE SAVING BUTTONS
        newDraftButton.setOnAction(e -> {
            fileController.handleNewDraftRequest(this);
        });
        loadDraftButton.setOnAction(e -> {
            fileController.handleLoadDraftRequest(this);
        });
        saveDraftButton.setOnAction(e -> {
            if (saveField.getText() == null) {
                messageDialog.show(props.getProperty(DraftKit_PropertyType.NO_SAVE_NAME_MESSAGE));
            } else {
                String s = saveField.getText();
                //s = s.replace(' ', '_');
                fileController.handleSaveDraftRequest(this, dataManager.getDraft(), s);
            }
        });
        exportSiteButton.setOnAction(e -> {
            fileController.handleExportDraftRequest(this);
        });
        exitButton.setOnAction(e -> {
            fileController.handleExitRequest(this);
        });

        // THEN THE SCREEN TOOLBAR CONTROLS
        teamsButton.setOnAction(e -> {
            workspacePane.setCenter(teamScreen);
            if (teamComboBox.getSelectionModel().getSelectedItem() != null) {
                lineupTable.setItems(dataManager.getDraft().getTeam(teamComboBox.getSelectionModel().getSelectedItem().toString()).getPlayers());
            }
        });
        playersButton.setOnAction(e -> {
            workspacePane.setCenter(playerScreen);
        });
        standingsButton.setOnAction(e -> {
            fantasyTeamTable.setItems(FXCollections.observableArrayList(dataManager.getDraft().getTeams()));
            workspacePane.setCenter(standingsScreen);
        });
        summaryButton.setOnAction(e -> {
            workspacePane.setCenter(summaryScreen);
        });
        MLBTeamsButton.setOnAction(e -> {
            workspacePane.setCenter(MLBScreen);
        });

        // THEN PLAYER ADDING/REMOVING CONTROLS.0
        addPlayerButton.setOnAction(e -> {
            playerController.handleAddPlayerRequest(this);
            updatePlayerTable();
        });

        playerTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                Player p = playerTable.getSelectionModel().getSelectedItem();
                playerTable.getSelectionModel().selectNext();
                playerController.handleEditPlayerRequest(this, p);
                updatePlayerTable();
            }
        });

        removePlayerButton.setOnAction(e -> {
            playerController.handleRemovePlayerRequest(this, playerTable.getSelectionModel().getSelectedItem());
            updatePlayerTable();
        });

        saveField.textProperty().addListener((observable, oldValue, newValue) -> {
            getFileController().markAsEdited(this);
        });

        // THEN TEAM ADDING/REMOVING/EDITING CONTROLS
        addTeamButton.setOnAction(e -> {
            teamController.handleAddTeamRequest(this);
            updateTeamComboBox();
        });

        editTeamButton.setOnAction(e -> {
            teamController.handleEditTeamRequest(this, dataManager.getDraft().getTeam(teamComboBox.getSelectionModel().getSelectedItem().toString()));
            updateTeamComboBox();
        });

        removeTeamButton.setOnAction(e -> {
            teamController.handleRemoveTeamRequest(this, dataManager.getDraft().getTeam(teamComboBox.getSelectionModel().getSelectedItem().toString()));
            updateTeamComboBox();
        });

        teamComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (teamComboBox.getSelectionModel().getSelectedItem() != null) {
                lineupTable.setItems(dataManager.getDraft().getTeam(teamComboBox.getSelectionModel().getSelectedItem().toString()).getPlayers());
                taxiTable.setItems(dataManager.getDraft().getTeam(teamComboBox.getSelectionModel().getSelectedItem().toString()).getTaxi());
            }
        });

        proTeamComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (proTeamComboBox.getSelectionModel().getSelectedItem() != null) {
                proTeamTable.setItems(dataManager.getDraft().getProTeam(newValue.toString()));
            }
        });

        lineupTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                Player p = lineupTable.getSelectionModel().getSelectedItem();
                lineupTable.getSelectionModel().selectNext();
                playerController.handleEditPlayerRequest(this, p);
                updatePlayerTable();
            }
        });

        taxiTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                Player p = taxiTable.getSelectionModel().getSelectedItem();
                taxiTable.getSelectionModel().selectNext();
                playerController.handleEditPlayerRequest(this, p);
                updatePlayerTable();
            }
        });

        // THEN THE FILTER RADIO BUTTONS
        all.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getPlayers());
            setPlayerTableColumnsAll();
        });

        c.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getHittersPosition(RADIO_C));
            setPlayerTableColumnsHitters();
        });

        cI.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getHittersPosition(RADIO_CI));
            setPlayerTableColumnsHitters();
        });

        firstB.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getHittersPosition(RADIO_1B));
            setPlayerTableColumnsHitters();
        });

        secondB.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getHittersPosition(RADIO_2B));
            setPlayerTableColumnsHitters();
        });

        thirdB.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getHittersPosition(RADIO_3B));
            setPlayerTableColumnsHitters();
        });

        MI.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getHittersPosition(RADIO_MI));
            setPlayerTableColumnsHitters();
        });

        SS.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getHittersPosition(RADIO_SS));
            setPlayerTableColumnsHitters();
        });

        OF.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getHittersPosition(RADIO_OF));
            setPlayerTableColumnsHitters();
        });

        U.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getHittersPosition(RADIO_U));
            setPlayerTableColumnsHitters();
        });

        P.setOnAction(e -> {
            playerTable = setPlayerTable(dataManager.getDraft().getPitcherPlayers());
            setPlayerTableColumnsPitchers();
        });

        // THEN THE EDITABLE NOTES COLUMN
        player_notes.setOnEditCommit(
                new EventHandler<CellEditEvent<String, String>>() {
                    @Override
                    public void handle(CellEditEvent<String, String> t) {
                        ((Player) playerTable.getItems().get(
                                t.getTablePosition().getRow())).setNotes(t.getNewValue());
                    }
                }
        );

        // THEN THE DRAFT EDITING CONTROLS
        // TEXT FIELDS HAVE A DIFFERENT WAY OF LISTENING FOR TEXT CHANGES
        // AND NOW THE LECTURE ADDING AND EDITING CONTROLS
        // AND NOW THE LECTURE TABLE
        // AND NOW THE HW ADDING AND EDITING CONTROLS
        // AND NOW THE LECTURE TABLE
    }

    // INIT A BUTTON AND ADD IT TO A CONTAINER IN A TOOLBAR
    private Button initChildButton(Pane toolbar, DraftKit_PropertyType icon, DraftKit_PropertyType tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        button.setMaxHeight(Double.MAX_VALUE);
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }

    // INIT A LABEL AND SET IT'S STYLESHEET CLASS
    private Label initLabel(DraftKit_PropertyType labelProperty, String styleClass) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String labelText = props.getProperty(labelProperty);
        Label label = new Label(labelText);
        label.getStyleClass().add(styleClass);
        return label;
    }

    // INIT A LABEL AND PLACE IT IN A GridPane INIT ITS PROPER PLACE
    private Label initGridLabel(GridPane container, DraftKit_PropertyType labelProperty, String styleClass, int col, int row, int colSpan, int rowSpan) {
        Label label = initLabel(labelProperty, styleClass);
        container.add(label, col, row, colSpan, rowSpan);
        return label;
    }

    // INIT A LABEL AND PUT IT IN A TOOLBAR
    private Label initChildLabel(Pane container, DraftKit_PropertyType labelProperty, String styleClass) {
        Label label = initLabel(labelProperty, styleClass);
        container.getChildren().add(label);
        return label;
    }

    // INIT A COMBO BOX AND PUT IT IN A GridPane
    private ComboBox initGridComboBox(GridPane container, int col, int row, int colSpan, int rowSpan) throws IOException {
        ComboBox comboBox = new ComboBox();
        container.add(comboBox, col, row, colSpan, rowSpan);
        return comboBox;
    }

    private TextField initGridTextField(GridPane container, int size, String initText, boolean editable, int col, int row, int colSpan, int rowSpan) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.add(tf, col, row, colSpan, rowSpan);
        return tf;
    }

    public String getSaveName() {
        return saveField.getText();
    }

    public void setSaveName(String s) {
        saveField.setText(s);
    }

    public void sortLineupTable() {
        if (teamComboBox.getSelectionModel().getSelectedItem() != null) {
        lineupTable.getSortOrder().add(lineup_position);
        lineup_position.setSortType(SortType.ASCENDING);
        lineup_position.setSortable(true);
        lineup_position.setSortable(false);
        }
    }
    
    public void setDraftPickTable() {
        summaryTable.setItems(FXCollections.observableArrayList(dataManager.getDraft().getDraftPicks()));
    }
}
