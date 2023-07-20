import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEquipement } from '../equipement.model';
import { EquipementService } from '../service/equipement.service';

export const equipementResolve = (route: ActivatedRouteSnapshot): Observable<null | IEquipement> => {
  const id = route.params['id'];
  if (id) {
    return inject(EquipementService)
      .find(id)
      .pipe(
        mergeMap((equipement: HttpResponse<IEquipement>) => {
          if (equipement.body) {
            return of(equipement.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default equipementResolve;
