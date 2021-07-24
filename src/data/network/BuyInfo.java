package data.network;

import java.io.Serializable;

public class BuyInfo implements Serializable {
    private MessageHeader messageHeader;
    private String playerName;
    private String clubName;

    public BuyInfo(MessageHeader messageHeader, String playerName, String clubName) {
        this.messageHeader = messageHeader;
        this.playerName = playerName;
        this.clubName = clubName;
    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
