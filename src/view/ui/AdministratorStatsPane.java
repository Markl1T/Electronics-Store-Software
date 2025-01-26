package view.ui;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class AdministratorStatsPane extends BorderPane{
	private final DateSelectionPane statsDateSelector = new DateSelectionPane();
	private final GridPane statsPane = new GridPane();
	private final TextField totalRevenueField = new TextField();
	private final TextField totalCostsField = new TextField();
	private final TextField itemCostsField = new TextField();
	private final TextField staffCostsField = new TextField();
	private final TextField totalProfitsField = new TextField();
	
	public AdministratorStatsPane() {
		setView();
	}
	
	private void setView() {
		setTop(new ToolBar(statsDateSelector));
		
		statsPane.setAlignment(Pos.CENTER);
		statsPane.setHgap(4);
		statsPane.addRow(0, new Label("Total Revenue: "), totalRevenueField);
		statsPane.addRow(1, new Label("Total Costs: "), totalCostsField);
		statsPane.addRow(2, new Separator(Orientation.HORIZONTAL));
		statsPane.addRow(3, new Label("-- Item Costs: "), itemCostsField);
		statsPane.addRow(4, new Label("-- Staff Costs: "), staffCostsField);
		
		setCenter(statsPane);
		BorderPane.setAlignment(statsPane, Pos.CENTER);
		setBottom(new ToolBar(new Label("Total Profits: "), totalProfitsField));
	}

	
	public DateSelectionPane getStatsDateSelector() {
		return statsDateSelector;
	}

	public GridPane getStatsPane() {
		return statsPane;
	}

	public TextField getTotalRevenueField() {
		return totalRevenueField;
	}

	public TextField getTotalCostsField() {
		return totalCostsField;
	}

	public TextField getItemCostsField() {
		return itemCostsField;
	}

	public TextField getStaffCostsField() {
		return staffCostsField;
	}

	public TextField getTotalProfitsField() {
		return totalProfitsField;
	}
	
	
	
	
}
