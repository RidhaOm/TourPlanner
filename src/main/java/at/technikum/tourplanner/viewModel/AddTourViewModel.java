package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.dto.MapQuestApiResponse;
import at.technikum.tourplanner.service.TourService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AddTourViewModel {
    private final StringProperty tourNameTextField = new SimpleStringProperty("");
    private final StringProperty fromTextField = new SimpleStringProperty("");
    private final StringProperty toTextField = new SimpleStringProperty("");
    private final StringProperty informationTextfield = new SimpleStringProperty("");

    private final TourService tourService;

    public AddTourViewModel(TourService tourService) {
        this.tourService = tourService;
    }

    public String getTourNameTextField() {
        return tourNameTextField.get();
    }

    public void setTourNameTextField(String tourName) {
        tourNameTextField.set(tourName);
    }

    public StringProperty tourNameTextFieldProperty() {
        return tourNameTextField;
    }

    public String getFromTextField() {
        return fromTextField.get();
    }

    public void setFromTextField(String from) {
        fromTextField.set(from);
    }

    public StringProperty fromTextFieldProperty() {
        return fromTextField;
    }

    public String getToTextField() {
        return toTextField.get();
    }

    public void setToTextField(String to) {
        toTextField.set(to);
    }

    public StringProperty toTextFieldProperty() {
        return toTextField;
    }
    public String getInformationTextField() {
        return informationTextfield.get();
    }

    public void setInformationTextField(String from) {
        informationTextfield.set(from);
    }
    public StringProperty informationTextFieldProperty() {
        return informationTextfield;
    }

    public void saveTour() {
        String key = "M9j8nWh6SKj8IsZuJbUX3eKTg5DAjx9x";
        String name = tourNameTextField.get();
        String from = fromTextField.get();
        String to = toTextField.get();
        Double distance;
        String time;
        String description = "No description yet";
        String transportType = "Walking";
        String routeInformation = "No informations yet";



        String uri = "https://www.mapquestapi.com/directions/v2/route?";
        uri += "key=" + key;
        uri += "&from=" + from;
        uri += "&to=" + to;
        uri += "&unit=k";
        uri += "&transportMode=WALKING";

        String responseJson = "";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            responseJson = response.body();

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String information = "From " + from + " to " + to + ": ";

        try {
            MapQuestApiResponse apiResponse = mapper.readValue(
                    responseJson,
                    MapQuestApiResponse.class
            );
            distance = apiResponse.getRoute().getDistance();
            time = apiResponse.getRoute().getFormattedTime();
            information += distance;
            information += " Time: " + time;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        setInformationTextField(information);
        System.out.println(information);
        tourService.save(name, from, to, distance, time, description, transportType, routeInformation);
        setTourNameTextField("");
        setFromTextField("");
        setToTextField("");

    }
}
