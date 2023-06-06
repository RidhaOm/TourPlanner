package at.technikum.tourplanner.service;

import at.technikum.tourplanner.model.Tour;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.net.URL;

public class PDFService {
    private static final String IMAGE_PATH = "/at/technikum/tourplanner/maps/";
    private static final String PDF_PATH = "src/main/resources/at/technikum/tourplanner/pdf/";

    public static void export(Tour tour) {
        try {
            URL imageUrl = PDFService.class.getResource(IMAGE_PATH + tour.getName() + ".jpg");
            if (imageUrl == null) {
                System.out.println("Map image for tour '" + tour.getName() + "' not found.");
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
            pdf.add(new Paragraph("Route Information: " + tour.getRouteInformation()));
            pdf.add(img);

            pdf.close();
            System.out.println("PDF exported successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: PDF file not found.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error occurred while exporting PDF.");
            e.printStackTrace();
        }
    }
}
