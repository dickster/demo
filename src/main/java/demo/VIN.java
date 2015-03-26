package demo;

import java.io.Serializable;

public class VIN implements Serializable {
    private String formattedText;
    private Integer year;
    private String manufacturer;
    private String model;
    private String vin;

    public VIN() {
        this(2014, "KIA", "FakeCar", "XXX0000");
    }

    public VIN(Integer year, String manufacturer, String model, String vin) {
        this.year = year;
        this.manufacturer = manufacturer;
        this.model = model;
        this.vin = vin;
    }

    public VIN(VIN vin) {
        this.formattedText = vin.getFormattedText();
        this.year = vin.getYear();
        this.manufacturer = vin.getManufacturer();
        this.model = vin.getModel();
        this.vin = vin.getVin();
    }

    public String getFormattedText() {
        return formattedText;
    }

    public void setFormattedText(String formattedText) {
        this.formattedText = formattedText;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String description() {
        return String.format("(%d %s %s)", year, manufacturer, model);
    }
}
