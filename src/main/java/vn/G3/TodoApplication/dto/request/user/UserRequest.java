package vn.G3.TodoApplication.dto.request.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
	@Size(min = 6, max = 20, message = "Length Username is not valid")
	String username;
	@Size(min = 6, max = 20, message = "Length Password is not valid")
	String password;
	String fullName;
	LocalDate dob;
	String role;

}
