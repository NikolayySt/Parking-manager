package org.fmi.masters.parkingmanager.modules.driver.ui;

import java.util.Collection;

import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.modules.base.SearchPresenter;
import org.fmi.masters.parkingmanager.modules.driver.ui.filter.DriverFilterBean;
import org.fmi.masters.parkingmanager.modules.driver.ui.filter.DriverFilterView;

public interface DriversView {
    
    void setDrivers(Collection<Driver> drivers);

    interface Presenter extends SearchPresenter<Driver, DriverFilterBean>, DriverFilterView.Presenter {
        void setView(DriversView view);
    }

}
