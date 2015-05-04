package draftkit.data;

import java.util.ArrayList;

/**
 * An instance of player is literally
 * 
 * @author H2
 */
public abstract class Player {
    private String firstName;
    private String lastName;
    private String proTeam;
    private String team;
    private String positions_String;
    private String position;
    private ArrayList<String> positions;
    private int yearOfBirth;
    private String nationOfBirth;
    private int r_w;
    private int hr_sv;
    private int rbi_k;
    private double sb_era;
    private double ba_whip;
    private int rank;
    private int estimatedValue;
    private String notes;
    private int salary;
    private String contract;

    public Player() {
    }
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the proTeam
     */
    public String getProTeam() {
        return proTeam;
    }

    /**
     * @param proTeam the proTeam to set
     */
    public void setProTeam(String proTeam) {
        this.proTeam = proTeam;
    }

    /**
     * @return the team
     */
    public String getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * @return the positions
     */
    public ArrayList<String> getPositions() {
        return positions;
    }

    /**
     * @param positions the positions to set
     */
    public void setPositions() {
        this.positions = new ArrayList<String>();
        for (int i = 0; i < positions_String.split("_").length; i++) {
            positions.add(positions_String.split("_")[i]);
        }
    }

    /**
     * @return the yearOfBirth
     */
    public int getYearOfBirth() {
        return yearOfBirth;
    }

    /**
     * @param yearOfBirth the yearOfBirth to set
     */
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * @return the r_w
     */
    public int getR_w() {
        return r_w;
    }

    /**
     * @param r_w the r_w to set
     */
    public void setR_w(int r_w) {
        this.r_w = r_w;
    }

    /**
     * @return the hr_sv
     */
    public int getHr_sv() {
        return hr_sv;
    }

    /**
     * @param hr_sv the hr_sv to set
     */
    public void setHr_sv(int hr_sv) {
        this.hr_sv = hr_sv;
    }

    /**
     * @return the rbi_k
     */
    public int getRbi_k() {
        return rbi_k;
    }

    /**
     * @param rbi_k the rbi_k to set
     */
    public void setRbi_k(int rbi_k) {
        this.rbi_k = rbi_k;
    }

    /**
     * @return the sb_era
     */
    public double getSb_era() {
        return sb_era;
    }

    /**
     * @param sb_era the sb_era to set
     */
    public void setSb_era(double sb_era) {
        this.sb_era = sb_era;
    }

    /**
     * @return the ba_whip
     */
    public double getBa_whip() {
        return ba_whip;
    }

    /**
     * @param ba_whip the ba_whip to set
     */
    public void setBa_whip(double ba_whip) {
        this.ba_whip = ba_whip;
    }

    /**
     * @return the estimatedValue
     */
    public int getEstimatedValue() {
        return estimatedValue;
    }

    /**
     * @param estimatedValue the estimatedValue to set
     */
    public void setEstimatedValue(int estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the nationOfBirth
     */
    public String getNationOfBirth() {
        return nationOfBirth;
    }

    /**
     * @param nationOfBirth the nationOfBirth to set
     */
    public void setNationOfBirth(String nationOfBirth) {
        this.nationOfBirth = nationOfBirth;
    }

    /**
     * @return the positions_String
     */
    public String getPositions_String() {
        return positions_String;
    }

    /**
     * @param positions_String the positions_String to set
     */
    public void setPositions_String(String positions_String) {
        this.positions_String = positions_String;
        setPositions();
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * @return the contract
     */
    public String getContract() {
        return contract;
    }

    /**
     * @param contract the contract to set
     */
    public void setContract(String contract) {
        this.contract = contract;
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    
}
