import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Prestataire } from './prestataire.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Prestataire>;

@Injectable()
export class PrestataireService {

    private resourceUrl =  SERVER_API_URL + 'api/prestataires';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/prestataires';

    constructor(private http: HttpClient) { }

    create(prestataire: Prestataire): Observable<EntityResponseType> {
        const copy = this.convert(prestataire);
        return this.http.post<Prestataire>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(prestataire: Prestataire): Observable<EntityResponseType> {
        const copy = this.convert(prestataire);
        return this.http.put<Prestataire>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Prestataire>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Prestataire[]>> {
        const options = createRequestOption(req);
        return this.http.get<Prestataire[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Prestataire[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Prestataire[]>> {
        const options = createRequestOption(req);
        return this.http.get<Prestataire[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Prestataire[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Prestataire = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Prestataire[]>): HttpResponse<Prestataire[]> {
        const jsonResponse: Prestataire[] = res.body;
        const body: Prestataire[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Prestataire.
     */
    private convertItemFromServer(prestataire: Prestataire): Prestataire {
        const copy: Prestataire = Object.assign({}, prestataire);
        return copy;
    }

    /**
     * Convert a Prestataire to a JSON which can be sent to the server.
     */
    private convert(prestataire: Prestataire): Prestataire {
        const copy: Prestataire = Object.assign({}, prestataire);
        return copy;
    }
}
