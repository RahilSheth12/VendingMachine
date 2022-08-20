package edu.vhhs.rahil.vending_machine.modules;

public class Products {
    private long id;
    private String name;
    private String description;
    private String image_url;
    private float cost;

    public Products(long id, String name, String description, String image_url, float cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image_url='" + image_url + '\'' +
                ", cost=" + cost +
                '}';
    }
}
