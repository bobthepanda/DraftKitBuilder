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
        EDIT_ICON,
        
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
        EDIT_TEAM_TOOLTIP,
        
        //FOR SCREEN SWITCH TOOLBAR
        TEAMS_ICON,
        PLAYERS_ICON,
        STANDINGS_ICON,
        SUMMARY_ICON,
        MLB_ICON,
        TEAMS_TOOLTIP,
        PLAYERS_TOOLTIP,
        STANDINGS_TOOLTIP,
        SUMMARY_TOOLTIP,
        MLB_TOOLTIP,

        // FOR DRAFT EDIT WORKSPACE
        FANTASY_TEAMS_LABEL,
        AVAILABLE_PLAYERS_LABEL,
        SEARCH_LABEL,
        FANTASY_STANDINGS_LABEL,
        DRAFT_SUMMARY_LABEL,
        MLB_TEAMS_LABEL,
        
        // FOR FANTASY TEAM SCREEN
        DRAFT_NAME_LABEL,
        SELECT_FANTASY_TEAM_LABEL,
        STARTING_LINEUP_LABEL,
        TAXI_SQUAD_LABEL,
        
        //FOR STANDINGS SCREEN
        PRO_TEAM_LABEL,
        
        // AND VERIFICATION MESSAGES
        NEW_DRAFT_CREATED_MESSAGE,
        DRAFT_LOADED_MESSAGE,
        DRAFT_SAVED_MESSAGE,
        SITE_EXPORTED_MESSAGE,
        SAVE_UNSAVED_WORK_MESSAGE,
        REMOVE_ITEM_MESSAGE,
        
        ILLEGAL_POSITION_MESSAGE,
        ILLEGAL_SALARY_MESSAGE,
        INSUFFICIENT_INFO_MESSAGE,
        ILLEGAL_NAME_MESSAGE,
        NO_SAVE_NAME_MESSAGE
}
