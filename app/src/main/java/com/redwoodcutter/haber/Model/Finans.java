package com.redwoodcutter.haber.Model;

public class Finans {
    private String isim;
    private String tanim;
    private String icerik;
    private String tarih;
    private String url;
    private String kategori;
    private String resim;

    public Finans(String isim, String tarih, String url, String kategori, String tanim, String icerik) {
        this.isim = isim;
        this.tanim = tanim;
        this.tarih = tarih;
        this.url = url;
        this.kategori = kategori;
        this.icerik = icerik;
    }
    @Override
    public String toString() {
        return "Haber : " + isim + " \n " + "Tarih : " + tarih + " \n " + "Url : \n " + url + "\n" + "İçerik : \n " + icerik;
    }
}