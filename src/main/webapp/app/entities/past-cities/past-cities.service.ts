import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPastCities } from 'app/shared/model/past-cities.model';

type EntityResponseType = HttpResponse<IPastCities>;
type EntityArrayResponseType = HttpResponse<IPastCities[]>;

@Injectable({ providedIn: 'root' })
export class PastCitiesService {
  public resourceUrl = SERVER_API_URL + 'api/past-cities';

  constructor(protected http: HttpClient) {}

  create(pastCities: IPastCities): Observable<EntityResponseType> {
    return this.http.post<IPastCities>(this.resourceUrl, pastCities, { observe: 'response' });
  }

  update(pastCities: IPastCities): Observable<EntityResponseType> {
    return this.http.put<IPastCities>(this.resourceUrl, pastCities, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPastCities>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPastCities[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
