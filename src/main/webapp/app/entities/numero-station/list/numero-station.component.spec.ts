import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { NumeroStationService } from '../service/numero-station.service';

import { NumeroStationComponent } from './numero-station.component';

describe('NumeroStation Management Component', () => {
  let comp: NumeroStationComponent;
  let fixture: ComponentFixture<NumeroStationComponent>;
  let service: NumeroStationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'numero-station', component: NumeroStationComponent }]),
        HttpClientTestingModule,
        NumeroStationComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(NumeroStationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NumeroStationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NumeroStationService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.numeroStations?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to numeroStationService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getNumeroStationIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getNumeroStationIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
