import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EquipementService } from '../service/equipement.service';

import { EquipementComponent } from './equipement.component';

describe('Equipement Management Component', () => {
  let comp: EquipementComponent;
  let fixture: ComponentFixture<EquipementComponent>;
  let service: EquipementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'equipement', component: EquipementComponent }]),
        HttpClientTestingModule,
        EquipementComponent,
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
      .overrideTemplate(EquipementComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EquipementComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EquipementService);

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
    expect(comp.equipements?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to equipementService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getEquipementIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getEquipementIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
