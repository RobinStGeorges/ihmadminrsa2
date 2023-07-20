import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjet } from '../projet.model';
import { ProjetService } from '../service/projet.service';

export const projetResolve = (route: ActivatedRouteSnapshot): Observable<null | IProjet> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProjetService)
      .find(id)
      .pipe(
        mergeMap((projet: HttpResponse<IProjet>) => {
          if (projet.body) {
            return of(projet.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default projetResolve;
