package org.fmi.masters.parkingmanager.modules;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinRequest;

@WebInitParam(name = "compatibilityMode",
        value = "true")
@WebServlet(urlPatterns = { "/app/*", "/VAADIN/*" })
public class MainUi extends UI {

    private static final long serialVersionUID = 1L;

    @Override
    protected void init(VaadinRequest request) {
        super.init(request);
    }

}
