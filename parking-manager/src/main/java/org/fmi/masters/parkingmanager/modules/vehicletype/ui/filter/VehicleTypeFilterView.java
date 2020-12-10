package org.fmi.masters.parkingmanager.modules.vehicletype.ui.filter;

import org.fmi.masters.parkingmanager.modules.base.FilterPresenter;

public interface VehicleTypeFilterView {

    interface Presenter extends FilterPresenter {
        void setView(VehicleTypeFilterView view);
    }

}
