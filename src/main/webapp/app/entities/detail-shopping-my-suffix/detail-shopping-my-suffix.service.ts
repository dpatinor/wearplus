import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DetailShoppingMySuffix } from './detail-shopping-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DetailShoppingMySuffix>;

@Injectable()
export class DetailShoppingMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/detail-shoppings';

    constructor(private http: HttpClient) { }

    create(detailShopping: DetailShoppingMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(detailShopping);
        return this.http.post<DetailShoppingMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(detailShopping: DetailShoppingMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(detailShopping);
        return this.http.put<DetailShoppingMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DetailShoppingMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DetailShoppingMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<DetailShoppingMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DetailShoppingMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DetailShoppingMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DetailShoppingMySuffix[]>): HttpResponse<DetailShoppingMySuffix[]> {
        const jsonResponse: DetailShoppingMySuffix[] = res.body;
        const body: DetailShoppingMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DetailShoppingMySuffix.
     */
    private convertItemFromServer(detailShopping: DetailShoppingMySuffix): DetailShoppingMySuffix {
        const copy: DetailShoppingMySuffix = Object.assign({}, detailShopping);
        return copy;
    }

    /**
     * Convert a DetailShoppingMySuffix to a JSON which can be sent to the server.
     */
    private convert(detailShopping: DetailShoppingMySuffix): DetailShoppingMySuffix {
        const copy: DetailShoppingMySuffix = Object.assign({}, detailShopping);
        return copy;
    }
}
