import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from './app.constants';
import {ProjectsComponent} from "./projects/projects.component";
import {ProjectsAddComponent} from "./projects/projects-add/projects-add.component";

const LAYOUT_ROUTES = [
    navbarRoute,
    ...errorRoute,
    {
        path:'projects',component:ProjectsComponent
    },
    {
        path:'project-new',component: ProjectsAddComponent

    }
];

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true , enableTracing: DEBUG_INFO_ENABLED })
    ],
    exports: [
        RouterModule
    ]
})
export class PfaprojectAppRoutingModule {}
