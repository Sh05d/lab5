package org.example.projecttracker.Controller;

import org.example.projecttracker.ApiResponse.ApiResponse;
import org.example.projecttracker.Model.Project;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/project-tracker")
public class ProjectController {

    ArrayList<Project> projects = new ArrayList<>();

    @PostMapping("/create-project")
    public ApiResponse CreateProject(@RequestBody Project newProject){
        for(Project project: projects){
            if(project.getId().equals(newProject.getId()))
                return new ApiResponse("This project exist before");
        }
        projects.add(newProject);
        return new ApiResponse("Project added successfully");
    }

    @GetMapping("/display-projects")
    public ArrayList<Project> displayProjects(){
        return projects;
    }

    @PutMapping("/update-project/{id}")
    public ApiResponse updateProject(@PathVariable String id,@RequestBody Project updatedProject){
        for(int i=0; i<projects.size(); i++){
            if(projects.get(i).getId().equals(id)){
                updatedProject.setId(id);
                projects.set(i, updatedProject);
                return new ApiResponse("Project updated successfully");
            }
        }
        return new ApiResponse("Project "+id+" not found");
    }

    @DeleteMapping("/delete-project/{id}")
    public ApiResponse deleteProject(@PathVariable String id){
        for(int i=0; i< projects.size(); i++){
            if(projects.get(i).getId().equals(id)){
                projects.remove(i);
                return new ApiResponse("Project deleted successfully");
            }
        }
        return new ApiResponse("Project "+id+" not found");
    }

    @PutMapping("/change-status/{id}/{status}")
    public ApiResponse changeStatus(@PathVariable String id,@PathVariable String status){
        if(!status.equalsIgnoreCase("not done") && !status.equalsIgnoreCase("done"))
            return new ApiResponse("Status should be either done or not done");
        for(int i=0; i < projects.size(); i++){
            if(projects.get(i).getId().equals(id)){
                projects.get(i).setStatus(status);
                return new ApiResponse("Status changed successfully");
            }
        }
        return new ApiResponse("Project "+id+" not found");
    }

    @GetMapping("/search-by-title/{title}")
    public ArrayList<Project> searchByTitle(@PathVariable String title){
        ArrayList<Project> projectsByTitle = new ArrayList<>();
        for(Project project: projects){
            if(project.getTitle().equals(title))
                projectsByTitle.add(project);
        }
        return projectsByTitle;
    }

    @GetMapping("/company-projects/{companyName}")
    public ArrayList<Project> displayCompanyProjects(@PathVariable String companyName){
        ArrayList<Project> companyProjects = new ArrayList<>();
        for(Project project: projects){
            if(project.getCompanyName().equals(companyName))
                companyProjects.add(project);

        }
        return companyProjects;
    }
}
