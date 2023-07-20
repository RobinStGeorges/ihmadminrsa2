import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INumeroStation, NewNumeroStation } from '../numero-station.model';

export type PartialUpdateNumeroStation = Partial<INumeroStation> & Pick<INumeroStation, 'id'>;

export type EntityResponseType = HttpResponse<INumeroStation>;
export type EntityArrayResponseType = HttpResponse<INumeroStation[]>;

@Injectable({ providedIn: 'root' })
export class NumeroStationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/numero-stations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(numeroStation: NewNumeroStation): Observable<EntityResponseType> {
    return this.http.post<INumeroStation>(this.resourceUrl, numeroStation, { observe: 'response' });
  }

  update(numeroStation: INumeroStation): Observable<EntityResponseType> {
    return this.http.put<INumeroStation>(`${this.resourceUrl}/${this.getNumeroStationIdentifier(numeroStation)}`, numeroStation, {
      observe: 'response',
    });
  }

  partialUpdate(numeroStation: PartialUpdateNumeroStation): Observable<EntityResponseType> {
    return this.http.patch<INumeroStation>(`${this.resourceUrl}/${this.getNumeroStationIdentifier(numeroStation)}`, numeroStation, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<INumeroStation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INumeroStation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNumeroStationIdentifier(numeroStation: Pick<INumeroStation, 'id'>): string {
    return numeroStation.id;
  }

  compareNumeroStation(o1: Pick<INumeroStation, 'id'> | null, o2: Pick<INumeroStation, 'id'> | null): boolean {
    return o1 && o2 ? this.getNumeroStationIdentifier(o1) === this.getNumeroStationIdentifier(o2) : o1 === o2;
  }

  addNumeroStationToCollectionIfMissing<Type extends Pick<INumeroStation, 'id'>>(
    numeroStationCollection: Type[],
    ...numeroStationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const numeroStations: Type[] = numeroStationsToCheck.filter(isPresent);
    if (numeroStations.length > 0) {
      const numeroStationCollectionIdentifiers = numeroStationCollection.map(
        numeroStationItem => this.getNumeroStationIdentifier(numeroStationItem)!
      );
      const numeroStationsToAdd = numeroStations.filter(numeroStationItem => {
        const numeroStationIdentifier = this.getNumeroStationIdentifier(numeroStationItem);
        if (numeroStationCollectionIdentifiers.includes(numeroStationIdentifier)) {
          return false;
        }
        numeroStationCollectionIdentifiers.push(numeroStationIdentifier);
        return true;
      });
      return [...numeroStationsToAdd, ...numeroStationCollection];
    }
    return numeroStationCollection;
  }
}
