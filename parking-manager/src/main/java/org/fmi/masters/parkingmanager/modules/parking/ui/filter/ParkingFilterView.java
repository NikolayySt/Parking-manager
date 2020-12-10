package org.fmi.masters.parkingmanager.modules.parking.ui.filter;

import org.fmi.masters.parkingmanager.modules.base.FilterPresenter;

public interface ParkingFilterView {

    interface Presenter extends FilterPresenter {
        void setView(ParkingFilterView view);
    }

}
