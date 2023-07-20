import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CollaborateurFormService } from './collaborateur-form.service';
import { CollaborateurService } from '../service/collaborateur.service';
import { ICollaborateur } from '../collaborateur.model';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';
import { IEquipement } from 'app/entities/equipement/equipement.model';
import { EquipementService } from 'app/entities/equipement/service/equipement.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';

import { CollaborateurUpdateComponent } from './collaborateur-update.component';

describe('Collaborateur Management Update Component', () => {
  let comp: CollaborateurUpdateComponent;
  let fixture: ComponentFixture<CollaborateurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let collaborateurFormService: CollaborateurFormService;
  let collaborateurService: CollaborateurService;
  let projetService: ProjetService;
  let equipementService: EquipementService;
  let localisationService: LocalisationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CollaborateurUpdateComponent],
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
      .overrideTemplate(CollaborateurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CollaborateurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    collaborateurFormService = TestBed.inject(CollaborateurFormService);
    collaborateurService = TestBed.inject(CollaborateurService);
    projetService = TestBed.inject(ProjetService);
    equipementService = TestBed.inject(EquipementService);
    localisationService = TestBed.inject(LocalisationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Projet query and add missing value', () => {
      const collaborateur: ICollaborateur = { id: 'CBA' };
      const projets: IProjet[] = [{ id: 'fd399cd7-a692-48b9-abcf-65a46a6cf772' }];
      collaborateur.projets = projets;

      const projetCollection: IProjet[] = [{ id: '795eada5-b651-4657-a8de-38546c983740' }];
      jest.spyOn(projetService, 'query').mockReturnValue(of(new HttpResponse({ body: projetCollection })));
      const additionalProjets = [...projets];
      const expectedCollection: IProjet[] = [...additionalProjets, ...projetCollection];
      jest.spyOn(projetService, 'addProjetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ collaborateur });
      comp.ngOnInit();

      expect(projetService.query).toHaveBeenCalled();
      expect(projetService.addProjetToCollectionIfMissing).toHaveBeenCalledWith(
        projetCollection,
        ...additionalProjets.map(expect.objectContaining)
      );
      expect(comp.projetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Equipement query and add missing value', () => {
      const collaborateur: ICollaborateur = { id: 'CBA' };
      const equipements: IEquipement[] = [{ id: '4a07157b-7383-4d49-bcda-30b6dd630294' }];
      collaborateur.equipements = equipements;

      const equipementCollection: IEquipement[] = [{ id: 'e2309c95-033a-4b2f-8c4f-fecd8af91197' }];
      jest.spyOn(equipementService, 'query').mockReturnValue(of(new HttpResponse({ body: equipementCollection })));
      const additionalEquipements = [...equipements];
      const expectedCollection: IEquipement[] = [...additionalEquipements, ...equipementCollection];
      jest.spyOn(equipementService, 'addEquipementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ collaborateur });
      comp.ngOnInit();

      expect(equipementService.query).toHaveBeenCalled();
      expect(equipementService.addEquipementToCollectionIfMissing).toHaveBeenCalledWith(
        equipementCollection,
        ...additionalEquipements.map(expect.objectContaining)
      );
      expect(comp.equipementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Localisation query and add missing value', () => {
      const collaborateur: ICollaborateur = { id: 'CBA' };
      const localisation: ILocalisation = { id: 'd0e8cd0d-e09a-4b1e-9366-1f41a02ae3a9' };
      collaborateur.localisation = localisation;

      const localisationCollection: ILocalisation[] = [{ id: '33dcc0c6-32c5-4e80-9b81-cc7fbfa56daa' }];
      jest.spyOn(localisationService, 'query').mockReturnValue(of(new HttpResponse({ body: localisationCollection })));
      const additionalLocalisations = [localisation];
      const expectedCollection: ILocalisation[] = [...additionalLocalisations, ...localisationCollection];
      jest.spyOn(localisationService, 'addLocalisationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ collaborateur });
      comp.ngOnInit();

      expect(localisationService.query).toHaveBeenCalled();
      expect(localisationService.addLocalisationToCollectionIfMissing).toHaveBeenCalledWith(
        localisationCollection,
        ...additionalLocalisations.map(expect.objectContaining)
      );
      expect(comp.localisationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const collaborateur: ICollaborateur = { id: 'CBA' };
      const projet: IProjet = { id: 'e3b548f5-300e-4ca3-b460-51031d4efc0f' };
      collaborateur.projets = [projet];
      const equipements: IEquipement = { id: 'b4ebce56-f3a6-4007-8510-ea81a1c1ed54' };
      collaborateur.equipements = [equipements];
      const localisation: ILocalisation = { id: '5fe4a118-1445-466a-89f7-8c38addb2ee6' };
      collaborateur.localisation = localisation;

      activatedRoute.data = of({ collaborateur });
      comp.ngOnInit();

      expect(comp.projetsSharedCollection).toContain(projet);
      expect(comp.equipementsSharedCollection).toContain(equipements);
      expect(comp.localisationsSharedCollection).toContain(localisation);
      expect(comp.collaborateur).toEqual(collaborateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICollaborateur>>();
      const collaborateur = { id: 'ABC' };
      jest.spyOn(collaborateurFormService, 'getCollaborateur').mockReturnValue(collaborateur);
      jest.spyOn(collaborateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ collaborateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: collaborateur }));
      saveSubject.complete();

      // THEN
      expect(collaborateurFormService.getCollaborateur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(collaborateurService.update).toHaveBeenCalledWith(expect.objectContaining(collaborateur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICollaborateur>>();
      const collaborateur = { id: 'ABC' };
      jest.spyOn(collaborateurFormService, 'getCollaborateur').mockReturnValue({ id: null });
      jest.spyOn(collaborateurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ collaborateur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: collaborateur }));
      saveSubject.complete();

      // THEN
      expect(collaborateurFormService.getCollaborateur).toHaveBeenCalled();
      expect(collaborateurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICollaborateur>>();
      const collaborateur = { id: 'ABC' };
      jest.spyOn(collaborateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ collaborateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(collaborateurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProjet', () => {
      it('Should forward to projetService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(projetService, 'compareProjet');
        comp.compareProjet(entity, entity2);
        expect(projetService.compareProjet).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEquipement', () => {
      it('Should forward to equipementService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(equipementService, 'compareEquipement');
        comp.compareEquipement(entity, entity2);
        expect(equipementService.compareEquipement).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLocalisation', () => {
      it('Should forward to localisationService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(localisationService, 'compareLocalisation');
        comp.compareLocalisation(entity, entity2);
        expect(localisationService.compareLocalisation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
