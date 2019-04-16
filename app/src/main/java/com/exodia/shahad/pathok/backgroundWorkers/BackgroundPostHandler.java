package com.exodia.shahad.pathok.backgroundWorkers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundPostHandler extends AsyncTask<String, Void, Void> {
    private Context context;
    private String userId, bookId, postData, reference;

    public BackgroundPostHandler(Context context, String reference, String userId, String bookId, String postData) {
        this.context = context;
        this.userId = userId;
        this.bookId = bookId;
        this.postData = postData;
        this.reference = reference;
    }

    public BackgroundPostHandler(Context context, String reference, String userId, String postData) {
        this.context = context;
        this.userId = userId;
        this.postData = postData;
        this.reference = reference;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String page_url = "https://db.pathok.xyz/post_handler.php";

        try {
            URL url = new URL(page_url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            String post_to_page;

            if (reference.equals("creative_writing")) {
                bookId = "";
            }

            post_to_page = URLEncoder.encode("reference", String.valueOf(StandardCharsets.UTF_8)) + "=" + URLEncoder.encode(reference, String.valueOf(StandardCharsets.UTF_8))
                    + "&" + URLEncoder.encode("user_id", String.valueOf(StandardCharsets.UTF_8)) + "=" + URLEncoder.encode(userId, String.valueOf(StandardCharsets.UTF_8))
                    + "&" + URLEncoder.encode("book_id", String.valueOf(StandardCharsets.UTF_8)) + "=" + URLEncoder.encode(bookId, String.valueOf(StandardCharsets.UTF_8))
                    + "&" + URLEncoder.encode("post_data", String.valueOf(StandardCharsets.UTF_8)) + "=" + URLEncoder.encode(postData, String.valueOf(StandardCharsets.UTF_8));

            bufferedWriter.write(post_to_page);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

//            Log.e("post_result", String.valueOf(result));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
