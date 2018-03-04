import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ClothesMySuffix } from './clothes-my-suffix.model';
import { ClothesMySuffixService } from './clothes-my-suffix.service';

@Component({
    selector: 'jhi-clothes-my-suffix-detail',
    templateUrl: './clothes-my-suffix-detail.component.html'
})
export class ClothesMySuffixDetailComponent implements OnInit, OnDestroy {

    clothes: ClothesMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private clothesService: ClothesMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClothes();
    }

    load(id) {
        this.clothesService.find(id)
            .subscribe((clothesResponse: HttpResponse<ClothesMySuffix>) => {
                this.clothes = clothesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClothes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clothesListModification',
            (response) => this.load(this.clothes.id)
        );
    }
}
