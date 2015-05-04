package org.corenel.rabbitmqsupport.message;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CommandMessage extends Message implements Serializable{
	
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
