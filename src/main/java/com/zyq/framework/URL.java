package com.zyq.framework;

import java.io.Serializable;

public class URL implements Serializable{
    private String hostName;
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public URL(String hostName, Integer port) {
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
        URL url = (URL) o;
        if (hostName != null ? !hostName.equals(url.hostName) : url.hostName != null) return false;
        return port != null ? port.equals(url.port) : url.port == null;
    }
    @Override
    public int hashCode() {
        int result = hostName != null ? hostName.hashCode() : 0;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }
}
