package com.example.models;

import java.util.Objects;

public class TableRow {
    private String companyName;
    private String ticker;
    private String cobDate;
    private String stockPrice;
    private String marketCap;

    public TableRow(String companyName, String ticker, String cobDate, String stockPrice, String marketCap) {
        this.companyName = companyName;
        this.ticker = ticker;
        this.cobDate = cobDate;
        this.stockPrice = stockPrice;
        this.marketCap = marketCap;
    }

    public String getCompanyName() { return companyName; }
    public String getTicker() { return ticker; }
    public String getCobDate() { return cobDate; }
    public String getStockPrice() { return stockPrice; }
    public String getMarketCap() { return marketCap; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableRow tableRow = (TableRow) o;
        return Objects.equals(ticker, tableRow.ticker) &&
                Objects.equals(companyName, tableRow.companyName) &&
                Objects.equals(cobDate, tableRow.cobDate) &&
                Objects.equals(stockPrice, tableRow.stockPrice) &&
                Objects.equals(marketCap, tableRow.marketCap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, companyName, cobDate, stockPrice, marketCap);
    }

    @Override
    public String toString() {
        return "TableRow{" +
                "companyName='" + companyName + '\'' +
                ", ticker='" + ticker + '\'' +
                ", cobDate='" + cobDate + '\'' +
                ", stockPrice='" + stockPrice + '\'' +
                ", marketCap='" + marketCap + '\'' +
                '}';
    }
}