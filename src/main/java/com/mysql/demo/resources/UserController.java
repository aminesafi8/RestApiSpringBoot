package com.mysql.demo.resources;

import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mysql.demo.entities.Role;
import com.mysql.demo.entities.User;
import com.mysql.demo.repository.UserRepository;
import com.mysql.demo.utils.ApiKeyGenerator;

@RestController
@CrossOrigin("http://localhost:4200")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/user/{username}/{password}")
	public ResponseEntity userExist(@PathVariable String username, @PathVariable String password) {

		for (User item : userRepository.findAll()) {
			if (item.getUsername().equals(username) && item.getPassword().equals(password))
				return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/api/user")
	public List<User> index() {
		return userRepository.findAll();
	}

	@GetMapping("/api/user/{id}")
	public User show(@PathVariable int id) {
		return userRepository.findById(id).get();
	}

	@PostMapping("/api/user")
	public ResponseEntity<Void> createUser(@RequestBody User user) {
		user.setDateInscription(new Date());
		user.setRole(Role.Developper);
		user.setApiKey(ApiKeyGenerator.generateRandomHexToken(15));
		userRepository.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/api/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {

		User userFounded = userRepository.findById(id).get();
		user.setRole(userFounded.getRole());
		user.setDateInscription(userFounded.getDateInscription());
		user.setApiKey(userFounded.getApiKey());

		User updatedUser = userRepository.save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
