package quiz5;

import java.util.List;

public class DummyCommunicationManager extends CommunicationManager {
    private int counter = 0;
    private static final String[] dummyResponses = {
            "Hello! How can I assist you today?",
            "The current time is 9:00 AM.",
            "I'm here to help!",
            "Goodbye! Have a great day!"
    };

    private String specialServiceUrl;

    public DummyCommunicationManager(String commonServiceUrl, String specialServiceUrl) {
        super();
        this.specialServiceUrl = specialServiceUrl;
    }

    public DummyCommunicationManager() {
        super();
    }

    @Override
    public String sendMessage(String userId, String timestamp, String message, List<String> conversationHistory) {
        boolean shouldUseSpecialService = message.toLowerCase().contains("help")
                || conversationHistory.stream().anyMatch(entry -> entry.toLowerCase().contains("help"));

        if (shouldUseSpecialService) {
            setServiceUrl(specialServiceUrl); // Switch to special service URL
        }

        // Simulate normal response based on counter
        String response = dummyResponses[counter % dummyResponses.length];
        counter++;
        return response;
    }

    private void setServiceUrl(String serviceUrl) {
        super.serviceUrl = serviceUrl;
    }

    public static void main(String[] args) {
        String commonServiceUrl = "http://common-service-url";
        String specialServiceUrl = "http://special-service-url";

        DummyCommunicationManager tester = new DummyCommunicationManager(commonServiceUrl, specialServiceUrl);
        UserInteractionManager userInteractionManager = new UserInteractionManager(tester);

        // Start conversation interaction
        userInteractionManager.start();
    }
}
