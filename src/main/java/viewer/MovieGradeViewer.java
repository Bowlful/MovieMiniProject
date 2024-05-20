package viewer;

import controller.MovieController;
import controller.MovieGradeController;
import controller.UserController;
import lombok.Setter;
import model.MovieDTO;
import model.MovieGradeDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class MovieGradeViewer {
    @Setter
    private Scanner scanner;
    @Setter
    private UserController userController;
    @Setter
    private MovieController movieController;
    @Setter
    private MovieGradeController movieGradeController;

    public void showMovieGradeMenu() {
        String message = "1. 영화별 전체 평점 2. 영화별 평론가 평점 3. 영화별 일반 관람객 평점 4. 뒤로가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);

            if(userChoice == 1) {
                showTotalGrade();
            } else if (userChoice == 2) {
                showProGrade();
            } else if (userChoice == 3) {
                showNormalGrade();
            } else if (userChoice == 4) {
                break;
            }
        }
    }

    private void showTotalGrade() {
        ArrayList<MovieDTO> movieList = movieController.selectAll();
        if(movieList.isEmpty()) {
            System.out.println("등록된 영화가 없습니다.");
        } else {
            for(MovieDTO movie : movieList) {
                System.out.println("영화 제목 : " + movie.getTitle());
                System.out.printf("영화 평점 : %.1f\n", movieGradeController.getMovieTotalGradeAverage(movie.getId()));
            }
        }
    }

    private void showProGrade() {
        ArrayList<MovieDTO> movieList = movieController.selectAll();
        if(movieList.isEmpty()) {
            System.out.println("등록된 영화가 없습니다.");
        } else {
            for(MovieDTO movie : movieList) {
                System.out.println("영화 제목 : " + movie.getTitle());
                System.out.printf("영화 평점 : %.1f\n", movieGradeController.getMovieProGradeAverage(movie.getId()));
            }
        }
    }

    private void showNormalGrade() {
        ArrayList<MovieDTO> movieList = movieController.selectAll();
        if(movieList.isEmpty()) {
            System.out.println("등록된 영화가 없습니다.");
        } else {
            for(MovieDTO movie : movieList) {
                System.out.println("영화 제목 : " + movie.getTitle());
                System.out.printf("영화 평점 : %.1f\n", movieGradeController.getMovieNormalGradeAverage(movie.getId()));
            }
        }
    }

    public void insetMovieGrade(int movieId, int writerId, int writerGrade) {
        MovieGradeDTO movieGradeDTO = new MovieGradeDTO();
        movieGradeDTO.setMovieId(movieId);
        movieGradeDTO.setWriterId(writerId);

        String message = "평점은 몇입니까?(1~5)";
        movieGradeDTO.setGrade(ScannerUtil.nextInt(scanner, message, 1, 5));

        if(writerGrade == 2) {
            message = "영화에 대한 평론을 해주세요.";
            movieGradeDTO.setReview(ScannerUtil.nextLine(scanner, message));
        }

        movieGradeController.insert(movieGradeDTO);
    }
}
