package main;

import controller.*;
import viewer.*;

import java.util.Scanner;

public class MovieMain {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UserController userController = new UserController();
        MovieController movieController = new MovieController();
        MovieGradeController movieGradeController = new MovieGradeController();
        CinemaController cinemaController = new CinemaController();
        ScreenInfoController screenInfoController = new ScreenInfoController();

        movieGradeController.setUserController(userController);

        UserViewer userViewer = new UserViewer();
        MovieViewer movieViewer = new MovieViewer();
        MovieGradeViewer movieGradeViewer = new MovieGradeViewer();
        CinemaViewer cinemaViewer = new CinemaViewer();
        AdminViewer adminViewer = new AdminViewer();

        userViewer.setScanner(scanner);
        userViewer.setUserController(userController);
        userViewer.setMovieViewer(movieViewer);
        userViewer.setAdminViewer(adminViewer);
        userViewer.setCinemaViewer(cinemaViewer);

        movieViewer.setScanner(scanner);
        movieViewer.setUserController(userController);
        movieViewer.setMovieController(movieController);
        movieViewer.setMovieGradeController(movieGradeController);
        movieViewer.setMovieGradeViewer(movieGradeViewer);
        movieViewer.setScreenInfoController(screenInfoController);
        movieViewer.setCinemaController(cinemaController);

        movieGradeViewer.setScanner(scanner);
        movieGradeViewer.setUserController(userController);
        movieGradeViewer.setMovieController(movieController);
        movieGradeViewer.setMovieGradeController(movieGradeController);

        cinemaViewer.setScanner(scanner);
        cinemaViewer.setCinemaController(cinemaController);
        cinemaViewer.setMovieController(movieController);
        cinemaViewer.setScreenInfoController(screenInfoController);


        adminViewer.setScanner(scanner);
        adminViewer.setUserController(userController);
        adminViewer.setMovieController(movieController);
        adminViewer.setCinemaController(cinemaController);
        adminViewer.setScreenInfoController(screenInfoController);



        userViewer.showIndex();

    }
}
