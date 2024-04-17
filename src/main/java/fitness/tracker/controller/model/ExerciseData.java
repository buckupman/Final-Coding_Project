package fitness.tracker.controller.model;

import fitness.tracker.entity.Exercise;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExerciseData {
	private Long exerciseId;
	private String exerciseName;
	private String description;
	private int sets;
	private int reps;
	private String targetArea;

	// Constructor
	public ExerciseData(Exercise exercise) {
		exerciseId = exercise.getExerciseId();
		exerciseName = exercise.getExerciseName();
		description = exercise.getDescription();
		sets = exercise.getSets();
		reps = exercise.getReps();
		targetArea = exercise.getTargetArea();

	}
}
