package com.thomaskioko.jkuat_codelab.api;


import com.thomaskioko.jkuat_codelab.api.model.Movie;
import com.thomaskioko.jkuat_codelab.api.model.Reviews;
import com.thomaskioko.jkuat_codelab.api.model.Videos;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;

/**
 * Class to test all endpoints.
 *
 * @author Thomas Kioko
 */
public class MovieApiTest extends BaseTestCase {

    @Test
    public void getTopRatedMovies() throws IOException {

        Call<Movie> topRatedList = getTmdbApiClient().movieInterface().getTopRatedMovies();
        Response<Movie> movieResponse = topRatedList.execute();

        assertEquals(movieResponse.code(), 200);
        assertEquals(true, movieResponse.isSuccessful());

    }
}
