package main;

public class StockManager {
    public TwoThreeTree<Float, String> stocksTree;

    public StockManager() {
        this.stocksTree = new TwoThreeTree<>();
    }


    // 1. Initialize the system
    public void initStocks() {
        stocksTree.init("99999999999999999999999999999999999" , Float.POSITIVE_INFINITY,"99999999999999999999999999999999999" , Float.POSITIVE_INFINITY , "99999999999999999999999999999999999" , Float.POSITIVE_INFINITY);
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
        Node<Float,String> temp = new Node<Float,String>(stockId, price, new Stock(stockId,price,timestamp));
        Node<Float,String> isIN = stocksTree.search(temp,stockId);
        if(isIN.equals(stockId)){
            throw new IllegalArgumentException();
        }
        else  stocksTree.insert(temp);
    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
        // add code here
    }

    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        // add code here
    }

    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
        // add code here
        Node<Float,String> temp  = stocksTree.search(stocksTree.getRoot(),stockId);
        if (temp==null){
            throw new IllegalArgumentException();
        }
        return temp.getValue();

    }

    // 6. Remove a specific timestamp from a stock's history
    public void removeStockTimestamp(String stockId, long timestamp) {
        // add code here
        return;
    }

    // 7. Get the amount of stocks in a given price range
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        // add code here
        return 0;
    }

    // 8. Get a list of stock IDs within a given price range
    public String[] getStocksInPriceRange(Float price1, Float price2) {
        // add code here
        return null;

    }
}


