package org.fmi.masters.parkingmanager.modules.parkingplacetype.ui;

import java.util.Collection;

import org.fmi.masters.parkingmanager.dto.ParkingPlaceType;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;
import org.fmi.masters.parkingmanager.modules.parkingplacetype.ui.filter.ParkingPlaceTypeFilterBean;
import org.fmi.masters.parkingmanager.modules.parkingplacetype.ui.filter.ParkingPlaceTypeFilterView;

public interface ParkingPlaceTypeView {

    void setParkingPlaceTypes(Collection<ParkingPlaceType> parkingPlaceTypes);

    interface Presenter extends SearchPresenter<ParkingPlaceType, ParkingPlaceTypeFilterBean>, ParkingPlaceTypeFilterView.Presenter {
        void setView(ParkingPlaceTypeView view);
    }

}
