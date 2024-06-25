package com.sifu.mymovie;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sifu.mymovie.Model.Movie;

import java.lang.reflect.Type;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        String urlString = "https://freetestapi.com/api/v1/movies";
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());
                reader = new BufferedReader(inputStream);
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String jsonResponse = stringBuilder.toString();

                Gson gson = new Gson();
                Type movieListType = new TypeToken<List<Movie>>() {}.getType();
                List<Movie> movieList = gson.fromJson(jsonResponse, movieListType);

                return movieList;
            } else {
                System.err.println("Error response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        if (movies != null) {
            // Handle the list of movies here, for example, update the UI
        } else {
            System.err.println("Failed to fetch movies");
        }
    }
}

