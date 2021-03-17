package fms.fxController;
import fms.model.*;
import fms.hibernate.hibernateControl;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.event.ActionEvent;


import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class MainFinanceManagementWindow implements Initializable {
    @FXML
    public TreeView<Category> categoryTree;

    @FXML
    public TableView financeTable;

    private FinanceManagementSystem fms;

    public void setFms(FinanceManagementSystem fms){
        this.fms = fms;
        updateCategoryTreeView();
    }

    public void showAllUsers(ActionEvent actionEvent){

        Dialog<Category> dialog = new Dialog<>();
        dialog.setTitle("users");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TableView<User> usersTable = new TableView<>(
                FXCollections.observableArrayList(fms.getSystemUsers())
        );

        TableColumn<User, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(c -> {
            User user = c.getValue();
            if (user instanceof EmployeeUser) {
                return new SimpleObjectProperty<>(((EmployeeUser) user).getName());
            } else if (user instanceof CompanyUser) {
                return new SimpleObjectProperty<>(((CompanyUser) user).getCompanyName());
            }
            return null;

        });
        TableColumn<User, String> column2 = new TableColumn<>("Surname");
        column2.setCellValueFactory(c -> {
            User user = c.getValue();
            if (user instanceof EmployeeUser) {
                return new SimpleObjectProperty<>(((EmployeeUser) user).getSurname());
            }
            return null;

        });
        TableColumn<User, String> column3 = new TableColumn<>("contactInfo");
        column3.setCellValueFactory(c -> {
            User user = c.getValue();
            if (user instanceof EmployeeUser) {
                return new SimpleObjectProperty<>(((EmployeeUser) user).getContactInfo());
            }
            return null;

        });

        usersTable.setRowFactory(t -> {
            final TableRow<User> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();

            MenuItem editItem = new MenuItem("Edit");
            editItem.setOnAction(e -> {
                System.out.println("edit");

            });

            MenuItem removeItem = new MenuItem("Remove");
            removeItem.setOnAction(e -> {
                fms.getSystemUsers().remove(row.getItem());

                try {
                    System.out.println("kEk");
                    hibernateControl.users.remove(row.getItem().getUserIDInsideDB());

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                usersTable.getItems().remove(row.getItem());
            });

            rowMenu.getItems().addAll(editItem, removeItem);
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu)null));

            return row;
        });

        usersTable.getColumns().setAll(column1, column2, column3);

        dialog.getDialogPane().setContent(usersTable);

        dialog.showAndWait();
    }

    private void updateCategoryTreeView() {
        TreeItem<Category> rootTreeItem = new TreeItem<>();

        rootTreeItem.getChildren().addAll(
                createCategoriesTreeItems(fms.getCategories())
        );
        categoryTree.setRoot(rootTreeItem);
        updateFinanceTable();
    }

    private List<TreeItem<Category>> createCategoriesTreeItems(List<Category> categories) {
        return categories.stream().map(category -> {
            TreeItem<Category> treeItem = new TreeItem(category);
            if (category.getSubCategories() != null) {
                treeItem.getChildren().addAll(
                        createCategoriesTreeItems(category.getSubCategories())
                );
            }
            return treeItem;
        }).collect(Collectors.toList());
    }

    public void addCategory(ActionEvent actionEvent) {
        TreeItem<Category> selectedItem = categoryTree.getSelectionModel().getSelectedItem();
        Category selectedCategory = null;
        if (selectedItem != null) {
            selectedCategory = selectedItem.getValue();
        }

        CategoryDialog.createItem(selectedCategory, fms);

        updateCategoryTreeView();
   }

   private Optional<Pair<String, Integer>> showFinanceDialog() {
       Dialog<Pair<String, Integer>> dialog = new Dialog<>();
       dialog.setTitle("Create");

       ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
       dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

       GridPane gridPane = new GridPane();
       gridPane.setHgap(8);
       gridPane.setVgap(8);
       gridPane.setPadding(new Insets(24d, 16d, 16d, 16d));

       TextField nameField = new TextField();

       TextField moneyField = new TextField("0");

       gridPane.addColumn(0,
               new Label("Name"),
               nameField,
               new Label("Money"),
               moneyField
       );

       dialog.getDialogPane().setContent(gridPane);

       final Button okButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
       okButton.disableProperty().bind(
               Bindings.or(
                       nameField.textProperty().isEqualTo(""),
                       moneyField.textProperty().isEqualTo("")
               )
       );

       dialog.setResultConverter(dialogButton -> {
           if (dialogButton == saveButtonType) {
               return new Pair(nameField.getText(), Integer.parseInt(moneyField.getText()));
           }
           return null;
       });

       return dialog.showAndWait();
   }

   private Category getSelectedCategory() {
       TreeItem<Category> selectedItem = categoryTree.getSelectionModel().getSelectedItem();
       Category selectedCategory = null;
       if (selectedItem != null) {
           selectedCategory = selectedItem.getValue();
       }
       return selectedCategory;
   }

   public void addIncome(ActionEvent actionEvent) {
       Category category = getSelectedCategory();

       if (category != null) {
           showFinanceDialog().ifPresent(pair -> {
               Income income = new Income(pair.getKey(), pair.getValue(), category);
               category.getIncome().add(income);
               hibernateControl.finance.create(income);
               updateFinanceTable();
           });
       }
   }

    public void addExpense(ActionEvent actionEvent) {
        Category category = getSelectedCategory();

        if (category != null) {
            showFinanceDialog().ifPresent(pair -> {
                Expense expense = new Expense(pair.getKey(), pair.getValue(), category);
                category.getExpense().add(expense);
                hibernateControl.finance.create(expense);
                updateFinanceTable();
            });
        }
    }

    public void deleteCategory(ActionEvent actionEvent) {
        Category category = getSelectedCategory();

        if (category != null) {
            CategoryDialog.removeItem(category);
            updateCategoryTreeView();
        }
    }

    private void updateFinanceTable() {
        Category category = getSelectedCategory();

        if (category != null) {
            ObservableList<Finance> list = FXCollections.observableArrayList();
            list.addAll(category.getExpense());
            list.addAll(category.getIncome());

            System.out.println("select" + category + category.getIncome());

            financeTable.setItems(list);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryTree.setCellFactory(new Callback<TreeView<Category>, TreeCell<Category>>() {
            @Override
            public TreeCell<Category> call(TreeView<Category> stringTreeView) {
                return new TreeCell<>() {
                    @Override
                    protected void updateItem(Category item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                            return;
                        }
                        String name = item == null ? "All categories" : item.getName();
                        setText(name);
                        setGraphic(getTreeItem().getGraphic());
                    }
                };
            }
        });

        categoryTree.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            updateFinanceTable();
        }));

        TableColumn<Finance, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getName()));

        TableColumn<Finance, String> column2 = new TableColumn<>("Money");
        column2.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getMoney() + ""));

        TableColumn<Finance, String> column3 = new TableColumn<>("Type");
        column3.setCellValueFactory(c -> {
            Finance finance = c.getValue();
            String value = finance instanceof Expense
                    ? "Expense"
                    : "Income";
            return new SimpleObjectProperty<>(value);
        });


        financeTable.getColumns().addAll(column1, column2, column3);

    }
}
