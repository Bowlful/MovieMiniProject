package model;

import lombok.Data;


@Data
public class MovieGradeDTO {

    private int id;
    private int writerId;
    private int movieId;
    private int grade;
    private String review;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieGradeDTO that)) return false;
        return id == that.id;
    }

}
