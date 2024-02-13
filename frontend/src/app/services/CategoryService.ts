import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SERVER_PATH } from "../constants";
import { Observable } from "rxjs";
import { CategoryDTO } from "../dto/CategoryDTO";

@Injectable({
    providedIn: 'root'
})

export class CategoryService {

    constructor(private http: HttpClient) { }

    getCategories(): Observable<CategoryDTO[]> {
        return this.http.get<CategoryDTO[]>(SERVER_PATH+"/categories");
            
    }

}