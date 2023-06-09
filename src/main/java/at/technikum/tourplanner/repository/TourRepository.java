package at.technikum.tourplanner.repository;

import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.data.HibernateSessionFactory;
import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.apache.log4j.Logger;


import java.util.List;

public class TourRepository {

    private final HibernateSessionFactory sessionFactory;
    private final EventAggregator eventAggregator;
    private static final Logger logger = Logger.getLogger(TourRepository.class);


    public TourRepository(
            HibernateSessionFactory sessionFactory,
            EventAggregator eventAggregator
    ) {
        this.sessionFactory = sessionFactory;
        this.eventAggregator = eventAggregator;
    }

    public void save(Tour name) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(name);
            session.getTransaction().commit();
            logger.info("Tour saved successfully: " + name);
        }
        catch (Exception e) {
            logger.error("Error occurred while saving tour", e);
        e.printStackTrace();
        }

        eventAggregator.publish(Event.NEW_TOUR);
    }

    public void delete(String existingTourName) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Tour tour = findByName(existingTourName);
            if (tour != null) {
                session.delete(tour);
                session.getTransaction().commit();
                eventAggregator.publish(Event.TOUR_DELETED);
                logger.info("Tour deleted successfully: " + existingTourName);
            } else {
                session.getTransaction().rollback();
                session.getTransaction().rollback();
                logger.error("Tour not found for deletion: " + existingTourName);
                // Handle case when the tour with the given name doesn't exist
                // You can throw an exception, log an error, or handle it in any way you prefer.
            }
        }
    }

    public void modify(String tourName, Tour newTour) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Tour existingTour = findByName(tourName);
            if (existingTour != null) {
                existingTour.setName(newTour.getName());
                existingTour.setTourFrom(newTour.getTourFrom());
                existingTour.setTourTo(newTour.getTourTo());
                existingTour.setTime(newTour.getTime());
                existingTour.setTransportType(newTour.getTransportType());
                existingTour.setDescription(newTour.getDescription());
                // Update other properties of the tour as needed

                session.merge(existingTour);
                session.getTransaction().commit();
                logger.info("Tour modified successfully: " + tourName);
            } else {
                session.getTransaction().rollback();
                logger.error("Tour not found for modification: " + tourName);
                // Handle case when the tour with the given name doesn't exist
                // You can throw an exception, log an error, or handle it in any way you prefer.
            }
        }
    }
    public void selectTourName(String tourName){
        eventAggregator.publish(Event.TOUR_SELECTED);
    }
    public List<Tour> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Tour> criteria = builder.createQuery(Tour.class);
            criteria.from(Tour.class);

            return session.createQuery(criteria).getResultList();
        }
    }

    public Tour findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Tour.class, id);
        }
    }

    public Tour findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Tour> criteria = builder.createQuery(Tour.class);
            Root<Tour> root = criteria.from(Tour.class);
            criteria.where(builder.equal(root.get("name"), name));

            return session.createQuery(criteria).uniqueResult();
        }
    }

}
