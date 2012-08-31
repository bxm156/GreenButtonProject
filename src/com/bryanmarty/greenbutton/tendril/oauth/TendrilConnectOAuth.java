package com.bryanmarty.greenbutton.tendril.oauth;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

public class TendrilConnectOAuth {
	private static final String CLIENT_ID = "d46388d4cfb59a935c728dc570c451c1";
	private static final String CLIENT_SECRET = "5c42fb65c0f18c140338636c8aaf11e9";
	private static final String GRANT_TYPE = "password";
	private static final String EXTENDED_PERMISSIONS = "greenbutton";
	
	private static final String TOKEN_URL = "https://dev.tendrilinc.com/oauth/access_token";
	
	public static TendrilConnectOAuthResponse authenticate(Context context, String username, String password) {
		try {
			HttpClient httpClient = new TendrilHttpClient(context);
			HttpPost httpPost = new HttpPost(TOKEN_URL);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>(6);
			parameters.add(new BasicNameValuePair("grant_type",GRANT_TYPE));
			parameters.add(new BasicNameValuePair("username", "bxm156@case.edu"));
			parameters.add(new BasicNameValuePair("password", "bmartyua4917"));
			parameters.add(new BasicNameValuePair("scope",EXTENDED_PERMISSIONS));
			parameters.add(new BasicNameValuePair("client_id",CLIENT_ID));
			parameters.add(new BasicNameValuePair("client_secret",CLIENT_SECRET));
			httpPost.setEntity(new UrlEncodedFormEntity(parameters));
			httpPost.setHeader("Accept","application/json");
			
			
			HttpResponse response = httpClient.execute(httpPost);
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			if(statusCode != HttpStatus.SC_OK) {
				Log.i("Status Code","" + statusCode);
				TendrilConnectOAuthResponse re = new TendrilConnectOAuthResponse();
				re.access_token = "Not OK";
				//return re;
			}
			
			InputStream is = response.getEntity().getContent();
			Reader r = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(r);
			String line;
			while ( (line = br.readLine()) != null) {
				Log.i("Content", line);
			}
			
			Gson gson = new Gson();
			TendrilConnectOAuthResponse result = gson.fromJson(br, TendrilConnectOAuthResponse.class);
			return result;
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
