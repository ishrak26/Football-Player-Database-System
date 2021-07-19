package data.network;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageHeader messageHeader;
    private String message;

    public Message(MessageHeader messageHeader, String message) {
        this.messageHeader = messageHeader;
        this.message = message;
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
