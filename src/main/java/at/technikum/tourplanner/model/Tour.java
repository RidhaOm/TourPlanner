package at.technikum.tourplanner.model;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @CsvBindByPosition(position = 0)
    private Long id;
    @Column
    @CsvBindByPosition(position = 1)
    private String name;
    @Column
    @CsvBindByPosition(position = 2)
    private String tourFrom;
    @Column
    @CsvBindByPosition(position = 3)
    private String tourTo;
    @Column
    @CsvBindByPosition(position = 4)
    private Double distance;
    @Column
    @CsvBindByPosition(position = 5)
    private String time;
    @Column
    @CsvBindByPosition(position = 6)
    private String transportType;
    @Column
    @CsvBindByPosition(position = 7)
    private String description;


    public Tour() {
    }

    public Tour(String name, String tourFrom, String tourTo, Double distance, String time, String description, String transportType) {
        this.name = name;
        this.tourFrom = tourFrom;
        this.tourTo = tourTo;
        this.distance = distance;
        this.time = time;
        this.description = description;
        this.transportType = transportType;
    }

    public Tour(String name, String tourFrom, String tourTo) {
        this.name = name;
        this.tourFrom = tourFrom;
        this.tourTo = tourTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }
}
