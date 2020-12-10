package org.fmi.masters.parkingmanager.modules.parking;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.modules.parking.ui.ParkingView;
import org.fmi.masters.parkingmanager.modules.parking.ui.dialog.ParkingDialog;
import org.fmi.masters.parkingmanager.modules.parking.ui.filter.ParkingFilterBean;
import org.fmi.masters.parkingmanager.modules.parking.ui.filter.ParkingFilterView;
import org.fmi.masters.parkingmanager.service.ParkingPlaceService;
import org.fmi.masters.parkingmanager.service.ParkingService;
import org.fmi.masters.parkingmanager.service.filter.ParkingFilter;
import org.fmi.masters.parkingmanager.service.filter.ParkingPlaceFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

@Component
public class ParkingPresenter implements ParkingView.Presenter, ParkingFilterView.Presenter {

    @Resource
    private ParkingService parkingService;

    @Resource
    private ParkingPlaceService parkingPlaceService;

    private ParkingView view;

    private ParkingFilterView filterView;

    private ParkingFilterBean currentFilter;

    @Override
    public void onSubmit(ParkingFilterBean filter) {
        if (filter != null) {
            currentFilter = filter;
        }
        else {
            currentFilter = new ParkingFilterBean();
        }

        List<Parking> results = parkingService.findAll(constructFilter(currentFilter));

        if (CollectionUtils.isEmpty(results)) {
            Notification.show("No data found", 1000, Position.MIDDLE);
        }

        view.setParkings(results);
    }

    @Override
    public void setView(ParkingView view) {
        this.view = view;
    }

    @Override
    public void onAdd() {
        ParkingDialog dialog = new ParkingDialog(new Parking());
        dialog.setOnSave(this::onSave);
        dialog.setNameValidator(this::isNameUnique);

        dialog.open();
    }

    @Override
    public void onEdit(Parking selected) {
        if (selected == null) {
            Notification.show("Please select a parking in order to edit.", 1000, Position.MIDDLE);
            return;
        }

        ParkingDialog dialog = new ParkingDialog(selected);
        dialog.setOnSave(this::onSave);
        dialog.setNameValidator(this::isNameUnique);

        dialog.open();
    }

    @Override
    public void onDelete(Parking selected) {
        if (selected == null) {
            Notification.show("Please select a parking in order to delete.", 1000, Position.MIDDLE);
            return;
        }
        Long parkingId = selected.getId();

        ParkingPlaceFilter parkingPlaceFilter = new ParkingPlaceFilter();
        parkingPlaceFilter.setParkingId(parkingId);

        if (!CollectionUtils.isEmpty(parkingPlaceService.findAll(parkingPlaceFilter))) {
            Notification.show("Deletion of this parking is not allowed. It`s connected to parking place record.", 3000, Position.MIDDLE);
            return;
        }

        parkingService.deleteById(parkingId);
        onSubmit(currentFilter);
    }

    private void onSave(Parking bean) {
        parkingService.save(bean);
        onSubmit(currentFilter);
    }

    private boolean isNameUnique(String name) {
        if (StringUtils.isBlank(name)) {
            return false;
        }

        return parkingService.findByName(name.trim()) == null;
    }

    @Override
    public void loadFilters() {
        /* Nothing to load */
    }

    @Override
    public void setView(ParkingFilterView view) {
        this.filterView = view;
    }

    private ParkingFilter constructFilter(ParkingFilterBean filterBean) {
        ParkingFilter filter = new ParkingFilter();

        if (StringUtils.isNotBlank(filterBean.getName())) {
            filter.setName(filterBean.getName());
        }

        return filter;
    }

}
