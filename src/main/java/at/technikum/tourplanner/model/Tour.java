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
   // @Column
   // private String description;
    @Column
    private String tourFrom;
    @Column
    private String tourTo;
    /*@Column
    private String transportType;
    @Column
    private Long distance;
    @Column
    private String time;
    @Column
    private String routeInformation;*/


    public Tour() {
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

    public String getValue() {
        return name;
    }

    public void setValue(String value) {
        this.name = value;
    }
}
