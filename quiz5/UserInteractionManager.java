package quiz5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserInteractionManager {
    private CommunicationManager communicationManager;
    private List<String> conversationHistory;
    private String userId = "user_123";

    public UserInteractionManager(CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
        this.conversationHistory = new ArrayList<>();
    }

    public void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        try {
            while (true) {
                System.out.print("User: ");
                userInput = reader.readLine();
                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }
                sendMessage(userInput);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String message) {
        String timestamp = java.time.Instant.now().toString();
        conversationHistory.add(createJsonRequest(userId, timestamp, message, conversationHistory));

        String response = communicationManager.sendMessage(userId, timestamp, message, conversationHistory);
        System.out.println("Chatbot: " + response);
    }

    private String createJsonRequest(String userId, String timestamp, String message, List<String> conversationHistory) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"user_id\":\"").append(userId).append("\",");
        json.append("\"timestamp\":\"").append(timestamp).append("\",");
        json.append("\"message\":\"").append(message).append("\",");
        json.append("\"conversation_history\":[");
        for (int i = 0; i < conversationHistory.size(); i++) {
            String[] parts = conversationHistory.get(i).split(",");
            json.append("{");
            json.append("\"user_id\":\"").append(parts[0]).append("\",");
            json.append("\"timestamp\":\"").append(parts[1]).append("\",");
            json.append("\"message\":\"").append(parts[2]).append("\"");
            json.append("}");
            if (i < conversationHistory.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        json.append("}");
        return json.toString();
    }

    public static void main(String[] args) {
        CommunicationManager communicationManager = new DummyCommunicationManager();
        UserInteractionManager userInteractionManager = new UserInteractionManager(communicationManager);
        userInteractionManager.start();
    }
}
