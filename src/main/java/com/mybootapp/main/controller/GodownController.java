package com.mybootapp.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybootapp.main.model.Godown;
import com.mybootapp.main.model.Manager;
import com.mybootapp.main.service.GodownService;
import com.mybootapp.main.service.ManagerService;

@RestController
@RequestMapping("/godown")
public class GodownController {

	@Autowired
	private GodownService godownService;

	@Autowired
	private ManagerService managerService;

	@PostMapping("/add/{managerID}")
	public ResponseEntity<?> insertGodown(@PathVariable("managerID") int managerID, @RequestBody Godown godown) {
		// Step 0: validation, if needed for Request body, is done is ProductController in PUT api.

		/* Step 1: Validate and fetch Manager from managerId */
		Manager manager = managerService.getById(managerID);
		if (manager == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Manager ID");

		/* Step 2: attach manager to godown object */
		godown.setManager(manager);

		/* Step 3: save godown object */
		godown = godownService.insert(godown);

		return ResponseEntity.status(HttpStatus.OK).body(godown);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Godown>> getAllGodowns() {
		List<Godown> godowns = godownService.getAllGodowns();
		return ResponseEntity.ok(godowns);
	}

	@GetMapping("/getOne/{godownId}")
	public ResponseEntity<?> getGodownById(@PathVariable("godownId") int godownId) {
		Godown godown = godownService.getById(godownId);
		if (godown == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Godown not found");
		return ResponseEntity.ok(godown);
	}

	@DeleteMapping("/delete/{godownId}")
	public ResponseEntity<String> deleteGodown(@PathVariable("godownId") int godownId) {
		Godown godown = godownService.getById(godownId);
		if (godown == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Godown not found");
		godownService.delete(godown);
		return ResponseEntity.ok("Godown deleted successfully");
	}

	@PutMapping("/update/{godownId}")
	public ResponseEntity<?> updateGodown(@PathVariable("godownId") int godownId, @RequestBody Godown updatedGodown) {
		Godown godown = godownService.getById(godownId);
		if (godown == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Godown not found");

		/* Step 1: Validate and fetch Manager from managerId */
	

		/* Step 2: Update godown details */
		godown.setLocation(updatedGodown.getLocation());
		godown.setCapacityInQuintals(updatedGodown.getCapacityInQuintals());
		

		/* Step 3: Save updated godown */
		godown = godownService.insert(godown);

		return ResponseEntity.status(HttpStatus.OK).body(godown);
	}
}
