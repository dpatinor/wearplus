import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ShoppingMySuffix } from './shopping-my-suffix.model';
import { ShoppingMySuffixService } from './shopping-my-suffix.service';

@Component({
    selector: 'jhi-shopping-my-suffix-detail',
    templateUrl: './shopping-my-suffix-detail.component.html'
})
export class ShoppingMySuffixDetailComponent implements OnInit, OnDestroy {

    shopping: ShoppingMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
	
    constructor(
        private eventManager: JhiEventManager,
        private shoppingService: ShoppingMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShoppings();
     }

    load(id) {
        this.shoppingService.find(id)
            .subscribe((shoppingResponse: HttpResponse<ShoppingMySuffix>) => {
                this.shopping = shoppingResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShoppings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shoppingListModification',
            (response) => this.load(this.shopping.id)
        );
    }
}
