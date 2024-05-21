package viewer;

import controller.UserController;
import lombok.Getter;
import lombok.Setter;
import model.UserDTO;
import util.ScannerUtil;

import java.util.Scanner;

public class UserViewer {

    @Setter
    private Scanner scanner;
    @Setter
    private UserController userController;
    @Setter
    private MovieViewer movieViewer;
    @Setter
    private CinemaViewer cinemaViewer;
    @Setter
    private AdminViewer adminViewer;
    @Getter
    private UserDTO logIn;

    // 로그인 화면 메인
    public void showIndex() {
        String message = "1. 로그인 2. 회원가입 3. 프로그램 종료";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                auth();
                if (logIn != null) {
                    // 회원 메뉴 실행
                    movieViewer.setLogIn(logIn);
                    showMenu();
                }
            } else if (userChoice == 2) {
                register();
            } else if (userChoice == 3) {
                System.out.println("사용해주셔서 감사합니다.");
                break;
            }
        }
    }

    // 로그인
    private void auth() {
        String message;
        message = "아이디를 입력해주세요.";
        String id = ScannerUtil.nextLine(scanner, message);

        message = "비밀번호를 입력해주세요.";
        String password = ScannerUtil.nextLine(scanner, message);

        logIn = userController.auth(id, password);

        if (logIn == null) {
            System.out.println("잘못 입력하셨습니다. 로그인 정보를 다시 확인해주세요.");
        }
    }

    // 회원가입
    private void register() {
        String message = "사용하실 아이디를 입력해주세요.";
        String id = ScannerUtil.nextLine(scanner, message);
        if (userController.validateId(id)) {
            // 중복되지 않은 아이디이므로 나머지 정보를 입력 받는다.
            message = "사용하실 비밀번호를 입력해주세요.";
            String password = ScannerUtil.nextLine(scanner, message);

            message = "사용하실 닉네임을 입력해주세요.";
            String nickname = ScannerUtil.nextLine(scanner, message);

            UserDTO temp = new UserDTO();
            temp.setId(id);
            temp.setPassword(password);
            temp.setNickName(nickname);
            temp.setGrade(1);

            userController.insert(temp);
        } else {
            System.out.println("중복된 아이디는 사용하실 수 없습니다.");
        }
    }

    // 로그인 성공 화면
    private void showMenu() {
        if(logIn.getGrade() == 3) {
            // 로그인 성공 관리자 화면
            String message = "1. 영화 2. 극장 3. 관리자 화면 4. 로그아웃";
            while (logIn != null) {
                int userChoice = ScannerUtil.nextInt(scanner, message);
                if (userChoice == 1) {
                    movieViewer.showMovie();
                } else if (userChoice == 2) {
                    cinemaViewer.showCinema();
                } else if (userChoice == 3) {
                    adminViewer.showAdminMenu();
                } else if (userChoice == 4) {
                    logIn = null;
                    System.out.println("성공적으로 로그아웃 되었습니다.");
                }
            }
        } else {
            // 로그인 성공 일반 유저 화면
            String message = "1. 영화 보기 2. 극장 보기 3. 회원 정보 수정 4. 로그아웃";
            while (logIn != null) {
                int userChoice = ScannerUtil.nextInt(scanner, message);
                if (userChoice == 1) {
                    movieViewer.showMovie();
                } else if (userChoice == 2) {
                    cinemaViewer.showCinema();
                } else if (userChoice == 3) {
                    printInfo();
                } else if (userChoice == 4) {
                    logIn = null;
                    System.out.println("성공적으로 로그아웃 되었습니다.");
                }
            }
        }
    }

    // 회원 정보 출력
    private void printInfo() {
        System.out.println("========================");
        System.out.println(logIn.getId() + " 회원님의 정보");
        System.out.println("------------------------");
        System.out.println("회원 번호: " + logIn.getUid());
        System.out.println("회원 아이디: " + logIn.getId());
        System.out.println("회원 닉네임: " + logIn.getNickName());
        System.out.println("회원 등급: " + (logIn.getGrade() == 1 ? "일반 관람객" : "전문 평론가"));
        System.out.println("========================");

        String message = "1. 회원 정보 수정 2. 회원 탈퇴 3. 뒤로 가기";
        int userChoice = ScannerUtil.nextInt(scanner, message);

        if (userChoice == 1) {
            update();
        } else if (userChoice == 2) {
            delete();
        }

    }

    // 회원 정보 수정
    private void update() {
        String message = "새로운 닉네임을 입력해주세요.";
        String newNickname = ScannerUtil.nextLine(scanner, message);

        message = "새로운 비밀번호를 입력해주세요.";
        String newPassword = ScannerUtil.nextLine(scanner, message);

        message = "기존 비밀번호를 입력해주세요.";
        String oldPassword = ScannerUtil.nextLine(scanner, message);
        if (oldPassword.equals(logIn.getPassword())) {
            logIn.setNickName(newNickname);
            logIn.setPassword(newPassword);

            userController.update(logIn);
        } else {
            System.out.println("기존 비밀번호와 틀려서 회원 정보 수정을 할 수 없습니다.");
        }
    }

    // 회원 정보 삭제
    private void delete() {
        String message = "정말로 탈퇴하시겠습니까? Y/N";
        String answer = ScannerUtil.nextLine(scanner, message);
        if (answer.equalsIgnoreCase("Y")) {
            message = "비밀번호를 입력해주세요.";
            String password = ScannerUtil.nextLine(scanner, message);

            if (password.equals(logIn.getPassword())) {
                userController.delete(logIn.getUid());
                logIn = null;
            } else {
                System.out.println("비밀번호가 정확하지 않습니다.");
            }
        }
    }

}
