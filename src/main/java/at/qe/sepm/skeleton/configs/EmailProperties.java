package at.qe.sepm.skeleton.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties("email")
public class EmailProperties {
    private String host = "smtp.gmail.com";
    private String username = "evs2.uibk@gmail.com";
    private String password = "evs2!uibk2019";
    private int port = 587;
    private String protocol = "smtp";
    private boolean mailAuth = true;
    private boolean mailTls = true;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isMailAuth() {
        return mailAuth;
    }

    public void setMailAuth(boolean mailAuth) {
        this.mailAuth = mailAuth;
    }

    public boolean isMailTls() {
        return mailTls;
    }

    public void setMailTls(boolean mailTls) {
        this.mailTls = mailTls;
    }
}
