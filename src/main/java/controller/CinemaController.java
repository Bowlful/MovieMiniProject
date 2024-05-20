package controller;

import model.CinemaDTO;
import model.UserDTO;

import java.util.ArrayList;

public class CinemaController {
    private ArrayList<CinemaDTO> list;
    private int nextId;

    public CinemaController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public ArrayList<CinemaDTO> selectAll() {
        return list;
    }

    public CinemaDTO selectOne(int id) {
        CinemaDTO temp = new CinemaDTO();
        temp.setId(id);
        if (list.contains(temp)) {
            return list.get(list.indexOf(temp));
        }
        return null;
    }

    public void insert(CinemaDTO cinemaDTO) {
        cinemaDTO.setId(nextId++);
        list.add(cinemaDTO);
    }

    public void update(CinemaDTO cinema) {
        list.set(list.indexOf(cinema), cinema);
    }

    public void delete(int id) {
        CinemaDTO temp = new CinemaDTO();
        temp.setId(id);
        list.remove(temp);
    }

    public boolean validateId(int id) {
        if(id == 0) return true;
        for (CinemaDTO c : list) {
            if (c.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public String getCinemaNameById(int id) {
        CinemaDTO cinema = selectOne(id);
        return cinema.getCinemaName();
    }

}
