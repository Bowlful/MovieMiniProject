package model;

import lombok.Data;

@Data
public class UserDTO {

    private int uid;
    private String id;
    private String password;
    private String nickName;
    private int grade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        return uid == userDTO.uid;
    }

}
