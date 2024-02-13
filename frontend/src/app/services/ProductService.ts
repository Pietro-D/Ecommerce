import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ProductDTO } from "../dto/ProductDTO";
import { SERVER_PATH } from "../constants";

@Injectable({
    providedIn: 'root'
})

export class ProductService {

    constructor(private http: HttpClient) { }

    getProducts(category: string, pageNumber: number, pageSize: number): Observable<ProductDTO[]> {
        let params=new HttpParams().set('pageNumber',pageNumber).set('pageSize',pageSize);
        return this.http.get<ProductDTO[]>(SERVER_PATH+"/products/category/"+category, {'params':params});
               
    }
    totalProducts(category: string): Observable<number>{
        return this.http.get<number>(SERVER_PATH+"/products/category/"+category+"/total");
    }

    searchProducts(query: string, pageNumber: number, pageSize: number): Observable<ProductDTO[]> {
        let params=new HttpParams().set('pageNumber',pageNumber).set('pageSize',pageSize);
        return this.http.get<ProductDTO[]>(SERVER_PATH+"/products/"+query, {'params':params});
    }

    totalResultsSearch(query: string): Observable<number>{
        return this.http.get<number>(SERVER_PATH+"/products/"+query+"/total");
    }
               

}