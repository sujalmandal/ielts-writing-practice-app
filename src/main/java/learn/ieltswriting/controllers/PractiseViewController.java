package learn.ieltswriting.controllers;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;

import org.fxmisc.richtext.InlineCssTextArea;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.IndexRange;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import learn.ieltswriting.App;
import learn.ieltswriting.Controller;
import learn.ieltswriting.FileLogger;
import learn.ieltswriting.constants.Constants;
import learn.ieltswriting.model.Selection;
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
	private InlineCssTextArea textQuestion;

	@FXML
	private ImageView qTypeImgView;

	@FXML
	private VBox root;

	private int elapsedTime = 0;

	private int totalTime = 0;

	private Timeline timeline;

	private Set<Selection> selections = new HashSet<>();
	MenuItem highlight = new MenuItem("highlight");
	MenuItem clear = new MenuItem("clear");
	MenuItem clearAll = new MenuItem("clear all");
	MenuItem addNotes = new MenuItem("add notes");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		log.info("initialising practise view");
		setupStyledTextArea();
		textAreaAnswer.setOnKeyPressed(event -> wordCountText
				.setText("word count : " + WordsUtils.totalWords(textAreaAnswer.getText().trim())));
		timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> {
			if ((totalTime - elapsedTime) > 0) {
				elapsedTime += 1;
				timeText.setText((totalTime - elapsedTime) + " minutes left");
			} else {
				textAreaAnswer.setEditable(false);
				PractiseViewController.this.timeline.stop();
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Test ended.");
					alert.setContentText("Your exam has ended!");
					alert.showAndWait();
				});
			}
		}));
		textAreaAnswer.setOnMouseClicked(mEvent -> {
			textAreaAnswer.requestFocus();
		});
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	public void setupStyledTextArea() {
		textQuestion.setPadding(new Insets(20, 20, 20, 20));
		textQuestion.setWrapText(true);
		textQuestion.setEditable(false);
		textQuestion.setStyle("-fx-font-size: 16;-fx-font-weight:bold;");
		textQuestion.setContextMenu(new ContextMenu());
		textQuestion.getContextMenu().getItems().addAll(highlight, addNotes, clear, clearAll);

		highlight.setOnAction(event -> {
			int startIndex = textQuestion.getSelection().getStart();
			int endIndex = textQuestion.getSelection().getEnd();
			Selection selection = new Selection(startIndex, endIndex);
			adjustSelection(selection);
			if (!selections.contains(selection)) {
				selections.add(selection);
				textQuestion.setStyle(selection.getStart(), selection.getEnd(), Constants.HIGHLIGHT_CSS);
			}
		});

		clear.setOnAction(event -> {
			int startIndex = textQuestion.getSelection().getStart();
			int endIndex = textQuestion.getSelection().getEnd();
			Selection selection = new Selection(startIndex, endIndex);
			System.out.println("current selection: " + selection);
			System.out.println("all selections: " + selections);
			System.out.println("overlaps: " + overlaps(selections, selection));
			if (overlaps(selections, selection).isPresent()) {
				Selection matchedSelection = overlaps(selections, selection).get();
				selections.remove(matchedSelection);
				textQuestion.setStyle(matchedSelection.getStart(), matchedSelection.getEnd(),
						Constants.NO_HIGHLIGHT_CSS);
			}
		});

		addNotes.setOnAction(event -> {
			if (textQuestion.getSelectedText().trim().length() != 0) {
				int startIndex = textQuestion.getSelection().getStart();
				int endIndex = textQuestion.getSelection().getEnd();
				Selection selection = new Selection(startIndex, endIndex);
				adjustSelection(selection);
				if (!selections.contains(selection)) {
					String trueSelectedText = textQuestion
							.getText(new IndexRange(selection.getStart(), selection.getEnd()));
					selection.setSelectedText(trueSelectedText);
					System.out.println("selected: |" + trueSelectedText + "|");
					Stage popup = createPopup(selection);
					popup.setAlwaysOnTop(true);
					selection.setPopup(popup);
					selections.add(selection);
					textQuestion.setStyle(selection.getStart(), selection.getEnd(), Constants.HIGHLIGHT_CSS_NOTES);
					popup.show();
				}
			}
		});

		clearAll.setOnAction(event -> {
			selections.forEach(selection -> {
				textQuestion.setStyle(selection.getStart(), selection.getEnd(), Constants.NO_HIGHLIGHT_CSS);
				addNotes.setDisable(false);
				highlight.setDisable(false);
			});
			selections.clear();
		});

		// show previously saved popup
		textQuestion.setOnMouseClicked(mEvent -> {
			if (mEvent.getButton() == MouseButton.PRIMARY && mEvent.getClickCount() == 2) {
				Optional<Selection> selection = selections.stream().filter(Selection::isNotes).filter(s -> {
					boolean found = false;
					found = textQuestion.getSelection().getStart() >= s.getStart()
							&& textQuestion.getSelection().getEnd() <= s.getEnd();
					return found;
				}).findFirst();
				if (selection.isPresent()) {
					Point p = MouseInfo.getPointerInfo().getLocation();
					selection.get().getPopup().setAlwaysOnTop(true);
					Stage popup = selection.get().getPopup();
					popup.show();
				}
			}
		});

		// enable and disable menu items
		textQuestion.setOnMouseReleased(mEvent -> {
			System.out.println("setOnMouseReleased");
			int start = textQuestion.getSelection().getStart();
			int end = textQuestion.getSelection().getEnd();
			if (start == end) {
				addNotes.setDisable(true);
				highlight.setDisable(true);
			} else {
				addNotes.setDisable(false);
				highlight.setDisable(false);
			}
			if (!selections.isEmpty()) {
				boolean overlaps = overlaps(selections, new Selection(start, end)).isPresent();
				System.out.println(selections);
				System.out.println(start + ":" + end);
				System.out.println(overlaps);
				if (overlaps) {
					addNotes.setDisable(true);
					highlight.setDisable(true);
				} else {
					addNotes.setDisable(false);
					highlight.setDisable(false);
				}
			}
		});
	}

	private Optional<Selection> overlaps(Set<Selection> selections, Selection currentSelection) {
		return selections.stream().filter(testSelection -> {
			if (currentSelection.getStart() >= testSelection.getStart()
					&& currentSelection.getEnd() <= testSelection.getEnd()) {
				return true;
			}
			if (currentSelection.getStart() < testSelection.getStart()
					&& currentSelection.getEnd() >= testSelection.getStart()
					&& currentSelection.getEnd() < testSelection.getEnd()) {
				return true;
			}
			if (currentSelection.getStart() >= testSelection.getStart()
					&& currentSelection.getStart() < testSelection.getEnd()
					&& currentSelection.getEnd() > testSelection.getEnd()) {
				return true;
			}
			if (currentSelection.getStart() < testSelection.getStart()
					&& currentSelection.getEnd() > testSelection.getEnd()) {
				return true;
			}
			return false;
		}).findFirst();
	}

	private void adjustSelection(Selection selection) {
		int startIndex = selection.getStart();
		int endIndex = selection.getEnd();
		if (textQuestion.getText().charAt(startIndex) == ' ') {
			startIndex++;
		} else {
			while (startIndex > 0 && textQuestion.getText().charAt(startIndex - 1) != ' ') {
				startIndex--;
			}
		}
		if (textQuestion.getText().charAt(endIndex - 1) == ' ') {
			endIndex--;
		} else {
			while (endIndex < textQuestion.getText().length() && textQuestion.getText().charAt(endIndex) != ' ') {
				endIndex++;
			}
		}
		selection.setStart(startIndex);
		selection.setEnd(endIndex);
	}

	public void setQuestion(String question, int type) {
		textQuestion.appendText(question);
		if (type == 1) {
			totalTime = 20;
			qTypeImgView.setImage(new Image(App.class.getResource(Constants.GT1_IMAGE).toString(), true));
		}
		if (type == 2) {
			totalTime = 40;
			qTypeImgView.setImage(new Image(App.class.getResource(Constants.GT2_IMAGE).toString(), true));
		}
		timeText.setText((totalTime - elapsedTime) + " minutes left");
	}

	@FXML
	public void fullScreenExit(KeyEvent e) {
		if (e.getCode() == KeyCode.ESCAPE) {
			root.getScene().getWindow().setHeight(720);
			root.getScene().getWindow().setWidth(1200);
			root.getScene().getWindow().centerOnScreen();
		}
	}

	private Stage createPopup(Selection selection) {
		Stage popup = new Stage();
		FXMLLoader loader = new FXMLLoader(App.class.getResource(Constants.POPUP_VIEW + Constants.FXML));
		try {
			Scene content = new Scene(loader.load());
			StickyNoteController controller = loader.getController();
			popup.initStyle(StageStyle.UTILITY);
			controller.init(selection);
			popup.setScene(content);
			popup.setResizable(false);
			popup.setOnCloseRequest(e -> {
				popup.hide();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return popup;
	}

}