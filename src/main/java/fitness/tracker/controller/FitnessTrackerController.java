package fitness.tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fitness.tracker.controller.model.ExerciseData;
import fitness.tracker.controller.model.UserData;
import fitness.tracker.controller.model.WorkoutData;
import fitness.tracker.service.FitnessTrackerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fitness_tracker")
@Slf4j
public class FitnessTrackerController {

	@Autowired
	private FitnessTrackerService fitnessService;

	// Work-out Methods

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public WorkoutData createWorkout(@RequestBody WorkoutData workoutData) {

		log.info("Received request to create a workout: {}", workoutData);

		return fitnessService.saveWorkout(workoutData);
	}

	@PutMapping("/workouts/{workoutId}")
	public WorkoutData editWorkoutInfo(@PathVariable Long workoutId, @RequestBody WorkoutData workoutData) {
		log.info("Editing workout informatin for ID = {}: {}", workoutId, workoutData);
		workoutData.setWorkoutId(workoutId);
		return fitnessService.saveWorkout(workoutData);
	}

	@GetMapping
	public List<WorkoutData> retrieveAllWorkouts() {
		log.info("Retrieve all workouts.");
		return fitnessService.retrieveAllWorkouts();
	}

	@DeleteMapping("/workouts/{workoutId}")
	public Map<String, String> deleteWokoutById(@PathVariable Long workoutId) {
		log.info("Deleting workout with ID = {}", workoutId);

		fitnessService.deleteWorkoutById(workoutId);

		return Map.of("message", "Workout with ID = " + workoutId + " has been successfully deleted.");
	}

	@PostMapping("/workouts/{workoutId}/exercises")
	@ResponseStatus(HttpStatus.CREATED)
	public ExerciseData addExerciseToWorkout(@PathVariable Long workoutId, @RequestBody ExerciseData exerciseData) {
		log.info("Adding an exercise to a workout with ID= {}: {}", workoutId, exerciseData);

		return fitnessService.saveExercise(workoutId, exerciseData);
	}

	// User Methods

	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public UserData createUser(@RequestBody UserData userData) {

		log.info("Received request to create a user: {}", userData);

		return fitnessService.saveUser(userData);
	}

	@PutMapping("/users/{userId}")
	public UserData editUserInfo(@PathVariable Long userId, @RequestBody UserData userData) {
		log.info("Editing user informatin for ID = {}: {}", userId, userData);
		userData.setUserId(userId);
		return fitnessService.saveUser(userData);
	}

	@DeleteMapping("/users/{userId}")
	public Map<String, String> deleteUserById(@PathVariable Long userId) {
		log.info("Deleting user with ID = {}", userId);

		fitnessService.deleteUserById(userId);

		return Map.of("message", "User with ID = " + userId + " has been successfully deleted.");
	}

	@PostMapping("/users/{userId}/workouts")
	@ResponseStatus(HttpStatus.CREATED)
	public UserData addWorkoutToUser(@PathVariable Long userId, @RequestBody WorkoutData workoutData) {
		log.info("Adding a workout to a user with ID = {}: {}", userId, workoutData);

		return fitnessService.addWorkoutToUser(userId, workoutData);
	}

	// Exercise Methods

	@PostMapping("/exercises")
	@ResponseStatus(HttpStatus.CREATED)
	public ExerciseData createExercise(@RequestBody ExerciseData exerciseData) {

		log.info("Received request to create an exercise: {}", exerciseData);

		return fitnessService.saveExercise(exerciseData);
	}

	@DeleteMapping("/exercises/{exerciseId}")
	public Map<String, String> deleteExerciseById(@PathVariable Long exerciseId) {
		log.info("Deleting exercise with ID = {}", exerciseId);

		fitnessService.deleteExerciseById(exerciseId);

		return Map.of("message", "Exercise with ID = " + exerciseId + " has been successfully deleted.");
	}

}
