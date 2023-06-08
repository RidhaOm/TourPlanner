package at.technikum.tourplanner.repository;

import at.technikum.tourplanner.data.HibernateSessionFactory;
import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.TourLog;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import java.util.List;

public class TourLogRepository {
    private final HibernateSessionFactory sessionFactory;
    private final EventAggregator eventAggregator;

    public TourLogRepository(HibernateSessionFactory sessionFactory,EventAggregator eventAggregator) {
        this.sessionFactory = sessionFactory;
        this.eventAggregator = eventAggregator;
    }

    public void save(TourLog tourLog) {
        System.out.println("Create tour log for the tour: "+tourLog.getTourName() + "\n"+ tourLog.getDate() +"\n"+ tourLog.getDuration() +"\n"+ tourLog.getDifficulty() +"\n" + tourLog.getRanking() +"\n"+tourLog.getComment());
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(tourLog);
            session.getTransaction().commit();
        }

        eventAggregator.publish(Event.NEW_TOUR_LOG);
    }

    public List<TourLog> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteria = builder.createQuery(TourLog.class);
            criteria.from(TourLog.class);

            return session.createQuery(criteria).getResultList();
        }
    }
}
