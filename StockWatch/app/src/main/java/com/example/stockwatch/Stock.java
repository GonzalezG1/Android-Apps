package com.example.stockwatch;

public class Stock {
    private String symbol;
    private String company;
    private double latestPrice;
    private double change;
    private double changePercent;


    public Stock(){
        symbol = "";
        company = "";
        latestPrice = 0.0;
        change = 0.0;
        changePercent = 0.0;
    }

    public Stock(String s, String co, double lp, double ch, double cp){
        symbol = s;
        company = co;
        latestPrice = lp;
        change = ch;
        changePercent = cp;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompany() {
        return company;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    //-----------------

    public double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }
}
