import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILocalisation } from '../localisation.model';
import { LocalisationService } from '../service/localisation.service';

export const localisationResolve = (route: ActivatedRouteSnapshot): Observable<null | ILocalisation> => {
  const id = route.params['id'];
  if (id) {
    return inject(LocalisationService)
      .find(id)
      .pipe(
        mergeMap((localisation: HttpResponse<ILocalisation>) => {
          if (localisation.body) {
            return of(localisation.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default localisationResolve;
