package org.fmi.masters.parkingmanager.modules.parkingplace;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.dto.ParkingPlace;
import org.fmi.masters.parkingmanager.modules.parkingplace.ui.ParkingPlaceView;
import org.fmi.masters.parkingmanager.modules.parkingplace.ui.dialog.ParkingPlaceDialog;
import org.fmi.masters.parkingmanager.modules.parkingplace.ui.filter.ParkingPlaceFilterBean;
import org.fmi.masters.parkingmanager.modules.parkingplace.ui.filter.ParkingPlaceFilterView;
import org.fmi.masters.parkingmanager.service.DriverService;
import org.fmi.masters.parkingmanager.service.ParkingPlaceService;
import org.fmi.masters.parkingmanager.service.ParkingPlaceTypeService;
import org.fmi.masters.parkingmanager.service.ParkingService;
import org.fmi.masters.parkingmanager.service.filter.DriverFilter;
import org.fmi.masters.parkingmanager.service.filter.ParkingFilter;
import org.fmi.masters.parkingmanager.service.filter.ParkingPlaceFilter;
import org.fmi.masters.parkingmanager.service.filter.ParkingPlaceTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

@Component
public class ParkingPlacePresenter implements ParkingPlaceView.Presenter, ParkingPlaceFilterView.Presenter {

    @Resource
    private ParkingService parkingService;

    @Resource
    private ParkingPlaceService parkingPlaceService;

    @Resource
    private DriverService driverService;

    @Resource
    private ParkingPlaceTypeService parkingPlaceTypeService;

    private ParkingPlaceView view;

    private ParkingPlaceFilterView filterView;

    private ParkingPlaceFilterBean currentFilter;

    @Override
    public void onSubmit(ParkingPlaceFilterBean filter) {
        if (filter != null) {
            currentFilter = filter;
        }
        else {
            currentFilter = new ParkingPlaceFilterBean();
        }

        Collection<ParkingPlace> results = parkingPlaceService.findAll(constructFilter(currentFilter));
        if (CollectionUtils.isEmpty(results)) {
            Notification.show("No data found", 1000, Position.MIDDLE);
        }

        view.setParkingPlaces(results);
    }

    @Override
    public void setView(ParkingPlaceView view) {
        this.view = view;
    }

    @Override
    public void setView(ParkingPlaceFilterView view) {
        this.filterView = view;
    }

    @Override
    public void loadFilters() {
        filterView.setDrivers(driverService.findAll(new DriverFilter()));
        filterView.setParkings(parkingService.findAll(new ParkingFilter()));
        filterView.setPlaceTypes(parkingPlaceTypeService.findAll(new ParkingPlaceTypeFilter()));
    }

    @Override
    public void onAdd() {
        ParkingPlaceDialog dialog = new ParkingPlaceDialog(new ParkingPlace(), parkingService.findAll(new ParkingFilter()),
                parkingPlaceTypeService.findAll(new ParkingPlaceTypeFilter()),
                driverService.findAll(new DriverFilter()));
        dialog.setOnSave(this::onSave);
        dialog.setNameValidator(this::isNameUnique);
        
        dialog.open();
    }

    @Override
    public void onEdit(ParkingPlace selected) {
        if (selected == null) {
            Notification.show("Please select a parking place in order to edit.", 1000, Position.MIDDLE);
            return;
        }

        ParkingPlaceDialog dialog = new ParkingPlaceDialog(selected, parkingService.findAll(new ParkingFilter()),
                parkingPlaceTypeService.findAll(new ParkingPlaceTypeFilter()),
                driverService.findAll(new DriverFilter()));
        dialog.setOnSave(this::onSave);
        dialog.setNameValidator(this::isNameUnique);

        dialog.open();
    }

    @Override
    public void onDelete(ParkingPlace selected) {
        if (selected == null) {
            Notification.show("Please select a parking place in order to delete.", 1000, Position.MIDDLE);
            return;
        }

        parkingPlaceService.deleteById(selected.getId());
        onSubmit(currentFilter);
    }

    private void onSave(ParkingPlace bean) {
        parkingPlaceService.save(bean);
        onSubmit(currentFilter);
    }

    private boolean isNameUnique(String name, Parking parking) {
        if (StringUtils.isBlank(name) || parking == null) {
            return false;
        }

        return parkingPlaceService.findByNameAndParking(name.trim(), parking) == null;
    }

    private ParkingPlaceFilter constructFilter(ParkingPlaceFilterBean filterBean) {
        ParkingPlaceFilter filter = new ParkingPlaceFilter();

        if (StringUtils.isNotBlank(filterBean.getName())) {
            filter.setName(filterBean.getName());
        }

        if (filterBean.getParking() != null) {
            filter.setParkingId(filterBean.getParking()
                    .getId());
        }

        if (filterBean.getDriver() != null) {
            filter.setDriverId(filterBean.getDriver()
                    .getId());
        }

        if (filterBean.getPlaceType() != null) {
            filter.setPlaceTypeId(filterBean.getPlaceType()
                    .getId());
        }

        return filter;
    }

}
