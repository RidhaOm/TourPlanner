package com.example.tourplanner.Service;

import com.example.tourplanner.Model.Tour;
import com.example.tourplanner.data.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
