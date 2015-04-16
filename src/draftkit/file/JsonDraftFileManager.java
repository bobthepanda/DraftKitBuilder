package draftkit.file;

import static draftkit.DraftKit_StartupConstants.PATH_DRAFTS;
import draftkit.data.Draft;
import draftkit.data.Hitter;
import draftkit.data.Pitcher;
import draftkit.data.Player;
import draftkit.data.Team;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonValue;

/**
 * This is a DraftFileManager that uses the JSON file format to implement the
 * necessary functions for loading and saving different data for our drafts,
 * instructors, and subjects.
 *
 * @author Richard McKenna
 */
public class JsonDraftFileManager implements DraftFileManager {

    // JSON FILE READING AND WRITING CONSTANTS

    String JSON_TEAM = "TEAM";
    String JSON_LAST_NAME = "LAST_NAME";
    String JSON_FIRST_NAME = "FIRST_NAME";
    String JSON_NOTES = "NOTES";
    String JSON_YEAR_OF_BIRTH = "YEAR_OF_BIRTH";
    String JSON_NATION_OF_BIRTH = "NATION_OF_BIRTH";
    String JSON_PITCHERS = "Pitchers";
    String JSON_PITCHERS_IP = "IP";
    String JSON_PITCHERS_ER = "ER";
    String JSON_PITCHERS_W = "W";
    String JSON_PITCHERS_SV = "SV";
    String JSON_PITCHERS_H = "H";
    String JSON_PITCHERS_BB = "BB";
    String JSON_PITCHERS_K = "K";
    String JSON_HITTERS = "Hitters";
    String JSON_HITTERS_QP = "QP";
    String JSON_HITTERS_AB = "AB";
    String JSON_HITTERS_R = "R";
    String JSON_HITTERS_H = "H";
    String JSON_HITTERS_HR = "HR";
    String JSON_HITTERS_RBI = "RBI";
    String JSON_HITTERS_SB = "SB";
    String JSON_EXT = ".json";
    String SLASH = "/";
    int i = 0;

    /**
     * This method saves all the data associated with a draft to a JSON file.
     *
     * @param draftToSave The draft whose data we are saving.
     *
     * @throws IOException Thrown when there are issues writing to the JSON
     * file.
     */
    @Override
    public void saveDraft(Draft draftToSave) throws IOException {
        // BUILD THE FILE PATH
        /*String draftListing = "Draft" + i;
        String jsonFilePath = PATH_DRAFTS + SLASH + draftListing + JSON_EXT;
        i++;

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);

        // MAKE A JSON ARRAY FOR THE PAGES ARRAY
        JsonArray pagesJsonArray = makePagesJsonArray(draftToSave.getPages());

        // AND AN OBJECT FOR THE HITTERS
        JsonObject instructorJsonObject = makeInstructorJsonObject(draftToSave.getInstructor());

        // ONE FOR EACH OF OUR DATES
        JsonObject startingMondayJsonObject = makeLocalDateJsonObject(draftToSave.getStartingMonday());
        JsonObject endingFridayJsonObject = makeLocalDateJsonObject(draftToSave.getEndingFriday());

        // THE LECTURE DAYS ARRAY
        JsonArray lectureDaysJsonArray = makeLectureDaysJsonArray(draftToSave.getLectureDays());

        // THE SCHEDULE ITEMS ARRAY
        JsonArray scheduleItemsJsonArray = makeHittersJsonArray(draftToSave.getHitters());

        // THE LECTURES ARRAY
        JsonArray lecturesJsonArray = makeLecturesJsonArray(draftToSave.getLectures());

        // THE HWS ARRAY
        JsonArray hwsJsonArray = makeHWsJsonArray(draftToSave.getAssignments());

        // NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
        JsonObject draftJsonObject = Json.createObjectBuilder()
                .add(JSON_SUBJECT, draftToSave.getSubject().toString())
                .add(JSON_NUMBER, draftToSave.getNumber())
                .add(JSON_TITLE, draftToSave.getTitle())
                .add(JSON_SEMESTER, draftToSave.getSemester().toString())
                .add(JSON_YEAR, draftToSave.getYear())
                .add(JSON_PAGES, pagesJsonArray)
                .add(JSON_HITTERS, instructorJsonObject)
                .add(JSON_STARTING_MONDAY, startingMondayJsonObject)
                .add(JSON_ENDING_FRIDAY, endingFridayJsonObject)
                .add(JSON_LECTURE_DAYS, lectureDaysJsonArray)
                .add(JSON_SCHEDULE_ITEMS, scheduleItemsJsonArray)
                .add(JSON_LECTURES, lecturesJsonArray)
                .add(JSON_HWS, hwsJsonArray)
                .build();

        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(draftJsonObject);*/
    }

