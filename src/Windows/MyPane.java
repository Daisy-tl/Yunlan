package Windows;

import java.awt.Label;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ProMusic.MyLyric;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

//生成具有基本功能的面板类，同时添加了按键功能
public class MyPane extends Pane {
	// 控制进度条
	boolean mouse = false;
	MediaView view = new MediaView();
	Stage stage = new Stage();
	HBox[] hbox_list = new HBox[100];

	// 定制的进度条
	double a = 0;
	HBox first = new HBox();
	HBox second = new HBox();
	Image icon1 = new Image("icon/桃子.png");
	ImageView iconSlider = new ImageView(icon1);

	// 播放控制的控件
	ImageView rv1;
	ImageView rv2;
	
	//存歌词
	String path_lyric;
	HashMap<Double, MyLyric> list = new HashMap<Double, MyLyric>();
    int num = 0;
	// 初始化一个Pane
	public MyPane(Stage stage) {
		// 设置定制Slider的图标
		setIconSlider();
		this.stage = stage;
		this.setPrefSize(1000, 600);
		
		// 设置频谱
		for (int i = 0; i < 100; i++) {
			hbox_list[i] = gen_musicScore(10 * i, 600, 0, 0);
			this.getChildren().add(hbox_list[i]);
		}
		// 设置颜色和圆角#333
		this.setBackground(new Background(new BackgroundFill(Color.valueOf("#888"), new CornerRadii(15), null)));
		this.getChildren().add(view);
		this.getChildren().add(controlBar());
	
		// 定制Slider
		this.getChildren().add(MySlider(first));
		this.getChildren().add(MySlider(second));
		this.getChildren().add(iconSlider);
		
		iconSlider.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mouse = true;

			}

		});
		iconSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				double position = (iconSlider.getLayoutX() - 50) / first.getWidth();
				double time = position * view.getMediaPlayer().getTotalDuration().toSeconds();
				view.getMediaPlayer().seek(Duration.seconds(time));
				mouse = false;
			}

		});
	}

	// 控制滑动条的图标
	public void setIconSlider() {
		iconSlider.setFitWidth(25);
		iconSlider.setFitHeight(25);
		iconSlider.setPickOnBounds(true);
		iconSlider.setLayoutX(43);
		iconSlider.setLayoutY(490);
		iconSlider.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				a = event.getX();
			}

		});
		iconSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				iconSlider.setLayoutX(event.getSceneX() - a);
				second.setBackground(
						new Background(new BackgroundFill(Color.valueOf("#F0506D"), new CornerRadii(15), null)));
				second.setPrefWidth(event.getSceneX() - 45);
				if (event.getSceneX() < 50) {
					iconSlider.setLayoutX(45);
				}
				if (event.getSceneX() > 950) {
					iconSlider.setLayoutX(942);
					second.setBackground(
							new Background(new BackgroundFill(Color.valueOf("#F0506D"), new CornerRadii(15), null)));
					second.setPrefWidth(first.getWidth());
				}

			}

		});
	}

	// 设置自我定制的Slider
	public HBox MySlider(HBox hbox) {
		hbox.setPrefSize(900, 6);
		hbox.setBackground(new Background(new BackgroundFill(Color.valueOf("#FEFAFA"), new CornerRadii(15), null)));
		hbox.setLayoutX(50);
		hbox.setLayoutY(500);
		return hbox;
	}

	// 设置控制键
	public HBox controlBar() {
		HBox image_hbox = new HBox();
		Image r1 = new Image("icon/播放.png");
		rv1 = changeIcon(r1);

		Image r2 = new Image("icon/目录.png");
		rv2 = changeIcon(r2);
		//
		// Image r3 = new Image("icon/关闭白色.png");
		// ImageView rv3 = new ImageView(r3);
		// rv3.setFitWidth(15);
		// rv3.setFitHeight(15);
		// rv3.setPickOnBounds(true);

		rv1.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				playMusic();
			}

		});
		rv2.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				doOpen(new Stage());
			}

		});
		image_hbox.setSpacing(20);
		image_hbox.getChildren().addAll(rv2, rv1);
		image_hbox.setLayoutX(480);
		image_hbox.setLayoutY(550);
		image_hbox.setAlignment(Pos.CENTER);
		return image_hbox;
	}

	// 生成频谱
	public HBox gen_musicScore(double x, double y, double width, double height) {
		// 设置一个柱子
		HBox h = new HBox();
		h.setPrefSize(width, height);
		h.setLayoutX(x);
		h.setLayoutY(y);

		Rotate rotate = new Rotate(-180, 0, 0);
		h.getTransforms().add(rotate);
		h.setStyle("-fx-background-color: linear-gradient(to bottom, #FFB5C5 0%, #FFB5C5	100%)");
		h.setOpacity(0.5);
		return h;

	}

	// 打开文件和监听播放动作
	public void doOpen(Stage stage) {
		if (view.getMediaPlayer() != null) {
			System.out.println("原来有歌曲在播放");
			view.getMediaPlayer().stop();
			for (MyLyric v : list.values()) {
				 v.getText().setVisible(false);
				 }
			list.clear();
		}
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("打开文件");
		File select = fileChooser.showOpenDialog(new Stage());// stage
		String path = select.getPath();
		path_lyric = path.replace("mp3", "lrc");
        
		//初始化歌词文件
		lyric(path_lyric);
		
		//加载歌词组件
        for (MyLyric value : list.values()) {
        	this.getChildren().add(value.getText());
//        	System.out.println(value.getSign()+value.getText().getText());
		}
		
		File file = new File(path);
		String url = file.toURI().toString();
		Media media = new Media(url);
		MediaPlayer play = new MediaPlayer(media);
		// 绑定到view上
		view.setMediaPlayer(play);
		// 自动播放
		view.getMediaPlayer().setAutoPlay(true);
//		System.out.println("这个歌曲的信息为"+media.getMetadata());
		// 写一个滑动条和歌词监听
		play.currentTimeProperty().addListener(new ChangeListener<Duration>() {

			@Override
			public void changed(ObservableValue<? extends Duration> arg0, Duration oldValue, Duration newValue) {
				//滑动条部分
				if (mouse == false) {
					double position = newValue.toSeconds() / view.getMediaPlayer().getTotalDuration().toSeconds();
					second.setBackground(new Background(new BackgroundFill(Color.valueOf("#F0506D"), new CornerRadii(15), null)));
					second.setPrefWidth(first.getWidth() * position);
					iconSlider.setLayoutX(43 + position * first.getWidth());
				}
				 DecimalFormat df =new DecimalFormat("#0.0");
				 double value = Double.valueOf(df.format(newValue.toSeconds()));
				 
				 if(list.containsKey(value)) {
					 list.get(value).getText().setVisible(true);
					 for (MyLyric v : list.values()) {
					        if(v.getText().isVisible()) {
					        	v.getText().setLayoutY(v.getText().getLayoutY()-20);
//					        	 list.get(value).getText().setVisible(true);
					        }
						 }
				 }
			}

		});
		// 写一个音乐频谱监听
		play.setAudioSpectrumListener(new AudioSpectrumListener() {
			// 下列参数依次代表当前音乐的时间，间隔的时间,震级
			@Override
			public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] arg3) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						for (int i = 0; i < 100; i++) {
							hbox_list[i].setPrefSize(8, Math.abs(-2 * magnitudes[i] - 120) * 2);
						}

					}
				});
			}

		});
		// 写一个音乐图标的监听
		play.statusProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				if (newValue == Status.PLAYING) {
					rv1.setImage(new Image("icon/暂停.png"));
				}
				if (newValue == Status.PAUSED) {
					rv1.setImage(new Image("icon/播放.png"));
				}

			}

		});
     

	}

	// 控制音乐播放
	public void playMusic() {
		MediaPlayer tempPlayer = view.getMediaPlayer();
		if (tempPlayer.getStatus() == Status.PLAYING) {
			tempPlayer.pause();
		} else {
			tempPlayer.play();
		}
	}

	// 控制图标的变换
	public ImageView changeIcon(Image image) {
		ImageView imageview = new ImageView(image);
		imageview.setFitWidth(25);
		imageview.setFitHeight(25);
		imageview.setPickOnBounds(true);
		return imageview;
	}
	
	//处理歌词文件
	public void lyric(String path) {
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "gbk"));
			String line = null;
			int flag = 0;
			while ((line = br.readLine()) != null) {
				flag++;
				if (flag > 5) {
					Pattern p = Pattern.compile("\\[(.*?)\\]");
					Matcher m = p.matcher(line);
					while (m.find()) {
						String s = String.valueOf(m.group(1));// m.group(1)不包括这两个字符
						 double result = Double.valueOf(s.substring(0, 2)) * 60 + Double.valueOf(s.substring(3, 8));
						 String mytext = line.substring(10,line.length());
						 //保证精确
						 DecimalFormat df =new DecimalFormat("#0.0");
						 double newResult = Double.valueOf(df.format(result));
						 MyLyric mylyric = new MyLyric(newResult,new Text(mytext));
						 mylyric.setSign(num);
						 list.put(newResult,mylyric);
					}
				}
				else {
					MyLyric mylyric = new MyLyric(0,new Text(""));
					mylyric.setSign(num);
					list.put(0.0, mylyric);
				}
				num++;
			}
			br.close();
			fis.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}


}
