package org.fmi.masters.parkingmanager.modules.vehicle.ui;

import java.util.Collection;

import org.fmi.masters.parkingmanager.dto.Vehicle;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;
import org.fmi.masters.parkingmanager.modules.vehicle.ui.filter.VehicleFilterBean;
import org.fmi.masters.parkingmanager.modules.vehicle.ui.filter.VehicleFilterView;

public interface VehicleView {

    void setVehicles(Collection<Vehicle> vehicles);

    interface Presenter extends SearchPresenter<Vehicle, VehicleFilterBean>, VehicleFilterView.Presenter {
        void setView(VehicleView view);

    }

}
