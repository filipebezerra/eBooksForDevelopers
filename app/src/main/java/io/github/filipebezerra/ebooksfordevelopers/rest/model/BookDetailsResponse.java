package io.github.filipebezerra.ebooksfordevelopers.rest.model;
import com.google.gson.annotations.*;

public class BookDetailsResponse extends BaseResponse {

	@SerializedName("ID")
	private Long mId;

	@SerializedName("Title")
	private String mTitle;

	@SerializedName("SubTitle")
	private String mSubtitle;

	@SerializedName("Description")
	private String mDescription;

	@SerializedName("Author")
	private String mAuthor;

	@SerializedName("ISBN")
	private String mIsbn;

	@SerializedName("Page")
	private String mPages;

	@SerializedName("Year")
	private String mYear;

	@SerializedName("Publisher")
	private String mPublisher;

	@SerializedName("Image")
	private String mImage;

	@SerializedName("Download")
	private String mDownload;

	public BookDetailsResponse() {
	}

	public BookDetailsResponse(Long id, String title, String subtitle, String description, String author, String isbn, String pages, String year, String publisher, String image, String download) {
		mId = id;
		mTitle = title;
		mSubtitle = subtitle;
		mDescription = description;
		mAuthor = author;
		mIsbn = isbn;
		mPages = pages;
		mYear = year;
		mPublisher = publisher;
		mImage = image;
		mDownload = download;
	}

	public BookDetailsResponse(String error, Double time, Long id, String title, String subtitle, String description, String author, String isbn, String pages, String year, String publisher, String image, String download) {
		super(error, time);
		mId = id;
		mTitle = title;
		mSubtitle = subtitle;
		mDescription = description;
		mAuthor = author;
		mIsbn = isbn;
		mPages = pages;
		mYear = year;
		mPublisher = publisher;
		mImage = image;
		mDownload = download;
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

	public void setAuthor(String author) {
		mAuthor = author;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public void setIsbn(String isbn) {
		mIsbn = isbn;
	}

	public String getIsbn() {
		return mIsbn;
	}

	public void setPages(String pages) {
		mPages = pages;
	}

	public String getPages() {
		return mPages;
	}

	public void setYear(String year) {
		mYear = year;
	}

	public String getYear() {
		return mYear;
	}

	public void setPublisher(String publisher) {
		mPublisher = publisher;
	}

	public String getPublisher() {
		return mPublisher;
	}

	public void setImage(String image) {
		mImage = image;
	}

	public String getImage() {
		return mImage;
	}

	public void setDownload(String download) {
		mDownload = download;
	}

	public String getDownload() {
		return mDownload;
	}

}
