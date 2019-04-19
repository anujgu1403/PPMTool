package io.agileintelligence.ppmtool.web;

import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.services.MapValidationErrorService;
import io.agileintelligence.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id, Principal principal) {

		ResponseEntity<?> erroMap = mapValidationErrorService.MapValidationService(result);
		if (erroMap != null)
			return erroMap;

		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

	}

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {
		return projectTaskService.findBacklogById(backlog_id, principal.getName());
	}

	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<ProjectTask> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
		ProjectTask projectTask = projectTaskService.findProjectTaskBySequence(backlog_id, pt_id, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}
	
	@PatchMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
		ResponseEntity<?> erroMap = mapValidationErrorService.MapValidationService(result);
		if (erroMap != null)
			return erroMap;
		
		ProjectTask projectTaskResp = projectTaskService.updateProjectTaskBySequence(projectTask, backlog_id, pt_id, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTaskResp, HttpStatus.OK);
	}
	
	@DeleteMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
		projectTaskService.deleteProjectTaskBySequence(backlog_id, pt_id, principal.getName());
		return new ResponseEntity<String>("Project Task "+pt_id+" has been deleted", HttpStatus.OK);
	}
}
