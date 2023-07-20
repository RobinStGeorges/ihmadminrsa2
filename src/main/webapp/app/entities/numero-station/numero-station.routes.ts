import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NumeroStationComponent } from './list/numero-station.component';
import { NumeroStationDetailComponent } from './detail/numero-station-detail.component';
import { NumeroStationUpdateComponent } from './update/numero-station-update.component';
import NumeroStationResolve from './route/numero-station-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const numeroStationRoute: Routes = [
  {
    path: '',
    component: NumeroStationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NumeroStationDetailComponent,
    resolve: {
      numeroStation: NumeroStationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NumeroStationUpdateComponent,
    resolve: {
      numeroStation: NumeroStationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NumeroStationUpdateComponent,
    resolve: {
      numeroStation: NumeroStationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default numeroStationRoute;
