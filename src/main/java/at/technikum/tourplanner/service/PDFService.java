package at.technikum.tourplanner.service;

import at.technikum.tourplanner.model.Tour;
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

public class PDFService {
    private static final String IMAGE_PATH = "/at/technikum/tourplanner/maps/";
    private static final String PDF_PATH = "src/main/resources/at/technikum/tourplanner/pdf/";
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

        // Assumes distance is a double. Adjust as needed for your Tour constructor
        return new Tour(name, tourFrom, tourTo, Double.parseDouble(distance), time, transportType, description);
    }
}

