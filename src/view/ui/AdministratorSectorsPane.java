package view.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import model.Manager;
import model.Sector;

public class AdministratorSectorsPane extends VBox{
	
	private TableView<Sector> sectorTable = new TableView<>();
  
	private TableColumn<Sector, String> nameColumn = new TableColumn<>("Name");
	private TableColumn<Sector, Manager> managerColumn = new TableColumn<>("Manager");
  
	private Button updateButton = new Button("Update Sector");
  
	@SuppressWarnings("deprecation")
	public AdministratorSectorsPane() {
    	sectorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		sectorTable.setPlaceholder(new Label ("No Sector Data"));
		sectorTable.setEditable(true);
   	
		sectorTable.getColumns().add(nameColumn);
		sectorTable.getColumns().add(managerColumn);
   	
		updateButton.getStyleClass().add("update-button");

		this.getChildren().add(sectorTable);
		this.getChildren().add(new ToolBar(updateButton));
   	}
  
   
	public TableView<Sector> getSectorTable(){
		return sectorTable;
	}
  
	public TableColumn<Sector, String> getNameColumn(){
		return nameColumn;
	}
  
	public TableColumn<Sector, Manager> getManagerColumn(){
		return managerColumn;
	}
   
	public Button getUpdateButton() {
		return updateButton;
	}
 
  
}


