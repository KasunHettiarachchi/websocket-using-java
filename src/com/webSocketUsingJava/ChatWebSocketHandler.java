package com.webSocketUsingJava;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ChatWebSocketHandler {
    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String username = "User" + EntryPoint.nextUserNumber++;
        EntryPoint.userUsernameMap.put(user, username);
        EntryPoint.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = EntryPoint.userUsernameMap.get(user);
        EntryPoint.userUsernameMap.remove(user);
        EntryPoint.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        EntryPoint.broadcastMessage(sender = EntryPoint.userUsernameMap.get(user), msg = message);
    }
}
