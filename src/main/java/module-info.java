module learn.ieltswriting {
	
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
	requires transitive javafx.media;
	requires transitive java.desktop;
	requires transitive javafx.graphics;
	requires transitive org.jfxtras.styles.jmetro;
	requires transitive javafx.web;
	requires transitive com.google.gson;
	requires transitive javafx.base;
	requires java.logging;
	requires java.base;
	requires org.controlsfx.controls;
	requires org.fxmisc.richtext;
	
	opens learn.ieltswriting to javafx.fxml;
	opens learn.ieltswriting.controllers to javafx.fxml;
	
	exports learn.ieltswriting;
	
}