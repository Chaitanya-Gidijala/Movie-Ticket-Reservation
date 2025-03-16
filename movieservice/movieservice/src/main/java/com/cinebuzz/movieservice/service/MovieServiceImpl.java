package com.cinebuzz.movieservice.service;

import com.cinebuzz.movieservice.client.TheatreFeignClient;
import com.cinebuzz.movieservice.dto.MovieDto;
import com.cinebuzz.movieservice.dto.TheatreDto;
import com.cinebuzz.movieservice.entity.Movie;
import com.cinebuzz.movieservice.exception.ResourceNotFoundException;
import com.cinebuzz.movieservice.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TheatreFeignClient theatreFeignClient;

    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        Movie movie = modelMapper.map(movieDto, Movie.class);
        Movie savedMovie = movieRepository.save(movie);
        return modelMapper.map(savedMovie, MovieDto.class);
    }

    @Override
    public MovieDto getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + movieId));
        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MovieDto updateMovie(Long movieId, MovieDto movieDto) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        movie.setMovieName(movieDto.getMovieName());
        movie.setGenre(movieDto.getGenre());
        movie.setDuration(movieDto.getDuration());
        movie.setTicketBasePrice(movieDto.getTicketBasePrice());

        Movie updatedMovie = movieRepository.save(movie);
        return modelMapper.map(updatedMovie, MovieDto.class);
    }

    @Override
    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        movieRepository.delete(movie);
    }

    @Override
    public TheatreDto getTheatreDetails(Long theatreId) {
        return theatreFeignClient.getTheatre(theatreId);
    }
}
