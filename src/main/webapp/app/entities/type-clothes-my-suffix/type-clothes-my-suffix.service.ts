import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeClothesMySuffix } from './type-clothes-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeClothesMySuffix>;

@Injectable()
export class TypeClothesMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/type-clothes';

    constructor(private http: HttpClient) { }

    create(typeClothes: TypeClothesMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(typeClothes);
        return this.http.post<TypeClothesMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeClothes: TypeClothesMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(typeClothes);
        return this.http.put<TypeClothesMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeClothesMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeClothesMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeClothesMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeClothesMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeClothesMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeClothesMySuffix[]>): HttpResponse<TypeClothesMySuffix[]> {
        const jsonResponse: TypeClothesMySuffix[] = res.body;
        const body: TypeClothesMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeClothesMySuffix.
     */
    private convertItemFromServer(typeClothes: TypeClothesMySuffix): TypeClothesMySuffix {
        const copy: TypeClothesMySuffix = Object.assign({}, typeClothes);
        return copy;
    }

    /**
     * Convert a TypeClothesMySuffix to a JSON which can be sent to the server.
     */
    private convert(typeClothes: TypeClothesMySuffix): TypeClothesMySuffix {
        const copy: TypeClothesMySuffix = Object.assign({}, typeClothes);
        return copy;
    }
}
