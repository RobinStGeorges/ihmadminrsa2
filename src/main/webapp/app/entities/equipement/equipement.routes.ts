import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EquipementComponent } from './list/equipement.component';
import { EquipementDetailComponent } from './detail/equipement-detail.component';
import { EquipementUpdateComponent } from './update/equipement-update.component';
import EquipementResolve from './route/equipement-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const equipementRoute: Routes = [
  {
    path: '',
    component: EquipementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EquipementDetailComponent,
    resolve: {
      equipement: EquipementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EquipementUpdateComponent,
    resolve: {
      equipement: EquipementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EquipementUpdateComponent,
    resolve: {
      equipement: EquipementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default equipementRoute;
