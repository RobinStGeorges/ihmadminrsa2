import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CollaborateurService } from '../service/collaborateur.service';

import { CollaborateurComponent } from './collaborateur.component';

describe('Collaborateur Management Component', () => {
  let comp: CollaborateurComponent;
  let fixture: ComponentFixture<CollaborateurComponent>;
  let service: CollaborateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'collaborateur', component: CollaborateurComponent }]),
        HttpClientTestingModule,
        CollaborateurComponent,
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
      .overrideTemplate(CollaborateurComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CollaborateurComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CollaborateurService);

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
    expect(comp.collaborateurs?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to collaborateurService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getCollaborateurIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getCollaborateurIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
