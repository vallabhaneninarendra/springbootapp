package parkinglotsystem.model;

public class Car {
    private String carColor;
    private String carNumber;

    public Car() {
    	
    }
    public Car(String carColor, String carNumber){
        this.carColor = carColor;
        this.carNumber = carNumber;
    }
    public String getCarNumber() {
        return carNumber;
    }

    public String getCarColor() {
        return  this.carColor;
    }
}
