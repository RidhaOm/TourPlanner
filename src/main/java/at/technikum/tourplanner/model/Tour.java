package at.technikum.tourplanner.model;

import jakarta.persistence.*;

@Entity
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String tourFrom;
    @Column
    private String tourTo;
    @Column
    private Double distance;
    @Column
    private String time;
    @Column
    private String transportType;
    @Column
    private String description;
    @Column
    private String routeInformation;


    public Tour() {
    }

    public Tour(String name, String tourFrom, String tourTo, Double distance, String time, String description, String transportType, String routeInformation) {
        this.name = name;
        this.tourFrom = tourFrom;
        this.tourTo = tourTo;
        this.distance = distance;
        this.time = time;
        this.description = description;
        this.transportType = transportType;
        this.routeInformation = routeInformation;
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
