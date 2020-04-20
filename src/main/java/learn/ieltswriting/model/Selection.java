package learn.ieltswriting.model;

import javafx.stage.Popup;

public class Selection {

	int start;
	int end;
	Popup popup;
	String selectedText;
	String notes;
	
	public Selection(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public Popup getPopup() {
		return popup;
	}
	public void setPopup(Popup popup) {
		this.popup = popup;
	}
	public String getSelectedText() {
		return selectedText;
	}
	public void setSelectedText(String selectedText) {
		this.selectedText = selectedText;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
