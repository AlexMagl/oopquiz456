package quiz5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CommunicationManager {
    protected String serviceUrl;

    public CommunicationManager() {
        this.serviceUrl = serviceUrl;
    }

    public String sendMessage(String userId, String timestamp, String message, List<String> conversationHistory) {
        try {
            URL url = new URL(serviceUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = createJsonRequest(userId, timestamp, message, conversationHistory);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with the chatbot service.";
        }
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
}
