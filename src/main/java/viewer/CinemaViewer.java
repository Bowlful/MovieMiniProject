package viewer;

import controller.CinemaController;
import controller.MovieController;
import controller.MovieGradeController;
import controller.ScreenInfoController;
import lombok.Setter;
import model.CinemaDTO;
import model.ScreenInfoDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class CinemaViewer {

    @Setter
    private Scanner scanner;
    @Setter
    private CinemaController cinemaController;
    @Setter
    private MovieController movieController;
    @Setter
    private ScreenInfoController screenInfoController;

    public void showCinema() {
        String message = "1. 극장 목록 보기 2. 뒤로가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);

            if(userChoice == 1) {
                printCinemaList();
            } else if (userChoice == 2) {
                break;
            }
        }
    }

    private void printCinemaList() {
        ArrayList<CinemaDTO> cinemaList = cinemaController.selectAll();
        for(CinemaDTO cinema : cinemaList) {
            System.out.printf("%d. %s\n", cinema.getId(), cinema.getCinemaName());
        }

        String message = "상세보기할 극장번호나 0을 눌러 뒤로가세요";
        int userChoice = ScannerUtil.nextInt(scanner, message);

        while (!cinemaController.validateId(userChoice)) {
            System.out.println("잘못 입력하셨습니다.");
            userChoice = ScannerUtil.nextInt(scanner, message);
        }

        if(userChoice != 0) {
            printCinemaOne(userChoice);
        }
    }

    private void printCinemaOne(int id) {
        CinemaDTO cinema = cinemaController.selectOne(id);
        System.out.println("=================================");
        System.out.println("극장 이름 : " + cinema.getCinemaName());
        System.out.println("극장 주소 : " + cinema.getCinemaAddress());
        System.out.println("극장 전화번호 : " + cinema.getCinemaPhoneNumber());
        System.out.println("=================================");

        ArrayList<ScreenInfoDTO> screenInfoList = screenInfoController.selectAll();
        if(!screenInfoList.isEmpty()) {
            for(ScreenInfoDTO screenInfo : screenInfoList) {
                if(screenInfo.getId() == id) {
                    System.out.printf("%d. %s - %s\n", screenInfo.getId(), movieController.getMovieNameById(screenInfo.getMovieId()), screenInfo.getScreenTime());
                    System.out.println("---------------------------------");
                }
            }
            System.out.println("=================================");
        } else {
            System.out.println("상영 정보가 없습니다.");
            System.out.println("=================================");
        }
    }
}
