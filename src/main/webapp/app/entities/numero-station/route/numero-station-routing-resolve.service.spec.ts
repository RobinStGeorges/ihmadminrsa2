import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap, RouterStateSnapshot } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { INumeroStation } from '../numero-station.model';
import { NumeroStationService } from '../service/numero-station.service';

import numeroStationResolve from './numero-station-routing-resolve.service';

describe('NumeroStation routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: NumeroStationService;
  let resultNumeroStation: INumeroStation | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(NumeroStationService);
    resultNumeroStation = undefined;
  });

  describe('resolve', () => {
    it('should return INumeroStation returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        numeroStationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultNumeroStation = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultNumeroStation).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        numeroStationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultNumeroStation = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNumeroStation).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<INumeroStation>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        numeroStationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultNumeroStation = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultNumeroStation).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
