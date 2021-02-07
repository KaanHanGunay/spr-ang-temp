import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPhone } from 'app/shared/model/phone.model';

type EntityResponseType = HttpResponse<IPhone>;
type EntityArrayResponseType = HttpResponse<IPhone[]>;

@Injectable({ providedIn: 'root' })
export class PhoneService {
  public resourceUrl = SERVER_API_URL + 'api/phones';

  constructor(protected http: HttpClient) {}

  create(phone: IPhone): Observable<EntityResponseType> {
    return this.http.post<IPhone>(this.resourceUrl, phone, { observe: 'response' });
  }

  update(phone: IPhone): Observable<EntityResponseType> {
    return this.http.put<IPhone>(this.resourceUrl, phone, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhone>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhone[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
