package Appli;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Base.Etudiant;
import Base.User;
import controleurs.LoginController;
import controleurs.MainController;

public class main extends Application {
    private static Stage primaryStage;
    private static MainController mainController;
    private static User currentUser;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/MainLayout.fxml"));
        BorderPane root = loader.load(); 
        mainController = loader.getController();
        mainController.setLogoutButtonVisible(false);
        mainController.chargerPage("Login.fxml");
        primaryStage.setScene(new Scene(root,300,275));
        primaryStage.setTitle("Application java");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        LoginController.gene();
        
        primaryStage.show();
        
    }
    public static MainController getMainController() {
        return mainController;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public static void setCurrentUser(User user) {
    	currentUser = user ;
    }
   public static User getCurrentUser() {
	   if(currentUser instanceof Etudiant) {
		   return (Etudiant)currentUser;
	   }
	return currentUser;
}
}