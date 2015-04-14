package draftkit.gui;

import static draftkit.DraftKit_StartupConstants.*;
import draftkit.DraftKit_PropertyType;
//import draftkit.controller.DraftEditController;
import draftkit.data.Draft;
import draftkit.data.DraftDataManager;
import draftkit.data.DraftDataView;
import draftkit.controller.FileController;
/*
import draftkit.controller.TeamEditController;
import draftkit.controller.PlayerEditController;
*/
import draftkit.data.Hitter;
import draftkit.data.Pitcher;
import draftkit.data.Player;
import draftkit.data.DraftTeam;
import draftkit.data.Team;
import draftkit.data.Draft;
import draftkit.file.DraftFileManager;
//import draftkit.file.DraftSiteExporter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
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

    // THIS MANAGES COURSE FILE I/O
    DraftFileManager draftFileManager;

    // THIS MANAGES EXPORTING OUR SITE PAGES
    //DraftSiteExporter siteExporter;

    // THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    //FileController fileController;

    // THIS HANDLES INTERACTIONS WITH COURSE INFO CONTROLS
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

    
    
    // AND TABLE COLUMNS
    static final String COL_DESCRIPTION = "Description";
    static final String COL_DATE = "Date";
    static final String COL_LINK = "Link";
    static final String COL_TOPIC = "Topic";
    static final String COL_SESSIONS = "Number of Sessions";
    static final String COL_NAME = "Name";
    static final String COL_TOPICS = "Topics";
    
    // HERE ARE OUR DIALOGS
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
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
    public DraftSiteExporter getSiteExporter() {
        return siteExporter;
    }

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
    public void setSiteExporter(DraftSiteExporter initSiteExporter) {
        siteExporter = initSiteExporter;
    }

    /**
     * This method fully initializes the user interface for use.
     *
     * @param windowTitle The text to appear in the UI window's title bar.
     * @param subjects The list of subjects to choose from.
     * @throws IOException Thrown if any initialization files fail to load.
     */
    public void initGUI(String windowTitle, ArrayList<String> subjects) throws IOException {
        // INIT THE DIALOGS
        initDialogs();
        
        // INIT THE TOOLBAR
        initFileToolbar();

        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
        initWorkspace(subjects);

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
        draftController.enable(false);

        // FIRST LOAD ALL THE BASIC COURSE INFO
        draftSubjectComboBox.setValue(draftToReload.getSubject());
        draftNumberTextField.setText("" + draftToReload.getNumber());
        draftSemesterComboBox.setValue(draftToReload.getSemester());
        draftYearComboBox.setValue(draftToReload.getYear());
        draftTitleTextField.setText(draftToReload.getTitle());
        instructorNameTextField.setText(draftToReload.getInstructor().getName());
        instructorURLTextField.setText(draftToReload.getInstructor().getHomepageURL());
        indexPageCheckBox.setSelected(draftToReload.hasDraftPage(DraftPage.INDEX));
        syllabusPageCheckBox.setSelected(draftToReload.hasDraftPage(DraftPage.SYLLABUS));
        schedulePageCheckBox.setSelected(draftToReload.hasDraftPage(DraftPage.SCHEDULE));
        hwsPageCheckBox.setSelected(draftToReload.hasDraftPage(DraftPage.HWS));
        projectsPageCheckBox.setSelected(draftToReload.hasDraftPage(DraftPage.PROJECTS));

        // THEN THE DATE PICKERS
        LocalDate startDate = draftToReload.getStartingMonday();
        startDatePicker.setValue(startDate);
        LocalDate endDate = draftToReload.getEndingFriday();
        endDatePicker.setValue(endDate);

        // THE LECTURE DAY CHECK BOXES
        mondayCheckBox.setSelected(draftToReload.hasLectureDay(DayOfWeek.MONDAY));
        tuesdayCheckBox.setSelected(draftToReload.hasLectureDay(DayOfWeek.TUESDAY));
        wednesdayCheckBox.setSelected(draftToReload.hasLectureDay(DayOfWeek.WEDNESDAY));
        thursdayCheckBox.setSelected(draftToReload.hasLectureDay(DayOfWeek.THURSDAY));
        fridayCheckBox.setSelected(draftToReload.hasLectureDay(DayOfWeek.FRIDAY));
        
        // THE SCHEDULE ITEMS TABLE
        
       
        // THE LECTURES TABLE
        
        // THE HWS TABLE

        // NOW WE DO WANT TO RESPOND WHEN THE USER INTERACTS WITH OUR CONTROLS
        draftController.enable(true);
    }

    /**
     * This method is used to activate/deactivate toolbar buttons when
     * they can and cannot be used so as to provide foolproof design.
     * 
     * @param saved Describes whether the loaded Draft has been saved or not.
     */
    public void updateToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT COURSE
        // HAS BEEN SAVED OR NOT
        saveDraftButton.setDisable(saved);

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST COURSE BEGINS
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
        draft.setSubject(Subject.valueOf(draftSubjectComboBox.getSelectionModel().getSelectedItem().toString()));
        draft.setNumber(Integer.parseInt(draftNumberTextField.getText()));
        draft.setSemester(Semester.valueOf(draftSemesterComboBox.getSelectionModel().getSelectedItem().toString()));
        draft.setYear((int) draftYearComboBox.getSelectionModel().getSelectedItem());
        draft.setTitle(draftTitleTextField.getText());
        Instructor instructor = draft.getInstructor();
        instructor.setName(instructorNameTextField.getText());
        instructor.setHomepageURL(instructorURLTextField.getText());
        updatePageUsingCheckBox(indexPageCheckBox, draft, DraftPage.INDEX);
        updatePageUsingCheckBox(syllabusPageCheckBox, draft, DraftPage.SYLLABUS);
        updatePageUsingCheckBox(schedulePageCheckBox, draft, DraftPage.SCHEDULE);
        updatePageUsingCheckBox(hwsPageCheckBox, draft, DraftPage.HWS);
        updatePageUsingCheckBox(projectsPageCheckBox, draft, DraftPage.PROJECTS);
        draft.setStartingMonday(startDatePicker.getValue());
        draft.setEndingFriday(endDatePicker.getValue());
        draft.selectLectureDay(DayOfWeek.MONDAY, mondayCheckBox.isSelected());
        draft.selectLectureDay(DayOfWeek.TUESDAY, tuesdayCheckBox.isSelected());
        draft.selectLectureDay(DayOfWeek.WEDNESDAY, wednesdayCheckBox.isSelected());
        draft.selectLectureDay(DayOfWeek.THURSDAY, thursdayCheckBox.isSelected());
        draft.selectLectureDay(DayOfWeek.FRIDAY, fridayCheckBox.isSelected());
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
        newDraftButton = initChildButton(fileToolbarPane, DraftKit_PropertyType.NEW_COURSE_ICON, DraftKit_PropertyType.NEW_COURSE_TOOLTIP, false);
        loadDraftButton = initChildButton(fileToolbarPane, DraftKit_PropertyType.LOAD_COURSE_ICON, DraftKit_PropertyType.LOAD_COURSE_TOOLTIP, false);
        saveDraftButton = initChildButton(fileToolbarPane, DraftKit_PropertyType.SAVE_COURSE_ICON, DraftKit_PropertyType.SAVE_COURSE_TOOLTIP, true);
        exportSiteButton = initChildButton(fileToolbarPane, DraftKit_PropertyType.EXPORT_PAGE_ICON, DraftKit_PropertyType.EXPORT_PAGE_TOOLTIP, true);
        exitButton = initChildButton(fileToolbarPane, DraftKit_PropertyType.EXIT_ICON, DraftKit_PropertyType.EXIT_TOOLTIP, false);
    }

    // CREATES AND SETS UP ALL THE CONTROLS TO GO IN THE APP WORKSPACE
    private void initWorkspace(ArrayList<String> subjects) throws IOException {
        // THE WORKSPACE HAS A FEW REGIONS, THIS 
        // IS FOR BASIC COURSE EDITING CONTROLS
        initBasicDraftInfoControls(subjects);

        // THIS IS FOR SELECTING PAGE LINKS TO INCLUDE
        initPageSelectionControls();

        // THE TOP WORKSPACE HOLDS BOTH THE BASIC COURSE INFO
        // CONTROLS AS WELL AS THE PAGE SELECTION CONTROLS
        initTopWorkspace();

        // THIS IS FOR MANAGING SCHEDULE EDITING
        initScheduleItemsControls();

        // THIS HOLDS ALL OUR WORKSPACE COMPONENTS, SO NOW WE MUST
        // ADD THE COMPONENTS WE'VE JUST INITIALIZED
        workspacePane = new BorderPane();
        workspacePane.setTop(topWorkspacePane);
        workspacePane.setCenter(schedulePane);
        workspacePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        // AND NOW PUT IT IN THE WORKSPACE
        workspaceScrollPane = new ScrollPane();
        workspaceScrollPane.setContent(workspacePane);
        workspaceScrollPane.setFitToWidth(true);

        // NOTE THAT WE HAVE NOT PUT THE WORKSPACE INTO THE WINDOW,
        // THAT WILL BE DONE WHEN THE USER EITHER CREATES A NEW
        // COURSE OR LOADS AN EXISTING ONE FOR EDITING
        workspaceActivated = false;
    }
    
    // INITIALIZES THE TOP PORTION OF THE WORKWPACE UI
    private void initTopWorkspace() {
        // HERE'S THE SPLIT PANE, ADD THE TWO GROUPS OF CONTROLS
        topWorkspaceSplitPane = new SplitPane();
        topWorkspaceSplitPane.getItems().add(draftInfoPane);
        topWorkspaceSplitPane.getItems().add(pagesSelectionPane);

        // THE TOP WORKSPACE PANE WILL ONLY DIRECTLY HOLD 2 THINGS, A LABEL
        // AND A SPLIT PANE, WHICH WILL HOLD 2 ADDITIONAL GROUPS OF CONTROLS
        topWorkspacePane = new VBox();
        topWorkspacePane.getStyleClass().add(CLASS_BORDERED_PANE);

        // HERE'S THE LABEL
        draftHeadingLabel = initChildLabel(topWorkspacePane, DraftKit_PropertyType.COURSE_HEADING_LABEL, CLASS_HEADING_LABEL);

        // AND NOW ADD THE SPLIT PANE
        topWorkspacePane.getChildren().add(topWorkspaceSplitPane);
    }

    // INITIALIZES THE CONTROLS IN THE LEFT HALF OF THE TOP WORKSPACE
    private void initBasicDraftInfoControls(ArrayList<String> subjects) throws IOException {
        // THESE ARE THE CONTROLS FOR THE BASIC SCHEDULE PAGE HEADER INFO
        // WE'LL ARRANGE THEM IN THE LEFT SIDE IN A VBox
        draftInfoPane = new GridPane();

        // FIRST THE HEADING LABEL
        draftInfoLabel = initGridLabel(draftInfoPane, DraftKit_PropertyType.COURSE_INFO_LABEL, CLASS_SUBHEADING_LABEL, 0, 0, 4, 1);

        // THEN CONTROLS FOR CHOOSING THE SUBJECT
        draftSubjectLabel = initGridLabel(draftInfoPane, DraftKit_PropertyType.COURSE_SUBJECT_LABEL, CLASS_PROMPT_LABEL, 0, 1, 1, 1);
        draftSubjectComboBox = initGridComboBox(draftInfoPane, 1, 1, 1, 1);
        loadSubjectComboBox(subjects);

        // THEN CONTROLS FOR UPDATING THE COURSE NUMBER
        draftNumberLabel = initGridLabel(draftInfoPane, DraftKit_PropertyType.COURSE_NUMBER_LABEL, CLASS_PROMPT_LABEL, 2, 1, 1, 1);
        draftNumberTextField = initGridTextField(draftInfoPane, SMALL_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 3, 1, 1, 1);

        // THEN THE COURSE SEMESTER
        draftSemesterLabel = initGridLabel(draftInfoPane, DraftKit_PropertyType.COURSE_SEMESTER_LABEL, CLASS_PROMPT_LABEL, 0, 2, 1, 1);
        draftSemesterComboBox = initGridComboBox(draftInfoPane, 1, 2, 1, 1);
        ObservableList<String> semesterChoices = FXCollections.observableArrayList();
        for (Semester s : Semester.values()) {
            semesterChoices.add(s.toString());
        }
        draftSemesterComboBox.setItems(semesterChoices);

        // THEN THE COURSE YEAR
        draftYearLabel = initGridLabel(draftInfoPane, DraftKit_PropertyType.COURSE_YEAR_LABEL, CLASS_PROMPT_LABEL, 2, 2, 1, 1);
        draftYearComboBox = initGridComboBox(draftInfoPane, 3, 2, 1, 1);
        ObservableList<Integer> yearChoices = FXCollections.observableArrayList();
        for (int i = LocalDate.now().getYear(); i <= LocalDate.now().getYear() + 1; i++) {
            yearChoices.add(i);
        }
        draftYearComboBox.setItems(yearChoices);

        // THEN THE COURSE TITLE
        draftTitleLabel = initGridLabel(draftInfoPane, DraftKit_PropertyType.COURSE_TITLE_LABEL, CLASS_PROMPT_LABEL, 0, 3, 1, 1);
        draftTitleTextField = initGridTextField(draftInfoPane, LARGE_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 1, 3, 3, 1);

        // THEN THE INSTRUCTOR NAME
        instructorNameLabel = initGridLabel(draftInfoPane, DraftKit_PropertyType.INSTRUCTOR_NAME_LABEL, CLASS_PROMPT_LABEL, 0, 4, 1, 1);
        instructorNameTextField = initGridTextField(draftInfoPane, LARGE_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 1, 4, 3, 1);

        // AND THE INSTRUCTOR HOMEPAGE
        instructorURLLabel = initGridLabel(draftInfoPane, DraftKit_PropertyType.INSTRUCTOR_URL_LABEL, CLASS_PROMPT_LABEL, 0, 5, 1, 1);
        instructorURLTextField = initGridTextField(draftInfoPane, LARGE_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 1, 5, 3, 1);
    }

    // INITIALIZES THE CONTROLS IN THE RIGHT HALF OF THE TOP WORKSPACE
    private void initPageSelectionControls() {
        // THESE ARE THE CONTROLS FOR SELECTING WHICH PAGES THE SCHEDULE
        // PAGE WILL HAVE TO LINK TO
        pagesSelectionPane = new VBox();
        pagesSelectionPane.getStyleClass().add(CLASS_SUBJECT_PANE);
        pagesSelectionLabel = initChildLabel(pagesSelectionPane, DraftKit_PropertyType.PAGES_SELECTION_HEADING_LABEL, CLASS_SUBHEADING_LABEL);
        indexPageCheckBox = initChildCheckBox(pagesSelectionPane, DraftSiteExporter.INDEX_PAGE);
        syllabusPageCheckBox = initChildCheckBox(pagesSelectionPane, DraftSiteExporter.SYLLABUS_PAGE);
        schedulePageCheckBox = initChildCheckBox(pagesSelectionPane, DraftSiteExporter.SCHEDULE_PAGE);
        hwsPageCheckBox = initChildCheckBox(pagesSelectionPane, DraftSiteExporter.HWS_PAGE);
        projectsPageCheckBox = initChildCheckBox(pagesSelectionPane, DraftSiteExporter.PROJECTS_PAGE);
    }
    
    // INITIALIZE THE SCHEDULE ITEMS CONTROLS
    private void initScheduleItemsControls() {
        // FOR THE LEFT
        dateBoundariesPane = new GridPane();
        dateBoundariesLabel = initGridLabel(dateBoundariesPane, DraftKit_PropertyType.DATE_BOUNDARIES_LABEL, CLASS_SUBHEADING_LABEL, 0, 0, 1, 1);
        startDateLabel = initGridLabel(dateBoundariesPane, DraftKit_PropertyType.STARTING_MONDAY_LABEL, CLASS_PROMPT_LABEL, 0, 1, 1, 1);
        startDatePicker = initGridDatePicker(dateBoundariesPane, 1, 1, 1, 1);
        endDateLabel = initGridLabel(dateBoundariesPane, DraftKit_PropertyType.ENDING_FRIDAY_LABEL, CLASS_PROMPT_LABEL, 0, 2, 1, 1);
        endDatePicker = initGridDatePicker(dateBoundariesPane, 1, 2, 1, 1);

        // THIS ONE IS ON THE RIGHT
        lectureDaySelectorPane = new VBox();
        lectureDaySelectLabel = initChildLabel(lectureDaySelectorPane, DraftKit_PropertyType.LECTURE_DAY_SELECT_LABEL, CLASS_SUBHEADING_LABEL);
        mondayCheckBox = initChildCheckBox(lectureDaySelectorPane, DraftSiteExporter.MONDAY_HEADER);
        tuesdayCheckBox = initChildCheckBox(lectureDaySelectorPane, DraftSiteExporter.TUESDAY_HEADER);
        wednesdayCheckBox = initChildCheckBox(lectureDaySelectorPane, DraftSiteExporter.WEDNESDAY_HEADER);
        thursdayCheckBox = initChildCheckBox(lectureDaySelectorPane, DraftSiteExporter.THURSDAY_HEADER);
        fridayCheckBox = initChildCheckBox(lectureDaySelectorPane, DraftSiteExporter.FRIDAY_HEADER);

        // THIS SPLITS THE TOP
        splitScheduleInfoPane = new SplitPane();
        splitScheduleInfoPane.getItems().add(dateBoundariesPane);
        splitScheduleInfoPane.getItems().add(lectureDaySelectorPane);
        
        // NOW THE CONTROLS FOR ADDING SCHEDULE ITEMS
        scheduleItemsBox = new VBox();
        scheduleItemsToolbar = new HBox();
        scheduleItemsLabel = initLabel(DraftKit_PropertyType.SCHEDULE_ITEMS_HEADING_LABEL, CLASS_SUBHEADING_LABEL);
        addScheduleItemButton = initChildButton(scheduleItemsToolbar, DraftKit_PropertyType.ADD_ICON, DraftKit_PropertyType.ADD_ITEM_TOOLTIP, false);
        removeScheduleItemButton = initChildButton(scheduleItemsToolbar, DraftKit_PropertyType.MINUS_ICON, DraftKit_PropertyType.REMOVE_ITEM_TOOLTIP, false);
        scheduleItemsTable = new TableView();
        scheduleItemsBox.getChildren().add(scheduleItemsLabel);
        scheduleItemsBox.getChildren().add(scheduleItemsToolbar);
        scheduleItemsBox.getChildren().add(scheduleItemsTable);
        scheduleItemsBox.getStyleClass().add(CLASS_BORDERED_PANE);
        
        // NOW SETUP THE TABLE COLUMNS
        itemDescriptionsColumn = new TableColumn(COL_DESCRIPTION);
        itemDatesColumn = new TableColumn(COL_DATE);
        linkColumn = new TableColumn(COL_LINK);
        
        // AND LINK THE COLUMNS TO THE DATA
        itemDescriptionsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("description"));
        itemDatesColumn.setCellValueFactory(new PropertyValueFactory<LocalDate, String>("date"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<URL, String>("link"));
        scheduleItemsTable.getColumns().add(itemDescriptionsColumn);
        scheduleItemsTable.getColumns().add(itemDatesColumn);
        scheduleItemsTable.getColumns().add(linkColumn);
        scheduleItemsTable.setItems(dataManager.getDraft().getScheduleItems());
        
        //NOW THE CONTROLS FOR ADDING LECTURES
        lectureBox = new VBox();
        lectureToolbar = new HBox();
        lectureLabel = initLabel(DraftKit_PropertyType.LECTURES_HEADING_LABEL, CLASS_SUBHEADING_LABEL);
        addLectureButton = initChildButton(lectureToolbar, DraftKit_PropertyType.ADD_ICON, DraftKit_PropertyType.ADD_ITEM_TOOLTIP, false);
        removeLectureButton = initChildButton(lectureToolbar, DraftKit_PropertyType.MINUS_ICON, DraftKit_PropertyType.REMOVE_ITEM_TOOLTIP, false);
        upButton = initChildButton(lectureToolbar,DraftKit_PropertyType.MOVE_UP_ICON,DraftKit_PropertyType.MOVE_UP_LECTURE_TOOLTIP, false);
        downButton = initChildButton(lectureToolbar,DraftKit_PropertyType.MOVE_DOWN_ICON,DraftKit_PropertyType.MOVE_DOWN_LECTURE_TOOLTIP,false);
        lectureTable = new TableView();
        lectureBox.getChildren().add(lectureLabel);
        lectureBox.getChildren().add(lectureToolbar);
        lectureBox.getChildren().add(lectureTable);
        lectureBox.getStyleClass().add(CLASS_BORDERED_PANE);
        
        // NOW SETUP THE TABLE COLUMNS
        lectureTopicColumn = new TableColumn(COL_TOPIC);
        numSessionsColumn = new TableColumn(COL_SESSIONS);
        
        // AND LINK THE COLUMNS TO THE DATA
        lectureTopicColumn.setCellValueFactory(new PropertyValueFactory<String, String>("topic"));
        numSessionsColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("sessions"));
        lectureTable.getColumns().add(lectureTopicColumn);
        lectureTable.getColumns().add(numSessionsColumn);
        lectureTable.setItems(dataManager.getDraft().getLectures());
        
        // NOW THE CONTROLS FOR ADDING HW ITEMS
        hwBox = new VBox();
        hwToolbar = new HBox();
        hwLabel = initLabel(DraftKit_PropertyType.HWS_HEADING_LABEL, CLASS_SUBHEADING_LABEL);
        addHwButton = initChildButton(hwToolbar, DraftKit_PropertyType.ADD_ICON, DraftKit_PropertyType.ADD_ITEM_TOOLTIP, false);
        removeHwButton = initChildButton(hwToolbar, DraftKit_PropertyType.MINUS_ICON, DraftKit_PropertyType.REMOVE_ITEM_TOOLTIP, false);
        hwTable = new TableView();
        hwBox.getChildren().add(hwLabel);
        hwBox.getChildren().add(hwToolbar);
        hwBox.getChildren().add(hwTable);
        hwBox.getStyleClass().add(CLASS_BORDERED_PANE);
        
        // NOW SETUP THE TABLE COLUMNS
        hwNameColumn = new TableColumn(COL_NAME);
        hwTopicColumn = new TableColumn(COL_TOPICS);
        hwDatesColumn = new TableColumn(COL_DATE);
        
        // AND LINK THE COLUMNS TO THE DATA
        hwNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        hwTopicColumn.setCellValueFactory(new PropertyValueFactory<LocalDate, String>("topics"));
        hwDatesColumn.setCellValueFactory(new PropertyValueFactory<URL, String>("date"));
        hwTable.getColumns().add(hwNameColumn);
        hwTable.getColumns().add(hwTopicColumn);
        hwTable.getColumns().add(hwDatesColumn);
        hwTable.setItems(dataManager.getDraft().getAssignments());
        
        // NOW LET'S ASSEMBLE ALL THE CONTAINERS TOGETHER

        // THIS IS FOR STUFF IN THE TOP OF THE SCHEDULE PANE, WE NEED TO PUT TWO THINGS INSIDE
        scheduleInfoPane = new VBox();

        // FIRST OUR SCHEDULE HEADER
        scheduleInfoHeadingLabel = initChildLabel(scheduleInfoPane, DraftKit_PropertyType.SCHEDULE_HEADING_LABEL, CLASS_HEADING_LABEL);

        // AND THEN THE SPLIT PANE
        scheduleInfoPane.getChildren().add(splitScheduleInfoPane);

        // FINALLY, EVERYTHING IN THIS REGION ULTIMATELY GOES INTO schedulePane
        schedulePane = new VBox();
        schedulePane.getChildren().add(scheduleInfoPane);
        schedulePane.getChildren().add(scheduleItemsBox);
        schedulePane.getChildren().add(lectureBox);
        schedulePane.getChildren().add(hwBox);
        schedulePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        
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
        // THE USER STARTS EDITING A COURSE
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
        fileController = new FileController(messageDialog, yesNoCancelDialog, draftFileManager, siteExporter);
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

        // THEN THE COURSE EDITING CONTROLS
        draftController = new DraftEditController();
        draftSubjectComboBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        draftSemesterComboBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        draftYearComboBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        indexPageCheckBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        syllabusPageCheckBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        schedulePageCheckBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        hwsPageCheckBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        projectsPageCheckBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });

        // TEXT FIELDS HAVE A DIFFERENT WAY OF LISTENING FOR TEXT CHANGES
        registerTextFieldController(draftNumberTextField);
        registerTextFieldController(draftTitleTextField);
        registerTextFieldController(instructorNameTextField);
        registerTextFieldController(instructorURLTextField);

        // THE DATE SELECTION ONES HAVE PARTICULAR CONCERNS, AND SO
        // GO THROUGH A DIFFERENT METHOD
        startDatePicker.setOnAction(e -> {
            draftController.handleDateSelectionRequest(this, startDatePicker, endDatePicker);
        });
        endDatePicker.setOnAction(e -> {
            draftController.handleDateSelectionRequest(this, startDatePicker, endDatePicker);
        });

        // AND THE LECTURE DAYS CHECKBOXES
        mondayCheckBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        tuesdayCheckBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        wednesdayCheckBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        thursdayCheckBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        fridayCheckBox.setOnAction(e -> {
            draftController.handleDraftChangeRequest(this);
        });
        
        // AND NOW THE SCHEDULE ITEM ADDING AND EDITING CONTROLS
        scheduleController = new ScheduleEditController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        addScheduleItemButton.setOnAction(e -> {
            scheduleController.handleAddScheduleItemRequest(this);
        });
        removeScheduleItemButton.setOnAction(e -> {
            scheduleController.handleRemoveScheduleItemRequest(this, scheduleItemsTable.getSelectionModel().getSelectedItem());
        });
        
        // AND NOW THE SCHEDULE ITEMS TABLE
        scheduleItemsTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                ScheduleItem si = scheduleItemsTable.getSelectionModel().getSelectedItem();
                scheduleController.handleEditScheduleItemRequest(this, si);
            }
        });
        
        // AND NOW THE LECTURE ADDING AND EDITING CONTROLS
        lectureController = new LectureEditController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        addLectureButton.setOnAction(e -> {
            lectureController.handleAddLectureRequest(this);
        });
        removeLectureButton.setOnAction(e -> {
            lectureController.handleRemoveLectureRequest(this, lectureTable.getSelectionModel().getSelectedItem());
        });
        
        // AND NOW THE LECTURE MOVING CONTROLS
        upButton.setOnAction(e -> {
            lectureController.handleUpRequest(this, lectureTable.getSelectionModel(), lectureTable.getSelectionModel().getSelectedItem());
        });
        
        downButton.setOnAction(e -> {
            lectureController.handleDownRequest(this, lectureTable.getSelectionModel(), lectureTable.getSelectionModel().getSelectedItem());
        });
        
        // AND NOW THE LECTURE TABLE
        lectureTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                Lecture l = lectureTable.getSelectionModel().getSelectedItem();
                lectureController.handleEditLectureRequest(this, l);
            }
        });
        
        // AND NOW THE HW ADDING AND EDITING CONTROLS
        hwController = new hwEditController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        addHwButton.setOnAction(e -> {
            hwController.handleAddHwRequest(this);
        });
        removeHwButton.setOnAction(e -> {
            hwController.handleRemoveHwRequest(this, hwTable.getSelectionModel().getSelectedItem());
        });
        
        // AND NOW THE LECTURE TABLE
        hwTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                Assignment hw = hwTable.getSelectionModel().getSelectedItem();
                hwController.handleEditHwRequest(this, hw);
            }
        });
    }

    // REGISTER THE EVENT LISTENER FOR A TEXT FIELD
    private void registerTextFieldController(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            draftController.handleDraftChangeRequest(this);
        });
    }
    
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
    private void loadSubjectComboBox(ArrayList<String> subjects) {
        for (String s : subjects) {
            draftSubjectComboBox.getItems().add(s);
        }
    }

    // INIT A TEXT FIELD AND PUT IT IN A GridPane
    private TextField initGridTextField(GridPane container, int size, String initText, boolean editable, int col, int row, int colSpan, int rowSpan) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.add(tf, col, row, colSpan, rowSpan);
        return tf;
    }

    // INIT A DatePicker AND PUT IT IN A GridPane
    private DatePicker initGridDatePicker(GridPane container, int col, int row, int colSpan, int rowSpan) {
        DatePicker datePicker = new DatePicker();
        container.add(datePicker, col, row, colSpan, rowSpan);
        return datePicker;
    }

    // INIT A CheckBox AND PUT IT IN A TOOLBAR
    private CheckBox initChildCheckBox(Pane container, String text) {
        CheckBox cB = new CheckBox(text);
        container.getChildren().add(cB);
        return cB;
    }

    // INIT A DatePicker AND PUT IT IN A CONTAINER
    private DatePicker initChildDatePicker(Pane container) {
        DatePicker dp = new DatePicker();
        container.getChildren().add(dp);
        return dp;
    }
    
    // LOADS CHECKBOX DATA INTO A Draft OBJECT REPRESENTING A DraftPage
    private void updatePageUsingCheckBox(CheckBox cB, Draft draft, DraftPage cP) {
        if (cB.isSelected()) {
            draft.selectPage(cP);
        } else {
            draft.unselectPage(cP);
        }
    }    
}
