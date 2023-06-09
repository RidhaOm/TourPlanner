package at.technikum.tourplanner.repository;

import at.technikum.tourplanner.data.HibernateSessionFactory;
import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.model.TourLog;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.apache.log4j.Logger;


import java.util.List;

public class TourLogRepository {
    private final HibernateSessionFactory sessionFactory;
    private final EventAggregator eventAggregator;
    private static final Logger logger = Logger.getLogger(TourLogRepository.class);


    public TourLogRepository(HibernateSessionFactory sessionFactory,EventAggregator eventAggregator) {
        this.sessionFactory = sessionFactory;
        this.eventAggregator = eventAggregator;
    }

    public void save(TourLog tourLog) {
        logger.info("Create tour log for the tour: " + tourLog.getTourName() + "\n" + tourLog.getDate() + "\n" + tourLog.getDuration() + "\n" + tourLog.getDifficulty() + "\n" + tourLog.getRanking() + "\n" + tourLog.getComment());
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(tourLog);
            session.getTransaction().commit();
        }

        eventAggregator.publish(Event.NEW_TOUR_LOG);
    }

    public void delete(String existingTourLogName) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            TourLog tourLog= findByName(existingTourLogName);
            if (tourLog != null) {
                session.delete(tourLog);
                session.getTransaction().commit();
                eventAggregator.publish(Event.TOUR_LOG_DELETED);
            } else {
                session.getTransaction().rollback();
                logger.error("Tour log with name '" + existingTourLogName + "' not found.");
                // Handle case when the tour with the given name doesn't exist
                // You can throw an exception, log an error, or handle it in any way you prefer.
            }
        }
    }
    public void modify(String tourLogName, TourLog newTourLog) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            TourLog existingTourLog = findByName(tourLogName);
            if (existingTourLog != null) {
                existingTourLog.setTourName(newTourLog.getTourName());
                existingTourLog.setDate(newTourLog.getDate());
                existingTourLog.setDuration(newTourLog.getDuration());
                existingTourLog.setDifficulty(newTourLog.getDifficulty());
                existingTourLog.setRanking(newTourLog.getRanking());
                existingTourLog.setComment(newTourLog.getComment());
                existingTourLog.setName(newTourLog.getName());


                session.merge(existingTourLog);
                session.getTransaction().commit();
                eventAggregator.publish(Event.TOUR_LOG_MODIFIED);
            } else {
                session.getTransaction().rollback();
                logger.error("Tour log with name '" + tourLogName + "' not found.");
                // Handle case when the tour with the given name doesn't exist
                // You can throw an exception, log an error, or handle it in any way you prefer.
            }
        }
    }

    public List<TourLog> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteria = builder.createQuery(TourLog.class);
            criteria.from(TourLog.class);

            return session.createQuery(criteria).getResultList();
        }
    }
    public List<TourLog> findByTourName(String tourName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteria = builder.createQuery(TourLog.class);
            Root<TourLog> root = criteria.from(TourLog.class);
            criteria.where(builder.equal(root.get("tourName"), tourName));

            return session.createQuery(criteria).getResultList();
        }
    }
    public TourLog findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteria = builder.createQuery(TourLog.class);
            Root<TourLog> root = criteria.from(TourLog.class);
            criteria.where(builder.equal(root.get("name"), name));

            return session.createQuery(criteria).uniqueResult();
        }
    }
}