    /**
     * Loads the draftToLoad argument using the data found in the json file.
     *
     * @param draftToLoad Draft to load.
     * @param jsonFilePath File containing the data to load.
     *
     * @throws IOException Thrown when IO fails.
     */
    @Override
    public void loadDraft(Draft draftToLoad, String jsonFilePath) throws IOException {
        /*// LOAD THE JSON FILE WITH ALL THE DATA
         JsonObject json = loadJSONFile(jsonFilePath);

         // NOW LOAD THE COURSE
         draftToLoad.setSubject(Subject.valueOf(json.getString(JSON_SUBJECT)));
         draftToLoad.setNumber(json.getInt(JSON_NUMBER));
         draftToLoad.setSemester(Semester.valueOf(json.getString(JSON_SEMESTER)));
         draftToLoad.setYear(json.getInt(JSON_YEAR));
         draftToLoad.setTitle(json.getString(JSON_TITLE));

         // GET THE PAGES TO INCLUDE 
         draftToLoad.clearPages();
         JsonArray jsonPagesArray = json.getJsonArray(JSON_PAGES);
         for (int i = 0; i < jsonPagesArray.size(); i++)
         draftToLoad.addPage(DraftPage.valueOf(jsonPagesArray.getString(i)));

         // GET THE LECTURE DAYS TO INCLUDE
         draftToLoad.clearLectureDays();
         JsonArray jsonLectureDaysArray = json.getJsonArray(JSON_LECTURE_DAYS);
         for (int i = 0; i < jsonLectureDaysArray.size(); i++)
         draftToLoad.addLectureDay(DayOfWeek.valueOf(jsonLectureDaysArray.getString(i)));

         // LOAD AND SET THE HITTERS
         JsonObject jsonInstructor = json.getJsonObject(JSON_HITTERS);
         Instructor instructor = new Instructor( jsonInstructor.getString(JSON_HITTERS_NAME),
         jsonInstructor.getString(JSON_HOMEPAGE_URL));
         draftToLoad.setInstructor(instructor);

         // GET THE STARTING MONDAY
         JsonObject startingMonday = json.getJsonObject(JSON_STARTING_MONDAY);
         int year = startingMonday.getInt(JSON_YEAR);
         int month = startingMonday.getInt(JSON_MONTH);
         int day = startingMonday.getInt(JSON_DAY);
         draftToLoad.setStartingMonday(LocalDate.of(year, month, day));

         // GET THE ENDING FRIDAY
         JsonObject endingFriday = json.getJsonObject(JSON_ENDING_FRIDAY);
         year = endingFriday.getInt(JSON_YEAR);
         month = endingFriday.getInt(JSON_MONTH);
         day = endingFriday.getInt(JSON_DAY);
         draftToLoad.setEndingFriday(LocalDate.of(year, month, day));

         // GET THE SCHEDULE ITEMS
         draftToLoad.clearScheduleItems();
         JsonArray jsonScheduleItemsArray = json.getJsonArray(JSON_SCHEDULE_ITEMS);
         for (int i = 0; i < jsonScheduleItemsArray.size(); i++) {
         JsonObject jso = jsonScheduleItemsArray.getJsonObject(i);
         ScheduleItem si = new ScheduleItem();
         si.setDescription(jso.getString(JSON_SCHEDULE_ITEM_DESCRIPTION));
         JsonObject jsoDate = jso.getJsonObject(JSON_SCHEDULE_ITEM_DATE);
         year = jsoDate.getInt(JSON_YEAR);
         month = jsoDate.getInt(JSON_MONTH);
         day = jsoDate.getInt(JSON_DAY);            
         si.setDate(LocalDate.of(year, month, day));
         si.setLink(jso.getString(JSON_SCHEDULE_ITEM_LINK));

         // ADD IT TO THE COURSE
         draftToLoad.addScheduleItem(si);
         }

         // GET THE LECTURES
         JsonArray jsonLecturesArray = json.getJsonArray(JSON_LECTURES);
         draftToLoad.clearLectures();
         for (int i = 0; i < jsonLecturesArray.size(); i++) {
         JsonObject jso = jsonLecturesArray.getJsonObject(i);
         Lecture l = new Lecture();
         l.setTopic(jso.getString(JSON_LECTURE_TOPIC));
         l.setSessions(Integer.parseInt(jso.getString(JSON_LECTURE_SESSIONS));

         // ADD IT TO THE COURSE
         draftToLoad.addLecture(l);
         }

         // GET THE HWS
         JsonArray jsonHWsArray = json.getJsonArray(JSON_HWS);
         draftToLoad.clearHWs();
         for (int i = 0; i < jsonHWsArray.size(); i++) {
         JsonObject jso = jsonHWsArray.getJsonObject(i);
         Assignment a = new Assignment();
         a.setName(jso.getString(JSON_ASSIGNMENT_NAME));
         JsonObject jsoDate = jso.getJsonObject(JSON_ASSIGNMENT_DATE);
         year = jsoDate.getInt(JSON_YEAR);
         month = jsoDate.getInt(JSON_MONTH);
         day = jsoDate.getInt(JSON_DAY);            
         a.setDate(LocalDate.of(year, month, day));
         a.setTopics(jso.getString(JSON_ASSIGNMENT_TOPICS));

         // ADD IT TO THE COURSE
         draftToLoad.addAssignment(a);
         }*/
    }

