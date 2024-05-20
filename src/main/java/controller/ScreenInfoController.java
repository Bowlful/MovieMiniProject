package controller;

import model.ScreenInfoDTO;
import model.UserDTO;

import java.util.ArrayList;

public class ScreenInfoController {
    private ArrayList<ScreenInfoDTO> list;
    private int nextId;

    public ScreenInfoController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public ArrayList<ScreenInfoDTO> selectAll() {
        return list;
    }

    public ScreenInfoDTO selectOne(int id) {
        ScreenInfoDTO temp = new ScreenInfoDTO();
        temp.setId(id);
        if (list.contains(temp)) {
            return list.get(list.indexOf(temp));
        }
        return null;
    }

    public void insert(ScreenInfoDTO screenInfo) {
        screenInfo.setId(nextId++);
        list.add(screenInfo);
    }

    public void update(ScreenInfoDTO screenInfo) {
        list.set(list.indexOf(screenInfo), screenInfo);
    }

    public void delete(int id) {
        ScreenInfoDTO screenInfoDTO = new ScreenInfoDTO();
        screenInfoDTO.setId(id);
        list.remove(screenInfoDTO);
    }

    public boolean validateId(int id) {
        if(id == 0) return true;
        for (ScreenInfoDTO s : list) {
            if (s.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
