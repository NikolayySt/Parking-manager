package org.fmi.masters.parkingmanager.modules.vehicletype.ui;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.modules.base.BasicView;
import org.fmi.masters.parkingmanager.modules.base.CrudPresenter;
import org.fmi.masters.parkingmanager.modules.base.CrudSearchView;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.vehicletype.ui.filter.VehicleTypeFilterBean;
import org.fmi.masters.parkingmanager.modules.vehicletype.ui.filter.VehicleTypeModuleFilter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "vehicletypes",
        layout = BasicView.class)
@PageTitle("Vehicle types")
public class VehicleTypeViewImpl extends CrudSearchView<VehicleType, VehicleTypeFilterBean> implements VehicleTypeView {

    private static final long serialVersionUID = 1L;

    @Resource
    private Presenter presenter;

    private VehicleTypeModuleFilter moduleFilter;

    public VehicleTypeViewImpl() {
        constructMainLayout();
    }

    @PostConstruct
    private void init() {
        presenter.setView(this);
        moduleFilter.setSearchPresenter(presenter);
        moduleFilter.setFilterPresenter(presenter);
    }

    @Override
    public void setVehicleTypes(Collection<VehicleType> vehicleTypes) {
        grid.setItems(vehicleTypes);
    }

    @Override
    protected void initGrid() {
        grid = new Grid<VehicleType>(VehicleType.class);
        grid.setSizeFull();

        grid.removeColumnByKey("id");
        
    }

    @Override
    protected CrudPresenter<VehicleType> getPresenter() {
        return presenter;
    }

    @Override
    protected ModuleFilter<VehicleType, VehicleTypeFilterBean> constructFilter() {
        if (moduleFilter == null) {
            moduleFilter = new VehicleTypeModuleFilter();
        }

        return moduleFilter;
    }

}
