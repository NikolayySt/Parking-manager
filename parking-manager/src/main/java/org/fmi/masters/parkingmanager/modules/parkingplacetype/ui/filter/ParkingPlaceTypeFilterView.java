package org.fmi.masters.parkingmanager.modules.parkingplacetype.ui.filter;

import java.util.Collection;

import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.modules.base.FilterPresenter;

public interface ParkingPlaceTypeFilterView {

    void setVehicleTypes(Collection<VehicleType> vehicleTypes);

    interface Presenter extends FilterPresenter {
        void setView(ParkingPlaceTypeFilterView view);
    }

}
