package org.corenel.rabbitmqsupport.message;

public class CommandMessage extends Message{
	
	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

}
