package com.example.jajal;

public class Subset {
    private String parent;
    private Integer rank;

    public Subset(String parent, Integer rank) {
        this.parent = parent;
        this.rank = rank;
    }

    public String getParent() {
        return parent;
    }

    public Integer getRank() {
        return rank;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
