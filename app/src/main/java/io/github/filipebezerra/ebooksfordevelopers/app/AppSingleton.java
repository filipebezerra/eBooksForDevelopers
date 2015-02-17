package io.github.filipebezerra.ebooksfordevelopers.app;

import android.app.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;

public class AppSingleton extends Application {

	private static AppSingleton mInstance;
	
	private static final String TAG_REQUEST = AppSingleton.class.getSimpleName();
	
	private RequestQueue mRequestQueue;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}
	
	public static AppSingleton getInstance() {
		return mInstance;
	}
	
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
		
		return mRequestQueue;
	}
	
	public <T> void addToRequestQueue(final String tag, Request<T> request) {
		request.setTag(tag != null ? tag : TAG_REQUEST);
		getRequestQueue().add(request);
	}
	
	public <T> void cancelAll(final String tag) {
		getRequestQueue().cancelAll(tag);
	}
	
}
