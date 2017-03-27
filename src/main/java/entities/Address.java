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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordsLat() {
        return coordsLat;
    }

    public void setCoordsLat(String coordsLat) {
        this.coordsLat = coordsLat;
    }

    public String getCoordsLon() {
        return coordsLon;
    }

    public void setCoordsLon(String coordsLon) {
        this.coordsLon = coordsLon;
    }
}
