package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;

import controller.ManagerController;
import dao.FileHandler;
import javafx.stage.Stage;
import model.Category;
import model.Item;
import model.Manager;
import model.Sector;
import model.Supplier;
import view.ManagerView;

public class ManagerControllerFxTest extends ApplicationTest {

    protected ManagerView view;
    protected Manager manager;

    @Override
    public void start(Stage stage) {
        manager = mock(Manager.class);
        when(manager.getName()).thenReturn("Manager");
        when(manager.getSectorList()).thenReturn(new ArrayList<>());

        view = new ManagerView(manager);
        stage.setScene(view.getScene());
        stage.show();
    }


    @Test
    void clickingItemsMenuShouldNavigate() {
        ManagerView spyView = spy(view);

        ManagerController.controlMenu(spyView);

        clickOn(spyView.getItemsMenu());

        verify(spyView).showItemsView();
    }


    @Test
    void createCategoryWithEmptyFieldsShouldShowError() {
        ManagerController.handleNewCategory(view);

        clickOn(view.getNewCategoryPane().getCreateButton());

        assertTrue(view.getExceptionLabel().isVisible());
    }

    @Test
    void createCategoryValidInputShouldAppendFile() throws Exception {
        Sector sector = new Sector("Food");

        when(manager.getSectorList()).thenReturn(new ArrayList<>(List.of(sector)));

        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {

            ManagerController.handleNewCategory(view);

            view.getNewCategoryPane().getNameField().setText("Dairy");
            view.getNewCategoryPane().getSectorComboBox().setValue(sector);
            view.getNewCategoryPane().getMinQuantityField().setText("10");

            clickOn(view.getNewCategoryPane().getCreateButton());

            fh.verify(() ->
                FileHandler.appendFile(eq(FileHandler.CATEGORY), any(Category.class))
            );
        }
    }

    /* ---------------- NEW ITEM ---------------- */

    @Test
    void createItemWithValidDataShouldAppendFile() throws Exception {
        Sector sector = new Sector("Food");
        Category category = new Category("Milk", sector, 5);
        Supplier supplier = new Supplier("Supp", "123", "s@s.com");

        sector.getCategoryList().add(category);
        when(manager.getSectorList()).thenReturn(new ArrayList<>(List.of(sector)));

        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {

            fh.when(() -> FileHandler.readFile(FileHandler.SUPPLIER))
              .thenReturn(new ArrayList<>(List.of(supplier)));

            ManagerController.handleNewItem(view);

            view.getNewItemPane().getSectorComboBox().setValue(sector);
            view.getNewItemPane().getCategoryComboBox().setValue(category);
            view.getNewItemPane().getSupplierComboBox().setValue(supplier);
            view.getNewItemPane().getNameField().setText("Milk 1L");
            view.getNewItemPane().getSellingPriceField().setText("2.5");

            clickOn(view.getNewItemPane().getCreateButton());

            fh.verify(() ->
                FileHandler.appendFile(eq(FileHandler.ITEM), any(Item.class))
            );
        }
    }

    /* ---------------- NEW STOCK ---------------- */

    @Test
    void addStockValidInputShouldCallAddStock() throws Exception {
        Item item = mock(Item.class);
        Sector sector = mock(Sector.class);

        when(sector.getItemList()).thenReturn(List.of(item));
        when(manager.getSectorList()).thenReturn(List.of(sector));

        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {

            ManagerController.handleNewStock(view);

            view.getNewStockPane().getItemComboBox().setValue(item);
            view.getNewStockPane().getStockQuantityField().setText("5");
            view.getNewStockPane().getPurchasePriceField().setText("10");

            clickOn(view.getNewStockPane().getCreateButton());

            fh.verify(() ->
                FileHandler.addStock(eq(item), eq(5), eq(10.0))
            );
        }
    }


    @Test
    void createSupplierValidInputShouldAppendSupplier() throws Exception {
        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {

            ManagerController.handleNewSupplier(view);

            view.getNewSupplierPane().getNameField().setText("Supp");
            view.getNewSupplierPane().getPhoneNumberField().setText("123");
            view.getNewSupplierPane().getEmailField().setText("s@s.com");

            clickOn(view.getNewSupplierPane().getAddButton());

            fh.verify(() ->
                FileHandler.appendFile(eq(FileHandler.SUPPLIER), any(Supplier.class))
            );
        }
    }


    @Test
    void typingInSearchCategoryShouldFilterResults() throws Exception {
        Category c1 = mock(Category.class);
        when(c1.getName()).thenReturn("Food");

        Category c2 = mock(Category.class);
        when(c2.getName()).thenReturn("Electronics");

        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {

            fh.when(() -> FileHandler.readFile(FileHandler.CATEGORY))
              .thenReturn(new ArrayList<>(List.of(c1, c2)));

            ManagerController.handleCategories(view);

            clickOn(view.getCategoryPane().getSearchCategoryField()).write("Food");

            assertEquals(1,
                view.getCategoryPane().getCategoryTable().getItems().size()
            );
        }
    }

    @Test
    void searchItemByNameShouldFilter() throws Exception {
        Item i1 = mock(Item.class);
        when(i1.getName()).thenReturn("Milk");

        Item i2 = mock(Item.class);
        when(i2.getName()).thenReturn("Bread");

        try (MockedStatic<FileHandler> fh = mockStatic(FileHandler.class)) {

            fh.when(() -> FileHandler.readFile(FileHandler.ITEM))
              .thenReturn(new ArrayList<>(List.of(i1, i2)));

            ManagerController.handleItems(view);

            clickOn(view.getItemPane().getSearchItemField()).write("Milk");

            assertEquals(1,
                view.getItemPane().getItemTable().getItems().size()
            );
        }
    }


    @Test
    void lowStockItemShouldNotCrash() {
        Category cat = mock(Category.class);
        when(cat.getMinQuantity()).thenReturn(10);

        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(2);
        when(item.getCategory()).thenReturn(cat);
        when(item.getName()).thenReturn("Rice");

        Sector sector = mock(Sector.class);
        when(sector.getItemList()).thenReturn(List.of(item));

        assertTrue(() ->
            ManagerController.checkLowStockLevelsForSector(
                new ArrayList<>(List.of(sector))
            ) == null
        );
    }
}
