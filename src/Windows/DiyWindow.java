package Windows;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//该类为基本窗口，生成一个无修饰的可以拖动的普通窗口
public class DiyWindow extends Stage{
	Pane root = new Pane();
	public DiyWindow() {
		
	}
	public DiyWindow(Pane root) {
		this.root = root;
		//设置无边框
		initStyle(StageStyle.TRANSPARENT);
		//设置可拖动
		initDragSupport();
		//指定舞台大小
		sizeToScene();
		//设置图标
		getIcons().add(new Image("icon/花.png"));
	}
	////////////////////////////右上角的空间
	public HBox top_right_layout(Stage stage) {
		HBox image_hbox = new HBox();
		Image r1 = new Image("icon/最小化白色.png");
		ImageView rv1 = new ImageView(r1);
		rv1.setFitWidth(15);
		rv1.setFitHeight(15);
		rv1.setPickOnBounds(true);

		Image r2 = new Image("icon/全屏白色.png");
		ImageView rv2 = new ImageView(r2);
		rv2.setFitWidth(19);
		rv2.setFitHeight(19);
		rv2.setPickOnBounds(true);

		Image r3 = new Image("icon/关闭白色.png");
		ImageView rv3 = new ImageView(r3);
		rv3.setFitWidth(15);
		rv3.setFitHeight(15);
		rv3.setPickOnBounds(true);

		image_hbox.setSpacing(20);
		image_hbox.getChildren().addAll(rv1, rv2, rv3);
		image_hbox.setLayoutX(900);
		image_hbox.setLayoutY(10);

		// 显示监听
		rv1.hoverProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true) {
					rv1.setImage(new Image("icon/最小化绿色.png"));
				} else {
					rv1.setImage(new Image("icon/最小化白色.png"));
				}

			}
		});
		rv2.hoverProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true) {
					rv2.setImage(new Image("icon/全屏绿色.png"));
				} else {
					rv2.setImage(new Image("icon/全屏白色.png"));
				}

			}
		});
		rv3.hoverProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true) {
					rv3.setImage(new Image("icon/关闭绿色.png"));
				} else {
					rv3.setImage(new Image("icon/关闭白色.png"));
				}

			}
		});
		// 功能控制
		rv1.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				stage.setIconified(true);
				
			}
		});
		//最大化
		rv2.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				stage.setFullScreen(true);
				
			}
		});
		//关闭
		rv3.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				stage.close();
				
			}
		});
		return image_hbox;
	}
	////////////////////////////添加拖动支持
    private Stage stage; // 窗口
	private boolean dragging = false;
	private double windowX = 0, windowY = 0; // 初始窗口坐标
	private double windowW = 0, windowH = 0; // 初始窗口大小
	private double startX, startY;  // 鼠标按下位置的相对坐标
	private double startScreenX, startScreenY;  // 鼠标相对屏幕Screen坐标
	private double startSceneX,startSceneY;
	public void initDragSupport()
	{
		stage = this;//指的是这个类本身,一个Stage
		
		root.setOnMousePressed((MouseEvent e)->{
			
			// 记录鼠标点下时，窗口的坐标、大小			
			windowX = stage.getX(); 
			windowY = stage.getY(); 
			windowW = stage.getWidth();
			windowH = stage.getHeight();
			
			// 记录鼠标点下时，鼠标的位置	
			startX = e.getX();
			startY = e.getY();
			startScreenX = e.getScreenX();
			startScreenY = e.getScreenY();
			startSceneX = e.getSceneX();
			startSceneY = e.getSceneY();
			
		});
		//检测拖动
		root.setOnDragDetected((MouseEvent e)->{
			if(e.getButton() == MouseButton.PRIMARY) 
			{
				dragging = true;
			}			
		});
		//计算位移
		root.setOnMouseDragged((MouseEvent e)->{
			if(!dragging) return;
			
			// 计算鼠标的位移 , 起始点A点，当前位置 B点
			double dx = e.getScreenX() - startScreenX;
			double dy = e.getScreenY() - startScreenY;
			if(startSceneY > 506 || startSceneY<494) {
				stage.setX ( windowX + dx);
				stage.setY ( windowY + dy);
			}
		});
		
		root.setOnMouseReleased((MouseEvent e)->{
			dragging = false;
		});
	}

}
