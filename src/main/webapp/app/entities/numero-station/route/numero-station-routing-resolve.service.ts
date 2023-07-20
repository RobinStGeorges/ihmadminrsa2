import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INumeroStation } from '../numero-station.model';
import { NumeroStationService } from '../service/numero-station.service';

export const numeroStationResolve = (route: ActivatedRouteSnapshot): Observable<null | INumeroStation> => {
  const id = route.params['id'];
  if (id) {
    return inject(NumeroStationService)
      .find(id)
      .pipe(
        mergeMap((numeroStation: HttpResponse<INumeroStation>) => {
          if (numeroStation.body) {
            return of(numeroStation.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default numeroStationResolve;
