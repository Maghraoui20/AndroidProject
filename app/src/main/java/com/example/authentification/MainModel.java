package com.example.authentification;

public class MainModel {
    String description, name, quantite, ref, url;


    MainModel(){

    }

    public MainModel(String description, String name, String quantite, String ref, String url) {
        this.description = description;
        this.name = name;
        this.quantite = quantite;
        this.ref = ref;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
