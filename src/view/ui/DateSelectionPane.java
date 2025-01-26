package view.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
public class DateSelectionPane extends HBox {
   private final Label startDateLbl = new Label("Start Date: ");
   private final DatePicker startDatePicker = new DatePicker();
   private final Label endDateLbl = new Label("End Date: ");
   private final DatePicker endDatePicker = new DatePicker();
   private final Button enterButton = new Button("Enter");

   public DateSelectionPane() {
       this.getChildren().addAll(startDateLbl, startDatePicker, endDateLbl, endDatePicker, enterButton);
       HBox.setMargin(startDateLbl, new Insets(4, 2, 4, 4));
       HBox.setMargin(startDatePicker, new Insets(4, 8, 4, 2));
       HBox.setMargin(endDateLbl, new Insets(4, 2, 4, 8));
       HBox.setMargin(endDatePicker, new Insets(4, 4, 4, 2));
       HBox.setMargin(enterButton, new Insets(4, 2, 4, 8));
       this.setPadding(new Insets(8));
       this.setAlignment(Pos.CENTER);
   }


   public DatePicker getStartDatePicker() {
       return startDatePicker;
   }
   public DatePicker getEndDatePicker() {
       return endDatePicker;
   }
   public Button getEnterButton() {
	   return enterButton;
   }
}

