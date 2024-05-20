package model;

import lombok.Data;

@Data
public class MovieDTO {

    private int id;
    private String title;
    private String summary;
    private String grade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDTO movieDTO)) return false;
        return id == movieDTO.id;
    }

}
