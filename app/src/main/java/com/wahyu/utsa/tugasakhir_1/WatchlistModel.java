package com.wahyu.utsa.tugasakhir_1;

public class WatchlistModel {
    private String cryptoName, CoinId, username;

    public WatchlistModel(){}

    public WatchlistModel(String cryptoName, String coinId, String username) {
        this.cryptoName = cryptoName;
        this.CoinId = coinId;
        this.username = username;
    }

    public String getCryptoName() {
        return cryptoName;
    }

    public void setCryptoName(String cryptoName) {
        this.cryptoName = cryptoName;
    }

    public String getCoinId() {
        return CoinId;
    }

    public void setCoinId(String coinId) {
        CoinId = coinId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
