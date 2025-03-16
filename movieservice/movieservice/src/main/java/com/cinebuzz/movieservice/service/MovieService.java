package com.cinebuzz.movieservice.service;

import com.cinebuzz.movieservice.dto.MovieDto;
import com.cinebuzz.movieservice.dto.TheatreDto;

import java.util.List;

public interface MovieService {
    MovieDto createMovie(MovieDto movieDto);
    MovieDto getMovieById(Long movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Long movieId, MovieDto movieDto);
    void deleteMovie(Long movieId);
    TheatreDto getTheatreDetails(Long theatreId);

}
