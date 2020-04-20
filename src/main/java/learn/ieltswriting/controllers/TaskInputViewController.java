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
	private TextArea questionTextArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		log.info("initialising task input view");
	}
	
	@FXML
	public void startTaskOne() throws IOException {
		String question = questionTextArea.getText();
		PractiseViewController ctnlr= (PractiseViewController) App.setRootFullScreen(Constants.PRACTICE_VIEW);
		ctnlr.setQuestion(question,1);
	}
	
	
	@FXML
	public void startTaskTwo() throws IOException {
		String question = questionTextArea.getText();
		PractiseViewController ctnlr= (PractiseViewController) App.setRootFullScreen(Constants.PRACTICE_VIEW);
		ctnlr.setQuestion(question,2);
	}
}