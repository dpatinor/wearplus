import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ShoppingMySuffix } from './shopping-my-suffix.model';
import { createRequestOption } from '../../shared';

import { TypeClothesMySuffix } from './type-clothes-my-suffix.model';
import { ClothesMySuffix } from './clothes-my-suffix.model';

import { HttpParams } from '@angular/common/http';

export type EntityResponseType = HttpResponse<ShoppingMySuffix>;

@Injectable()
export class ShoppingMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'rest/shoppings';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(shopping: ShoppingMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(shopping);
        return this.http.post<ShoppingMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(shopping: ShoppingMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(shopping);
        return this.http.put<ShoppingMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ShoppingMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ShoppingMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<ShoppingMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ShoppingMySuffix[]>) => this.convertArrayResponse(res));
    }

    queryTypeClothes(req?: any): Observable<HttpResponse<TypeClothesMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeClothesMySuffix[]>(SERVER_API_URL + 'rest/type-clothes', { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeClothesMySuffix[]>) => this.convertArrayResponse(res));
    }

    queryClothes(typeId: any): Observable<HttpResponse<ClothesMySuffix[]>> {
        const options: HttpParams = new HttpParams();
        options.set('typeClothesId', typeId);
        debugger;
        return this.http.get<ClothesMySuffix[]>(SERVER_API_URL + 'rest/clothes', { params: options, observe: 'response' })
            .map((res: HttpResponse<ClothesMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ShoppingMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ShoppingMySuffix[]>): HttpResponse<ShoppingMySuffix[]> {
        const jsonResponse: ShoppingMySuffix[] = res.body;
        const body: ShoppingMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ShoppingMySuffix.
     */
    private convertItemFromServer(shopping: ShoppingMySuffix): ShoppingMySuffix {
        const copy: ShoppingMySuffix = Object.assign({}, shopping);
        copy.dateOrder = this.dateUtils
            .convertDateTimeFromServer(shopping.dateOrder);
        copy.dateDelivery = this.dateUtils
            .convertDateTimeFromServer(shopping.dateDelivery);
        return copy;
    }

    /**
     * Convert a ShoppingMySuffix to a JSON which can be sent to the server.
     */
    private convert(shopping: ShoppingMySuffix): ShoppingMySuffix {
        const copy: ShoppingMySuffix = Object.assign({}, shopping);

        copy.dateOrder = this.dateUtils.toDate(shopping.dateOrder);

        copy.dateDelivery = this.dateUtils.toDate(shopping.dateDelivery);
        return copy;
    }
}
