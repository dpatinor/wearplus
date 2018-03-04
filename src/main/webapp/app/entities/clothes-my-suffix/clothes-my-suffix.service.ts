import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ClothesMySuffix } from './clothes-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ClothesMySuffix>;

@Injectable()
export class ClothesMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/clothes';

    constructor(private http: HttpClient) { }

    create(clothes: ClothesMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(clothes);
        return this.http.post<ClothesMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(clothes: ClothesMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(clothes);
        return this.http.put<ClothesMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ClothesMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ClothesMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<ClothesMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ClothesMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ClothesMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ClothesMySuffix[]>): HttpResponse<ClothesMySuffix[]> {
        const jsonResponse: ClothesMySuffix[] = res.body;
        const body: ClothesMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ClothesMySuffix.
     */
    private convertItemFromServer(clothes: ClothesMySuffix): ClothesMySuffix {
        const copy: ClothesMySuffix = Object.assign({}, clothes);
        return copy;
    }

    /**
     * Convert a ClothesMySuffix to a JSON which can be sent to the server.
     */
    private convert(clothes: ClothesMySuffix): ClothesMySuffix {
        const copy: ClothesMySuffix = Object.assign({}, clothes);
        return copy;
    }
}
