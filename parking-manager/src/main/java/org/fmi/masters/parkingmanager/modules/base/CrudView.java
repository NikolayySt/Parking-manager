package org.fmi.masters.parkingmanager.modules.base;

import static org.fmi.masters.parkingmanager.util.GridUtils.getSelected;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class CrudView<BEAN> extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    protected Grid<BEAN> grid;
    protected Button addButton;
    protected Button editButton;
    protected Button deleteButton;

    protected void constructMainLayout() {
        setSizeFull();
        setSpacing(true);

        initActionButtons();
        initGrid();
        add(new HorizontalLayout(addButton, editButton, deleteButton));
        add(grid);
    }

    protected abstract void initGrid();

    protected abstract CrudPresenter<BEAN> getPresenter();

    private void initActionButtons() {
        addButton = new Button("Add");
        addButton.addClickListener(event -> getPresenter().onAdd());

        editButton = new Button("Edit");
        editButton.addClickListener(event -> getPresenter().onEdit(getSelected(grid)));

        deleteButton = new Button("Delete");
        deleteButton.addClickListener(event -> getPresenter().onDelete(getSelected(grid)));
    }

}
