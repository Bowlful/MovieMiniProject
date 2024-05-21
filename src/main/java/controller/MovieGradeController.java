package controller;

import lombok.Setter;
import model.MovieGradeDTO;

import java.util.ArrayList;

public class MovieGradeController {

    @Setter
    private UserController userController;

    private ArrayList<MovieGradeDTO> list;
    private int nextId;

    public MovieGradeController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public ArrayList<MovieGradeDTO> selectAll() {
        return list;
    }

    public ArrayList<MovieGradeDTO> selectByMovieId(int movieId) {
        ArrayList<MovieGradeDTO> temp = new ArrayList<>();
        for(MovieGradeDTO movieGrade : list) {
            if(movieGrade.getMovieId() == movieId) {
                temp.add(movieGrade);
            }
        }
        return temp;
    }

    // 영화 전체 평점 계산
    public double getMovieTotalGradeAverage(int movieId) {
        int sum = 0;
        int count = 0;
        for(MovieGradeDTO movieGrade : list) {
            if(movieGrade.getMovieId() == movieId) {
                sum += movieGrade.getGrade();
                count++;
            }
        }
        if(count == 0) {
            return 0;
        } else {
            return (double) sum / count;
        }
    }

    // 영화 평론가 평점 계산
    public double getMovieProGradeAverage(int movieId) {
        int sum = 0;
        int count = 0;
        for(MovieGradeDTO movieGrade : list) {
            if(movieGrade.getMovieId() == movieId && userController.getGradeByUid(movieGrade.getWriterId()) == 2) {
                sum += movieGrade.getGrade();
                count++;
            }
        }

        if(count == 0) {
            return 0;
        } else {
            return (double) sum / count;
        }
    }

    // 영화 일반 사용자 평점 계산
    public double getMovieNormalGradeAverage(int movieId) {
        int sum = 0;
        int count = 0;
        for(MovieGradeDTO movieGrade : list) {
            if(movieGrade.getMovieId() == movieId && userController.getGradeByUid(movieGrade.getWriterId()) == 1) {
                sum += movieGrade.getGrade();
                count++;
            }
        }

        if(count == 0) {
            return 0;
        } else {
            return (double) sum / count;
        }
    }

    public void insert(MovieGradeDTO movieGrade) {
        movieGrade.setId(nextId++);
        list.add(movieGrade);
    }

}
