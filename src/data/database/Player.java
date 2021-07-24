package data.database;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String country;
    private int age;
    private double height;
    private String club;
    private String position;
    private int number;
    private double salary;
    private double price;
    private String imgSource;
    private boolean isInTransferList;

    public Player() {
        // will delete later
        imgSource = "/images/player/unknown.png";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setImgSource("/images/player/" + name.replace(' ', '_') + ".png");
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        try {
            new Image(getClass().getResourceAsStream(imgSource));
            this.imgSource = imgSource;
        } catch (Exception e) {
            this.imgSource = "/images/player/unknown.png";
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInTransferList() {
        return isInTransferList;
    }

    public void setInTransferList(boolean inTransferList) {
        isInTransferList = inTransferList;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n"
                + "Country: " + country + "\n"
                    + "Age: " + age + " years" + "\n"
                        + "Height: " + height + " meters" + "\n"
                            + "Club: " + club + "\n"
                                + "Position: " + position + "\n"
                                    + "Number: " + number + "\n"
                                        + "Weekly salary: " + salary;
    }
}
