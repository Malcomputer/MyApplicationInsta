package com.firechat.myapplicationtest.Models;

import com.firechat.myapplicationtest.Utils.Libraries.commons.models.IMessage;

import java.util.Date;

public class Message implements IMessage {

	private String message, user_id, user;
	private Date createdAt;

	public Message(String message, String user_id, String user, Date createdAt) {
		this.message = message;
		this.user_id = user_id;
		this.user = user;
		this.createdAt = createdAt;
	}

	public Message() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Message{" +
				"message='" + message + '\'' +
				", user_id='" + user_id + '\'' +
				", user=" + user +
				", createdAt=" + createdAt +
				'}';
	}
}