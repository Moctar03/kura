package org.eclipse.kura.web.shared.model;

public class GwtDhcpLease {
    
    private String macAddress;
    private String ipAddress;
    private String hostname;
    
    public GwtDhcpLease() {
        this.macAddress = "";
        this.ipAddress = "";
        this.hostname = "";
    }
    
    public GwtDhcpLease(String macAddress, String ipAddress, String hostname) {
        super();
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.hostname = hostname;
    }
    
    public String getMacAddress() {
        return macAddress;
    }
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getHostname() {
        return hostname;
    }
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(macAddress + "\t").append(ipAddress + "\t").append(hostname);
        return sb.toString();
    }
    
}
