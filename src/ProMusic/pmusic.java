package ProMusic;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

import Windows.DiyWindow;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


public class pmusic extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Media media = new Media("https://win-web-rg01-sycdn.kuwo.cn/6c54681396a6630a6fb99b42b979c24e/5e3ab6db/resource/n2/37/30/3252857194.mp3"); 
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer); 
		mediaPlayer.play();
		System.out.println(media.getMetadata());
//		File music = new File("初见.mp3");
//		String url = music.toURI().toString();
//		Media media = new Media(url);
//		MediaPlayer player = new MediaPlayer(media);
//		player.play();
//		BorderPane root = new BorderPane();
//		root.setPrefSize(400, 400);
//		Scene scene = new Scene(root);
//		primaryStage.setScene(scene);
//		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
