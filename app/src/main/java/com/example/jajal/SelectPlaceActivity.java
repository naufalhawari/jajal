package com.example.jajal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SelectPlaceActivity extends AppCompatActivity {

    int minimumTotalTimeSpend;
    List<Edge> bestRoute;
    List<Edge> userTravelGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place);

    }

    Edge searchEdgeData(String asal, String tujuan) {

        List<Edge> travelCompleteGraph = new ArrayList<>(
                Arrays.asList(new Edge("", "", 10), new Edge("0", "", 6),
                        new Edge("", "", 5), new Edge("", "", 15),
                        new Edge("", "", 5), new Edge("", "", 5),
                        new Edge("", "", 5), new Edge("", "", 5),
                        new Edge("", "", 5), new Edge("", "", 5))
        );

        for (Edge edge: travelCompleteGraph) {
            if ((edge.getAsal().equals(asal) && edge.getTujuan().equals(tujuan)) ||
                    (edge.getAsal().equals(tujuan) && edge.getTujuan().equals(asal))) {
                return edge;
            }
        }

        return null;
    }

    void addEdgeToList(String asal, String tujuan) {
        Edge edge = searchEdgeData(asal, tujuan);
        userTravelGraph.add(edge);
    }

    void kruskalMST(List<Edge> E) {
        List<Edge> kruskalBestRoute = new ArrayList<>();
        minimumTotalTimeSpend = 0;

        E.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.getBobot() - o2.getBobot();
            }
        });

        for (Edge edge: E) {
            if (!kruskalBestRoute.contains(edge)) {
                kruskalBestRoute.add(edge);
            }

            if (kruskalBestRoute.size() == E.size()) {
                break;
            }
        }

        bestRoute = kruskalBestRoute;
    }

    void bruteForceMST(List<Edge> E, int k) {

        for (int i = k; i < E.size(); i++) {
            java.util.Collections.swap(E, i, k);
            bruteForceMST(E, k + 1);
            java.util.Collections.swap(E, k, i);
        }
        if (k == E.size() - 1) {
            int t = computeTotalTimeSpend(E);
            if (t < minimumTotalTimeSpend) {
                bestRoute = E;
                minimumTotalTimeSpend = t;
            }
        }
    }

    int computeTotalTimeSpend(List<Edge> E) {
        int total = 0;

        for (int i = 0; i < E.size(); i++) {
            total +=  E.get(i).getBobot();
        }

        return total;
    }
}