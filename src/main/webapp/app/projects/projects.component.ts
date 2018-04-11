import { Component, OnInit } from '@angular/core';
import {ProjectService} from "../entities/project";
import {Project} from '../entities/project/project.model';
import {forEach} from "@angular/router/src/utils/collection";

@Component({
  selector: 'jhi-projects',
  templateUrl: './projects.component.html',
  styles: []
})
export class ProjectsComponent implements OnInit {

    projects: Project[];

  constructor(private projectService:ProjectService) { }

  ngOnInit() {


      this.projectService.query().subscribe( res=>{
          this.projects =res.body;
          console.log(res.body);
      })

  }

}
