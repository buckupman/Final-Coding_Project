package fitness.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fitness.tracker.entity.Workout;

public interface WorkoutDao extends JpaRepository<Workout, Long> {

//	List<WorkoutData> findByTargetArea(String targetArea);

}
