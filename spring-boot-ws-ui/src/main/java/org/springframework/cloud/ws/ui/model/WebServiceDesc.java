package org.springframework.cloud.ws.ui.model;

import java.io.Serializable;

/**
 *
 * Author:Tiger Shi
 * Date:2024/12/4
 */
public class WebServiceDesc implements Serializable {

    private static final long serialVersionUID = 7606115337827672912L;
    private String name;
    private String webServiceUri;
    private String desc;
    private String lastUpdateTime;
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getWebServiceUri() {
        return webServiceUri;
    }

    public void setWebServiceUri(String webServiceUri) {
        this.webServiceUri = webServiceUri;
    }
}
