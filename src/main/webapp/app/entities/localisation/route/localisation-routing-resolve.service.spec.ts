import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap, RouterStateSnapshot } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILocalisation } from '../localisation.model';
import { LocalisationService } from '../service/localisation.service';

import localisationResolve from './localisation-routing-resolve.service';

describe('Localisation routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: LocalisationService;
  let resultLocalisation: ILocalisation | null | undefined;

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
    service = TestBed.inject(LocalisationService);
    resultLocalisation = undefined;
  });

  describe('resolve', () => {
    it('should return ILocalisation returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        localisationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultLocalisation = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultLocalisation).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        localisationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultLocalisation = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLocalisation).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ILocalisation>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        localisationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultLocalisation = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultLocalisation).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
