package fitness.tracker.controller.model;

import fitness.tracker.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserData {
	private Long userId;
	private String name;
	private String gender;
	private int age;
	private double weight;
	private String height;

	public UserData(User user) {
		userId = user.getUserId();
		name = user.getName();
		gender = user.getGender();
		age = user.getAge();
		weight = user.getWeight();
		height = user.getHeight();

	}

}
