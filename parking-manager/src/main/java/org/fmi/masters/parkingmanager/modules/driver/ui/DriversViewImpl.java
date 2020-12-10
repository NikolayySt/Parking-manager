package org.fmi.masters.parkingmanager.modules.driver.ui;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.fmi.masters.parkingmanager.dto.Driver;
import org.fmi.masters.parkingmanager.modules.base.BasicView;
import org.fmi.masters.parkingmanager.modules.base.CrudPresenter;
import org.fmi.masters.parkingmanager.modules.base.CrudSearchView;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.driver.ui.filter.DriverFilterBean;
import org.fmi.masters.parkingmanager.modules.driver.ui.filter.DriverModuleFilter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "drivers",
        layout = BasicView.class)
@PageTitle("Drivers")
public class DriversViewImpl extends CrudSearchView<Driver, DriverFilterBean> implements DriversView {

    private static final long serialVersionUID = 1L;

    @Resource
    private Presenter presenter;

    private DriverModuleFilter moduleFilter;

    public DriversViewImpl() {
        constructMainLayout();
    }

    @PostConstruct
    protected void init() {
        presenter.setView(this);
        moduleFilter.setSearchPresenter(presenter);
        moduleFilter.setFilterPresenter(presenter);
    }

    @Override
    public void setDrivers(Collection<Driver> drivers) {
        grid.setItems(drivers);
    }

    @Override
    protected void initGrid() {
        grid = new Grid<Driver>(Driver.class);
        grid.setSizeFull();

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("vehicle");
        
        grid.addColumn(item -> constructVehicleText(item))
                .setHeader("Vehicle");
    }

    private String constructVehicleText(Driver item) {
        StringBuilder builder = new StringBuilder();
        builder.append(item.getVehicle()
                .getBrand());
        builder.append(" ");
        builder.append(item.getVehicle()
                .getModel());
        builder.append(" (");
        builder.append(item.getVehicle()
                .getNumberPlate());
        builder.append(")");

        return builder.toString();
    }

    @Override
    protected CrudPresenter<Driver> getPresenter() {
        return presenter;
    }

    @Override
    protected ModuleFilter<Driver, DriverFilterBean> constructFilter() {
        if (moduleFilter == null) {
            moduleFilter = new DriverModuleFilter();
        }

        return moduleFilter;
    }

}
