package view.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class ExceptionLabel extends Label{
	public ExceptionLabel() {
		super();
		getStyleClass().add("exception");
		setPadding(new Insets(4));
	}
	
	public ExceptionLabel(String message) {
		super(message);
		getStyleClass().add("exception");
		setPadding(new Insets(4));
	}
}
