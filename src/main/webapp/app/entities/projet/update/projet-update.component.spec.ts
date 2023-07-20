import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjetFormService } from './projet-form.service';
import { ProjetService } from '../service/projet.service';
import { IProjet } from '../projet.model';
import { ICollaborateur } from 'app/entities/collaborateur/collaborateur.model';
import { CollaborateurService } from 'app/entities/collaborateur/service/collaborateur.service';

import { ProjetUpdateComponent } from './projet-update.component';

describe('Projet Management Update Component', () => {
  let comp: ProjetUpdateComponent;
  let fixture: ComponentFixture<ProjetUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projetFormService: ProjetFormService;
  let projetService: ProjetService;
  let collaborateurService: CollaborateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProjetUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProjetUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjetUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projetFormService = TestBed.inject(ProjetFormService);
    projetService = TestBed.inject(ProjetService);
    collaborateurService = TestBed.inject(CollaborateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Collaborateur query and add missing value', () => {
      const projet: IProjet = { id: 'CBA' };
      const cps: ICollaborateur[] = [{ id: 'c1cfca7a-ae06-4e23-83f3-3114fef93306' }];
      projet.cps = cps;
      const dps: ICollaborateur[] = [{ id: '420b10dc-5438-4339-a463-583ed0e4ea2c' }];
      projet.dps = dps;

      const collaborateurCollection: ICollaborateur[] = [{ id: 'ea354eec-a9ec-482f-a0eb-eae965c484dc' }];
      jest.spyOn(collaborateurService, 'query').mockReturnValue(of(new HttpResponse({ body: collaborateurCollection })));
      const additionalCollaborateurs = [...cps, ...dps];
      const expectedCollection: ICollaborateur[] = [...additionalCollaborateurs, ...collaborateurCollection];
      jest.spyOn(collaborateurService, 'addCollaborateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ projet });
      comp.ngOnInit();

      expect(collaborateurService.query).toHaveBeenCalled();
      expect(collaborateurService.addCollaborateurToCollectionIfMissing).toHaveBeenCalledWith(
        collaborateurCollection,
        ...additionalCollaborateurs.map(expect.objectContaining)
      );
      expect(comp.collaborateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const projet: IProjet = { id: 'CBA' };
      const cp: ICollaborateur = { id: '1ae6475a-81e3-4fd6-aaca-e37d4e995984' };
      projet.cps = [cp];
      const dp: ICollaborateur = { id: '2b4b0896-1da6-496d-ada5-d888ea1de568' };
      projet.dps = [dp];

      activatedRoute.data = of({ projet });
      comp.ngOnInit();

      expect(comp.collaborateursSharedCollection).toContain(cp);
      expect(comp.collaborateursSharedCollection).toContain(dp);
      expect(comp.projet).toEqual(projet);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjet>>();
      const projet = { id: 'ABC' };
      jest.spyOn(projetFormService, 'getProjet').mockReturnValue(projet);
      jest.spyOn(projetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projet }));
      saveSubject.complete();

      // THEN
      expect(projetFormService.getProjet).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(projetService.update).toHaveBeenCalledWith(expect.objectContaining(projet));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjet>>();
      const projet = { id: 'ABC' };
      jest.spyOn(projetFormService, 'getProjet').mockReturnValue({ id: null });
      jest.spyOn(projetService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projet: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projet }));
      saveSubject.complete();

      // THEN
      expect(projetFormService.getProjet).toHaveBeenCalled();
      expect(projetService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjet>>();
      const projet = { id: 'ABC' };
      jest.spyOn(projetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projetService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCollaborateur', () => {
      it('Should forward to collaborateurService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(collaborateurService, 'compareCollaborateur');
        comp.compareCollaborateur(entity, entity2);
        expect(collaborateurService.compareCollaborateur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
