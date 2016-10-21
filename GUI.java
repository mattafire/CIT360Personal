package calling;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	Stage primaryStage = new Stage();
	Text title = new Text("Welcome");
	Text support = new Text("For Support Contact:\nMitchell Kelly at\nMattafireComputers@gmail.com\n\nAlso email to receive updates\non current versions. Version 1.1.3");
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void start(Stage stage) throws Exception{
		ButtonPane buttonPane = new ButtonPane();
		primaryStage = stage;
		
		
		title.setScaleX(2);
		title.setScaleY(2);
		
		GridPane txt = new GridPane();
		txt.getChildren().add(title);
		BorderPane.setMargin(txt,new Insets(20));
		txt.setAlignment(Pos.CENTER);
		
		//txt.setCenter(title);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(buttonPane);
		BorderPane.setMargin(buttonPane,new Insets(20));
		pane.setTop(txt);
		
		
		Scene scene = new Scene(pane,250,225);
		primaryStage.setTitle("Calling Finder");
		primaryStage.setScene(scene);
		primaryStage.show();

		buttonPane.btTop.setOnAction(e -> buttonPane.topButton());
		buttonPane.btBottom.setOnAction(e -> buttonPane.bottomButton());
		buttonPane.btBack.setOnAction(e -> buttonPane.backButton());
		buttonPane.btSupport.setOnAction(e -> buttonPane.supportHandler());
	}
	
	class ButtonPane extends GridPane{
		int btWidth = 160;
		/**starting menu*/
		Button btTop = new Button("Blank Form");			
		Button btBottom = new Button("Use Exisiting File");	
		Button btBack = new Button("Back");
		Button btSupport = new Button("Support");
		HBox otherBar = new HBox();
		Label label = new Label("(Need Callings, Calling Status)");
		int option1 = 0; //0 first menu not chosen 1 top button 2 bottom button on menu 1
			
		public ButtonPane(){
			setHgap(5);
			setVgap(5);
			add(btTop, 0, 0);
			add(btBottom, 0, 1);
			add(label, 0, 2);
			setAlignment(Pos.CENTER);
			btTop.setMinWidth(btWidth);
			btBottom.setMinWidth(btWidth);
		}
		void topButton(){
			if((option1 == 0)){
				//option1 = 1;
				BlankForm blank = new BlankForm();
				try {
					blank.start(primaryStage);
				} catch (Exception e) {
					//error needs to be here
				}
			}
			if((option1 == 2)){
				CallingList callList = new CallingList();
				try {
					callList.start(primaryStage);
				} catch (Exception e) {
					//error needs to be here
				}
			}
		}
		void bottomButton(){
			if((option1 == 0)){
				option1 = 2;
				btTop.setText("Calling List");
				btBottom.setText("Needs Calling");
				getChildren().remove(label);	
				title.setText("Existing Files");
				otherBar.getChildren().addAll(btBack, btSupport);
				otherBar.setSpacing(8);
				
				add(otherBar, 0, 2);
			}
			if((option1 == 2)){
				//load need calling gui
			}
		}
		void backButton(){
			if(option1 == 2){
			option1 = 0;
			btTop.setText("Blank Form");
			btBottom.setText("Use Existing File");
			add(label, 0, 2);
			title.setText("Welcome");
			getChildren().remove(otherBar);
			otherBar.getChildren().removeAll(btBack,btSupport);
			}
			if(option1 == 3){
				option1 = 0;
				otherBar.getChildren().removeAll(btBack, btSupport);
				getChildren().remove(otherBar);
				getChildren().addAll(btBottom,btTop);
				otherBar.getChildren().remove(btBack);
				getChildren().remove(support);
				bottomButton();
				
			}
		}
		void supportHandler(){
			option1 = 3;
			getChildren().removeAll(btBottom,btTop);
			title.setText("Support Information");
			otherBar.getChildren().remove(btSupport);
			getChildren().add(support);
			
		}
	}

}
