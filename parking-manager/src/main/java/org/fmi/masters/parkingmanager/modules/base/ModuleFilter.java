package org.fmi.masters.parkingmanager.modules.base;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;

public abstract class ModuleFilter<BEAN, FILTER> extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    private final Button searchButton = new Button();
    private final Button clearButton = new Button();

    protected Binder<FILTER> binder = new Binder<>();

    protected abstract HorizontalLayout getFields();

    protected abstract void clearFields();

    protected abstract SearchPresenter<BEAN, FILTER> getSearchPresenter();

    protected abstract void bindFields();

    protected void constuctFilterLayout() {
        bindFields();
        setSpacing(true);
        
        initActionButtons();
        add(getFields());
        add(createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        HorizontalLayout buttonsLayout = new HorizontalLayout(searchButton, clearButton);
        buttonsLayout.setSizeFull();
        buttonsLayout.setPadding(true);
        buttonsLayout.setMargin(true);

        return buttonsLayout;
    }

    private void initActionButtons() {
        searchButton.setIcon(VaadinIcon.SEARCH.create());
        clearButton.setIcon(VaadinIcon.TRASH.create());

        searchButton.addClickListener(event -> {
            getSearchPresenter().onSubmit(binder.getBean());
        });
        clearButton.addClickListener(event -> clearFields());
    }

}
