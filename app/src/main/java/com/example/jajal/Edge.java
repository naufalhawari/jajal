package com.example.jajal;

import java.lang.reflect.Constructor;

public class Edge {
    private String asal, tujuan;
    private Integer bobot;

    public Edge(String asal, String tujuan, Integer bobot) {
        this.asal = asal;
        this.tujuan = tujuan;
        this.bobot = bobot;
    }

    public String getAsal() {
        return asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public Integer getBobot() {
        return bobot;
    }


}
