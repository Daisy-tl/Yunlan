package ProMusic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MyLyric {
	
	double time;
	Text text;
	int sign;
	ArrayList<MyLyric> list = new ArrayList();
	public ArrayList<MyLyric> getList() {
		return list;
	}
	public void setList(ArrayList<MyLyric> list) {
		this.list = list;
	}
	public MyLyric() {
		
	}
	public MyLyric(double time,Text text) {
		this.time = time;
		this.text = text;
		this.text.setVisible(false);
		text.setLayoutX(550);
		text.setLayoutY(300);
		text.setFont(new Font(15));
		this.text.layoutYProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				if((double)newValue<150){
					text.setVisible(false);
					
				}
//				if((double)newValue==300) {
//					text.setFont(new Font(20));
//					
//				}
//				else {
//					text.setFont(new Font(15));
//				}
			}
			
		});
	}
	public double getTime() {
		return time;
	}
	public Text getText() {
		return text;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public void setText(Text text) {
		this.text = text;
	}
	
	public int getSign() {
		return sign;
	}
	public void setSign(int sign) {
		this.sign = sign;
	}
	public void generLyric(String path) {
		try {
			 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"gbk"));
			 String lineTxt = null;
			 int flag = 0;
	         while ((lineTxt = br.readLine()) != null){
	        	flag++;
	        	if(flag>5) {
	        		Pattern p = Pattern.compile("\\[(.*?)\\]");//正则表达式，取=和|之间的字符串，不包括=和|
		 	        Matcher m = p.matcher(lineTxt);
		 	        while(m.find()) {
		 	            String s = String.valueOf(m.group(1));//m.group(1)不包括这两个字符
		 	            double result = Double.valueOf(s.substring(0,2))*60+Double.valueOf(s.substring(3,5));
		 	            String mytext = lineTxt.substring(10,lineTxt.length());
		 	            list.add(new MyLyric(result,new Text(mytext)));
		 	        }
	        	}
	         }
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
