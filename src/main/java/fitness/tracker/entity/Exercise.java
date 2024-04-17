package fitness.tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Exercise {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long exerciseId;
	private String exerciseName;
	private String description;
	private int sets;
	private int reps;
	private String targetArea;

	
	@ManyToOne
	@JoinColumn(name = "workout_id")
	private Workout workout;

}
