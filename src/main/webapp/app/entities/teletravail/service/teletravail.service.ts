import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITeletravail, NewTeletravail } from '../teletravail.model';

export type PartialUpdateTeletravail = Partial<ITeletravail> & Pick<ITeletravail, 'id'>;

export type EntityResponseType = HttpResponse<ITeletravail>;
export type EntityArrayResponseType = HttpResponse<ITeletravail[]>;

@Injectable({ providedIn: 'root' })
export class TeletravailService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/teletravails');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(teletravail: NewTeletravail): Observable<EntityResponseType> {
    return this.http.post<ITeletravail>(this.resourceUrl, teletravail, { observe: 'response' });
  }

  update(teletravail: ITeletravail): Observable<EntityResponseType> {
    return this.http.put<ITeletravail>(`${this.resourceUrl}/${this.getTeletravailIdentifier(teletravail)}`, teletravail, {
      observe: 'response',
    });
  }

  partialUpdate(teletravail: PartialUpdateTeletravail): Observable<EntityResponseType> {
    return this.http.patch<ITeletravail>(`${this.resourceUrl}/${this.getTeletravailIdentifier(teletravail)}`, teletravail, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ITeletravail>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITeletravail[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTeletravailIdentifier(teletravail: Pick<ITeletravail, 'id'>): string {
    return teletravail.id;
  }

  compareTeletravail(o1: Pick<ITeletravail, 'id'> | null, o2: Pick<ITeletravail, 'id'> | null): boolean {
    return o1 && o2 ? this.getTeletravailIdentifier(o1) === this.getTeletravailIdentifier(o2) : o1 === o2;
  }

  addTeletravailToCollectionIfMissing<Type extends Pick<ITeletravail, 'id'>>(
    teletravailCollection: Type[],
    ...teletravailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const teletravails: Type[] = teletravailsToCheck.filter(isPresent);
    if (teletravails.length > 0) {
      const teletravailCollectionIdentifiers = teletravailCollection.map(
        teletravailItem => this.getTeletravailIdentifier(teletravailItem)!
      );
      const teletravailsToAdd = teletravails.filter(teletravailItem => {
        const teletravailIdentifier = this.getTeletravailIdentifier(teletravailItem);
        if (teletravailCollectionIdentifiers.includes(teletravailIdentifier)) {
          return false;
        }
        teletravailCollectionIdentifiers.push(teletravailIdentifier);
        return true;
      });
      return [...teletravailsToAdd, ...teletravailCollection];
    }
    return teletravailCollection;
  }
}
