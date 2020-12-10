package org.fmi.masters.parkingmanager.modules.parkingplacetype;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.ParkingPlaceType;
import org.fmi.masters.parkingmanager.modules.parkingplacetype.ui.ParkingPlaceTypeView;
import org.fmi.masters.parkingmanager.modules.parkingplacetype.ui.dialog.ParkingPlaceTypeDialog;
import org.fmi.masters.parkingmanager.modules.parkingplacetype.ui.filter.ParkingPlaceTypeFilterBean;
import org.fmi.masters.parkingmanager.modules.parkingplacetype.ui.filter.ParkingPlaceTypeFilterView;
import org.fmi.masters.parkingmanager.service.ParkingPlaceService;
import org.fmi.masters.parkingmanager.service.ParkingPlaceTypeService;
import org.fmi.masters.parkingmanager.service.VehicleTypeService;
import org.fmi.masters.parkingmanager.service.filter.ParkingPlaceFilter;
import org.fmi.masters.parkingmanager.service.filter.ParkingPlaceTypeFilter;
import org.fmi.masters.parkingmanager.service.filter.VehicleTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

@Component
public class ParkingPlaceTypePresenter implements ParkingPlaceTypeView.Presenter, ParkingPlaceTypeFilterView.Presenter {

    @Resource
    private ParkingPlaceTypeService parkingPlaceTypeService;

    @Resource
    private VehicleTypeService vehicleTypeService;

    @Resource
    private ParkingPlaceService parkingPlaceService;

    private ParkingPlaceTypeView view;

    private ParkingPlaceTypeFilterView filterView;

    private ParkingPlaceTypeFilterBean currentFilter;

    @Override
    public void onSubmit(ParkingPlaceTypeFilterBean filter) {
        if (filter != null) {
            currentFilter = filter;
        }
        else {
            currentFilter = new ParkingPlaceTypeFilterBean();
        }

        Collection<ParkingPlaceType> results = parkingPlaceTypeService.findAll(constructFilter(filter));

        if (CollectionUtils.isEmpty(results)) {
            Notification.show("No data found", 1000, Position.MIDDLE);
        }

        view.setParkingPlaceTypes(results);
    }

    @Override
    public void setView(ParkingPlaceTypeView view) {
        this.view = view;
    }

    @Override
    public void onAdd() {
        ParkingPlaceTypeDialog dialog = new ParkingPlaceTypeDialog(new ParkingPlaceType(), vehicleTypeService.findAll(new VehicleTypeFilter()));
        dialog.setOnSave(this::onSave);
        dialog.setNameValidator(this::isNameUnique);

        dialog.open();
    }

    @Override
    public void onEdit(ParkingPlaceType selected) {
        if (selected == null) {
            Notification.show("Please select a parking place type in order to edit.", 1000, Position.MIDDLE);
            return;
        }

        ParkingPlaceTypeDialog dialog = new ParkingPlaceTypeDialog(selected, vehicleTypeService.findAll(new VehicleTypeFilter()));
        dialog.setOnSave(this::onSave);
        dialog.setNameValidator(this::isNameUnique);

        dialog.open();
    }

    @Override
    public void onDelete(ParkingPlaceType selected) {
        if (selected == null) {
            Notification.show("Please select a parking place type in order to delete.", 1000, Position.MIDDLE);
            return;
        }

        Long parkingPlaceTypeId = selected.getId();
        ParkingPlaceFilter parkingPlaceFilter = new ParkingPlaceFilter();
        parkingPlaceFilter.setPlaceTypeId(parkingPlaceTypeId);

        if (!CollectionUtils.isEmpty(parkingPlaceService.findAll(parkingPlaceFilter))) {
            Notification.show("Deletion of this parking place type is not allowed. It`s connected to parking place record.", 3000, Position.MIDDLE);
            return;
        }

        parkingPlaceTypeService.deleteById(parkingPlaceTypeId);
        onSubmit(currentFilter);
    }

    private void onSave(ParkingPlaceType bean) {
        parkingPlaceTypeService.save(bean);
        onSubmit(currentFilter);
    }

    private boolean isNameUnique(String name) {
        if (StringUtils.isBlank(name)) {
            return false;
        }

        return parkingPlaceTypeService.findByName(name.trim()) == null;
    }

    @Override
    public void loadFilters() {
        filterView.setVehicleTypes(vehicleTypeService.findAll(new VehicleTypeFilter()));
    }

    @Override
    public void setView(ParkingPlaceTypeFilterView view) {
        this.filterView = view;
    }

    private ParkingPlaceTypeFilter constructFilter(ParkingPlaceTypeFilterBean filterBean) {
        ParkingPlaceTypeFilter filter = new ParkingPlaceTypeFilter();

        if (StringUtils.isNotBlank(filterBean.getName())) {
            filter.setName(filterBean.getName());
        }

        if (filterBean.getVehicleType() != null) {
            filter.setVehicleTypeId(filterBean.getVehicleType()
                    .getId());
        }

        return filter;
    }

}
