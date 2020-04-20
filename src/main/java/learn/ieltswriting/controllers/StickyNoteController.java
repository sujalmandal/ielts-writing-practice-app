package learn.ieltswriting.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Popup;
import learn.ieltswriting.model.Selection;

public class StickyNoteController {
	
	@FXML
	TextArea notes;
	private Popup popup;
	private Selection selection;
	
	public void init(Selection selection,Popup popup) {
		this.selection = selection;
		this.popup = popup;
	}
	
	@FXML
	public void saveNote(){
		this.selection.setNotes(notes.getText());
	}
	
	@FXML
	public void hide() {
		this.popup.hide();
	}
	
}
