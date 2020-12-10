package org.fmi.masters.parkingmanager.modules.base;

public interface SearchPresenter<BEAN, FILTER> extends CrudPresenter<BEAN> {

    void onSubmit(FILTER filter);

}
