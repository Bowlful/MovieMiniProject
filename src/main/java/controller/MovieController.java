package controller;

import model.MovieDTO;

import java.util.ArrayList;

public class MovieController {

    private ArrayList<MovieDTO> list;
    private int nextId;

    public MovieController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public ArrayList<MovieDTO> selectAll() {
        return list;
    }

    public void insert(MovieDTO movieDTO) {
        movieDTO.setId(nextId++);
        list.add(movieDTO);
    }

    public boolean validateId(int id) {
        if (id == 0) return true;
        for (MovieDTO m : list) {
            if (m.getId() == id) {
                return true;
            }
        }
        return false;
    }


    public MovieDTO selectOne(int id) {
        MovieDTO temp = new MovieDTO();
        temp.setId(id);
        if (list.contains(temp)) {
            return list.get(list.indexOf(temp));
        }

        return null;
    }

    public void update(MovieDTO movie) {
        list.set(list.indexOf(movie), movie);
    }

    public void delete(int id) {
        MovieDTO temp = new MovieDTO();
        temp.setId(id);

        list.remove(temp);
    }

    public String getMovieNameById(int id) {
        MovieDTO movie = selectOne(id);
        return movie.getTitle();
    }

}
