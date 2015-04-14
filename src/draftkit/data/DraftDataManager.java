package draftkit.data;

import draftkit.file.DraftFileManager;
import java.util.ArrayList;

/**
 * This class manages a Draft, which means it knows how to
 * reset one with default values and generate useful dates.
 * 
 * @author Richard McKenna
 */
public class DraftDataManager {
    // THIS IS THE COURSE BEING EDITED
    Draft draft;
    
    // THIS IS THE UI, WHICH MUST BE UPDATED
    // WHENEVER OUR MODEL'S DATA CHANGES
    DraftDataView view;
    
    // THIS HELPS US LOAD THINGS FOR OUR COURSE
    DraftFileManager fileManager;
    
    // DEFAULT INITIALIZATION VALUES FOR NEW COURSES
    
    public DraftDataManager(   DraftDataView initView,
                                ArrayList<Hitter> hitters,
                                ArrayList<Pitcher> pitchers) {
        view = initView;
        draft = new Draft(hitters, pitchers);
    }
    
    /**
     * Accessor method for getting the Draft that this class manages.
     */
    public Draft getDraft() {
        return draft;
    }
    
    /**
     * Accessor method for getting the file manager, which knows how
     * to read and write draft data from/to files.
     */
    public DraftFileManager getFileManager() {
        return fileManager;
    }

    /**
     * Resets the draft to its default initialized settings, triggering
     * the UI to reflect these changes.
     */
    public void reset() {
        draft.getTeams().clear();
        
        // AND THEN FORCE THE UI TO RELOAD THE UPDATED COURSE
        view.reloadDraft(draft);
    }
}
