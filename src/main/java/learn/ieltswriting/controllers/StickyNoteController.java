package learn.ieltswriting.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import learn.ieltswriting.model.Selection;

public class StickyNoteController {
	
	@FXML
	TextArea notes;
	private Selection selection;
	
	public void init(Selection selection) {
		this.selection = selection;
	}
	
	@FXML
	public void saveNote(){
		this.selection.setNotes(notes.getText());
	}
	
}
