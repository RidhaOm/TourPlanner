package at.technikum.tourplanner.Service;

import at.technikum.tourplanner.Model.Tour;
import at.technikum.tourplanner.data.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TourService {
    private final HibernateSessionFactory sessionFactory;

    public TourService(HibernateSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createTour(Tour tour) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(tour);
            transaction.commit();
        }
    }

    public Tour readTour(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Tour.class, id);
        }
    }

    public void updateTour(Tour tour) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(tour);
            transaction.commit();
        }
    }

    public void deleteTour(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Tour tour = session.get(Tour.class, id);
            if (tour != null) {
                session.delete(tour);
            }
            transaction.commit();
        }
    }
}
