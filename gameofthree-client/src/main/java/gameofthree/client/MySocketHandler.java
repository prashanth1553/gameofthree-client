package gameofthree.client;

import java.io.IOException;
import java.util.Queue;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.gameofthree.model.Message;
import com.google.gson.Gson;

public class MySocketHandler extends TextWebSocketHandler {
	private WebSocketSession webSocketSession;
	private MessageProcessor messageProcessor;
	private Queue<Message> messages;
	private Gson gson = new Gson();

	public MySocketHandler(MessageProcessor messageProcessor, Queue<Message> messages) {
		this.messageProcessor = messageProcessor;
		messageProcessor.setMySocketHandler(this);
		this.messages = messages;
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
		webSocketSession = session;
		synchronized (messages) {
			Message message = gson.fromJson(textMessage.getPayload(), Message.class);
			messages.add(message);
			messages.notify();
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		webSocketSession = session;
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		System.out.println("Transport Error");
		messageProcessor.stopProcessing();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		System.out.print("Connection Closed [" + status.getReason() + "]");
		messageProcessor.stopProcessing();
	}

	public void sendMessage(Message message) {
		try {
			webSocketSession.sendMessage(new TextMessage(gson.toJson(message)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
