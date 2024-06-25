package com.sifu.mymovie;

import com.sifu.mymovie.Model.Movie;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {
    @GET("movies")
    Call<List<Movie>> getMovies();
}
