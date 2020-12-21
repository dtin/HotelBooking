/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.google;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 *
 * @author Tin
 */
public class GoogleUtils {

    //Login by Google
    public static final String GOOGLE_CLIENT_ID = "8002935127-odnd77p91df6ufoklmgbgs09if8b9lbv.apps.googleusercontent.com";
    public static final String GOOGLE_CLIENT_SECRET = "rNYpsLA9GxXYuYSqSYVsPwk2";
    public static final String GOOGLE_REDIRECT_URI = "http://localhost:8084/BookingHotel/google-login";
    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static final String GOOGLE_GRANT_TYPE = "authorization_code";
    //Google Recaptcha
    public static final String SITE_KEY_RECAPTCHA = "6LcqVAsaAAAAAHnv9HkbrsE-Hq0C-iR09kYlaKoA";
    public static final String SERVER_KEY_RECAPTCHA = "6LcqVAsaAAAAAGdWVmrEGc_UPQcDV5nouldKgmLV";
    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public static String getToken(final String code) throws IOException {
        String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form().add("client_id", GOOGLE_CLIENT_ID)
                                .add("client_secret", GOOGLE_CLIENT_SECRET)
                                .add("redirect_uri", GOOGLE_REDIRECT_URI).add("code", code)
                                .add("grant_type", GOOGLE_GRANT_TYPE).build()
                )
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static GooglePojo getUserInfo(String accessToken) throws ClientProtocolException, IOException {
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        GooglePojo googlePojo = new Gson().fromJson(response, GooglePojo.class);
        return googlePojo;
    }

    public static boolean verifyRecaptcha(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }

        String response = Request.Post(SITE_VERIFY_URL)
                .bodyForm(
                        Form.form().add("secret", SERVER_KEY_RECAPTCHA)
                                .add("response", gRecaptchaResponse)
                                .build()
                ).execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

        return Boolean.parseBoolean(jobj.get("success").toString());
    }
}
