package com.wahyu.utsa.tugasakhir_1;

public class CryptoModal {
    private String CoinId;
    private String name;
    private String symbol;
    private double price;
    private double percentChange24h;
    private double percentChange1h;
    private double percentChange7d;

    public CryptoModal(String CoinId, String name, String symbol, double price, double percentChange24h, double percentChange1h, double percentChange7d) {
        this.CoinId = CoinId;
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.percentChange24h = percentChange24h;
        this.percentChange1h = percentChange1h;
        this.percentChange7d = percentChange7d;

    }

    public String getCoinId() {
        return CoinId;
    }

    public void setCoinId(String id) {
        this.CoinId = CoinId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public double getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(double percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public double getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(double percentChange7d) {
        this.percentChange7d = percentChange7d;
    }
}
