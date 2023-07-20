import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITeletravail } from '../teletravail.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../teletravail.test-samples';

import { TeletravailService } from './teletravail.service';

const requireRestSample: ITeletravail = {
  ...sampleWithRequiredData,
};

describe('Teletravail Service', () => {
  let service: TeletravailService;
  let httpMock: HttpTestingController;
  let expectedResult: ITeletravail | ITeletravail[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TeletravailService);
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

    it('should create a Teletravail', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const teletravail = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(teletravail).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Teletravail', () => {
      const teletravail = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(teletravail).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Teletravail', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Teletravail', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Teletravail', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTeletravailToCollectionIfMissing', () => {
      it('should add a Teletravail to an empty array', () => {
        const teletravail: ITeletravail = sampleWithRequiredData;
        expectedResult = service.addTeletravailToCollectionIfMissing([], teletravail);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teletravail);
      });

      it('should not add a Teletravail to an array that contains it', () => {
        const teletravail: ITeletravail = sampleWithRequiredData;
        const teletravailCollection: ITeletravail[] = [
          {
            ...teletravail,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTeletravailToCollectionIfMissing(teletravailCollection, teletravail);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Teletravail to an array that doesn't contain it", () => {
        const teletravail: ITeletravail = sampleWithRequiredData;
        const teletravailCollection: ITeletravail[] = [sampleWithPartialData];
        expectedResult = service.addTeletravailToCollectionIfMissing(teletravailCollection, teletravail);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teletravail);
      });

      it('should add only unique Teletravail to an array', () => {
        const teletravailArray: ITeletravail[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const teletravailCollection: ITeletravail[] = [sampleWithRequiredData];
        expectedResult = service.addTeletravailToCollectionIfMissing(teletravailCollection, ...teletravailArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const teletravail: ITeletravail = sampleWithRequiredData;
        const teletravail2: ITeletravail = sampleWithPartialData;
        expectedResult = service.addTeletravailToCollectionIfMissing([], teletravail, teletravail2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teletravail);
        expect(expectedResult).toContain(teletravail2);
      });

      it('should accept null and undefined values', () => {
        const teletravail: ITeletravail = sampleWithRequiredData;
        expectedResult = service.addTeletravailToCollectionIfMissing([], null, teletravail, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teletravail);
      });

      it('should return initial array if no Teletravail is added', () => {
        const teletravailCollection: ITeletravail[] = [sampleWithRequiredData];
        expectedResult = service.addTeletravailToCollectionIfMissing(teletravailCollection, undefined, null);
        expect(expectedResult).toEqual(teletravailCollection);
      });
    });

    describe('compareTeletravail', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTeletravail(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareTeletravail(entity1, entity2);
        const compareResult2 = service.compareTeletravail(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareTeletravail(entity1, entity2);
        const compareResult2 = service.compareTeletravail(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareTeletravail(entity1, entity2);
        const compareResult2 = service.compareTeletravail(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
