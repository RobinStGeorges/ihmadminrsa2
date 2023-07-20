import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICollaborateur } from '../collaborateur.model';
import { CollaborateurService } from '../service/collaborateur.service';

export const collaborateurResolve = (route: ActivatedRouteSnapshot): Observable<null | ICollaborateur> => {
  const id = route.params['id'];
  if (id) {
    return inject(CollaborateurService)
      .find(id)
      .pipe(
        mergeMap((collaborateur: HttpResponse<ICollaborateur>) => {
          if (collaborateur.body) {
            return of(collaborateur.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default collaborateurResolve;
