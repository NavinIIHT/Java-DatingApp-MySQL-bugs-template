package com.iiht.training.datingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.training.datingapp.dto.UserDto;
import com.iiht.training.datingapp.exceptions.InvalidDataException;
import com.iiht.training.datingapp.service.UserService;

@RestController
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<?> getUsers() {

		List<UserDto> list = userService.findAll();
		if (list.size() == 0) {
			return new ResponseEntity<List<UserDto>>(list, HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<?> getUserById(@RequestParam("userId") Long userId) {

		UserDto userDto = userService.getById(userId);

		return ResponseEntity.ok(userDto);

	}

	@PostMapping("/users")
	public ResponseEntity<?> saveUser(UserDto userDto, BindingResult result) {

		if (result.hasErrors()) {
			throw new InvalidDataException("Invalid User Data");
		}
		userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);

	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUser(@RequestParam Long userId) {

		userService.deleteUser(userId);
		return new ResponseEntity<>(true, HttpStatus.OK);

	}

	@PutMapping("/users")
	public ResponseEntity<?> updateUser(UserDto userDto, BindingResult result) {

		if (!result.hasErrors()) {
			throw new InvalidDataException("Invalid User Data");
		}
		userService.updateUser(userDto);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);

	}
}
