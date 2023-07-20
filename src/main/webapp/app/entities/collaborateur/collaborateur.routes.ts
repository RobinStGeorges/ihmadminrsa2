import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CollaborateurComponent } from './list/collaborateur.component';
import { CollaborateurDetailComponent } from './detail/collaborateur-detail.component';
import { CollaborateurUpdateComponent } from './update/collaborateur-update.component';
import CollaborateurResolve from './route/collaborateur-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const collaborateurRoute: Routes = [
  {
    path: '',
    component: CollaborateurComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CollaborateurDetailComponent,
    resolve: {
      collaborateur: CollaborateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CollaborateurUpdateComponent,
    resolve: {
      collaborateur: CollaborateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CollaborateurUpdateComponent,
    resolve: {
      collaborateur: CollaborateurResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default collaborateurRoute;
