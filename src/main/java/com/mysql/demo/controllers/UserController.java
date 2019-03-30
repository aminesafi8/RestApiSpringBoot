package com.mysql.demo.controllers;

import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

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
		user.setApiKey(generateRandomHexToken(15));
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

	/**
	 * This meethod is dedicated to generate random API KEY
	 * 
	 * @param byteLength
	 * @return
	 */
	public static String generateRandomHexToken(int byteLength) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] token = new byte[byteLength];
		secureRandom.nextBytes(token);
		return new BigInteger(1, token).toString(16); // hex encoding
	}

}
