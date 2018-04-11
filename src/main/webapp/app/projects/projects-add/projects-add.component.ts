import { Component, OnInit } from '@angular/core';
import {Project, ProjectService} from "../../entities/project";
import {Category, CategoryService} from "../../entities/category";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {JhiAlertService} from "ng-jhipster";

@Component({
  selector: 'jhi-projects-add',
  templateUrl: './projects-add.component.html',
  styles: []
})
export class ProjectsAddComponent implements OnInit {

   private project:Project= new Project();
    categories: Category[];
  constructor(private projectService:ProjectService,private jhiAlertService:JhiAlertService ,private categoryService: CategoryService ) { }

  ngOnInit() {
      this.categoryService.query()
          .subscribe((res: HttpResponse<Category[]>) => { this.categories = res.body; console.log(res.body) }, (res: HttpErrorResponse) => this.onError(res.message));

  }




  saveProject() {
        this.projectService.create(this.project).subscribe( res=>{
            console.log(res);
        },error2 => {
            console.log(error2);
        });
  }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }
}
