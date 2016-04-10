package com.iak.intermediate.session1.app.api;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.radyalabs.async.AsyncHttpClient;
import com.radyalabs.async.AsyncHttpResponseHandler;
import com.radyalabs.async.RequestParams;

import org.apache.http.entity.StringEntity;

import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public abstract class BaseApi {
	public static final String BASE_URL = "http://quranicfood.com/hospitalfinder/api/";
	
	protected Context context;
	protected RequestParams params;
	protected static String baseApi = BASE_URL;
	protected String endpointApi;
	protected AjaxType ajaxType;
	protected String jsonRaw;
	protected static final int timeOut = 60000;
	protected AsyncHttpResponseHandler responseHandler;
	protected String defaultMessage;
	protected boolean useAuthBasic;
	protected String username;
	protected String password;
	
	protected enum AjaxType{
		POST,
		GET,
		DELETE,
		POST_RAW_JSON,
	}
	
	public BaseApi(Context context){
		this.context = context;
		params = new RequestParams();
		endpointApi = "";
		ajaxType = AjaxType.GET;
		defaultMessage = "";
	}

	public void setAuthBasic(String username, String password){
		this.useAuthBasic = true;
		this.username = username;
		this.password = password;
	}

	protected void executeAjax(String url){
		AsyncHttpClient ajax = new AsyncHttpClient();

		if (useAuthBasic){
			ajax.addHeader("Authorization",
					"Basic " + Base64.encodeToString(
							(username+":"+password).getBytes(), Base64.NO_WRAP));
		}

		Log.d("BaseApi", url);
		Log.d("BaseApi", params.toString());

		ajax.setTimeout(timeOut);

		if (ajaxType == AjaxType.POST){
			Log.d("BaseApi", "post");
			ajax.post(url, params, responseHandler);
		}else if (ajaxType == AjaxType.GET){
			Log.d("BaseApi", "get");
			ajax.get(url, params, responseHandler);
		}else if (ajaxType == AjaxType.DELETE){
			Log.d("BaseApi", "delete");
			ajax.delete(context, url, null,params, responseHandler);
		}else if (ajaxType == AjaxType.POST_RAW_JSON){
			Log.d("BaseApi", "POST_RAW_JSON");
			try {
				StringEntity entity = new StringEntity(jsonRaw);
				entity.setContentEncoding(String.valueOf(new BasicHeader(HTTP.CONTENT_TYPE, "application/json")));
				ajax.post(context,url,entity,"application/json", responseHandler);
			} catch(Exception e) {
				e.printStackTrace();
				if (responseHandler != null){
					responseHandler.onFailure(0, null, null, e);
				}
			}
		}
	}
	
	public void executeAjax(){
		executeAjax(baseApi + endpointApi);
	}
	
	abstract public void onFinishRequest(boolean success, String returnItem);
}
