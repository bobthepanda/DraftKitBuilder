package draftkit.file;

import draftkit.data.Draft;
import draftkit.data.Hitter;
import draftkit.data.Pitcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface provides an abstraction of what a file manager should do. Note
 * that file managers know how to read and write courses, instructors, and subjects,
 * but now how to export sites.
 * 
 * @author Richard McKenna
 */
public interface DraftFileManager {
    public void                 saveDraft(Draft courseToSave, String filePath) throws IOException;
    public void                 loadDraft(Draft courseToLoad, String filePath) throws IOException;
    public ArrayList<Hitter>    loadHitters(String filePath) throws IOException;
    public ArrayList<Pitcher>   loadPitchers(String filePath) throws IOException;
}
