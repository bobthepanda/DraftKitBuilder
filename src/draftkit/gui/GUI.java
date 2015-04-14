package draftkit.gui;

import static draftkit.DraftKit_StartupConstants.*;
import draftkit.DraftKit_PropertyType;
//import draftkit.controller.DraftEditController;
import draftkit.data.DraftDataManager;
import draftkit.data.DraftDataView;
import draftkit.controller.FileController;
/*
import draftkit.controller.TeamEditController;
import draftkit.controller.PlayerEditController;
*/
import draftkit.data.Player;
import draftkit.data.Draft;
import draftkit.file.DraftFileManager;
//import draftkit.file.DraftSiteExporter;
import java.io.IOException;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
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

    // THIS HANDLES INTERACTIONS WITH DRAFT INFO CONTROLS
    //DraftEditController draftController;
    
    // THIS HANDLES REQUESTS TO ADD OR EDIT SCHEDULE STUFF
    //ScheduleEditController scheduleController;
    
    // THIS HANDLES REQUESTS TO ADD OR EDIT LECTURE STUFF
    //LectureEditController lectureController;

    // THIS HANDLES REQUESTS TO ADD OR EDIT HW STUFF
    //hwEditController hwController;
    
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
    VBox MLBTeamsScreen;
    Label MLBTeamsLabel;
    
    //PLAYER SCREEN CONTROLS
    HBox firstBox;
    Button addPlayerButton;
    Button removePlayerButton;
    Label searchLabel;
    
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
    
    //PLAYER SCREEN CONTROLS
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
    static final String COL_YEAR = "Year of Birth";
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
    static final String COL_ERA = "ERA";
    static final String COL_WHIP = "WHIP";
    
    // HERE ARE OUR DIALOGS
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    // SWITCH SCREEN TOOLBAR
    FlowPane switchToolbarPane;
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
     * When called this function puts the workspace into the window,
     * revealing the controls for editing a Draft.
     */
    public void activateWorkspace() {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            draftkitPane.setCenter(workspaceScrollPane);
            workspaceActivated = true;
        }
    }
    
    /**
     * This function takes all of the data out of the draftToReload 
     * argument and loads its values into the user interface controls.
     * 
     * @param draftToReload The Draft whose data we'll load into the GUI.
     */
    @Override
    public void reloadDraft(Draft draftToReload) {
        // FIRST ACTIVATE THE WORKSPACE IF NECESSARY
        if (!workspaceActivated) {
            activateWorkspace();
        }

        // WE DON'T WANT TO RESPOND TO EVENTS FORCED BY
        // OUR INITIALIZATION SELECTIONS
        //draftController.enable(false);

        // THE SCHEDULE ITEMS TABLE
        
       
        // THE LECTURES TABLE
        
        // THE HWS TABLE

        // NOW WE DO WANT TO RESPOND WHEN THE USER INTERACTS WITH OUR CONTROLS
        //draftController.enable(true);
    }

    /**
     * This method is used to activate/deactivate toolbar buttons when
     * they can and cannot be used so as to provide foolproof design.
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
     * This function loads all the values currently in the user interface
     * into the draft argument.
     * 
     * @param draft The draft to be updated using the data from the UI controls.
     */
    public void updateDraftInfo(Draft draft) {
    }

    /****************************************************************************/
    /* BELOW ARE ALL THE PRIVATE HELPER METHODS WE USE FOR INITIALIZING OUR GUI */
    /****************************************************************************/
    
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
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initSwitchToolbar() {
        switchToolbarPane = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        playersButton = initChildButton(switchToolbarPane, DraftKit_PropertyType.NEW_DRAFT_ICON, DraftKit_PropertyType.NEW_DRAFT_TOOLTIP, false);
        teamsButton = initChildButton(switchToolbarPane, DraftKit_PropertyType.LOAD_DRAFT_ICON, DraftKit_PropertyType.LOAD_DRAFT_TOOLTIP, false);
        standingsButton = initChildButton(switchToolbarPane, DraftKit_PropertyType.SAVE_DRAFT_ICON, DraftKit_PropertyType.SAVE_DRAFT_TOOLTIP, false);
        summaryButton = initChildButton(switchToolbarPane, DraftKit_PropertyType.EXPORT_PAGE_ICON, DraftKit_PropertyType.EXPORT_PAGE_TOOLTIP, false);
        MLBTeamsButton = initChildButton(switchToolbarPane, DraftKit_PropertyType.EXIT_ICON, DraftKit_PropertyType.EXIT_TOOLTIP, false);
    }
    
    private void initPlayerScreen() {
        
    }

    // CREATES AND SETS UP ALL THE CONTROLS TO GO IN THE APP WORKSPACE
    private void initWorkspace() throws IOException {
        // THE WORKSPACE HAS A FEW REGIONS, THIS 
        // IS FOR BASIC DRAFT EDITING CONTROLS
        initPlayerScreen();

        // THIS IS FOR SELECTING PAGE LINKS TO INCLUDE
        initSwitchToolbar();

        // THE TOP WORKSPACE HOLDS BOTH THE BASIC DRAFT INFO
        // CONTROLS AS WELL AS THE PAGE SELECTION CONTROLS

        // THIS IS FOR MANAGING SCHEDULE EDITING

        // THIS HOLDS ALL OUR WORKSPACE COMPONENTS, SO NOW WE MUST
        // ADD THE COMPONENTS WE'VE JUST INITIALIZED
        workspacePane = new BorderPane();
        workspacePane.setTop(playerScreen);
        workspacePane.setCenter(switchToolbarPane);
        workspacePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        // AND NOW PUT IT IN THE WORKSPACE
        workspaceScrollPane = new ScrollPane();
        workspaceScrollPane.setContent(workspacePane);
        workspaceScrollPane.setFitToWidth(true);

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

    // INIT ALL THE EVENT HANDLERS
    private void initEventHandlers() throws IOException {
        // FIRST THE FILE CONTROLS
        fileController = new FileController(messageDialog, yesNoCancelDialog, draftFileManager//, siteExporter
        );
        newDraftButton.setOnAction(e -> {
            fileController.handleNewDraftRequest(this);
        });
        loadDraftButton.setOnAction(e -> {
            fileController.handleLoadDraftRequest(this);
        });
        saveDraftButton.setOnAction(e -> {
            fileController.handleSaveDraftRequest(this, dataManager.getDraft());
        });
        exportSiteButton.setOnAction(e -> {
            fileController.handleExportDraftRequest(this);
        });
        exitButton.setOnAction(e -> {
            fileController.handleExitRequest(this);
        });

        // THEN THE DRAFT EDITING CONTROLS
       

        // TEXT FIELDS HAVE A DIFFERENT WAY OF LISTENING FOR TEXT CHANGES
       
        
        // AND NOW THE LECTURE ADDING AND EDITING CONTROLS
       
        
        // AND NOW THE LECTURE TABLE
        
        
        // AND NOW THE HW ADDING AND EDITING CONTROLS
        
        
        // AND NOW THE LECTURE TABLE
    }

    // REGISTER THE EVENT LISTENER FOR A TEXT FIELD
    
    /*
    private void registerTextFieldController(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            draftController.handleDraftChangeRequest(this);
        });
    }
    */
    
    // INIT A BUTTON AND ADD IT TO A CONTAINER IN A TOOLBAR
    private Button initChildButton(Pane toolbar, DraftKit_PropertyType icon, DraftKit_PropertyType tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
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

    // LOAD THE COMBO BOX TO HOLD Draft SUBJECTS
    /*
    private void loadSubjectComboBox(ArrayList<String> subjects) {
        for (String s : subjects) {
            draftSubjectComboBox.getItems().add(s);
        }
    }
    */

    // INIT A TEXT FIELD AND PUT IT IN A GridPane
    private TextField initGridTextField(GridPane container, int size, String initText, boolean editable, int col, int row, int colSpan, int rowSpan) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.add(tf, col, row, colSpan, rowSpan);
        return tf;
    }
}
