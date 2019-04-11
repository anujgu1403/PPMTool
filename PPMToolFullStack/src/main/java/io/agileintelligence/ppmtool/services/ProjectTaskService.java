package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.exceptions.ProjectIdException;
import io.agileintelligence.ppmtool.exceptions.ProjectNotFoundException;
import io.agileintelligence.ppmtool.repositories.BacklogRepository;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        //Exceptions: Project not found    	
        //PTs to be added to a specific project, project != null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        if(null==backlog){
        	throw new ProjectNotFoundException(projectIdentifier+ " project not found");
        }
        //set the bl to pt
        projectTask.setBacklog(backlog);
        //we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
        Integer BacklogSequence = backlog.getPTSequence();
        // Update the BL SEQUENCE
        BacklogSequence++;
        
        backlog.setPTSequence(BacklogSequence);
        //Add Sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
        projectTask.setProjectIdentifer(projectIdentifier);

        //INITIAL priority when priority null
        if(projectTask.getPriority()==null||projectTask.getPriority()==0){
            projectTask.setPriority(3);
       }
        //INITIAL status when status is null
        if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);        
    }
    
    public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		return projectTaskRepository.findByProjectIdentiferOrderByPriority(backlog_id);
	}
    
    public ProjectTask findProjectTaskBySequence(String backlog_id, String pt_id) {
    	
    	//TO check if backlog is exists
    	Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
    	  if(null==backlog){
          	throw new ProjectNotFoundException("Project task under "+backlog_id+ " backlog is not found");
          }
    	  
    	ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
    	 if(null==projectTask){
           	throw new ProjectNotFoundException("Project task with "+pt_id	+ " not found");
           }
    	
    	 if(!projectTask.getProjectIdentifer().equalsIgnoreCase(backlog_id)){
    		 throw new ProjectNotFoundException("Project task is not found for "+backlog_id+ " backlog");
    	 }
    	 
		return projectTask;
	}
    
 public ProjectTask updateProjectTaskBySequence(ProjectTask projectTask) {
    	
    	//TO check if backlog is exists
    	/*Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
    	  if(null==backlog){
          	throw new ProjectNotFoundException("Project task under "+backlog_id+ " backlog is not found");
          }*/
    	  
    	ProjectTask projectTask1 = projectTaskRepository.findByProjectSequence(projectTask.getProjectSequence());
    	 if(null==projectTask1){
           	throw new ProjectNotFoundException("Project task with "+projectTask.getProjectSequence()+ " not found");
           }    	     	
		return projectTaskRepository.save(projectTask);
	}
}
