package PointOfSale;

public class Article {

    private int Id;
    private String name;
    private double cost;

    public int getId() { return Id; }
    public String getName() { return name; }
    public double getCost() { return cost; }

    public void setId(int Id) { this.Id = Id; }
    public void setName(String name) { this.name = name; }
    public void setCost(float cost) { this.cost = cost; }

    public Article(int Id, String name, double cost) {
        this.Id = Id;
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return Id + " " + name + " " + cost;
    }
}
