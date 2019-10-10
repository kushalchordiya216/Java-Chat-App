package Profile;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = -1435280413237373714L;
    private String content;
    private Integer channelId; // groups to which message was sent
    private String senderName; // Username
    private Timestamp timestamp;

    public Message(String content, Integer senderId, String senderName, Integer groupId) {
        this.content = content;
        this.channelId = groupId;
        this.senderName = senderName;
        this.timestamp = new Timestamp(new Date().getTime());
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getChannel() {
        return channelId;
    }

    public void setGroup(int channel) {
        this.channelId = channel;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

}