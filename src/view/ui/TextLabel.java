package view.ui;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class TextLabel extends Label{
	public TextLabel(String text) {
		super(text);
		super.setFont(new Font("Arial", 13));
	}
}
