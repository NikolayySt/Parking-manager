package org.fmi.masters.parkingmanager.modules.base;

public abstract class CrudSearchView<BEAN, FILTER> extends CrudView<BEAN> {

    private static final long serialVersionUID = 1L;

    protected ModuleFilter<BEAN, FILTER> filter = constructFilter();

    @Override
    protected void constructMainLayout() {
        add(filter);
        super.constructMainLayout();
    }

    protected abstract ModuleFilter<BEAN, FILTER> constructFilter();

}
