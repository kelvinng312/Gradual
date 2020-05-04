package com.everydev.gradual.ui.donees;

import com.everydev.gradual.data.DataManager;
import com.everydev.gradual.data.network.pojo.Donee;
import com.everydev.gradual.ui.base.BasePresenter;
import com.everydev.gradual.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DoneesPresenter<V extends DoneesMvpView> extends BasePresenter<V> implements DoneesMvpPresenter<V> {

    @Inject
    public DoneesPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);

        fetchDonees();
    }

    @Override
    public void fetchDonees() {
        // check view
        if (!isViewAttached()) {
            return;
        }

        // show loading
        getMvpView().showLoading();



        getCompositeDisposable().add(getDataManager()
                .getDonees()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();

                    if (response == null)
                        return;

                    // Add
                    List<Donee> doneeList = new ArrayList<>();
//                    doneeList.add();

                    getMvpView().displayDonees(response);
                }, error -> {
                    getMvpView().hideLoading();
                    handleApiError(error);
                })
        );
    }
}
