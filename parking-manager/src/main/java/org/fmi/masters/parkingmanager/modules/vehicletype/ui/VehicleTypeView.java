package org.fmi.masters.parkingmanager.modules.vehicletype.ui;

import java.util.Collection;

import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;
import org.fmi.masters.parkingmanager.modules.vehicletype.ui.filter.VehicleTypeFilterBean;
import org.fmi.masters.parkingmanager.modules.vehicletype.ui.filter.VehicleTypeFilterView;

public interface VehicleTypeView {

    void setVehicleTypes(Collection<VehicleType> vehicleTypes);

    interface Presenter extends SearchPresenter<VehicleType, VehicleTypeFilterBean>, VehicleTypeFilterView.Presenter {
        void setView(VehicleTypeView view);

    }

}
