package at.technikum.tourplanner.service;

import at.technikum.tourplanner.configuration.ConfigurationManager;
import at.technikum.tourplanner.dto.MapQuestApiResponse;
import at.technikum.tourplanner.dto.Route;
import at.technikum.tourplanner.repository.TourRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class MapQuestRouteService implements RouteService{
//    private static String API_KEY = "M9j8nWh6SKj8IsZuJbUX3eKTg5DAjx9x";
    private static String API_KEY = new ConfigurationManager().getApiKey();
    private static final Logger logger = Logger.getLogger(TourRepository.class);

    @Override
    public Route getRoute(String from, String to, String transportType) {
        String uri = "https://www.mapquestapi.com/directions/v2/route?";
        uri += "key=" + API_KEY;
        uri += "&from=" + from;
        uri += "&to=" + to;
        uri += "&unit=k";
        uri += "&routeType=" + transformTransportType(transportType);

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

            if (response.statusCode() >= 400) {
                // Handle error
                logger.error("Error retrieving route: " + responseJson);
                return null; // Return null on error
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MapQuestApiResponse apiResponse = null;

        try {
            apiResponse = mapper.readValue(
                    responseJson,
                    MapQuestApiResponse.class
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return apiResponse.getRoute();
    }


    @Override
    public void saveMap(String sessionId, String filename) {

        String uri = "https://www.mapquestapi.com/staticmap/v5/map?";
        uri += "key=" + API_KEY;
        uri += "&session=" + sessionId;

        try {
            ReadableByteChannel readableByteChannel =
                    Channels.newChannel((new URL(uri)).openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            FileChannel fileChannel = fileOutputStream.getChannel();

            fileChannel
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            fileChannel.close();
            fileOutputStream.close();
            readableByteChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String transformTransportType(String transportType){
        return switch (transportType) {
            case "Car" -> "fastest";
            case "Bike" -> "bicycle";
            case "Walking" -> "pedestrian";
            default -> "";
        };
    }
}
