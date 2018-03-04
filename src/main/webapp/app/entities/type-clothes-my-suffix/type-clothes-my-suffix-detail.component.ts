import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeClothesMySuffix } from './type-clothes-my-suffix.model';
import { TypeClothesMySuffixService } from './type-clothes-my-suffix.service';

@Component({
    selector: 'jhi-type-clothes-my-suffix-detail',
    templateUrl: './type-clothes-my-suffix-detail.component.html'
})
export class TypeClothesMySuffixDetailComponent implements OnInit, OnDestroy {

    typeClothes: TypeClothesMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeClothesService: TypeClothesMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeClothes();
    }

    load(id) {
        this.typeClothesService.find(id)
            .subscribe((typeClothesResponse: HttpResponse<TypeClothesMySuffix>) => {
                this.typeClothes = typeClothesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeClothes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeClothesListModification',
            (response) => this.load(this.typeClothes.id)
        );
    }
}
