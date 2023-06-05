package at.technikum.tourplanner.repository;

import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.data.HibernateSessionFactory;
import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import java.util.List;

public class TourRepository {

    private final HibernateSessionFactory sessionFactory;
    private final EventAggregator eventAggregator;

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
        }

        eventAggregator.publish(Event.NEW_TOUR);
    }

    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Tour tour = session.get(Tour.class, id);
            if (tour != null) {
                session.delete(tour);
                session.getTransaction().commit();
                eventAggregator.publish(Event.DELETE_TOUR);
            } else {
                session.getTransaction().rollback();
                // Handle case when the tour with the given ID doesn't exist
                // You can throw an exception, log an error, or handle it in any way you prefer.
            }
        }
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
}
