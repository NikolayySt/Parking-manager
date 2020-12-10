package org.fmi.masters.parkingmanager.modules.driver.ui.filter;

import java.util.Collection;

import org.fmi.masters.parkingmanager.dto.Vehicle;
import org.fmi.masters.parkingmanager.modules.base.FilterPresenter;

public interface DriverFilterView {

    void setVehicles(Collection<Vehicle> vehicles);

    interface Presenter extends FilterPresenter {
        void setView(DriverFilterView view);
    }

}
