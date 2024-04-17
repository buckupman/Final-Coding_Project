package fitness.tracker.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fitness.tracker.controller.model.ExerciseData;
import fitness.tracker.controller.model.UserData;
import fitness.tracker.controller.model.WorkoutData;
import fitness.tracker.dao.ExerciseDao;
import fitness.tracker.dao.UserDao;
import fitness.tracker.dao.WorkoutDao;
import fitness.tracker.entity.Exercise;
import fitness.tracker.entity.User;
import fitness.tracker.entity.Workout;

@Service
public class FitnessTrackerService {

	@Autowired
	private ExerciseDao exerciseDao;

	@Autowired
	private WorkoutDao workoutDao;

	@Autowired
	private UserDao userDao;

	// User Methods

	public UserData saveUser(UserData userData) {
		User user = getOrCreateUser(userData.getUserId());

		copyUserFields(user, userData);

		user = userDao.save(user);

		return new UserData(user);
	}

	private User getOrCreateUser(Long userId) {
		if (Objects.isNull(userId)) {
			return new User();
		} else {
			User user = getUserById(userId);

			return user;
		}
	}

	private User getUserById(Long userId) {
		return userDao.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User with ID = " + userId + " not found"));
	}

	private void copyUserFields(User user, UserData userData) {
		user.setUserId(userData.getUserId());
		user.setName(userData.getName());
		user.setGender(userData.getGender());
		user.setAge(userData.getAge());
		user.setWeight(userData.getWeight());
		user.setHeight(userData.getHeight());
	}

	public User deleteUserById(Long userId) {
		User user = getUserById(userId);

		userDao.delete(user);

		return user;

	}

	@Transactional(readOnly = false)
	public UserData addWorkoutToUser(Long userId, WorkoutData workoutData) {
		User user = getUserById(userId);

		Workout workout = getOrCreateWorkout(workoutData.getWorkoutId());

		copyWorkoutFields(workout, workoutData);

		workout.getUsers().add(user);

		workoutDao.save(workout);

		return new UserData(user);
	}

	// Work-out Methods

	public WorkoutData saveWorkout(WorkoutData workoutData) {
		Workout workout = getOrCreateWorkout(workoutData.getWorkoutId());

		copyWorkoutFields(workout, workoutData);

		workout = workoutDao.save(workout);

		return new WorkoutData(workout);
	}

	private void copyWorkoutFields(Workout workout, WorkoutData workoutData) {
		workout.setWorkoutId(workoutData.getWorkoutId());
		workout.setWorkoutName(workoutData.getWorkoutName());
		workout.setDaysPerWeek(workoutData.getDaysPerWeek());
		workout.setDateStarted(workoutData.getDateStarted());
		workout.setDuration(workoutData.getDuration());
		workout.setCaloriesBurned(workoutData.getCaloriesBurned());

	}

	private Workout getOrCreateWorkout(Long workoutId) {
		if (Objects.isNull(workoutId)) {
			return new Workout();
		} else {
			Workout workout = getWorkoutById(workoutId);

			return workout;
		}
	}

	private Workout getWorkoutById(Long workoutId) {
		return workoutDao.findById(workoutId)
				.orElseThrow(() -> new NoSuchElementException("Workout with ID = " + workoutId + " not found"));
	}

	@Transactional(readOnly = true)
	public List<WorkoutData> retrieveAllWorkouts() {
		List<Workout> workouts = workoutDao.findAll();
		List<WorkoutData> result = new LinkedList<>();

		for (Workout workout : workouts) {
			WorkoutData workoutData = new WorkoutData(workout);

			result.add(workoutData);
		}
		return result;
	}

	public Workout deleteWorkoutById(Long workoutId) {

		Workout workout = getWorkoutById(workoutId);

		workoutDao.delete(workout);

		return workout;

	}

	// Exercise Methods

	@Transactional(readOnly = false)
	public ExerciseData saveExercise(Long workoutId, ExerciseData exerciseData) {
		Workout workout = getWorkoutById(workoutId);

		Long exerciseId = exerciseData.getExerciseId();

		Exercise exercise = findOrCreateExercise(workoutId, exerciseId);
		copyExerciseFields(exercise, exerciseData);

		workout.getExercises().add(exercise);
		exercise.setWorkout(workout);

		return new ExerciseData(exerciseDao.save(exercise));
	}

	private Exercise findOrCreateExercise(Long workoutId, Long exerciseId) {
		if (Objects.isNull(exerciseId)) {
			return new Exercise();
		}
		return findExerciseById(workoutId, exerciseId);
	}

	private Exercise findExerciseById(Long workoutId, Long exerciseId) {
		Exercise exercise = exerciseDao.findById(exerciseId)
				.orElseThrow(() -> new NoSuchElementException("Exercise with ID = " + exerciseId + "was not found"));
		if (exercise.getWorkout().getWorkoutId() != workoutId) {
			throw new IllegalArgumentException(
					"The exercise with ID = " + exerciseId + "is not part of the workout with ID = " + workoutId);
		}
		return exercise;
	}

	private void copyExerciseFields(Exercise exercise, ExerciseData exerciseData) {
		exercise.setExerciseId(exerciseData.getExerciseId());
		exercise.setExerciseName(exerciseData.getExerciseName());
		exercise.setDescription(exerciseData.getDescription());
		exercise.setSets(exerciseData.getSets());
		exercise.setReps(exerciseData.getReps());
		exercise.setTargetArea(exerciseData.getTargetArea());

	}

	public WorkoutData addExerciseToAWorkout(Long workoutId, ExerciseData exerciseData) {
		Workout workout = workoutDao.findById(workoutId)
				.orElseThrow(() -> new NoSuchElementException("Workout not found with ID = " + workoutId));

		Exercise exercise = new Exercise();
		copyExerciseFields(exercise, exerciseData);

		workout.getExercises().add(exercise);
		workout = workoutDao.save(workout);

		return new WorkoutData(workout);

	}

	public ExerciseData saveExercise(ExerciseData exerciseData) {
		Exercise exercise = new Exercise();

		copyExerciseFields(exercise, exerciseData);

		Exercise savedExercise = exerciseDao.save(exercise);

		return new ExerciseData(savedExercise);
	}

	public Exercise deleteExerciseById(Long exerciseId) {
		Exercise exercise = getExerciseById(exerciseId);

		exerciseDao.delete(exercise);

		return exercise;
	}

	private Exercise getExerciseById(Long exerciseId) {
		return exerciseDao.findById(exerciseId)
				.orElseThrow(() -> new NoSuchElementException("Exercise with ID = " + exerciseId + " not found"));
	}

}
