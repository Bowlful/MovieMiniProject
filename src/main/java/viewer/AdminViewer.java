package viewer;

import controller.CinemaController;
import controller.MovieController;
import controller.ScreenInfoController;
import controller.UserController;
import lombok.Setter;
import model.CinemaDTO;
import model.MovieDTO;
import model.ScreenInfoDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminViewer {

    @Setter
    private Scanner scanner;
    @Setter
    private UserController userController;
    @Setter
    private MovieController movieController;
    @Setter
    private CinemaController cinemaController;
    @Setter
    private ScreenInfoController screenInfoController;

    public void showAdminMenu(){
        String message = "1. 영화 등록 및 수정 2. 극장 등록 및 수정 3. 상영 정보 등록 및 수정 4. 회원 등급 수정 5. 뒤로 가기";
        while (true) {
            int adminChoice = ScannerUtil.nextInt(scanner, message);

            if(adminChoice == 1) {
                movieEdit();
            } else if (adminChoice == 2) {
                cinemaEdit();
            } else if (adminChoice == 3) {
                screenInfoEdit();
            } else if (adminChoice == 4) {
                updateUserGrade();
            } else if (adminChoice == 5) {
                System.out.println("메인 화면으로 돌아갑니다.");
                break;
            }
        }
    }

    private void screenInfoEdit() {
        String message = "1. 상영 정보 등록 2. 상영 정보 수정 3. 뒤로가기";
        int adminChoice = ScannerUtil.nextInt(scanner, message);

        if(adminChoice == 1) {
            screenInfoInsert();
        } else if(adminChoice == 2) {
            printScreenInfoList();
        }
    }

    private void printScreenInfoList() {
        ArrayList<ScreenInfoDTO> screenInfoList = screenInfoController.selectAll();
        for(ScreenInfoDTO screenInfo : screenInfoList) {
            System.out.printf("%d. %s - %s\n", screenInfo.getId(), movieController.getMovieNameById(screenInfo.getMovieId()), screenInfo.getScreenTime());
        }

        String message = "상세보기할 상영 정보를 선택하거나 0을눌러 뒤로가세요";
        int adminChoice = ScannerUtil.nextInt(scanner, message);

        while (!screenInfoController.validateId(adminChoice)) {
            System.out.println("잘못 입력하셨습니다.");
            adminChoice = ScannerUtil.nextInt(scanner, message);
        }

        if(adminChoice != 0) {
            printScreenInfoOne(adminChoice);
        }
    }

    private void printScreenInfoOne(int id) {
        ScreenInfoDTO screenInfo = screenInfoController.selectOne(id);
        System.out.println("=================================");
        System.out.println("상영 영화 : " + movieController.getMovieNameById(screenInfo.getMovieId()));
        System.out.println("상영 극장 : " + cinemaController.getCinemaNameById(screenInfo.getCinemaId()));
        System.out.println("상영 시간 : " + screenInfo.getScreenTime());
        System.out.println("=================================");

        String message = "1. 수정 2. 삭제 3. 뒤로가기";
        int adminChoice = ScannerUtil.nextInt(scanner, message);

        if(adminChoice == 1) {
            screenInfoUpDate(screenInfo);
            printScreenInfoOne(id);
        } else if (adminChoice == 2) {
            screenInfoDelete(id);
        }
    }

    private void screenInfoDelete(int id) {
        String message = "정말로 삭제하시겠습니까? Y/N";
        String answer = ScannerUtil.nextLine(scanner, message);
        if (answer.equalsIgnoreCase("Y")) {
            screenInfoController.delete(id);
            System.out.println("상영정보가 삭제 되었습니다.");
        }
    }

    private void screenInfoUpDate(ScreenInfoDTO screenInfo) {
        String message;
        ArrayList<MovieDTO> movieList = movieController.selectAll();

        if(!movieList.isEmpty()) {
            for(MovieDTO movie : movieList) {
                System.out.printf("%d. %s\n", movie.getId(), movie.getTitle());
            }
            message = "상영 영화를 다시 선택해주세요";
            screenInfo.setMovieId(ScannerUtil.nextInt(scanner, message, 1, movieList.size()));
        } else {
            System.out.println("상영할 영화가 없습니다.");
            return;
        }

        ArrayList<CinemaDTO> cinemaList = cinemaController.selectAll();

        if(!cinemaList.isEmpty()) {
            for(CinemaDTO cinema : cinemaList) {
                System.out.printf("%d. %s\n", cinema.getId(), cinema.getCinemaName());
            }
            message = "상영할 극장을 다시 선택해주세요";
            screenInfo.setCinemaId(ScannerUtil.nextInt(scanner, message, 1, cinemaList.size()));
        } else {
            System.out.println("상영할 극장이 없습니다.");
            return;
        }

        message = "상영할 시간을 선택해주세요(ex. 1시, 2시, 3시...)";
        screenInfo.setScreenTime(ScannerUtil.nextLine(scanner, message));

        screenInfoController.update(screenInfo);
    }

    private void screenInfoInsert() {
        ScreenInfoDTO screenInfo = new ScreenInfoDTO();
        String message;
        ArrayList<MovieDTO> movieList = movieController.selectAll();

        if(!movieList.isEmpty()) {
            for(MovieDTO movie : movieList) {
                System.out.printf("%d. %s\n", movie.getId(), movie.getTitle());
            }
            message = "상영 영화를 선택해주세요";
            screenInfo.setMovieId(ScannerUtil.nextInt(scanner, message, 1, movieList.size()));
        } else {
            System.out.println("상영할 영화가 없습니다.");
            return;
        }

        ArrayList<CinemaDTO> cinemaList = cinemaController.selectAll();

        if(!cinemaList.isEmpty()) {
            for(CinemaDTO cinema : cinemaList) {
                System.out.printf("%d. %s\n", cinema.getId(), cinema.getCinemaName());
            }
            message = "상영할 극장을 선택해주세요";
            screenInfo.setCinemaId(ScannerUtil.nextInt(scanner, message, 1, cinemaList.size()));
        } else {
            System.out.println("상영할 극장이 없습니다.");
            return;
        }

        message = "상영할 시간을 선택해주세요(ex. 1시, 2시, 3시...)";
        screenInfo.setScreenTime(ScannerUtil.nextLine(scanner, message));

        screenInfoController.insert(screenInfo);

    }

    private void cinemaEdit() {
        String message = "1. 극장 등록 2. 극장 정보 수정 3. 뒤로가기";
        int adminChoice = ScannerUtil.nextInt(scanner, message);

        if(adminChoice == 1) {
            cinemaInsert();
        } else if(adminChoice == 2) {
            printCinemaList();
        }
    }

    private void cinemaInsert() {
        CinemaDTO cinema = new CinemaDTO();

        String message = "극장 이름을 입력하세요";
        cinema.setCinemaName(ScannerUtil.nextLine(scanner, message));

        message = "극장 위치를 입력하세요";
        cinema.setCinemaAddress(ScannerUtil.nextLine(scanner, message));

        message = "극장 전화번호를 입력하세요";
        cinema.setCinemaPhoneNumber(ScannerUtil.nextInt(scanner, message));

        cinemaController.insert(cinema);
    }

    private void printCinemaList() {
        ArrayList<CinemaDTO> cinemaList = cinemaController.selectAll();
        for(CinemaDTO cinema : cinemaList) {
            System.out.printf("%d. %s\n", cinema.getId(), cinema.getCinemaName());
        }

        String message = "상세보기할 극장 번호를 선택하거나 0번을 눌러 뒤로가세요";
        int adminChoice = ScannerUtil.nextInt(scanner, message);

        while (!cinemaController.validateId(adminChoice)) {
            System.out.println("잘못 입력하셨습니다.");
            adminChoice = ScannerUtil.nextInt(scanner, message);
        }

        if(adminChoice != 0) {
            printCinemaOne(adminChoice);
        }

    }

    private void printCinemaOne(int id) {
        CinemaDTO cinema = cinemaController.selectOne(id);
        System.out.println("=================================");
        System.out.println("극장 이름 : " + cinema.getCinemaName());
        System.out.println("극장 주소 : " + cinema.getCinemaAddress());
        System.out.println("극장 전화번호 : " + cinema.getCinemaPhoneNumber());
        System.out.println("=================================");

        String message = "1. 수정 2. 삭제 3. 뒤로가기";
        int adminChoice = ScannerUtil.nextInt(scanner, message);

        if(adminChoice == 1) {
            cinemaUpade(cinema);
            printCinemaOne(id);
        } else if (adminChoice == 2) {
            cinemaDelete(id);
        }
    }

    private void cinemaUpade(CinemaDTO cinema) {

        String message = "극장 이름을 다시 입력하세요";
        cinema.setCinemaName(ScannerUtil.nextLine(scanner, message));

        message = "극장 위치를 다시 입력하세요";
        cinema.setCinemaAddress(ScannerUtil.nextLine(scanner, message));

        message = "극장 전화번호를 다시 입력하세요";
        cinema.setCinemaPhoneNumber(ScannerUtil.nextInt(scanner, message));

        cinemaController.update(cinema);
    }

    private void cinemaDelete(int id) {
        String message = "정말로 삭제하시겠습니까? Y/N";
        String answer = ScannerUtil.nextLine(scanner, message);
        if (answer.equalsIgnoreCase("Y")) {
            cinemaController.delete(id);
            System.out.println("극장이 삭제 되었습니다.");
        }
    }

    private void movieEdit() {
        String message = "1. 영화 등록 2. 영화 정보 수정 3. 뒤로 가기";
        int adminChoice = ScannerUtil.nextInt(scanner, message);

        if(adminChoice == 1) {
            movieInsert();
        } else if(adminChoice == 2){
            printMovieList();
        }
    }

    private void movieInsert() {
        MovieDTO movieDTO = new MovieDTO();

        String message = "영화 제목을 입력해주세요";
        movieDTO.setTitle(ScannerUtil.nextLine(scanner, message));

        message = "영화 줄거리를 입력해주세요";
        movieDTO.setSummary(ScannerUtil.nextLine(scanner, message));

        message = "영화 등급을 입력해주세요(ex. 전체이용가, 7세, 15세, 19세)";
        movieDTO.setGrade(ScannerUtil.nextLine(scanner, message));

        movieController.insert(movieDTO);

    }

    private void printMovieList() {
        ArrayList<MovieDTO> movieList = movieController.selectAll();
        for(MovieDTO movie : movieList) {
            System.out.printf("%d. %s\n",movie.getId(), movie.getTitle());
        }

        String message = "상세보기할 영화 번호를 선택하거나 0번을 눌러 뒤로가세요";
        int adminChoice = ScannerUtil.nextInt(scanner, message);

        while (!movieController.validateId(adminChoice)) {
            System.out.println("잘못 입력하셨습니다.");
            adminChoice = ScannerUtil.nextInt(scanner, message);
        }

        if(adminChoice != 0) {
            printMovieOne(adminChoice);
        }

    }

    private void printMovieOne(int id) {
        MovieDTO movie = movieController.selectOne(id);
        System.out.println("=================================");
        System.out.println("영화 제목 : " + movie.getTitle());
        System.out.println("영화 등급 : " + movie.getGrade());
        System.out.println("---------------------------------");
        System.out.println("영화 줄거리");
        System.out.println(movie.getSummary());
        System.out.println("=================================");

        String message = "1. 수정 2. 삭제 3. 뒤로가기";
        int adminChoice = ScannerUtil.nextInt(scanner, message);

        if(adminChoice == 1) {
            movieUpdate(movie);
            printMovieOne(id);
        } else if (adminChoice == 2) {
            movieDelete(id);
        }
    }

    private void movieUpdate(MovieDTO movieDTO) {
        String message = "영화 제목을 다시 입력해주세요";
        movieDTO.setTitle(ScannerUtil.nextLine(scanner, message));

        message = "영화 줄거리를 다시 입력해주세요";
        movieDTO.setSummary(ScannerUtil.nextLine(scanner, message));

        message = "영화 등급을 다시 입력해주세요(ex. 전체이용가, 7세, 15세, 19세)";
        movieDTO.setGrade(ScannerUtil.nextLine(scanner, message));

        movieController.update(movieDTO);
    }

    private void movieDelete(int id) {
        String message = "정말로 삭제하시겠습니까? Y/N";
        String answer = ScannerUtil.nextLine(scanner, message);
        if (answer.equalsIgnoreCase("Y")) {
            movieController.delete(id);
            System.out.println("영화가 삭제 되었습니다.");
        }
    }

    private void updateUserGrade() {
        for(int i = 1; i < userController.getListSize(); i++) {
            UserDTO userDTO = userController.selectOne(i);
            System.out.printf("%d. %s - %s\n", userDTO.getUid(), userDTO.getNickName(), userDTO.getGrade() == 1 ? "일반 관람객" : "전문 평론가");
        }

        String message = "등급을 변경할 회원 번호를 선택하거나 뒤로가실려면 0번을 입력하세요";
        int adminChoice = ScannerUtil.nextInt(scanner, message);

        while (!userController.validateUid(adminChoice)) {
            System.out.println("잘못 입력하셨습니다.");
            adminChoice = ScannerUtil.nextInt(scanner, message);
        }
        if(adminChoice != 0) {
            System.out.println("변경할 등급을 선택해주세요");
            message = "1. 일반등급 2. 전문 평론가";
            int grade = ScannerUtil.nextInt(scanner, message);

            UserDTO userDTO = userController.selectOne(adminChoice);
            userDTO.setGrade(grade);

            userController.update(userDTO);
            updateUserGrade();
        }
    }
}
