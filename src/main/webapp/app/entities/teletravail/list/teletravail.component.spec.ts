import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TeletravailService } from '../service/teletravail.service';

import { TeletravailComponent } from './teletravail.component';

describe('Teletravail Management Component', () => {
  let comp: TeletravailComponent;
  let fixture: ComponentFixture<TeletravailComponent>;
  let service: TeletravailService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'teletravail', component: TeletravailComponent }]),
        HttpClientTestingModule,
        TeletravailComponent,
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
      .overrideTemplate(TeletravailComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TeletravailComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TeletravailService);

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
    expect(comp.teletravails?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to teletravailService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getTeletravailIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getTeletravailIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
