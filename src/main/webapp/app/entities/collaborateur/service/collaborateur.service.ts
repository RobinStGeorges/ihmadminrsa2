import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICollaborateur, NewCollaborateur } from '../collaborateur.model';

export type PartialUpdateCollaborateur = Partial<ICollaborateur> & Pick<ICollaborateur, 'id'>;

type RestOf<T extends ICollaborateur | NewCollaborateur> = Omit<T, 'dateEntree' | 'dateSortie'> & {
  dateEntree?: string | null;
  dateSortie?: string | null;
};

export type RestCollaborateur = RestOf<ICollaborateur>;

export type NewRestCollaborateur = RestOf<NewCollaborateur>;

export type PartialUpdateRestCollaborateur = RestOf<PartialUpdateCollaborateur>;

export type EntityResponseType = HttpResponse<ICollaborateur>;
export type EntityArrayResponseType = HttpResponse<ICollaborateur[]>;

@Injectable({ providedIn: 'root' })
export class CollaborateurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/collaborateurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(collaborateur: NewCollaborateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collaborateur);
    return this.http
      .post<RestCollaborateur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(collaborateur: ICollaborateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collaborateur);
    return this.http
      .put<RestCollaborateur>(`${this.resourceUrl}/${this.getCollaborateurIdentifier(collaborateur)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(collaborateur: PartialUpdateCollaborateur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collaborateur);
    return this.http
      .patch<RestCollaborateur>(`${this.resourceUrl}/${this.getCollaborateurIdentifier(collaborateur)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCollaborateur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCollaborateur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCollaborateurIdentifier(collaborateur: Pick<ICollaborateur, 'id'>): string {
    return collaborateur.id;
  }

  compareCollaborateur(o1: Pick<ICollaborateur, 'id'> | null, o2: Pick<ICollaborateur, 'id'> | null): boolean {
    return o1 && o2 ? this.getCollaborateurIdentifier(o1) === this.getCollaborateurIdentifier(o2) : o1 === o2;
  }

  addCollaborateurToCollectionIfMissing<Type extends Pick<ICollaborateur, 'id'>>(
    collaborateurCollection: Type[],
    ...collaborateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const collaborateurs: Type[] = collaborateursToCheck.filter(isPresent);
    if (collaborateurs.length > 0) {
      const collaborateurCollectionIdentifiers = collaborateurCollection.map(
        collaborateurItem => this.getCollaborateurIdentifier(collaborateurItem)!
      );
      const collaborateursToAdd = collaborateurs.filter(collaborateurItem => {
        const collaborateurIdentifier = this.getCollaborateurIdentifier(collaborateurItem);
        if (collaborateurCollectionIdentifiers.includes(collaborateurIdentifier)) {
          return false;
        }
        collaborateurCollectionIdentifiers.push(collaborateurIdentifier);
        return true;
      });
      return [...collaborateursToAdd, ...collaborateurCollection];
    }
    return collaborateurCollection;
  }

  protected convertDateFromClient<T extends ICollaborateur | NewCollaborateur | PartialUpdateCollaborateur>(collaborateur: T): RestOf<T> {
    return {
      ...collaborateur,
      dateEntree: collaborateur.dateEntree?.format(DATE_FORMAT) ?? null,
      dateSortie: collaborateur.dateSortie?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCollaborateur: RestCollaborateur): ICollaborateur {
    return {
      ...restCollaborateur,
      dateEntree: restCollaborateur.dateEntree ? dayjs(restCollaborateur.dateEntree) : undefined,
      dateSortie: restCollaborateur.dateSortie ? dayjs(restCollaborateur.dateSortie) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCollaborateur>): HttpResponse<ICollaborateur> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCollaborateur[]>): HttpResponse<ICollaborateur[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
