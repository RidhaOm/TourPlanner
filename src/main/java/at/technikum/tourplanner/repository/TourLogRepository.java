package at.technikum.tourplanner.repository;

import at.technikum.tourplanner.data.HibernateSessionFactory;
import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.TourLog;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

public class TourLogRepository {
    private final HibernateSessionFactory sessionFactory;
    private final EventAggregator eventAggregator;
    private final TourRepository tourRepository;
    private static final Logger logger = Logger.getLogger(TourLogRepository.class);


    public TourLogRepository(HibernateSessionFactory sessionFactory,EventAggregator eventAggregator, TourRepository tourRepository) {
        this.sessionFactory = sessionFactory;
        this.eventAggregator = eventAggregator;
        this.tourRepository = tourRepository;
    }

    public void save(TourLog tourLog) {
        logger.info("Create tour log for the tour: " + tourLog.getTourName() + "\n" + tourLog.getDate() + "\n" + tourLog.getDuration() + "\n" + tourLog.getDifficulty() + "\n" + tourLog.getRanking() + "\n" + tourLog.getComment());
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(tourLog);
            session.getTransaction().commit();
            //Update popularity and ChildFriendliness
            String tourName = tourLog.getTourName();
            Tour newTour = tourRepository.findByName(tourName);
            newTour.setPopularity(newTour.getPopularity()+1);
            double childFriendliness = setChildFriendliness(tourName);
            newTour.setChildFriendliness(childFriendliness);
            tourRepository.modify(tourName,newTour);

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
                //Update popularity and ChildFriendliness
                String tourName = tourLog.getTourName();
                Tour newTour = tourRepository.findByName(tourName);
                newTour.setPopularity(newTour.getPopularity()-1);
                double childFriendliness = setChildFriendliness(tourName);
                newTour.setChildFriendliness(childFriendliness);
                tourRepository.modify(tourName,newTour);

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
    public void deleteAllByTourName(String tourName) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            List<TourLog> tourLogs = findByTourName(tourName);
            if (!tourLogs.isEmpty()) {
                for (TourLog tourLog : tourLogs) {
                    session.delete(tourLog);
                }
                session.getTransaction().commit();
                eventAggregator.publish(Event.TOUR_LOG_MODIFIED);
                logger.info("Deleted all tour logs for tour name: " + tourName);
            } else {
                session.getTransaction().rollback();
                logger.error("No tour logs found for tour name: " + tourName);
                // Handle case when no tour logs exist for the given tour name
                // You can throw an exception, log an error, or handle it in any way you prefer.
            }
        }
    }

    public double findDifficultyByTourName(String tourName){
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Double> criteria = builder.createQuery(Double.class);
            Root<TourLog> root = criteria.from(TourLog.class);

            criteria.select(builder.avg(root.get("difficulty")));
            criteria.where(builder.equal(root.get("tourName"), tourName));

            Double averageDifficulty = session.createQuery(criteria).uniqueResult();
            return averageDifficulty != null ? averageDifficulty : 0.0;
        }
    }
    public double findDurationByTourName(String tourName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Double> criteria = builder.createQuery(Double.class);
            Root<TourLog> root = criteria.from(TourLog.class);

            criteria.select(builder.avg(root.get("duration")));
            criteria.where(builder.equal(root.get("tourName"), tourName));

            Double averageDuration = session.createQuery(criteria).uniqueResult();
            return averageDuration != null ? averageDuration : 0.0;
        }
    }
    public double setChildFriendliness(String tourName){
        //Distance note:
        double distance = tourRepository.findByName(tourName).getDistance();
        int distanceNote ;
        if (distance<100){ distanceNote = 1; }
        else if (distance<200){ distanceNote = 2; }
        else if (distance<300){ distanceNote = 3; }
        else if (distance<400){ distanceNote = 4; }
        else {distanceNote = 5; }
        //Duration Note:
        int durationNote ;
        double duration = findDurationByTourName(tourName);
        if (duration<2){ durationNote = 1; }
        else if (duration<3){ durationNote = 2; }
        else if (duration<4){ durationNote = 3; }
        else if (duration<5){ durationNote = 4; }
        else {durationNote = 5; }
        //Difficulty Note:
        double difficultyNote = findDifficultyByTourName(tourName);
        //ChildFriendliness Note;
        double childFriendliness = (distanceNote + durationNote + difficultyNote)/3;
        return childFriendliness;
    }

    public List<TourLog> search(String text) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteria = builder.createQuery(TourLog.class);
            Root<TourLog> root = criteria.from(TourLog.class);

            // Create predicates for each attribute that you want to search
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.like(root.get("tourName"), "%" + text + "%"));
            predicates.add(builder.like(root.get("date"), "%" + text + "%"));
            predicates.add(builder.like(root.get("comment"), "%" + text + "%"));
            predicates.add(builder.like(root.get("name"), "%" + text + "%"));

            // Combine the predicates using OR
            Predicate searchPredicate = builder.or(predicates.toArray(new Predicate[0]));

            criteria.where(searchPredicate);

            return session.createQuery(criteria).getResultList();
        }
    }
}
