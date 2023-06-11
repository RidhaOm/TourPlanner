package at.technikum.tourplanner.service;

import at.technikum.tourplanner.configuration.ConfigurationManager;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.TourLog;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class PDFService {

    //private static final String IMAGE_PATH = "/at/technikum/tourplanner/maps/";
    //private static final String PDF_PATH = "Tours PDF/";
    //private static final String TOUR_REPORTS_PATH = "Tour Reports/";
    private static final String IMAGE_PATH = new ConfigurationManager().getImagePath();
    private static final String PDF_PATH = new ConfigurationManager().getPdfPath();
    private static final String TOUR_REPORTS_PATH = new ConfigurationManager().getTourReportsPath();
    private static final Logger logger = Logger.getLogger(PDFService.class);
    public static void export(Tour tour) {
        try {
            URL imageUrl = PDFService.class.getResource(IMAGE_PATH + tour.getName() + ".jpg");
            if (imageUrl == null) {
                logger.error("Map image for tour '" + tour.getName() + "' not found.");
                return;
            }

            ImageData data = ImageDataFactory.create(imageUrl);
            Image img = new Image(data);

            PdfWriter writer = new PdfWriter(PDF_PATH + tour.getName() + ".pdf");
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document pdf = new Document(pdfDoc);

            pdf.add(new Paragraph("Tour Name: " + tour.getName()));
            pdf.add(new Paragraph("From: " + tour.getTourFrom()));
            pdf.add(new Paragraph("To: " + tour.getTourTo()));
            pdf.add(new Paragraph("Distance: " + tour.getDistance()));
            pdf.add(new Paragraph("Time: " + tour.getTime()));
            pdf.add(new Paragraph("Transport Type: " + tour.getTransportType()));
            pdf.add(new Paragraph("Description: " + tour.getDescription()));
            pdf.add(new Paragraph("Popularity: " + tour.getPopularity()));
            pdf.add(new Paragraph("Child Friendliness: " + tour.getChildFriendliness()));
            pdf.add(img);

            pdf.close();
            logger.info("PDF exported successfully.");
        } catch (FileNotFoundException e) {
            logger.error("Error: PDF file not found.", e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Error occurred while exporting PDF.", e);
            e.printStackTrace();
        }
    }

    public static Tour importTour(File file) throws IOException {
        PdfReader reader = new PdfReader(file.getAbsolutePath());
        PdfDocument pdfDoc = new PdfDocument(reader);
        String text = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), new SimpleTextExtractionStrategy());
        reader.close();

        String[] lines = text.split("\n");
        String name = lines[0].replace("Tour Name: ", "").trim();
        String tourFrom = lines[1].replace("From: ", "").trim();
        String tourTo = lines[2].replace("To: ", "").trim();
        String distance = lines[3].replace("Distance: ", "").replace(" km", "").trim();
        String time = lines[4].replace("Time: ", "").trim();
        String transportType = lines[5].replace("Transport Type: ", "").trim();
        String description = lines[6].replace("Description: ", "").trim();
        String popularity = lines[7].replace("Popularity: ", "").trim();
        String childFriendliness = lines[8].replace("Child Friendliness: ", "").trim();

        Tour tour = new Tour(name, tourFrom, tourTo, Double.parseDouble(distance), time, description, transportType);
        tour.setPopularity(Integer.parseInt(popularity));
        tour.setChildFriendliness(Double.parseDouble(childFriendliness));

        return tour;
    }

    public static void generateTourReport(Tour tour, List<TourLog> tourLogs) {
        try {
            URL imageUrl = PDFService.class.getResource(IMAGE_PATH + tour.getName() + ".jpg");
            if (imageUrl == null) {
                logger.error("Map image for tour '" + tour.getName() + "' not found.");
                return;
            }

            ImageData data = ImageDataFactory.create(imageUrl);
            Image img = new Image(data);

            PdfWriter writer = new PdfWriter(TOUR_REPORTS_PATH + tour.getName() + ".pdf");
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document pdf = new Document(pdfDoc);

            // Add tour details
            pdf.add(new Paragraph("Tour Name: " + tour.getName()));
            pdf.add(new Paragraph("From: " + tour.getTourFrom()));
            pdf.add(new Paragraph("To: " + tour.getTourTo()));
            pdf.add(new Paragraph("Distance: " + tour.getDistance()));
            pdf.add(new Paragraph("Time: " + tour.getTime()));
            pdf.add(new Paragraph("Transport Type: " + tour.getTransportType()));
            pdf.add(new Paragraph("Description: " + tour.getDescription()));
            pdf.add(new Paragraph("Popularity: " + tour.getPopularity()));
            pdf.add(new Paragraph("Child Friendliness: " + tour.getChildFriendliness()));

            // Add tour logs
            pdf.add(new Paragraph("Tour Logs:"));
            if (!tourLogs.isEmpty()) {
                for (TourLog tourLog : tourLogs) {
                    pdf.add(new Paragraph("Date: " + tourLog.getDate()));
                    pdf.add(new Paragraph("Duration: " + tourLog.getDuration()));
                    pdf.add(new Paragraph("Difficulty: " + tourLog.getDifficulty()));
                    pdf.add(new Paragraph("Ranking: " + tourLog.getRanking()));
                    pdf.add(new Paragraph("Comment: " + tourLog.getComment()));
                    pdf.add(new Paragraph("-------------------------------------"));
                }
            } else {
                pdf.add(new Paragraph("No tour logs available."));
            }

            pdf.add(img);

            pdf.close();
            logger.info("Tour report generated successfully.");
        } catch (FileNotFoundException e) {
            logger.error("Error: PDF file not found.", e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Error occurred while generating tour report.", e);
            e.printStackTrace();
        }
    }

    public static void generateSummaryReport(List<Tour> allTours, List<TourLog> allLogs) {
        try {
            PdfWriter writer = new PdfWriter(TOUR_REPORTS_PATH + "SummaryReport.pdf");
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document pdf = new Document(pdfDoc);

            for (Tour tour : allTours) {

                List<TourLog> tourLogs = allLogs.stream()
                        .filter(tourLog -> tourLog.getTourName().equals(tour.getName()))
                        .collect(Collectors.toList());

                double avgRanking = tourLogs.stream()
                        .mapToInt(TourLog::getRanking)
                        .average().orElse(Double.NaN);

                pdf.add(new Paragraph("Tour Name: " + tour.getName()));
                pdf.add(new Paragraph("Average Distance: " + tour.getDistance())); // Use your logic if distance varies in tourLogs
                pdf.add(new Paragraph("Average Time: " + tour.getTime())); // Use your logic if time varies in tourLogs
                pdf.add(new Paragraph("Average Ranking: " + avgRanking));
                pdf.add(new Paragraph("\n")); // Added for space between different tour summaries
            }

            pdf.close();
            logger.info("Summary report generated successfully.");
        } catch (FileNotFoundException e) {
            logger.error("Error: PDF file not found.", e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Error occurred while generating summary report.", e);
            e.printStackTrace();
        }
    }


}

