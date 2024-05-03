import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class QLCPlusAPI {
    private static final String WEBSOCKET_URL = "ws://127.0.0.1:9999/qlcplusWS";
    private WebSocket webSocket;

    public QLCPlusAPI() {
        connectWebSocket();
    }

    private void connectWebSocket() {
        webSocket = HttpClient.newHttpClient().newWebSocketBuilder()
            .buildAsync(URI.create(WEBSOCKET_URL), new WebSocket.Listener() {
                @Override
                public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                    System.out.println("Received message: " + data);
                    return WebSocket.Listener.super.onText(webSocket, data, last);
                }

                @Override
                public void onError(WebSocket webSocket, Throwable error) {
                    System.out.println("WebSocket error: " + error.getMessage());
                }

                @Override
                public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
                    System.out.println("WebSocket closed with status: " + statusCode + ", reason: " + reason);
                    return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
                }
            }).join();  // Ensure WebSocket connection is established
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            webSocket.sendText(message, true);
        	//webSocket.sendText("0|255",true);
            System.out.print(message);
        } else {
            System.err.println("WebSocket is not connected. Message not sent.");
        }
    }

    public void closeWebSocket() {
        if (webSocket != null) {
            webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Ending session");
        }
    }
}
