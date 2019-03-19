package com.exodia.shahad.pathok.BackgroundWorkers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundLoginManager extends AsyncTask<String, Void, String> {
    private Context context;

    private static final String FILE = "user_details";
    private static final String USER_ID = "userID";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PROFILE_IMAGE = "profileImage";


    public BackgroundLoginManager(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String name = strings[0];
        String email = strings[1];
        String image = strings[2];
        String login_page_url = "https://db.pathok.xyz/login_handler.php";

        try {
            URL url = new URL(login_page_url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            String post_to_page = URLEncoder.encode("name", String.valueOf(StandardCharsets.UTF_8)) + "=" + URLEncoder.encode(name, String.valueOf(StandardCharsets.UTF_8))
                    + "&" + URLEncoder.encode("email", String.valueOf(StandardCharsets.UTF_8)) + "=" + URLEncoder.encode(email, String.valueOf(StandardCharsets.UTF_8))
                    + "&" + URLEncoder.encode("image", String.valueOf(StandardCharsets.UTF_8)) + "=" + URLEncoder.encode(image, String.valueOf(StandardCharsets.UTF_8));

            bufferedWriter.write(post_to_page);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));

            StringBuilder result = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine()) != null){
                result.append(line);
                result.append("\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            Log.e("result", String.valueOf(result));

            return String.valueOf(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject  jsonObject;

        try {
            jsonObject = new JSONObject(s);

            String success = jsonObject.getString("success");

            if(success.equals("true")){
                int dbUserId = jsonObject.getInt("userid");
                String dbName = jsonObject.getString("name");
                String dbEmail = jsonObject.getString("email");
                String dbImage = jsonObject.getString("image");

                saveToCache(String.valueOf(dbUserId), dbName, dbEmail, dbImage);
            }else if(success.equals("false")){
                String message = jsonObject.getString("message");
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveToCache(String userId, String userName, String userEmail, String userImage){
        SharedPreferences userDetails = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetails.edit();

        editor.putString(USER_ID, userId);
        editor.putString(NAME, userName);
        editor.putString(EMAIL, userEmail);
        editor.putString(PROFILE_IMAGE, userImage);

        editor.apply();
    }
}
