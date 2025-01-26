package view.ui;

import javafx.scene.control.Button;

public class HomeButton extends Button {
    public HomeButton(String text) {
        super(text);
        getStyleClass().add("home-button");
    }
}
