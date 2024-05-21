package viewer;

import controller.*;
import lombok.Setter;
import model.MovieDTO;
import model.MovieGradeDTO;
import model.ScreenInfoDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

@Setter
public class MovieViewer {

    private Scanner scanner;
    private UserController userController;
    private MovieController movieController;
    private MovieGradeController movieGradeController;
    private MovieGradeViewer movieGradeViewer;
    private ScreenInfoController screenInfoController;
    private CinemaController cinemaController;
    private UserDTO logIn;

    // 영화 메인
    public void showMovie() {

        String message = "1. 영화 목록 보기 2. 영화 평점 보기 3. 뒤로가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if(userChoice == 1) {
                printMovieList();
            } else if (userChoice == 2) {
                movieGradeViewer.showMovieGradeMenu();
            } else if (userChoice == 3) {
                System.out.println("메인 화면으로 돌아갑니다.");
                break;
            }
        }
    }

    // 영화 목록 출력
    private void printMovieList() {

        ArrayList<MovieDTO> movieList = movieController.selectAll();
        if(movieList.isEmpty()) {
            System.out.println("영화가 없습니다.");
        } else {
            for(MovieDTO movie : movieList) {
                System.out.printf("%d. %s\n", movie.getId(), movie.getTitle());
            }

            String message = "상세 보기할 영화를 선택하거나 0번을 눌러 뒤로가세요.";
            int userChoice = ScannerUtil.nextInt(scanner, message);

            while (!movieController.validateId(userChoice)) {
                System.out.println("잘못 입력하셨습니다.");
                userChoice = ScannerUtil.nextInt(scanner, message);
            }

            if(userChoice != 0) {
                movieDetail(userChoice);
            }
        }

    }

    // 영화 상세 정보
    private void movieDetail(int id) {
        MovieDTO movie = movieController.selectOne(id);
        printMovieOne(movie);

        ArrayList<ScreenInfoDTO> screenInfoList = screenInfoController.selectAll();
        if(!screenInfoList.isEmpty()) {
            System.out.println("상영 시간 정보");
            for(ScreenInfoDTO screenInfo : screenInfoList) {
                if(screenInfo.getMovieId() == id) {
                    System.out.println("상영 극장 : " + cinemaController.getCinemaNameById(screenInfo.getCinemaId()));
                    System.out.println("상영 시간 : " + screenInfo.getScreenTime());
                    System.out.println("---------------------------------");
                }
            }
            System.out.println("=================================");
        }

        ArrayList<MovieGradeDTO> movieGradeList = movieGradeController.selectByMovieId(id);
        if(!movieGradeList.isEmpty()) {
            System.out.println("영화 리뷰");
            for( MovieGradeDTO movieGrade : movieGradeList) {

                int grade = userController.getGradeByUid(movieGrade.getWriterId());
                System.out.printf("%d. %s - %d\n",movieGrade.getId(), userController.getUserNickNameById(movieGrade.getWriterId()) , movieGrade.getGrade());

                if(grade == 2) {
                    System.out.println("평론 : " + movieGrade.getReview());
                }
                System.out.println("---------------------------------");
            }
            System.out.println("=================================");
        }

        String message = "1. 평점 남기기 2. 뒤로가기";
        int userChoice = ScannerUtil.nextInt(scanner, message);

        if(userChoice == 1) {
            movieGradeViewer.insetMovieGrade(id, logIn.getUid(), logIn.getGrade());
            movieDetail(id);
        } else {
            printMovieList();
        }
    }

    // 영화 상세 출력
    public void printMovieOne(MovieDTO movie) {
        System.out.println("=================================");
        System.out.println("영화 제목 : " + movie.getTitle());
        System.out.println("영화 등급 : " + movie.getGrade());
        System.out.println("---------------------------------");
        System.out.println("영화 줄거리");
        System.out.println(movie.getSummary());
        System.out.println("=================================");
    }

}
