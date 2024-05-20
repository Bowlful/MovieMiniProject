package controller;

import model.UserDTO;

import java.util.ArrayList;

public class UserController {

    private ArrayList<UserDTO> list;
    private int nextId;

    public UserController() {
        list = new ArrayList<>();
        nextId = 1;
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(-999);
        userDTO.setId("admin");
        userDTO.setPassword("admin");
        userDTO.setNickName("admin");
        userDTO.setGrade(3);
        list.add(userDTO);
    }

    public void insert(UserDTO userDTO) {
        userDTO.setUid(nextId++);
        list.add(userDTO);
    }

    public boolean validateId(String id) {
        for (UserDTO u : list) {
            if (u.getId().equalsIgnoreCase(id)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateUid(int uid) {
        if (uid == 0) return true;
        for (UserDTO u : list) {
            if (u.getUid() == uid) {
                return true;
            }
        }
        return false;
    }

    public UserDTO selectOne(int id) {
        UserDTO temp = new UserDTO();
        temp.setUid(id);
        if (list.contains(temp)) {
            return list.get(list.indexOf(temp));
        }

        return null;
    }

    public String getUserNickNameById(int id) {
        UserDTO user = selectOne(id);
        return user.getNickName();
    }

    public int getGradeByUid(int uid) {
        UserDTO user = selectOne(uid);
        return user.getGrade();
    }


    public void update(UserDTO userDTO) {
        list.set(list.indexOf(userDTO), userDTO);
    }

    public void delete(int id) {
        UserDTO temp = new UserDTO();
        temp.setUid(id);

        list.remove(temp);
    }

    public UserDTO auth(String id, String password) {
        for (UserDTO u : list) {
            if (id.equalsIgnoreCase(u.getId())) {
                if (password.equals(u.getPassword())) {
                    return u;
                }
            }
        }
        return null;
    }

    public int getListSize() {
        return list.size();
    }

    public String selectNicknameById(int id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(id);

        return list.get(list.indexOf(userDTO)).getNickName();
    }
}
