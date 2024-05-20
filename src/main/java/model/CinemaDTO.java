package model;

import lombok.Data;

@Data
public class CinemaDTO {

    private int id;
    private String cinemaName;
    private String cinemaAddress;
    private int cinemaPhoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CinemaDTO cinemaDTO)) return false;
        return id == cinemaDTO.id;
    }

}
