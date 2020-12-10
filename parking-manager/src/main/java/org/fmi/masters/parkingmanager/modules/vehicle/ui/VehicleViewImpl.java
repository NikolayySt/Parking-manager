package org.fmi.masters.parkingmanager.modules.vehicle.ui;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.fmi.masters.parkingmanager.dto.Vehicle;
import org.fmi.masters.parkingmanager.modules.base.BasicView;
import org.fmi.masters.parkingmanager.modules.base.CrudPresenter;
import org.fmi.masters.parkingmanager.modules.base.CrudSearchView;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.vehicle.ui.filter.VehicleFilterBean;
import org.fmi.masters.parkingmanager.modules.vehicle.ui.filter.VehicleModuleFilter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "vehicles",
        layout = BasicView.class)
@PageTitle("Vehicles")
public class VehicleViewImpl extends CrudSearchView<Vehicle, VehicleFilterBean> implements VehicleView {

    private static final long serialVersionUID = 1L;

    @Resource
    private Presenter presenter;

    private VehicleModuleFilter moduleFilter;

    public VehicleViewImpl() {
        constructMainLayout();
    }

    @PostConstruct
    private void init() {
        presenter.setView(this);
        moduleFilter.setSearchPresenter(presenter);
        moduleFilter.setFilterPresenter(presenter);
    }

    @Override
    public void setVehicles(Collection<Vehicle> vehicles) {
        grid.setItems(vehicles);
    }

    @Override
    protected void initGrid() {
        grid = new Grid<>(Vehicle.class);
        grid.setSizeFull();

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("vehicleType");

        grid.addColumn(bean -> bean.getVehicleType()
                .getName())
                .setHeader("Vehicle type");
    }

    @Override
    protected CrudPresenter<Vehicle> getPresenter() {
        return presenter;
    }

    @Override
    protected ModuleFilter<Vehicle, VehicleFilterBean> constructFilter() {
        if (moduleFilter == null) {
            moduleFilter = new VehicleModuleFilter();
        }

        return moduleFilter;
    }

}
