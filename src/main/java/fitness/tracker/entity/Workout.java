package fitness.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Workout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long workoutId;
	private String workoutName;
	private int daysPerWeek;
	private String dateStarted;
	private String duration;
	private int caloriesBurned;


	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_workout", joinColumns = @JoinColumn(
			name = "workout_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Exercise> exercises = new HashSet<>();

}
