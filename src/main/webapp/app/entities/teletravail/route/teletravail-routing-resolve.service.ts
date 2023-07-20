import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITeletravail } from '../teletravail.model';
import { TeletravailService } from '../service/teletravail.service';

export const teletravailResolve = (route: ActivatedRouteSnapshot): Observable<null | ITeletravail> => {
  const id = route.params['id'];
  if (id) {
    return inject(TeletravailService)
      .find(id)
      .pipe(
        mergeMap((teletravail: HttpResponse<ITeletravail>) => {
          if (teletravail.body) {
            return of(teletravail.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default teletravailResolve;
