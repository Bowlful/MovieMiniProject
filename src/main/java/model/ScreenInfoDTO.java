package model;

import lombok.Data;

@Data
public class ScreenInfoDTO {

    private int id;
    private int movieId;
    private int cinemaId;
    private String screenTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScreenInfoDTO that)) return false;
        return id == that.id;
    }

}
