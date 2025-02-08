package com.shivam.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	@NotEmpty
	@Size(min=4, message="UserName should be minimum of 4 charater !!!")
	private String name;
	@Email(message="Invalid EmilId !!!")
	private String emailId;
	@NotEmpty
	@Size(min=8,  message="Password should be minimum of 8 charater !!!")
	private String password;
	
	private String about;

}
