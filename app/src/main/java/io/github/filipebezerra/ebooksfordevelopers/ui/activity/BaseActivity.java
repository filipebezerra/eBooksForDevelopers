package io.github.filipebezerra.ebooksfordevelopers.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.filipebezerra.ebooksfordevelopers.R;
import io.github.filipebezerra.ebooksfordevelopers.app.AppSingleton;

public abstract class BaseActivity extends ActionBarActivity {

    public static final String BASE_URL = "http://it-ebooks-api.info";
    public static final String END_POINT = BASE_URL + "/v1/";

    protected Toolbar mToolbar;
    protected ProgressWheel mProgressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        loadContentView();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract int getLayoutResource();

    protected abstract void loadContentView();

    protected void setActionBarIcon(int iconRes) {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(iconRes);
        }
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void startProgress() {
        if ((mProgressWheel != null) && (mProgressWheel.getVisibility() != View.VISIBLE))
            mProgressWheel.setVisibility(View.VISIBLE);
    }

    protected void stopProgress() {
        if ((mProgressWheel != null) && (mProgressWheel.getVisibility() != View.INVISIBLE))
            mProgressWheel.setVisibility(View.INVISIBLE);
    }

    protected void doSearch(final String url, final String tag) {
        startProgress();

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(tag, response.toString());

                try {
                    parseResponse(response);
                } catch (Exception e) {
                    showToast("JSON parse error");
                }

                stopProgress();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                // For AuthFailure, you can re login with user credentials.
                // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                // In this case you can check how client is forming the api and debug accordingly.
                // For ServerError 5xx, you can do retry or handle accordingly.
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                }

                stopProgress();
                showToast(error.getMessage());
            }
        });

        //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppSingleton.getInstance().addToRequestQueue(tag, jsonObjRequest);
    }

    protected abstract void parseResponse(JSONObject response) throws JSONException;

}

