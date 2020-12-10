package org.fmi.masters.parkingmanager.modules.parkingplacetype.ui;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.ParkingPlaceType;
import org.fmi.masters.parkingmanager.dto.VehicleType;
import org.fmi.masters.parkingmanager.modules.base.BasicView;
import org.fmi.masters.parkingmanager.modules.base.CrudPresenter;
import org.fmi.masters.parkingmanager.modules.base.CrudSearchView;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.parkingplacetype.ui.filter.ParkingPlaceTypeFilterBean;
import org.fmi.masters.parkingmanager.modules.parkingplacetype.ui.filter.ParkingPlaceTypeModuleFilter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "parking-place-types",
        layout = BasicView.class)
@PageTitle("Parking place types")
public class ParkingPlaceTypeViewImpl extends CrudSearchView<ParkingPlaceType, ParkingPlaceTypeFilterBean> implements ParkingPlaceTypeView {

    private static final long serialVersionUID = 1L;

    @Resource
    private Presenter presenter;

    private ParkingPlaceTypeModuleFilter moduleFilter;

    public ParkingPlaceTypeViewImpl() {
        constructMainLayout();
    }

    @PostConstruct
    private void init() {
        presenter.setView(this);
        moduleFilter.setSearchPresenter(presenter);
        moduleFilter.setFilterPresenter(presenter);
    }

    @Override
    public void setParkingPlaceTypes(Collection<ParkingPlaceType> parkingPlaceTypes) {
        grid.setItems(parkingPlaceTypes);
    }

    @Override
    protected void initGrid() {
        grid = new Grid<>(ParkingPlaceType.class);
        grid.setSizeFull();

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("vehicleTypes");

        grid.addColumn(
                this::constructVehicleTypes)
                .setHeader("Vehicle types");
    }

    private String constructVehicleTypes(ParkingPlaceType item) {
        if (item == null || item.getVehicleTypes() == null) {
            return StringUtils.EMPTY;
        }

        return item.getVehicleTypes()
                .stream()
                .map(VehicleType::getName)
                .collect(Collectors.joining(", "));
    }

    @Override
    protected CrudPresenter<ParkingPlaceType> getPresenter() {
        return presenter;
    }

    @Override
    protected ModuleFilter<ParkingPlaceType, ParkingPlaceTypeFilterBean> constructFilter() {
        if (moduleFilter == null) {
            moduleFilter = new ParkingPlaceTypeModuleFilter();
        }

        return moduleFilter;
    }

}
