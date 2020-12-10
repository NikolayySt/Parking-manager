package org.fmi.masters.parkingmanager.modules.base;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "overview",
        layout = BasicView.class)
@RouteAlias(value = "",
        layout = BasicView.class)
@PageTitle("Overview")
public class OverviewView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public OverviewView() {
        // TODO: add some text here
    }

}
