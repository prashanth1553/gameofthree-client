package gameofthree.client;

import static com.gameofthree.constants.GameConstants.GAME_OVER;
import static com.gameofthree.constants.GameConstants.PLAYER_DISCONNECTED;

import java.util.Queue;
import java.util.Scanner;

import com.gameofthree.constants.GameConstants;
import com.gameofthree.model.Message;

public class MessageProcessor implements Runnable {

	private MySocketHandler mySocketHandler;
	private Queue<Message> messages;
	private Boolean processMessages = true;
	private boolean isAutoAnswer;

	private Scanner scanner = new Scanner(System.in);

	public MessageProcessor(Queue<Message> messages) {
		this.messages = messages;
	}

	@Override
	public void run() {
		while (processMessages) {
			synchronized (messages) {
				if (messages.isEmpty()) {
					try {
						messages.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (!messages.isEmpty()) {
					Message message = messages.poll();
					if (message.isMessageFromServer()) {
						processServerMessage(message);
					} else {
						processMessage(message);
					}
				}
				messages.notify();
			}
		}

	}

	public void stopProcessing() {
		synchronized (messages) {
			messages.clear();
			processMessages = false;
			messages.notify();
			scanner.close();
		}
	}

	public void setMySocketHandler(MySocketHandler mySocketHandler) {
		this.mySocketHandler = mySocketHandler;
	}

	private void processMessage(Message message) {
		try {
			int input = message.getResultingNumber();
			printMove(false, message);
			Message replyMessage = getResponseToSend(input);
			if (replyMessage.getResultingNumber() == 1) {
				System.out.println("You have won!");
				processMessages = false;
				replyMessage.setContent(GAME_OVER);
				mySocketHandler.sendMessage(replyMessage);
			} else {
				printMove(true, replyMessage);
				mySocketHandler.sendMessage(replyMessage);
			}
		} catch (NumberFormatException exception) {
			System.out.println(message);
		}
	}

	private Message getResponseToSend(int input) {

		Message message = new Message();
		int addedNumber = 0;
		int r = input % 3;
		if (r == 2) {
			addedNumber = 1;
		} else if (r == 1) {
			addedNumber = -1;
		}
		message.setAddedNumber(addedNumber);
		message.setResultingNumber((input + addedNumber) / 3);
		if (!isAutoAnswer) {
			readInputFromUser(message.getResultingNumber());
		}
		return message;
	}

	private void printMove(boolean isYourMove, Message message) {
		String move = isYourMove ? "Your move : " : "Opponent move : ";
		move += String.format("Added number is %s, Resulting Number is %s.", message.getAddedNumber(),
				message.getResultingNumber());
		System.out.println(move);
	}

	private int readInputFromUser(int expectedInput) {
		while (!scanner.hasNext()) {

		}
		String readString = scanner.nextLine().trim();
		if (GameConstants.AUTOMATIC_MODE.equals(readString)) {
			isAutoAnswer = true;
			return -1;
		}
		try {
			int userInput = Integer.parseInt(readString);
			if (userInput == expectedInput) {
				return userInput;
			}
		} catch (NumberFormatException exception) {

		}
		System.out.println("Please enter a valid input");
		return readInputFromUser(expectedInput);
	}

	private void processServerMessage(Message message) {
		if (GAME_OVER.equals(message.getContent())) {
			processMessages = false;
			System.out.println("Sorry! you have lost the game");
		} else if (PLAYER_DISCONNECTED.equals(message.getContent())) {
			processMessages = false;
			System.out.println("Sorry! the opponent player has disconnected. Please wait for another play to join.");
		} else {
			System.out.println(message.getContent());
		}
	}
}
