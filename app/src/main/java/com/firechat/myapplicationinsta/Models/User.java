package com.firechat.myapplicationinsta.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

	private String user_id, photoURL, username, profilebackimage, email, name, bio;
	private boolean online;

	public User(String user_id, String photoURL, String profilebackimage, String email, String username, String name, String bio, boolean online) {
		this.user_id = user_id;
		this.photoURL = photoURL;
		this.profilebackimage = profilebackimage;
		this.email = email;
		this.username = username;
		this.name = name;
		this.bio = bio;
		this.online = online;
	}

	private User() {
	}


	private User(Parcel in) {
		user_id = in.readString();
		photoURL = in.readString();
		profilebackimage = in.readString();
		email = in.readString();
		name = in.readString();
		bio = in.readString();
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public String getProfilebackimage() {
		return profilebackimage;
	}

	public void setProfilebackimage(String profilebackimage) {
		this.profilebackimage = profilebackimage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "User{" +
				"user_id='" + user_id + '\'' +
				", photoURL='" + photoURL + '\'' +
				", username='" + username + '\'' +
				", profilebackimage='" + profilebackimage + '\'' +
				", email='" + email + '\'' +
				", name='" + name + '\'' +
				", bio='" + bio + '\'' +
				", online=" + online +
				'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(user_id);
		dest.writeString(photoURL);
		dest.writeString(profilebackimage);
		dest.writeString(email);
		dest.writeString(username);
		dest.writeString(name);
		dest.writeString(bio);

	}
}