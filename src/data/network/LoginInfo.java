package data.network;

import java.io.Serializable;

public class LoginInfo implements Serializable {
    private MessageHeader messageHeader;
    private String username;
    private String password;

    public LoginInfo(MessageHeader messageHeader, String username, String password) {
        this.messageHeader = messageHeader;
        this.username = username;
        this.password = password;
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
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
}
