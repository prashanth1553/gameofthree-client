package com.gameofthree.model;

public class Message {

	private String content = "";
	private boolean isMessageFromServer;
	private int resultingNumber;
	private int addedNumber;

	public Message() {

	}

	public Message(int resultingNumber, int addedNumber) {
		this.resultingNumber = resultingNumber;
		this.addedNumber = addedNumber;
	}

	public Message(boolean isMessageFromServer, String content) {
		this.content = content;
		this.isMessageFromServer = isMessageFromServer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isMessageFromServer() {
		return isMessageFromServer;
	}

	public void setMessageFromServer(boolean isMessageFromServer) {
		this.isMessageFromServer = isMessageFromServer;
	}

	public int getResultingNumber() {
		return resultingNumber;
	}

	public void setResultingNumber(int resultingNumber) {
		this.resultingNumber = resultingNumber;
	}

	public int getAddedNumber() {
		return addedNumber;
	}

	public void setAddedNumber(int addedNumber) {
		this.addedNumber = addedNumber;
	}

}
