package org.fmi.masters.parkingmanager.modules.parkingplace.ui;

import java.util.Collection;

import org.fmi.masters.parkingmanager.dto.ParkingPlace;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;
import org.fmi.masters.parkingmanager.modules.parkingplace.ui.filter.ParkingPlaceFilterBean;
import org.fmi.masters.parkingmanager.modules.parkingplace.ui.filter.ParkingPlaceFilterView;

public interface ParkingPlaceView {

    void setParkingPlaces(Collection<ParkingPlace> parkingPlaces);

    interface Presenter extends SearchPresenter<ParkingPlace, ParkingPlaceFilterBean>, ParkingPlaceFilterView.Presenter {
        void setView(ParkingPlaceView view);

    }

}
