package com.wahyu.utsa.tugasakhir_1;

public class KomenModel {
    private String komentar, cryptoName, username;

    public KomenModel(){}

    public KomenModel(String komentar, String cryptoName, String username) {
        this.komentar = komentar;
        this.cryptoName = cryptoName;
        this.username = username;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getCryptoName() {
        return cryptoName;
    }

    public void setCryptoName(String cryptoName) {
        this.cryptoName = cryptoName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
