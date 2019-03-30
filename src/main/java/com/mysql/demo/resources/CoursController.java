package com.mysql.demo.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mysql.demo.entities.Cours;
import com.mysql.demo.repository.CoursRepository;

@RestController
public class CoursController {

	@Autowired
	CoursRepository coursRepository;

	@GetMapping("/api/cours")
	public List<Cours> index() {
		return coursRepository.findAll();
	}

	@GetMapping("/api/cours/{id}")
	public Cours show(@PathVariable int id) {
		return coursRepository.findById(id).get();
	}

	@PostMapping("/api/cours/search")
	public List<Cours> search(@RequestBody Map<String, String> body) {
		List<Cours> listeCours = new ArrayList<>();
		String searchTerm = body.get("text");
		for (Cours item : index()) {
			if (item.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
				listeCours.add(item);
		}
		return listeCours;
	}

	@PostMapping("/api/cours")
	public ResponseEntity<Void> create(@RequestBody Map<String, String> body) {
		String titre = body.get("titre");
		String description = body.get("description");

		Cours cours = new Cours();
		cours.setTitre(titre);
		cours.setDescription(description);
		coursRepository.save(cours);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cours.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/api/cours/{id}")
	public Cours update(@PathVariable int id, @RequestBody Map<String, String> body) {
		Cours cours = coursRepository.findById(id).get();
		cours.setTitre(body.get("titre"));
		cours.setDescription(body.get("description"));
		return coursRepository.save(cours);
	}

	@DeleteMapping("/api/cours/{id}")
	public boolean delete(@PathVariable int id) {
		coursRepository.deleteById(id);
		return true;
	}

}
