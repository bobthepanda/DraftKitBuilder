package draftkit.file;

import static draftkit.DraftKit_StartupConstants.PATH_DRAFTS;
import draftkit.data.Draft;
import draftkit.data.DraftTeam;
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
import javafx.collections.FXCollections;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

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
    String JSON_FANTASY_TEAM = "FANTASY_TEAM";
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

    String JSON_TEAMS = "Teams";
    String JSON_TEAMS_NAME = "NAME";
    String JSON_TEAMS_OWNER = "OWNER";
    String JSON_TEAMS_CASH = "CASH";
    String JSON_TEAMS_PLAYERS_NEEDED = "PLAYERS_NEEDED";
    String JSON_TEAMS_POINTS = "POINTS";
    String JSON_TEAMS_HITTERS = "Hitters";
    String JSON_TEAMS_PITCHERS = "Pitchers";
    String JSON_TEAMS_LINEUP = "Lineup";
    String JSON_TEAMS_TAXI = "Taxi";
    String JSON_TEAMS_R = "r";
    String JSON_TEAMS_HR = "hr";
    String JSON_TEAMS_RBI = "rbi";
    String JSON_TEAMS_SB = "sb";
    String JSON_TEAMS_BA = "ba";
    String JSON_TEAMS_W = "w";
    String JSON_TEAMS_K = "k";
    String JSON_TEAMS_SV = "sv";
    String JSON_TEAMS_ERA = "era";
    String JSON_TEAMS_WHIP = "whip";
    String JSON_TEAMS_C = "c";
    String JSON_TEAMS_CI = "ci";
    String JSON_TEAMS_1B = "oneB";
    String JSON_TEAMS_2B = "twoB";
    String JSON_TEAMS_3B = "threeB";
    String JSON_TEAMS_MI = "mi";
    String JSON_TEAMS_SS = "ss";
    String JSON_TEAMS_U = "u";
    String JSON_TEAMS_OF = "of";

    String JSON_PLAYERS = "Players";
    String JSON_INDEX = "Index";
    String JSON_POSITION = "Position";
    String JSON_POSITIONS = "Positions";
    String JSON_R_W = "R_W";
    String JSON_HR_SV = "HR_SV";
    String JSON_RBI_K = "RBI_K";
    String JSON_SB_ERA = "SB_ERA";
    String JSON_BA_WHIP = "BA_WHIP";
    String JSON_CONTRACT = "CONTRACT";
    String JSON_SALARY = "SALARY";
    String JSON_EXT = ".json";
    String SLASH = "/";
    int i = 0;
    
    String JSON_PICKS = "Draft Picks";

    /**
     * This method saves all the data associated with a draft to a JSON file.
     *
     * @param draftToSave The draft whose data we are saving.
     *
     * @throws IOException Thrown when there are issues writing to the JSON
     * file.
     */
    @Override
    public void saveDraft(Draft draftToSave, String filePath) throws IOException {
        // BUILD THE FILE PATH
        String jsonFilePath = PATH_DRAFTS + SLASH + filePath + JSON_EXT;
        i++;

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);

        // MAKE A JSON ARRAY FOR THE PAGES ARRAY
        JsonArray hittersJsonArray = makeHittersJsonArray(draftToSave.getHitters());

        // AND AN OBJECT FOR THE HITTERS
        JsonArray pitchersJsonArray = makePitchersJsonArray(draftToSave.getPitchers());

        // THE LECTURE DAYS ARRAY
        JsonArray playersJsonArray = makePlayersJsonArray(draftToSave.getPlayers());

        // THE SCHEDULE ITEMS ARRAY
        JsonArray teamsJsonArray = makeTeamsJsonArray(draftToSave.getTeams());
        
        JsonArray draftPickJsonArray = makePlayersJsonArray(draftToSave.getDraftPicks());

        // NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
        JsonObject draftJsonObject = Json.createObjectBuilder()
                .add(JSON_HITTERS, hittersJsonArray)
                .add(JSON_PITCHERS, pitchersJsonArray)
                .add(JSON_PLAYERS, playersJsonArray)
                .add(JSON_TEAMS, teamsJsonArray)
                .add(JSON_PICKS, draftPickJsonArray)
                .build();

        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(draftJsonObject);
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
        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(jsonFilePath);

        // NOW LOAD THE COURSE
        draftToLoad.setTeams(loadTeams(json));
        draftToLoad.setPitchers(loadPitchers(json));
        draftToLoad.setHitters(loadHitters(json));
        draftToLoad.setPlayers(loadPlayers(json, JSON_PLAYERS));
        draftToLoad.setDraftPicks(loadPlayers(json, JSON_PICKS));
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
            String position = jso.getString(JSON_HITTERS_QP);
            if (position.contains("2B") || position.contains("SS")) {
                position = position.concat("_MI");
            }
            if (position.contains("1B") || position.contains("3B")) {
                position = position.concat("_CI");
            }
            position = position.concat("_U");
            h.setPositions_String(position);
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

    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED PITCHERS
    private JsonObject makePitcherJsonObject(Pitcher p) {
        try {
            JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM, p.getProTeam())
                    .add(JSON_PICKS, p.getIndex() + "")
                    .add(JSON_POSITION, p.getPosition())
                    .add(JSON_FANTASY_TEAM, p.getTeam())
                    .add(JSON_FIRST_NAME, p.getFirstName())
                    .add(JSON_LAST_NAME, p.getLastName())
                    .add(JSON_PITCHERS_IP, p.getIp() + "")
                    .add(JSON_PITCHERS_ER, p.getEr() + "")
                    .add(JSON_PITCHERS_W, p.getR_w() + "")
                    .add(JSON_PITCHERS_BB, p.getW() + "")
                    .add(JSON_PITCHERS_SV, p.getHr_sv() + "")
                    .add(JSON_PITCHERS_H, p.getH() + "")
                    .add(JSON_PITCHERS_K, p.getRbi_k() + "")
                    .add(JSON_CONTRACT, p.getContract())
                    .add(JSON_NOTES, p.getNotes())
                    .add(JSON_YEAR_OF_BIRTH, p.getYearOfBirth() + "")
                    .add(JSON_NATION_OF_BIRTH, p.getNationOfBirth())
                    .add(JSON_SALARY, p.getSalary() + "")
                    .build();
            return jso;
        } catch (NullPointerException e) {
            JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM, p.getProTeam())
                    .add(JSON_PICKS, p.getIndex() + "")
                    .add(JSON_FIRST_NAME, p.getFirstName())
                    .add(JSON_LAST_NAME, p.getLastName())
                    .add(JSON_PITCHERS_IP, p.getIp() + "")
                    .add(JSON_PITCHERS_ER, p.getEr() + "")
                    .add(JSON_PITCHERS_W, p.getR_w() + "")
                    .add(JSON_PITCHERS_BB, p.getW() + "")
                    .add(JSON_PITCHERS_SV, p.getHr_sv() + "")
                    .add(JSON_PITCHERS_H, p.getH() + "")
                    .add(JSON_PITCHERS_K, p.getRbi_k() + "")
                    .add(JSON_NOTES, p.getNotes())
                    .add(JSON_YEAR_OF_BIRTH, p.getYearOfBirth() + "")
                    .add(JSON_NATION_OF_BIRTH, p.getNationOfBirth())
                    .build();
            return jso;
        }
    }

    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED PITCHERS
    private JsonObject makeHitterJsonObject(Hitter h) {
        try {
            JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM, h.getProTeam())
                    .add(JSON_PICKS, h.getIndex() + "")
                    .add(JSON_POSITION, h.getPosition())
                    .add(JSON_FANTASY_TEAM, h.getTeam())
                    .add(JSON_FIRST_NAME, h.getFirstName() + "")
                    .add(JSON_LAST_NAME, h.getLastName() + "")
                    .add(JSON_HITTERS_QP, h.getPositions_String())
                    .add(JSON_HITTERS_AB, h.getAb() + "")
                    .add(JSON_HITTERS_R, h.getR_w() + "")
                    .add(JSON_HITTERS_HR, h.getHr_sv() + "")
                    .add(JSON_HITTERS_H, h.getH() + "")
                    .add(JSON_HITTERS_RBI, h.getRbi_k() + "")
                    .add(JSON_HITTERS_SB, h.getSb_era() + "")
                    .add(JSON_CONTRACT, h.getContract())
                    .add(JSON_NOTES, h.getNotes())
                    .add(JSON_YEAR_OF_BIRTH, h.getYearOfBirth() + "")
                    .add(JSON_NATION_OF_BIRTH, h.getNationOfBirth())
                    .add(JSON_SALARY, h.getSalary() + "")
                    .build();
            return jso;
        } catch (NullPointerException e) {
            JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM, h.getProTeam())
                    .add(JSON_PICKS, h.getIndex() + "")
                    .add(JSON_FIRST_NAME, h.getFirstName())
                    .add(JSON_LAST_NAME, h.getLastName())
                    .add(JSON_HITTERS_QP, h.getPositions_String())
                    .add(JSON_HITTERS_AB, h.getAb() + "")
                    .add(JSON_HITTERS_R, h.getR_w() + "")
                    .add(JSON_HITTERS_HR, h.getHr_sv() + "")
                    .add(JSON_HITTERS_H, h.getH() + "")
                    .add(JSON_HITTERS_RBI, h.getRbi_k() + "")
                    .add(JSON_HITTERS_SB, h.getSb_era() + "")
                    .add(JSON_NOTES, h.getNotes())
                    .add(JSON_YEAR_OF_BIRTH, h.getYearOfBirth() + "")
                    .add(JSON_NATION_OF_BIRTH, h.getNationOfBirth())
                    .build();
            return jso;
        }
    }

    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED TEAMS
    private JsonObject makeTeamJsonObject(Team t) {
        DraftTeam d = (DraftTeam) t;
        JsonObject jso = Json.createObjectBuilder().add(JSON_TEAMS_NAME, d.getName())
                .add(JSON_TEAMS_OWNER, d.getOwner())
                .add(JSON_TEAMS_CASH, d.getCash() + "")
                .add(JSON_TEAMS_PLAYERS_NEEDED, d.getPlayersNeeded() + "")
                .add(JSON_TEAMS_POINTS, d.getPoints() + "")
                .add(JSON_TEAMS_LINEUP, makePlayersJsonArray(d.getLineup()))
                .add(JSON_TEAMS_TAXI, makePlayersJsonArray(d.getTaxi()))
                .add(JSON_TEAMS_R, d.getR() + "")
                .add(JSON_TEAMS_HR, d.getHr() + "")
                .add(JSON_TEAMS_RBI, d.getRbi() + "")
                .add(JSON_TEAMS_SB, d.getSb() + "")
                .add(JSON_TEAMS_BA, d.getBa() + "")
                .add(JSON_TEAMS_W, d.getW() + "")
                .add(JSON_TEAMS_K, d.getK() + "")
                .add(JSON_TEAMS_SV, d.getSv() + "")
                .add(JSON_TEAMS_ERA, d.getEra() + "")
                .add(JSON_TEAMS_WHIP, d.getWhip() + "")
                .add(JSON_TEAMS_C, d.getC() + "")
                .add(JSON_TEAMS_CI, d.getCi() + "")
                .add(JSON_TEAMS_1B, d.getOneB() + "")
                .add(JSON_TEAMS_2B, d.getTwoB() + "")
                .add(JSON_TEAMS_3B, d.getThreeB() + "")
                .add(JSON_TEAMS_MI, d.getMi() + "")
                .add(JSON_TEAMS_SS, d.getSs() + "")
                .add(JSON_TEAMS_U, d.getU() + "")
                .add(JSON_TEAMS_OF, d.getOf() + "")
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

    private JsonArray makeTeamsJsonArray(List<Team> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Team t : data) {
            jsb.add(makeTeamJsonObject(t));
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    private JsonArray makePlayersJsonArray(List<Player> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Player p : data) {
            if (p instanceof Hitter) {
                jsb.add(makeHitterJsonObject((Hitter) p));
            } else {
                jsb.add(makePitcherJsonObject((Pitcher) p));
            }
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

    private ArrayList<Player> loadPlayers(JsonObject json, String path) throws IOException {
        JsonArray jsonPlayersArray = json.getJsonArray(path);
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < jsonPlayersArray.size(); i++) {
            JsonObject jso = jsonPlayersArray.getJsonObject(i);
            try {
                Hitter h = new Hitter();
                h.setIndex(Integer.parseInt(jso.getString(JSON_PICKS)));
                h.setProTeam(jso.getString(JSON_TEAM));
                try {
                    h.setTeam(jso.getString(JSON_FANTASY_TEAM));
                    h.setContract(jso.getString(JSON_CONTRACT));
                    h.setPosition(jso.getString(JSON_POSITION));
                    h.setSalary(Integer.parseInt(jso.getString(JSON_SALARY)));
                } catch (Exception e) {
                }
                h.setLastName(jso.getString(JSON_LAST_NAME));
                h.setFirstName(jso.getString(JSON_FIRST_NAME));
                String position = jso.getString(JSON_HITTERS_QP);
                if (position.contains("2B") || position.contains("SS")) {
                    while (position.contains("_MI")) {
                        position = position.replace("_MI", "");
                    }
                    position = position.concat("_MI");
                }
                if (position.contains("1B") || position.contains("3B")) {
                    while (position.contains("_CI")) {
                        position = position.replace("_CI", "");
                    }
                    position = position.concat("_CI");
                }
                while (position.contains("_U")) {
                    position = position.replace("_U", "");
                }
                position = position.concat("_U");
                h.setPositions_String(position);
                h.setAb(Integer.parseInt(jso.getString(JSON_HITTERS_AB)));
                h.setR_w(Integer.parseInt(jso.getString(JSON_HITTERS_R)));
                h.setHr_sv(Integer.parseInt(jso.getString(JSON_HITTERS_HR)));
                h.setH(Integer.parseInt(jso.getString(JSON_HITTERS_H)));
                h.setRbi_k(Integer.parseInt(jso.getString(JSON_HITTERS_RBI)));
                h.setSb_era((int) (Double.parseDouble(jso.getString(JSON_HITTERS_SB))));
                h.setBa_whip();
                h.setNotes(jso.getString(JSON_NOTES));
                h.setYearOfBirth(Integer.parseInt(jso.getString(JSON_YEAR_OF_BIRTH)));
                h.setNationOfBirth(jso.getString(JSON_NATION_OF_BIRTH));

                // ADD IT TO THE DRAFT
                players.add(h);
            } catch (Exception e) {
                Pitcher p = new Pitcher();
                p.setIndex(Integer.parseInt(jso.getString(JSON_PICKS)));
                p.setProTeam(jso.getString(JSON_TEAM));
                try {
                    p.setTeam(jso.getString(JSON_FANTASY_TEAM));
                    p.setContract(jso.getString(JSON_CONTRACT));
                    p.setPosition(jso.getString(JSON_POSITION));
                    p.setSalary(Integer.parseInt(jso.getString(JSON_SALARY)));
                } catch (Exception f) {
                }
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
                players.add(p);
            }
        }
        return players;
    }

    private ArrayList<Pitcher> loadPitchers(JsonObject json) throws IOException {
        JsonArray jsonPitchersArray = json.getJsonArray(JSON_PITCHERS);
        ArrayList<Pitcher> pitchers = new ArrayList<Pitcher>();
        for (int i = 0; i < jsonPitchersArray.size(); i++) {
            JsonObject jso = jsonPitchersArray.getJsonObject(i);
            Pitcher p = new Pitcher();
            p.setIndex(Integer.parseInt(jso.getString(JSON_PICKS)));
            p.setProTeam(jso.getString(JSON_TEAM));
            try {
                p.setTeam(jso.getString(JSON_FANTASY_TEAM));
                p.setContract(jso.getString(JSON_CONTRACT));
                p.setPosition(jso.getString(JSON_POSITION));
                p.setSalary(Integer.parseInt(jso.getString(JSON_SALARY)));
            } catch (Exception e) {
            }
            p.setLastName(jso.getString(JSON_LAST_NAME));
            p.setFirstName(jso.getString(JSON_FIRST_NAME));
            String s = jso.getString(JSON_PITCHERS_IP);
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

    private ArrayList<Team> loadTeams(JsonObject json) throws IOException {
        JsonArray jsonPitchersArray = json.getJsonArray(JSON_TEAMS);
        ArrayList<Team> teams = new ArrayList<Team>();
        for (int i = 0; i < jsonPitchersArray.size(); i++) {
            JsonObject jso = jsonPitchersArray.getJsonObject(i);
            DraftTeam t = new DraftTeam();
            t.setName(jso.getString(JSON_TEAMS_NAME));
            t.setOwner(jso.getString(JSON_TEAMS_OWNER));
            t.setCash(Integer.parseInt(jso.getString(JSON_TEAMS_CASH)));
            t.setPlayersNeeded(Integer.parseInt(jso.getString(JSON_TEAMS_PLAYERS_NEEDED)));
            t.setPoints(Integer.parseInt(jso.getString(JSON_TEAMS_POINTS)));
            t.setLineup(FXCollections.observableArrayList(loadPlayers(jso, JSON_TEAMS_LINEUP)));
            t.setTaxi(FXCollections.observableArrayList(loadPlayers(jso, JSON_TEAMS_TAXI)));
            t.setR(Integer.parseInt(jso.getString(JSON_TEAMS_R)));
            t.setHr(Integer.parseInt(jso.getString(JSON_TEAMS_HR)));
            t.setRbi(Integer.parseInt(jso.getString(JSON_TEAMS_RBI)));
            t.setSb(Integer.parseInt(jso.getString(JSON_TEAMS_SB)));
            t.setBa(Double.parseDouble(jso.getString(JSON_TEAMS_BA)));
            t.setW(Integer.parseInt(jso.getString(JSON_TEAMS_W)));
            t.setK(Integer.parseInt(jso.getString(JSON_TEAMS_K)));
            t.setSv(Integer.parseInt(jso.getString(JSON_TEAMS_SV)));
            t.setEra(Double.parseDouble(jso.getString(JSON_TEAMS_ERA)));
            t.setWhip(Double.parseDouble(jso.getString(JSON_TEAMS_WHIP)));
            t.setC(Integer.parseInt(jso.getString(JSON_TEAMS_C)));
            t.setCi(Integer.parseInt(jso.getString(JSON_TEAMS_CI)));
            t.setOneB(Integer.parseInt(jso.getString(JSON_TEAMS_1B)));
            t.setTwoB(Integer.parseInt(jso.getString(JSON_TEAMS_2B)));
            t.setThreeB(Integer.parseInt(jso.getString(JSON_TEAMS_3B)));
            t.setMi(Integer.parseInt(jso.getString(JSON_TEAMS_MI)));
            t.setSs(Integer.parseInt(jso.getString(JSON_TEAMS_SS)));
            t.setU(Integer.parseInt(jso.getString(JSON_TEAMS_U)));
            t.setOf(Integer.parseInt(jso.getString(JSON_TEAMS_OF)));

            // ADD IT TO THE DRAFT
            teams.add(t);
        }
        return teams;
    }

    public ArrayList<Hitter> loadHitters(JsonObject json) throws IOException {
        JsonArray jsonHittersArray = json.getJsonArray(JSON_HITTERS);
        ArrayList<Hitter> hitters = new ArrayList<Hitter>();
        for (int i = 0; i < jsonHittersArray.size(); i++) {
            JsonObject jso = jsonHittersArray.getJsonObject(i);
            Hitter h = new Hitter();
            h.setIndex(Integer.parseInt(jso.getString(JSON_PICKS)));
            h.setProTeam(jso.getString(JSON_TEAM));
            try {
                h.setTeam(jso.getString(JSON_FANTASY_TEAM));
                h.setContract(jso.getString(JSON_CONTRACT));
                h.setPosition(jso.getString(JSON_POSITION));
                h.setSalary(Integer.parseInt(jso.getString(JSON_SALARY)));
            } catch (Exception e) {
            }
            h.setLastName(jso.getString(JSON_LAST_NAME));
            h.setFirstName(jso.getString(JSON_FIRST_NAME));
            String position = jso.getString(JSON_HITTERS_QP);
            if (position.contains("2B") || position.contains("SS")) {
                while (position.contains("_MI")) {
                    position = position.replace("_MI", "");
                }
                position = position.concat("_MI");
            }
            if (position.contains("1B") || position.contains("3B")) {
                while (position.contains("_CI")) {
                    position = position.replace("_CI", "");
                }
                position = position.concat("_CI");
            }
            while (position.contains("_U")) {
                position = position.replace("_U", "");
            }
            position = position.concat("_U");
            h.setPositions_String(position);
            h.setAb(Integer.parseInt(jso.getString(JSON_HITTERS_AB)));
            h.setR_w(Integer.parseInt(jso.getString(JSON_HITTERS_R)));
            h.setHr_sv(Integer.parseInt(jso.getString(JSON_HITTERS_HR)));
            h.setH(Integer.parseInt(jso.getString(JSON_HITTERS_H)));
            h.setRbi_k(Integer.parseInt(jso.getString(JSON_HITTERS_RBI)));
            h.setSb_era((int) Double.parseDouble(jso.getString(JSON_HITTERS_SB)));
            h.setBa_whip();
            h.setNotes(jso.getString(JSON_NOTES));
            h.setYearOfBirth(Integer.parseInt(jso.getString(JSON_YEAR_OF_BIRTH)));
            h.setNationOfBirth(jso.getString(JSON_NATION_OF_BIRTH));

            // ADD IT TO THE DRAFT
            hitters.add(h);
        }
        return hitters;
    }

}
