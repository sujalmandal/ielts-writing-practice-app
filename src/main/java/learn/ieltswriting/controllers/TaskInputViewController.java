package learn.ieltswriting.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import learn.ieltswriting.App;
import learn.ieltswriting.Controller;
import learn.ieltswriting.FileLogger;
import learn.ieltswriting.constants.Constants;

public class TaskInputViewController implements Controller, Initializable {

	private static final Logger log = FileLogger.getLogger(TaskInputViewController.class.getName());
	@FXML
	private TextArea questionTextAreaT1;
	@FXML
	private TextArea questionTextAreaT2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		log.info("initialising task input view");
	}
	
	@FXML
	public void startTaskOne() throws IOException {
		PractiseViewController ctnlr= (PractiseViewController) App.setRoomMaximized(Constants.PRACTICE_VIEW);
		ctnlr.setQuestion(questionTextAreaT1.getText(),questionTextAreaT2.getText(),1);
	}
	
	
	@FXML
	public void startTaskTwo() throws IOException {
		PractiseViewController ctnlr= (PractiseViewController) App.setRoomMaximized(Constants.PRACTICE_VIEW);
		ctnlr.setQuestion(questionTextAreaT1.getText(),questionTextAreaT2.getText(),2);
	}
	
	@FXML
	public void startBothTasks() throws IOException {
		PractiseViewController ctnlr= (PractiseViewController) App.setRoomMaximized(Constants.PRACTICE_VIEW);
		ctnlr.setQuestion(questionTextAreaT1.getText(),questionTextAreaT2.getText(),0);
	}
}