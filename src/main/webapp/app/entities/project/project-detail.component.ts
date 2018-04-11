import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Project } from './project.model';
import { ProjectService } from './project.service';
import {Principal} from "../../shared";

@Component({
    selector: 'jhi-project-detail',
    templateUrl: './project-detail.component.html'
})
export class ProjectDetailComponent implements OnInit, OnDestroy {

    project: Project;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    private principal:Principal;
    private currentuserid:any;
    emailAffiche=false;

    constructor(
        private principale:Principal,
        private eventManager: JhiEventManager,
        private projectService: ProjectService,
        private route: ActivatedRoute
    ) {
        this.principal = principale;
         this.principal.identity().then(res=>{
            console.log(res);
            this.currentuserid = res.id;
        });
    }


    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProjects();
    }

    load(id) {
        this.projectService.find(id)
            .subscribe((projectResponse: HttpResponse<Project>) => {
                this.project = projectResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProjects() {
        this.eventSubscriber = this.eventManager.subscribe(
            'projectListModification',
            (response) => this.load(this.project.id)
        );
    }

    afficherMail() {
        this.emailAffiche= true;

    }
}
