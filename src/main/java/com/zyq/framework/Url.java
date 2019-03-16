package com.zyq.framework;

import java.io.Serializable;
import java.util.Objects;

public class Url implements Serializable{
    private String hostName;
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Url(String hostName, Integer port) {
        this.hostName = hostName;
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return Objects.equals(hostName, url.hostName) &&
                Objects.equals(port, url.port);
    }

    @Override
    public int hashCode() {

        return Objects.hash(hostName, port);
    }
}
