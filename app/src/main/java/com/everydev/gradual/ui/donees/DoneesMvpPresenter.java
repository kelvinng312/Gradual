package com.everydev.gradual.ui.donees;

import com.everydev.gradual.data.network.pojo.Donee;
import com.everydev.gradual.ui.base.MvpPresenter;

import java.util.List;

public interface DoneesMvpPresenter<V extends DoneesMvpView> extends MvpPresenter<V> {
    void fetchDonees();
}
