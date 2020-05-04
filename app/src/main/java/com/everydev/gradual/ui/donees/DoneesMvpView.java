package com.everydev.gradual.ui.donees;

import com.everydev.gradual.data.network.pojo.Donee;
import com.everydev.gradual.ui.base.MvpView;

import java.util.List;

public interface DoneesMvpView extends MvpView {
    void displayDonees(List<Donee> donees);
}
