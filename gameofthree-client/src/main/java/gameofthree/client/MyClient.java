package gameofthree.client;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.gameofthree.model.Message;

public class MyClient {

	private static String URL = "ws://localhost:8080/chat/name";
	private static final Queue<Message> messages = new LinkedList<>();

	public static void main(String[] args) throws InterruptedException {
		String socketURL = URL;
		WebSocketClient client = new StandardWebSocketClient();
		MessageProcessor messageProcessor = new MessageProcessor(messages);
		new Thread(messageProcessor).start();
		WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(client,
				new MySocketHandler(messageProcessor, messages), socketURL);
		connectionManager.start();

	}

}