    /**
     * This function saves the last instructor to a json file. This provides a
     * convenience to the user, who is likely always the same instructor.
     *
     * @param hitters List of Hitters to save.
     * @param jsonFilePath File in which to put the data.
     * @throws IOException Thrown when I/O fails.
     */
    @Override
    public void saveHitters(List<Hitter> hitters, String jsonFilePath) throws IOException {
        JsonArray hitterArray = makeHittersJsonArray(hitters);
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);
        JsonObject hitterJsonObject = Json.createObjectBuilder()
                                    .add(JSON_PITCHERS, hitterArray)
                .build();
        
        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(hitterJsonObject);
    }

    /**
     * Saves the subjects list to a json file.
     *
     * @param pitchers List of Pitchers to save.
     * @param jsonFilePath Path of json file.
     * @throws IOException Thrown when I/O fails.
     */
    @Override
    public void savePitchers(List<Pitcher> pitchers, String jsonFilePath) throws IOException {
        JsonArray pitcherArray = makePitchersJsonArray(pitchers);
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);
        JsonObject pitcherJsonObject = Json.createObjectBuilder()
                                    .add(JSON_PITCHERS, pitcherArray)
                .build();
        
        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(pitcherJsonObject);
    }

    /**
     * Loads pitchers from the json file.
     *
     * @param jsonFilePath Json file containing the subjects.
     * @return List full of Pitchers loaded from the file.
     * @throws IOException Thrown when I/O fails.
     */
    public ArrayList<Pitcher> loadPitchers(String jsonFilePath) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        JsonArray jsonPitchersArray = json.getJsonArray(JSON_PITCHERS);
        ArrayList<Pitcher> pitchers = new ArrayList<Pitcher>();
        for (int i = 0; i < jsonPitchersArray.size(); i++) {
            JsonObject jso = jsonPitchersArray.getJsonObject(i);
            Pitcher p = new Pitcher();
            p.setProTeam(jso.getString(JSON_TEAM));
            p.setLastName(jso.getString(JSON_LAST_NAME));
            p.setFirstName(jso.getString(JSON_FIRST_NAME));
            p.setIp(Double.parseDouble(jso.getString(JSON_PITCHERS_IP)));
            p.setEr(Integer.parseInt(jso.getString(JSON_PITCHERS_ER)));
            p.setR_w(Integer.parseInt(jso.getString(JSON_PITCHERS_W)));
            p.setW(Integer.parseInt(jso.getString(JSON_PITCHERS_BB)));
            p.setHr_sv(Integer.parseInt(jso.getString(JSON_PITCHERS_SV)));
            p.setH(Integer.parseInt(jso.getString(JSON_PITCHERS_H)));
            p.setRbi_k(Integer.parseInt(jso.getString(JSON_PITCHERS_K)));
            p.setSb_era();
            p.setBa_whip();
            p.setPositions_String("P");
            p.setNotes(jso.getString(JSON_NOTES));
            p.setYearOfBirth(Integer.parseInt(jso.getString(JSON_YEAR_OF_BIRTH)));
            p.setNationOfBirth(jso.getString(JSON_NATION_OF_BIRTH));

            // ADD IT TO THE DRAFT
            pitchers.add(p);
        }
        return pitchers;
    }
    
        /**
     * Loads hitters from the json file.
     *
     * @param jsonFilePath Json file containing the subjects.
     * @return List full of Pitchers loaded from the file.
     * @throws IOException Thrown when I/O fails.
     */
    public ArrayList<Hitter> loadHitters(String jsonFilePath) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        JsonArray jsonHittersArray = json.getJsonArray(JSON_HITTERS);
        ArrayList<Hitter> hitters = new ArrayList<Hitter>();
        for (int i = 0; i < jsonHittersArray.size(); i++) {
            JsonObject jso = jsonHittersArray.getJsonObject(i);
            Hitter h = new Hitter();
            h.setProTeam(jso.getString(JSON_TEAM));
            h.setLastName(jso.getString(JSON_LAST_NAME));
            h.setFirstName(jso.getString(JSON_FIRST_NAME));
            h.setPositions_String(jso.getString(JSON_HITTERS_QP));
            h.setAb(Integer.parseInt(jso.getString(JSON_HITTERS_AB)));
            h.setR_w(Integer.parseInt(jso.getString(JSON_HITTERS_R)));
            h.setHr_sv(Integer.parseInt(jso.getString(JSON_HITTERS_HR)));
            h.setH(Integer.parseInt(jso.getString(JSON_HITTERS_H)));
            h.setRbi_k(Integer.parseInt(jso.getString(JSON_HITTERS_RBI)));
            h.setSb_era(Integer.parseInt(jso.getString(JSON_HITTERS_SB)));
            h.setBa_whip();
            h.setNotes(jso.getString(JSON_NOTES));
            h.setYearOfBirth(Integer.parseInt(jso.getString(JSON_YEAR_OF_BIRTH)));
            h.setNationOfBirth(jso.getString(JSON_NATION_OF_BIRTH));

            // ADD IT TO THE DRAFT
            hitters.add(h);
        }
        return hitters;
    }

            // AND HERE ARE THE PRIVATE HELPER METHODS TO HELP THE PUBLIC ONES
    // LOADS A JSON FILE AS A SINGLE OBJECT AND RETURNS IT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

            // LOADS AN ARRAY OF A SPECIFIC NAME FROM A JSON FILE AND
    // RETURNS IT AS AN ArrayList FULL OF THE DATA FOUND
    private ArrayList<String> loadArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ArrayList<String> items = new ArrayList();
        JsonArray jsonArray = json.getJsonArray(arrayName);
        for (JsonValue jsV : jsonArray) {
            items.add(jsV.toString());
        }
        return items;
    }

    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED PITCHERS
    private JsonObject makePitcherJsonObject(Pitcher p) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM, p.getProTeam())
                .add(JSON_FIRST_NAME, p.getFirstName())
                .add(JSON_LAST_NAME, p.getLastName())
                .add(JSON_PITCHERS_IP, p.getIp())
                .add(JSON_PITCHERS_ER, p.getEr())
                .add(JSON_PITCHERS_W, p.getR_w())
                .add(JSON_PITCHERS_BB, p.getW())
                .add(JSON_PITCHERS_SV, p.getHr_sv())
                .add(JSON_PITCHERS_H, p.getH())
                .add(JSON_PITCHERS_K, p.getRbi_k())
                .add(JSON_NOTES, p.getNotes())
                .add(JSON_YEAR_OF_BIRTH, p.getYearOfBirth())
                .add(JSON_NATION_OF_BIRTH, p.getNationOfBirth())
                .build();
        return jso;
    }

    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED PITCHERS
    private JsonObject makeHitterJsonObject(Hitter h) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM, h.getProTeam())
                .add(JSON_FIRST_NAME, h.getFirstName())
                .add(JSON_LAST_NAME, h.getLastName())
                .add(JSON_HITTERS_QP, h.getPositions_String())
                .add(JSON_HITTERS_AB, h.getAb())
                .add(JSON_HITTERS_R, h.getR_w())
                .add(JSON_HITTERS_HR, h.getHr_sv())
                .add(JSON_HITTERS_RBI, h.getRbi_k())
                .add(JSON_HITTERS_SB, h.getSb_era())
                .add(JSON_NOTES, h.getNotes())
                .add(JSON_YEAR_OF_BIRTH, h.getYearOfBirth())
                .add(JSON_NATION_OF_BIRTH, h.getNationOfBirth())
                .build();
        return jso;
    }

    // MAKE AN ARRAY OF SCHEDULE ITEMS
    private JsonArray makeHittersJsonArray(List<Hitter> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Hitter h : data) {
            jsb.add(makeHitterJsonObject(h));
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    // MAKE AN ARRAY OF LECTURE ITEMS
    private JsonArray makePitchersJsonArray(List<Pitcher> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Pitcher p : data) {
            jsb.add(makePitcherJsonObject(p));
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    // BUILDS AND RETURNS A JsonArray CONTAINING THE PROVIDED DATA
    public JsonArray buildJsonArray(List<Object> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Object d : data) {
            jsb.add(d.toString());
        }
        JsonArray jA = jsb.build();
        return jA;
    }

            // BUILDS AND RETURNS A JsonObject CONTAINING A JsonArray
    // THAT CONTAINS THE PROVIDED DATA
    public JsonObject buildJsonArrayObject(List<Object> data) {
        JsonArray jA = buildJsonArray(data);
        JsonObject arrayObject = Json.createObjectBuilder().add(JSON_PITCHERS, jA).build();
        return arrayObject;
    }
}
