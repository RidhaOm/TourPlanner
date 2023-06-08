package at.technikum.tourplanner.model;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TourLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @CsvBindByPosition(position = 0)
    private Long id;
    @Column
    @CsvBindByPosition(position = 1)
    private String tourName;
    @Column
    @CsvBindByPosition(position = 2)
    private String date;
    @Column
    @CsvBindByPosition(position = 3)
    private double duration;
    @Column
    @CsvBindByPosition(position = 4)
    private int difficulty;
    @Column
    @CsvBindByPosition(position = 5)
    private int ranking;
    @Column
    @CsvBindByPosition(position = 6)
    private String comment;

    public TourLog(String tourName, String date, double duration, int difficulty, int ranking, String comment){
        this.tourName = tourName;
        this.date = date;
        this.duration = duration;
        this.difficulty = difficulty;
        this.ranking = ranking;
        this.comment = comment;
    }

    public TourLog() {

    }

    public String getTourLog(){
        String tourLog = tourName+": Date: " + date + " | Duration: " + duration + " | Difficulty: " + difficulty + " | Ranking: " + ranking + " | Comment: " + comment;
        return tourLog;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
