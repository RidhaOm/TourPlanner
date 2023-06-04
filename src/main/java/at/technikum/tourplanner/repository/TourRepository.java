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

    public void save(Tour word) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(word);
            session.getTransaction().commit();
        }

        eventAggregator.publish(Event.NEW_TOUR);
    }

    public List<Tour> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Tour> criteria = builder.createQuery(Tour.class);
            criteria.from(Tour.class);

            return session.createQuery(criteria).getResultList();
        }
    }

}
