package com.example.fire;

public class CafeItem {
    private String name;
    private int price;
    private String body;
    private String photoUrl; // 이건 뭐지
    private String imageUrl;

    public CafeItem() {
    }

    public CafeItem(String name, int price, String imageUrl) {
        this.price=price;
        this.name = name;
        // this.photoUrl = photoUrl;
        this.imageUrl = imageUrl;
    }


    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
