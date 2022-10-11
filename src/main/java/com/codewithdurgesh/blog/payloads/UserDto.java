package com.codewithdurgesh.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private int id;

	@NotNull
	@NotEmpty
	@Size(min = 4, message = "User Name must be of 4 characters")
	private String name;

	@Email(message = "Email address is not valid")
	private String email;

	@NotNull
	@Size(min=3,max=10,message = "Password must be min of 3 character and max of 10 character!!!")
	private String password;

	@NotNull
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
}
