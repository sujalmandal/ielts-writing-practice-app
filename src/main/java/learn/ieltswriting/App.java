package learn.ieltswriting;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import learn.ieltswriting.constants.Constants;
import learn.ieltswriting.util.ThemeUtil;

/**
 * JavaFX App
 */
public class App extends Application {

	private static final Logger log = FileLogger.getLogger(App.class.getName());

	private static Scene scene;
	private static Stage stage;
	private static HostServices hostServices;

	@Override
	public void start(Stage stage) throws IOException {
		log.info("starting main application");
		log.info("fetching hostServices");
		App.hostServices = this.getHostServices();
		App.stage = stage;
		stage.getIcons().add(new Image(App.class.getResource(Constants.ICON).toString()));
		App.scene = new Scene(
				new FXMLLoader(App.class.getResource(Constants.TASK_INPUT_VIEW + Constants.FXML)).load());
		ThemeUtil.setLightTheme(scene);
		stage.setTitle(Constants.APP_NAME + " " + Constants.APP_VERSION);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
		log.info("main app loaded.");
	}

	public static Controller setRoot(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + Constants.FXML));
		Parent root = fxmlLoader.load();
		stage.centerOnScreen();
		scene.setRoot(root);
		return fxmlLoader.getController();
	}
	
	public static Controller setRoomMaximized(String fxml) throws IOException {
		stage.setMaximized(true);
		return setRoot(fxml);
	}

	public static HostServices getAppHostServices() {
		log.info("returning hostServices");
		return hostServices;
	}

	public static void main(String[] args) {
		log.info("launching main app.");
		launch();
	}

}