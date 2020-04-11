package learn.ieltswriting.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import learn.ieltswriting.App;
import learn.ieltswriting.Controller;
import learn.ieltswriting.FileLogger;
import learn.ieltswriting.util.WordsUtils;

public class PractiseViewController implements Controller, Initializable {

	private static final Logger log = FileLogger.getLogger(PractiseViewController.class.getName());

	@FXML
	private Text timeText;

	@FXML
	private TextArea textAreaAnswer;

	@FXML
	private Text wordCountText;

	@FXML
	private Text textQuestion;

	@FXML
	private ImageView qTypeImgView;
	
	@FXML
	private VBox root;

	private int elapsedTime = 0;
	private int totalTime = 0;

	private Timeline timeline;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		log.info("initialising practise view");
		textAreaAnswer.setOnKeyPressed(event -> {
			wordCountText.setText("word count : " + WordsUtils.totalWords(textAreaAnswer.getText().trim()));
		});
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent actionEvent) {
				if((totalTime-elapsedTime)>0) {
					elapsedTime += 1;
					timeText.setText((totalTime - elapsedTime) + " minutes left");
				}
				else {
					textAreaAnswer.setEditable(false);
					PractiseViewController.this.timeline.stop();
					Platform.runLater(()->{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Test ended.");
						alert.setContentText("Your exam has ended!");
						alert.showAndWait();
					});
				}
			}
		}
		));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	public void setQuestion(String question, int type) {
		textQuestion.setText(question);
		if (type == 1) {
			totalTime = 20;
			qTypeImgView.setImage(new Image(App.class.getResource("image/gt1.jpg").toString(), true));
		}
		if (type == 2) {
			totalTime = 40;
			qTypeImgView.setImage(new Image(App.class.getResource("image/gt2.jpg").toString(), true));
		}
	}
	
	@FXML
	public void fullScreenExit(KeyEvent e) {
		if(e.getCode()==KeyCode.ESCAPE) {
			root.getScene().getWindow().setHeight(720);
			root.getScene().getWindow().setWidth(1200);
			root.getScene().getWindow().centerOnScreen();
		}
	}

}