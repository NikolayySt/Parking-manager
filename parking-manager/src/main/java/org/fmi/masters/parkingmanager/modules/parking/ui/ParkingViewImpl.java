package org.fmi.masters.parkingmanager.modules.parking.ui;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.fmi.masters.parkingmanager.dto.Parking;
import org.fmi.masters.parkingmanager.modules.base.BasicView;
import org.fmi.masters.parkingmanager.modules.base.CrudPresenter;
import org.fmi.masters.parkingmanager.modules.base.CrudSearchView;
import org.fmi.masters.parkingmanager.modules.base.ModuleFilter;
import org.fmi.masters.parkingmanager.modules.parking.ui.filter.ParkingFilterBean;
import org.fmi.masters.parkingmanager.modules.parking.ui.filter.ParkingModuleFilter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "parkings",
        layout = BasicView.class)
@PageTitle("Parkings")
public class ParkingViewImpl extends CrudSearchView<Parking, ParkingFilterBean> implements ParkingView {

    private static final long serialVersionUID = 1L;

    @Resource
    private Presenter presenter;

    private ParkingModuleFilter moduleFilter;

    public ParkingViewImpl() {
        constructMainLayout();
    }

    @PostConstruct
    protected void init() {
        presenter.setView(this);
        moduleFilter.setSearchPresenter(presenter);
    }

    @Override
    public void setParkings(Collection<Parking> parkings) {
        grid.setItems(parkings);
    }

    @Override
    protected void initGrid() {
        grid = new Grid<>(Parking.class);
        grid.setSizeFull();

        grid.removeColumnByKey("id");
    }

    @Override
    protected CrudPresenter<Parking> getPresenter() {
        return presenter;
    }

    @Override
    protected ModuleFilter<Parking, ParkingFilterBean> constructFilter() {
        if (moduleFilter == null) {
            moduleFilter = new ParkingModuleFilter();
        }

        return moduleFilter;
    }

}
