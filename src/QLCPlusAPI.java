import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class QLCPlusAPI {
    private static final String WEBSOCKET_URL = "ws://127.0.0.1:9999/qlcplusWS";
    private WebSocket webSocket;
    private CompletableFuture<WebSocket> webSocketFuture;

    public QLCPlusAPI() {
        connectWebSocket();
    }

    private void connectWebSocket() {
        webSocketFuture = HttpClient.newHttpClient().newWebSocketBuilder()
                .buildAsync(URI.create(WEBSOCKET_URL), new WebSocket.Listener() {
                    @Override
                    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                        System.out.println("Received message: " + data);
                        return WebSocket.Listener.super.onText(webSocket, data, last);
                    }

                    @Override
                    public void onError(WebSocket webSocket, Throwable error) {
                        System.out.println("WebSocket error: " + error.getMessage());
                        reconnectWebSocket();  // Attempt to reconnect on error
                    }

                    @Override
                    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
                        System.out.println("WebSocket closed with status: " + statusCode + ", reason: " + reason);
                        reconnectWebSocket();  // Attempt to reconnect on close
                        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
                    }
                });

        // Use thenAccept to set webSocket once the future is completed
        webSocketFuture.thenAccept(ws -> this.webSocket = ws);
    }

    public void sendMessage(String message) {
        if (webSocket != null && webSocketFuture.isDone()) {
            webSocket.sendText(message, true).thenRun(() -> System.out.println("Message sent: " + message));
        } else {
            System.err.println("WebSocket is not connected. Message not sent.");
        }
    }

    public void closeWebSocket() {
        if (webSocket != null) {
            webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Ending session");
        }
    }

    private void reconnectWebSocket() {
        System.out.println("Attempting to reconnect...");
        try {
            TimeUnit.SECONDS.sleep(10);  // Wait before reconnecting
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        connectWebSocket();  // Reconnect
    }
}
