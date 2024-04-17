package fitness.tracker.controller.model;

import java.util.HashSet;
import java.util.Set;

import fitness.tracker.entity.Exercise;
import fitness.tracker.entity.User;
import fitness.tracker.entity.Workout;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class WorkoutData {
	private Long workoutId;
	private String workoutName;
	private int daysPerWeek;
	private String dateStarted;
	private String duration;
	private int caloriesBurned;

	private Set<ExerciseData> exercises = new HashSet<>();
	
	private Set<UserData> users = new HashSet<>();

	// Constructor
	public WorkoutData(Workout workout) {
		workoutId = workout.getWorkoutId();
		workoutName = workout.getWorkoutName();
		daysPerWeek = workout.getDaysPerWeek();
		dateStarted = workout.getDateStarted();
		duration = workout.getDuration();
		caloriesBurned = workout.getCaloriesBurned();

		for (Exercise workoutExercise : workout.getExercises()) {
			exercises.add(new ExerciseData(workoutExercise));
		}
		
		for (User user : workout.getUsers()) {
			users.add(new UserData(user));
		}

	}

}