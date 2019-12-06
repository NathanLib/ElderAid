package uk.ac.rgu.elderaid;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "prescription")
public class Prescription {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int prescriptionId;

    private String name;
    private String exDate;
    private String level;

    //Constructor
    public Prescription(String name, String exDate, String level) {
        this.name = name;
        this.exDate = exDate;
        this.level = level;
    }

    // Getters
    public int getPrescriptionId(){
        return prescriptionId;
    }

    public String getName() {
        return name;
    }

    public String getExDate() {
        return exDate;
    }

    public String getLevel() {
        return level;
    }

    //Setters
    public void setPrescriptionId(int prescriptionId){
        this.prescriptionId = prescriptionId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setExDate(String exDate){
        this.exDate = exDate;
    }

    public void setLevel(String level) {
        this.level = level;
    }

   /* @Override
    public String toString() {
        final StringBuffer ps = new StringBuffer("Prescription");
        ps.append("prescriptionID: ").append(prescriptionId);
        ps.append(", prescription name: ").append(name).append('\'');
        ps.append(", ex date: ").append(exDate).append('\'');
        ps.append(", prescription level: ").append(level).append('\'');
        return ps.toString();
    }*/

}

