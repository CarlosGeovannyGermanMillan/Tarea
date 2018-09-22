package com.example.carlosgerman.lavendimia.Presentadores;

import com.example.carlosgerman.lavendimia.Interfaces.IBaseView;

import rx.Subscription;

public class BasePresenter {

    public IBaseView mBaseView;
    public Subscription observableSubscription;

    public BasePresenter() {
    }

    public void cancelarObservables() {
        if (observableSubscription != null && !observableSubscription.isUnsubscribed()) {
            observableSubscription.unsubscribe();
        }
    }
}
