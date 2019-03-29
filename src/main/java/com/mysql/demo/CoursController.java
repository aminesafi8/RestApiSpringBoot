package com.mysql.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursController {

	@Autowired
	CoursRepository coursRepository;

	@GetMapping("/cours")
	public List<Cours> index() {
		return coursRepository.findAll();
	}

	@GetMapping("/cours/{id}")
	public Cours show(@PathVariable int id) {
		return coursRepository.findById(id).get();
	}

	@PostMapping("/cours/search")
	public List<Cours> search(@RequestBody Map<String, String> body) {
		List<Cours> listeCours = new ArrayList<>();
		String searchTerm = body.get("text");
		for (Cours item : index()) {
			if (item.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
				listeCours.add(item);
		}
		return listeCours;
	}

	@PostMapping("/cours")
	public Cours create(@RequestBody Map<String, String> body) {
		String titre = body.get("titre");
		String description = body.get("description");
		return coursRepository.save(new Cours(titre, description));
	}

	@PutMapping("/cours/{id}")
	public Cours update(@PathVariable int id, @RequestBody Map<String, String> body) {
		Cours cours = coursRepository.findById(id).get();
		cours.setTitre(body.get("titre"));
		cours.setDescription(body.get("description"));
		return coursRepository.save(cours);
	}

	@DeleteMapping("cours/{id}")
	public boolean delete(@PathVariable int id) {
		coursRepository.deleteById(id);
		return true;
	}

}
