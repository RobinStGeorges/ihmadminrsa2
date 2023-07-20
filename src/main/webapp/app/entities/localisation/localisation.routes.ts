import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LocalisationComponent } from './list/localisation.component';
import { LocalisationDetailComponent } from './detail/localisation-detail.component';
import { LocalisationUpdateComponent } from './update/localisation-update.component';
import LocalisationResolve from './route/localisation-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const localisationRoute: Routes = [
  {
    path: '',
    component: LocalisationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LocalisationDetailComponent,
    resolve: {
      localisation: LocalisationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LocalisationUpdateComponent,
    resolve: {
      localisation: LocalisationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LocalisationUpdateComponent,
    resolve: {
      localisation: LocalisationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default localisationRoute;
