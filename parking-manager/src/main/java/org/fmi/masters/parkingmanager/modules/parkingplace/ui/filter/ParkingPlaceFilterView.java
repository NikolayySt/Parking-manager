package org.fmi.masters.parkingmanager.modules.parkingplace.ui.filter;

import java.util.Collection;

import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.dto.ParkingPlaceType;
import org.fmi.masters.parkingmanager.modules.base.FilterPresenter;

public interface ParkingPlaceFilterView {

    void setParkings(Collection<Parking> parkings);

    void setDrivers(Collection<Driver> drivers);

    void setPlaceTypes(Collection<ParkingPlaceType> placeTypes);

    interface Presenter extends FilterPresenter {
        void setView(ParkingPlaceFilterView view);
    }

}
