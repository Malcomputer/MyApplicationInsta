package com.firechat.myapplicationinsta.Utils.Libraries.commons.models;

import java.util.Date;

public interface IMessage {

	String getUser_id();

	String getMessage();

	String getUser();

	Date getCreatedAt();
}