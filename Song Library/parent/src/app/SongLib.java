/*
 * @author Alan Wang netid: aw795
 */


package src.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File; 					
// import java.io.FileNotFoundException;  
// import java.util.Scanner;

import src.view.OverhaulController;

public class SongLib extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		// create FXML loader
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/src/view/LayoutOverhaul.fxml"));

		// load fmxl, root layout manager in fxml file is AnchorPane
		AnchorPane root = (AnchorPane)loader.load();

		// get the controller (Do NOT create a new Controller()!!)
		// instead, get it through the loader

		OverhaulController oc = loader.getController();
		oc.start(primaryStage);


		// set scene to root
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Better Song Library");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {

		File songFile = new File("./data/songs.txt");
		boolean isRealFile = songFile.exists();
		if (isRealFile) {
			launch(args);
		} else { 
			System.out.println("Please make songlist file");
		}


	}

}
