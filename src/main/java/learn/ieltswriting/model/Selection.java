package learn.ieltswriting.model;

import java.util.Objects;

import javafx.stage.Stage;

public class Selection {

	int start;
	int end;
	Stage popup=null;
	String selectedText;
	String notes;

	public boolean isNotes() {
		return popup != null;
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

	public Stage getPopup() {
		popup.setResizable(false);
		return popup;
	}

	public void setPopup(Stage popup) {
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

	@Override
	public String toString() {
		return "Selection [start=" + start + ", end=" + end + ", selectedText=" + selectedText + ", notes=" + notes
				+ "]";
	}
	
	public Selection(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public int hashCode() {
		return Objects.hash(end, start);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Selection other = (Selection) obj;
		return end == other.end && start == other.start;
	}
	
}
