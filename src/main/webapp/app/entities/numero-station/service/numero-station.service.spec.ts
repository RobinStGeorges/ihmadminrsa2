import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INumeroStation } from '../numero-station.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../numero-station.test-samples';

import { NumeroStationService } from './numero-station.service';

const requireRestSample: INumeroStation = {
  ...sampleWithRequiredData,
};

describe('NumeroStation Service', () => {
  let service: NumeroStationService;
  let httpMock: HttpTestingController;
  let expectedResult: INumeroStation | INumeroStation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NumeroStationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a NumeroStation', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const numeroStation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(numeroStation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NumeroStation', () => {
      const numeroStation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(numeroStation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NumeroStation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NumeroStation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NumeroStation', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNumeroStationToCollectionIfMissing', () => {
      it('should add a NumeroStation to an empty array', () => {
        const numeroStation: INumeroStation = sampleWithRequiredData;
        expectedResult = service.addNumeroStationToCollectionIfMissing([], numeroStation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(numeroStation);
      });

      it('should not add a NumeroStation to an array that contains it', () => {
        const numeroStation: INumeroStation = sampleWithRequiredData;
        const numeroStationCollection: INumeroStation[] = [
          {
            ...numeroStation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNumeroStationToCollectionIfMissing(numeroStationCollection, numeroStation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NumeroStation to an array that doesn't contain it", () => {
        const numeroStation: INumeroStation = sampleWithRequiredData;
        const numeroStationCollection: INumeroStation[] = [sampleWithPartialData];
        expectedResult = service.addNumeroStationToCollectionIfMissing(numeroStationCollection, numeroStation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(numeroStation);
      });

      it('should add only unique NumeroStation to an array', () => {
        const numeroStationArray: INumeroStation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const numeroStationCollection: INumeroStation[] = [sampleWithRequiredData];
        expectedResult = service.addNumeroStationToCollectionIfMissing(numeroStationCollection, ...numeroStationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const numeroStation: INumeroStation = sampleWithRequiredData;
        const numeroStation2: INumeroStation = sampleWithPartialData;
        expectedResult = service.addNumeroStationToCollectionIfMissing([], numeroStation, numeroStation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(numeroStation);
        expect(expectedResult).toContain(numeroStation2);
      });

      it('should accept null and undefined values', () => {
        const numeroStation: INumeroStation = sampleWithRequiredData;
        expectedResult = service.addNumeroStationToCollectionIfMissing([], null, numeroStation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(numeroStation);
      });

      it('should return initial array if no NumeroStation is added', () => {
        const numeroStationCollection: INumeroStation[] = [sampleWithRequiredData];
        expectedResult = service.addNumeroStationToCollectionIfMissing(numeroStationCollection, undefined, null);
        expect(expectedResult).toEqual(numeroStationCollection);
      });
    });

    describe('compareNumeroStation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNumeroStation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareNumeroStation(entity1, entity2);
        const compareResult2 = service.compareNumeroStation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareNumeroStation(entity1, entity2);
        const compareResult2 = service.compareNumeroStation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareNumeroStation(entity1, entity2);
        const compareResult2 = service.compareNumeroStation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
