package org.fmi.masters.parkingmanager.modules.parkingplace.ui;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.fmi.masters.parkingmanager.dto.ParkingPlace;
import org.fmi.masters.parkingmanager.modules.base.BasicView;
import org.fmi.masters.parkingmanager.modules.base.CrudPresenter;
import org.fmi.masters.parkingmanager.modules.base.CrudSearchView;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.parkingplace.ui.filter.ParkingPlaceFilterBean;
import org.fmi.masters.parkingmanager.modules.parkingplace.ui.filter.ParkingPlaceModuleFilter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "parking-places",
        layout = BasicView.class)
@PageTitle("Parking places")
public class ParkingPlaceViewImpl extends CrudSearchView<ParkingPlace, ParkingPlaceFilterBean> implements ParkingPlaceView {

    private static final long serialVersionUID = 1L;

    @Resource
    private Presenter presenter;

    private ParkingPlaceModuleFilter moduleFilter;
    
    public ParkingPlaceViewImpl() {
        constructMainLayout();
    }

    @PostConstruct
    private void init() {
        presenter.setView(this);
        moduleFilter.setFilterPresenter(presenter);
        moduleFilter.setSearchPresenter(presenter);
    }

    @Override
    public void setParkingPlaces(Collection<ParkingPlace> parkingPlaces) {
        grid.setItems(parkingPlaces);
    }

    @Override
    protected void initGrid() {
        grid = new Grid<>(ParkingPlace.class);
        grid.setSizeFull();

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("parking");
        grid.removeColumnByKey("driver");
        grid.removeColumnByKey("parkingPlaceType");

        grid.addColumn(constructParkingText())
                .setHeader("Parking");
        grid.addColumn(constructDriverText())
                .setHeader("Driver");
        grid.addColumn(constrictParkingPlaceTypeText()).setHeader("Parking place type");
    }

    private ValueProvider<ParkingPlace, ?> constructParkingText() {
        return bean -> bean.getParking()
                .getName();
    }

    private ValueProvider<ParkingPlace, ?> constrictParkingPlaceTypeText() {
        return bean -> bean.getParkingPlaceType()
                .getName();
    }

    private ValueProvider<ParkingPlace, ?> constructDriverText() {
        return bean -> {
            if (bean.getDriver() == null) {
                return StringUtils.EMPTY;
            }

            return bean.getDriver()
                    .getFirstName() + ", "
                    + bean.getDriver()
                            .getLastName();
        };
    }

    @Override
    protected CrudPresenter<ParkingPlace> getPresenter() {
        return presenter;
    }

    @Override
    protected ModuleFilter<ParkingPlace, ParkingPlaceFilterBean> constructFilter() {
        if (moduleFilter == null) {
            moduleFilter = new ParkingPlaceModuleFilter();
        }

        return moduleFilter;
    }

}
