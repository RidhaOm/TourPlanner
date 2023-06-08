package at.technikum.tourplanner.service;

import at.technikum.tourplanner.dto.Route;

public interface RouteService {
    Route getRoute(String from, String to, String transportType);

    void saveMap(String sessionId, String filename);
}
