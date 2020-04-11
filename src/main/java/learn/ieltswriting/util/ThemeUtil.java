package learn.ieltswriting.util;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.Window;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class ThemeUtil {
	public static void setLightTheme(Scene scene) {
		JMetro jMetro = new JMetro(Style.LIGHT);
		jMetro.setScene(scene);
	}
	
	public static Window getCurrentWindow() {
		return Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
	}
	
	public static void bindElementToScreenSize(Region parent,double wFactor,double hFactor) {
		parent.prefWidthProperty().bind(Bindings.divide(ThemeUtil.getCurrentWindow().widthProperty(), wFactor));
		parent.prefHeightProperty().bind(Bindings.divide(ThemeUtil.getCurrentWindow().heightProperty(), hFactor));
	}
}
