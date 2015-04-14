package draftkit;

/**
 * These are properties that are to be loaded from properties.xml. They
 * will provide custom labels and other UI details for our Draft Kit Builder
 * application. The reason for doing this is to swap out UI text and icons
 * easily without having to touch our code. It also allows for language
 * independence.
 * 
 * @author Henry Chin 109265023
 */
public enum DraftKit_PropertyType {
        // LOADED FROM properties.xml
        PROP_APP_TITLE,
        
        // APPLICATION ICONS
        NEW_DRAFT_ICON,
        LOAD_DRAFT_ICON,
        SAVE_DRAFT_ICON,
        VIEW_DRAFT_ICON,
        EXPORT_PAGE_ICON,
        DELETE_ICON,
        EXIT_ICON,
        ADD_ICON,
        MINUS_ICON,
        
        // APPLICATION TOOLTIPS FOR BUTTONS
        NEW_DRAFT_TOOLTIP,
        LOAD_DRAFT_TOOLTIP,
        SAVE_DRAFT_TOOLTIP,
        VIEW_DRAFT_TOOLTIP,
        EXPORT_PAGE_TOOLTIP,
        DELETE_TOOLTIP,
        EXIT_TOOLTIP,
        ADD_PLAYER_TOOLTIP,
        REMOVE_PLAYER_TOOLTIP,
        ADD_TEAM_TOOLTIP,
        REMOVE_TEAM_TOOLTIP,

        // FOR COURSE EDIT WORKSPACE
        /*COURSE_HEADING_LABEL,
        COURSE_INFO_LABEL,
        COURSE_SUBJECT_LABEL,
        COURSE_NUMBER_LABEL,
        COURSE_SEMESTER_LABEL,
        COURSE_YEAR_LABEL,
        COURSE_TITLE_LABEL,
        INSTRUCTOR_NAME_LABEL,
        INSTRUCTOR_URL_LABEL,
        PAGES_SELECTION_HEADING_LABEL,
        SCHEDULE_ITEMS_HEADING_LABEL,
        LECTURES_HEADING_LABEL,
        HWS_HEADING_LABEL,

        // PAGE CHECKBOX LABELS
        INDEX_CHECKBOX_LABEL,
        SYLLABUS_CHECKBOX_LABEL,
        SCHEDULE_CHECKBOX_LABEL,
        HWS_CHECKBOX_LABEL,
        PROJECTS_CHECKBOX_LABEL,
                
        // FOR SCHEDULE EDITING
        SCHEDULE_HEADING_LABEL,
        DATE_BOUNDARIES_LABEL,
        STARTING_MONDAY_LABEL,
        ENDING_FRIDAY_LABEL,
        LECTURE_DAY_SELECT_LABEL,
        
        // ERROR DIALOG MESSAGES
        START_DATE_AFTER_END_DATE_ERROR_MESSAGE,
        START_DATE_NOT_A_MONDAY_ERROR_MESSAGE,
        END_DATE_NOT_A_FRIDAY_ERROR_MESSAGE,
        ILLEGAL_DATE_MESSAGE,
        */
        
        // AND VERIFICATION MESSAGES
        NEW_DRAFT_CREATED_MESSAGE,
        DRAFT_LOADED_MESSAGE,
        DRAFT_SAVED_MESSAGE,
        SITE_EXPORTED_MESSAGE,
        SAVE_UNSAVED_WORK_MESSAGE,
        REMOVE_ITEM_MESSAGE
}
