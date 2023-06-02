package at.technikum.tourplanner.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourLog {
    @JsonProperty(value = "id")
   private int id;

    @JsonProperty(value = "date")
    private Date date;

    @JsonProperty(value = "comment")
    private String comment;

    @JsonProperty(value = "difficulty")
    private String difficulty;

    @JsonProperty(value = "time")
    private String time;

    @JsonProperty(value = "rating")
    private int rating;
}
