package com.example.tourplanner.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import com.opencsv.bean.CsvBindByPosition;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tour implements Serializable {

    @CsvBindByPosition(position = 0)
    @JsonProperty(value = "id")
    private int id;

    @CsvBindByPosition(position = 1)
    @JsonProperty(value = "name")
    private String name;

    @CsvBindByPosition(position = 2)
    @JsonProperty(value = "description")
    private String description;

    @CsvBindByPosition(position = 3)
    @JsonProperty(value = "from")
    private String from;

    @CsvBindByPosition(position = 4)
    @JsonProperty(value = "to")
    private String to;

    @CsvBindByPosition(position = 5)
    @JsonProperty(value = "transportType")
    private String transportType;

    @CsvBindByPosition(position = 6)
    @JsonProperty(value = "tourDistance")
    private String tourDistance;

    @CsvBindByPosition(position = 7)
    @JsonProperty(value = "estimatedTime")
    private String estimatedTime;

    @CsvBindByPosition(position = 8)
    @JsonProperty(value = "routeInformation")
    private String routeInformation;

    @JsonProperty(value = "tourLogs")
    private List tour;

    @Override
    public String toString(){
        return name;
    }

}
