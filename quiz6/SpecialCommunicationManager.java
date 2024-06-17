package quiz6;

import quiz5.CommunicationManager;

import java.util.List;

public class SpecialCommunicationManager extends CommunicationManager {
    private String specialServiceUrl;

    public SpecialCommunicationManager(String commonServiceUrl, String specialServiceUrl) {
        super();
        this.specialServiceUrl = specialServiceUrl;
    }

    @Override
    public String sendMessage(String userId, String timestamp, String message, List<String> conversationHistory) {
        if (message.toLowerCase().contains("help")) {
            setServiceUrl(specialServiceUrl);
        }
        return super.sendMessage(userId, timestamp, message, conversationHistory);
    }

    private void setServiceUrl(String serviceUrl) {
        super.serviceUrl = serviceUrl;
    }
}
