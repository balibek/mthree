
package com.balibek.vendingmachine.dto;

import java.util.Objects;

public class Item {
    
    private String id;
    private String title;
    private int countOfItem;
    private String cost;

    public Item(String id) {
        this.id = id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCountOfItem() {
        return countOfItem;
    }

    public void setCountOfItem(int countOfItem) {
        this.countOfItem = countOfItem;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.title);
        hash = 17 * hash + this.countOfItem;
        hash = 17 * hash + Objects.hashCode(this.cost);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.countOfItem != other.countOfItem) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return Objects.equals(this.cost, other.cost);
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", title=" + title + ", countOfItem=" + countOfItem + ", cost=" + cost + '}';
    }
    
    
    
}
