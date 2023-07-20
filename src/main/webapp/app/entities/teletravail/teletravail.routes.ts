import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TeletravailComponent } from './list/teletravail.component';
import { TeletravailDetailComponent } from './detail/teletravail-detail.component';
import { TeletravailUpdateComponent } from './update/teletravail-update.component';
import TeletravailResolve from './route/teletravail-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const teletravailRoute: Routes = [
  {
    path: '',
    component: TeletravailComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TeletravailDetailComponent,
    resolve: {
      teletravail: TeletravailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TeletravailUpdateComponent,
    resolve: {
      teletravail: TeletravailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TeletravailUpdateComponent,
    resolve: {
      teletravail: TeletravailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default teletravailRoute;
