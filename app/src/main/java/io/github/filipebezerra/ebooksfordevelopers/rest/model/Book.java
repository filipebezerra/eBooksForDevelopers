package io.github.filipebezerra.ebooksfordevelopers.rest.model;

import com.google.gson.annotations.*;

public class Book {
	
	@SerializedName("ID")
	private Long mId;

	@SerializedName("Title")
	private String mTitle;

	@SerializedName("SubTitle")
	private String mSubtitle;

	@SerializedName("Description")
	private String mDescription;

	@SerializedName("Image")
	private String mImage;

	@SerializedName("isbn")
	private String mIsbn;
	
	public Book() {
	}

	public Book(Long id, String title, String subtitle, String description, String image, String isbn) {
		mId = id;
		mTitle = title;
		mSubtitle = subtitle;
		mDescription = description;
		mImage = image;
		mIsbn = isbn;
	}

	public void setId(Long id) {
		mId = id;
	}

	public Long getId() {
		return mId;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setSubtitle(String subtitle) {
		mSubtitle = subtitle;
	}

	public String getSubtitle() {
		return mSubtitle;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setImage(String image) {
		mImage = image;
	}

	public String getImage() {
		return mImage;
	}

	public void setIsbn(String isbn) {
		mIsbn = isbn;
	}

	public String getIsbn() {
		return mIsbn;
	}
	
}
