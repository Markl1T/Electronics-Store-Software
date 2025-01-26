package view.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class TitlePane extends BorderPane {
	
	private Label titleLbl = new Label();
	
	public TitlePane(String title) {
		titleLbl.setText(title);
		titleLbl.setPadding(new Insets(4));
        titleLbl.getStyleClass().add("title");
		this.setCenter(new ToolBar(titleLbl));
	}

	public Label getTitleLbl() {
		return titleLbl;
	}

	public void setTitleLbl(String title) {
		this.titleLbl.setText(title);
	}
}
