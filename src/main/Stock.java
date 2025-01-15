package main;

public class Stock {
    String stockId ;
    double price;
    long timeStamp;
    private TwoThreeTree<Long,Float> events;
    public Stock(String stockId, Float price) {
        this.stockId = stockId;
        this.price = price;
        this.events = new TwoThreeTree<>();
    }
    public Stock(String stockId, Float price, long timeStamp) {
        this.stockId = stockId;
        this.price = price;
        this.events = new TwoThreeTree<>();
        this.timeStamp = timeStamp;
    }
    public String getStockId(){
        return this.stockId;
    }
    public double getPrice(){
        return this.price;
    }
    @Override
    public String toString() {
        return "Stock{id='" + stockId + "', price=" + price + "}";
    }
}
