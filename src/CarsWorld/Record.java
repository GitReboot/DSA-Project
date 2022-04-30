import javafx.beans.property.SimpleStringProperty;

public class Record {
    private final SimpleStringProperty brand;
    private final SimpleStringProperty model;
    private final SimpleStringProperty year;
    private final SimpleStringProperty price;
    private final SimpleStringProperty category;

    public String getBrand() {
        return brand.get();
    }

    public String getModel() {
        return model.get();
    }

    public String getYear() {
        return year.get();
    }

    public String getPrice() {
        return price.get();
    }

    public String getCategory() {
        return category.get();
    }

    Record(String newBrand, String newModel, String newCategory, String newYear, String newPrice) {

        this.brand = new SimpleStringProperty(newBrand);
        this.model = new SimpleStringProperty(newModel);
        this.category = new SimpleStringProperty(newCategory);
        this.year = new SimpleStringProperty(newYear);
        this.price = new SimpleStringProperty(newPrice);

    }


}

