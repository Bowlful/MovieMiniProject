package viewer;

import controller.MovieController;
import controller.MovieGradeController;
import controller.UserController;
import lombok.Setter;
import model.MovieDTO;
import model.MovieGradeDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

@Setter
public class MovieGradeViewer {
    private Scanner scanner;
    private MovieController movieController;
    private MovieGradeController movieGradeController;

    // 영화 평점 메인
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

    // 전체 영화 평점
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

    // 일반 관객 영화 평점
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

    // 평론가 영화 평점
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

    // 평점 입력
    public void insetMovieGrade(int movieId, int writerId, int writerGrade) {
        MovieGradeDTO movieGrade = movieGradeController.selectOneByMovieIdAndWriterId(movieId, writerId);

        String message;
        if(movieGrade != null) {
            System.out.println("등록한 평점을 수정합니다.");
            message = "평점은 몇입니까?(1~5)";
            movieGrade.setGrade(ScannerUtil.nextInt(scanner, message, 1, 5));

            if(writerGrade == 2) {
                message = "영화에 대한 평론을 해주세요.";
                movieGrade.setReview(ScannerUtil.nextLine(scanner, message));
            }

            movieGradeController.update(movieGrade);
        } else {
            MovieGradeDTO newMovieGrade = new MovieGradeDTO();
            newMovieGrade.setMovieId(movieId);
            newMovieGrade.setWriterId(writerId);

            message = "평점은 몇입니까?(1~5)";
            newMovieGrade.setGrade(ScannerUtil.nextInt(scanner, message, 1, 5));

            if(writerGrade == 2) {
                message = "영화에 대한 평론을 해주세요.";
                newMovieGrade.setReview(ScannerUtil.nextLine(scanner, message));
            }

            movieGradeController.insert(newMovieGrade);
        }

    }
}
