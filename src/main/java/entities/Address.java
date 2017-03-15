package entities;

public class Address {
    int id;
    String name;
    String address;
    String coordsLat;
    String coordsLon;

    public Address(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
