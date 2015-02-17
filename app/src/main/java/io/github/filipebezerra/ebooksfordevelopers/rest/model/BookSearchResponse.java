package io.github.filipebezerra.ebooksfordevelopers.rest.model;

import java.util.*;
import com.google.gson.annotations.*;

public class BookSearchResponse extends BaseResponse {
	
	@SerializedName("Page")
	private Integer mPageResult;
	
	@SerializedName("Total")
	private String mTotalResult;
	
	@SerializedName("Books")
	private List<Book> mBooks = new ArrayList<>();
	
	public BookSearchResponse() {
	}

	public BookSearchResponse(String error, Double time, Integer pageResult, String totalResult) {
		super(error, time);
		mPageResult = pageResult;
		mTotalResult = totalResult;
	}

	public void setPageResult(Integer pageResult) {
		mPageResult = pageResult;
	}

	public Integer getPageResult() {
		return mPageResult;
	}

	public void setTotalResult(String totalResult) {
		mTotalResult = totalResult;
	}

	public String getTotalResult() {
		return mTotalResult;
	}

	public void setBooks(List<Book> books) {
		mBooks = books;
	}

	public List<Book> getBooks() {
		return mBooks;
	}
	
}
