package fms.fxController;


import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import fms.model.Category;
import fms.model.FinanceManagementSystem;
import fms.hibernate.hibernateControl;
import java.util.Optional;

public class CategoryDialog {
    private static Optional<Category> showCategoryEditDialog(String title, Category category) {
        Dialog<Category> dialog = new Dialog<>();
        dialog.setTitle(title);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(8);
        gridPane.setVgap(8);
        gridPane.setPadding(new Insets(24d, 16d, 16d, 16d));

        TextField nameField = new TextField(category.getName());
        TextArea descriptionArea = new TextArea(category.getDescription());

        gridPane.addColumn(0,
                new Label("Name"),
                nameField,
                new Label("Description"),
                descriptionArea
        );

        dialog.getDialogPane().setContent(gridPane);

        final Button okButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        okButton.disableProperty().bind(
                Bindings.or(
                        nameField.textProperty().isEqualTo(""),
                        descriptionArea.textProperty().isEqualTo("")
                )
        );

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                category.setName(nameField.getText());
                category.setDescription(descriptionArea.getText());

                return category;
            }
            return null;
        });

        return dialog.showAndWait();
    }

    public static void createItem(Category parentCategory, FinanceManagementSystem fms) {
        Category emptyCategory = parentCategory == null
                ? new Category("", "", fms)
                : new Category("", "", parentCategory);

        showCategoryEditDialog("Create new category", emptyCategory).ifPresent((newItem) -> {
            if (parentCategory != null) {
                parentCategory.getSubCategories().add(newItem);
            } else {
                fms.getCategories().add(newItem);
            }
            hibernateControl.categories.create(newItem);
        });
    }

    public static void editItem(Category category) {
        showCategoryEditDialog("Edit '" + category.getName() + "'", category).ifPresent((newItem) -> {
            hibernateControl.categories.edit(newItem);
        });
    }

    public static Boolean removeItem(Category category) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Are you sure you want to remove this item");

        ButtonType result = alert.showAndWait().orElse(null);
        if (result.equals(ButtonType.OK)) {
            Category parentCategory = category.getParentCategory();
            if (parentCategory != null) {
                parentCategory.getSubCategories().remove(category);
            } else {
                category.getFms().getCategories().remove(category);
            }
            hibernateControl.categories.remove(category.getCategoryID());
            return true;
        }
        return false;
    }
}
