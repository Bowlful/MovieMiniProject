package viewer;

import controller.CinemaController;
import controller.MovieController;
import controller.ScreenInfoController;
import lombok.Setter;
import model.CinemaDTO;
import model.ScreenInfoDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

@Setter
public class CinemaViewer {

    private Scanner scanner;
    private CinemaController cinemaController;
    private MovieController movieController;
    private ScreenInfoController screenInfoController;

    // 극장 메인
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

    // 극장 목록
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
            cinemaDetail(userChoice);
        }
    }

    // 극장 상세
    private void cinemaDetail(int id) {
        CinemaDTO cinema = cinemaController.selectOne(id);
        printCinemaOne(cinema);

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

    // 극장 상세 출력
    public void printCinemaOne(CinemaDTO cinema) {
        System.out.println("=================================");
        System.out.println("극장 이름 : " + cinema.getCinemaName());
        System.out.println("극장 주소 : " + cinema.getCinemaAddress());
        System.out.println("극장 전화번호 : " + cinema.getCinemaPhoneNumber());
        System.out.println("=================================");
    }
}
