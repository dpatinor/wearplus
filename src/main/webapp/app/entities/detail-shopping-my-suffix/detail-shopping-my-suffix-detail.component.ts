import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DetailShoppingMySuffix } from './detail-shopping-my-suffix.model';
import { DetailShoppingMySuffixService } from './detail-shopping-my-suffix.service';

@Component({
    selector: 'jhi-detail-shopping-my-suffix-detail',
    templateUrl: './detail-shopping-my-suffix-detail.component.html'
})
export class DetailShoppingMySuffixDetailComponent implements OnInit, OnDestroy {

    detailShopping: DetailShoppingMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private detailShoppingService: DetailShoppingMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDetailShoppings();
    }

    load(id) {
        this.detailShoppingService.find(id)
            .subscribe((detailShoppingResponse: HttpResponse<DetailShoppingMySuffix>) => {
                this.detailShopping = detailShoppingResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDetailShoppings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'detailShoppingListModification',
            (response) => this.load(this.detailShopping.id)
        );
    }
}
