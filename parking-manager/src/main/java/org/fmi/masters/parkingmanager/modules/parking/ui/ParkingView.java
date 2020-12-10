package org.fmi.masters.parkingmanager.modules.parking.ui;

import java.util.Collection;

import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;
import org.fmi.masters.parkingmanager.modules.parking.ui.filter.ParkingFilterBean;

public interface ParkingView {

    void setParkings(Collection<Parking> parkings);

    interface Presenter extends SearchPresenter<Parking, ParkingFilterBean> {
        void setView(ParkingView view);
    }

}
