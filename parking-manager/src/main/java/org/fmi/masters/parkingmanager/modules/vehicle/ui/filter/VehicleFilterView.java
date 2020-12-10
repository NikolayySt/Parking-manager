package org.fmi.masters.parkingmanager.modules.vehicle.ui.filter;

import java.util.Collection;

import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.modules.base.FilterPresenter;

public interface VehicleFilterView {

    void setVehicleTypes(Collection<VehicleType> vehicleTypes);

    interface Presenter extends FilterPresenter {
        void setView(VehicleFilterView view);
    }

}
