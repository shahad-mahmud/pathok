package com.exodia.shahad.pathok.BackgroundWorkers;

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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundBookCategoryLoader extends AsyncTask<Void, Void, String> {
    private Context context;
    private int from, to;

    public BackgroundBookCategoryLoader(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(Void... params) {
        String page_url = "https://db.pathok.xyz/book_suggesion_loader.php";

        try {
            URL url = new URL(page_url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            String post_to_page = URLEncoder.encode("type", String.valueOf(StandardCharsets.UTF_8)) + "=" + URLEncoder.encode("category", String.valueOf(StandardCharsets.UTF_8));
//                    + "&" + URLEncoder.encode("from", String.valueOf(StandardCharsets.UTF_8)) + "=" + URLEncoder.encode(String.valueOf(from), String.valueOf(StandardCharsets.UTF_8))
//                    + "&" + URLEncoder.encode("to", String.valueOf(StandardCharsets.UTF_8)) + "=" + URLEncoder.encode(String.valueOf(to), String.valueOf(StandardCharsets.UTF_8));

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "result not found";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
