package org.springframework.cloud.ws.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WebServiceModuleDesc implements Serializable {
    private static final long serialVersionUID = -3380911230956444816L;

    private String name;
    private String description;

    List<WebServiceDesc> uris = new ArrayList<>();


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WebServiceDesc> getUris() {
        return uris;
    }

    public void addUris(List<WebServiceDesc> uris) {
        this.uris.addAll(uris);
    }
    public void addWebServiceDesc(WebServiceDesc desc) {
        this.uris.add(desc);
    }
}
