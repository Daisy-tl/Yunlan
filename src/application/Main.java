package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Windows.DiyWindow;
import Windows.MyPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	DiyWindow mywindow;
	MyPane mypane;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			 //将定制的pane和stage连接起来
			 mypane = new MyPane(mywindow);
			 mywindow = new DiyWindow(mypane);
			 //添加右上角的控制键
			 mypane.getChildren().add(mywindow.top_right_layout(mywindow));
			 Scene scene = new Scene(mypane);
			 scene.setFill(Color.TRANSPARENT);
			 mywindow.setScene(scene);
			 mywindow.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

//	public void lyric() {
//		
//		HashMap map = new HashMap();
//		try {
//			File file = new File("C:\\Users\\DEL\\Music\\红昭愿.lrc");
//			FileInputStream fis = new FileInputStream(file);
//			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "gbk"));
//			String line = null;
//			int flag = 0;
//			while ((line = br.readLine()) != null) {
//				flag++;
//				if (flag > 5) {
//					Pattern p = Pattern.compile("\\[(.*?)\\]");
//					Matcher m = p.matcher(line);
//					while (m.find()) {
//						String s = String.valueOf(m.group(1));// m.group(1)不包括这两个字符
//						double result = Double.valueOf(s.substring(0, 2)) * 60 + Double.valueOf(s.substring(3, 8));
//					    map.put(s, result);
//					}
//				}
//			}
//			br.close();
//			fis.close();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//
//	}
}
