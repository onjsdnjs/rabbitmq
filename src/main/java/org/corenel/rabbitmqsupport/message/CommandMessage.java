package org.corenel.rabbitmqsupport.message;

public class CommandMessage extends Message{
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
