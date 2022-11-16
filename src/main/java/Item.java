public class Item {
    public int id;
    public String name;
    public double price;

    public Item(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public Item(String[] line) {
        this.id = Integer.parseInt(line[0]);
        this.name = line[1];
        this.price = Double.parseDouble(line[2]);
    }
    public Item() {
        this.id = 0;
        this.name = "";
        this.price = 0;
    }
    public String toString() {
        return this.id + "\t" + this.name + "\t" + this.price;
    }

    public String toCSV() {
        return this.id + ", " + this.name + ", " + this.price;
    }

}
