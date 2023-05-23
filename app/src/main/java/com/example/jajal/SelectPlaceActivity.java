package com.example.jajal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.jajal.databinding.ActivitySelectPlaceBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SelectPlaceActivity extends AppCompatActivity {

    int bruteMinimumTotalTimeSpend, kruskalMinimumTotalTimeSpend;
    long bruteExeTime, kruskalExeTime;
    List<Edge> bruteBestRoute = new ArrayList<>();
    List<Edge> kruskalBestRoute = new ArrayList<>();
    List<Edge> userTravelGraph = new ArrayList<>();
    List<String> userPlaceList = new ArrayList<>();
    ActivitySelectPlaceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectPlaceBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_select_place);

        userTravelGraph = createTravelGraph(userPlaceList);
        setListener();
    }

    void setListener(){
        binding.checkResultButton.setOnClickListener(e -> {
            if (binding.checkResultButton.getText().toString().equals("Check Result")) {
                binding.checkResultButton.setText("Back");
                binding.result.setVisibility(View.VISIBLE);
                binding.selectPlaceList.setVisibility(View.GONE);
                checkResult();
            } else {
                binding.checkResultButton.setText("Check Result");
                binding.selectPlaceList.setVisibility(View.VISIBLE);
                binding.result.setVisibility(View.GONE);
                clearAnswer();
            }
        });

    }

    void clearAnswer(){
        binding.checkApurva.setChecked(false);
        binding.checkBeachwalk.setChecked(false);
        binding.checkDreamland.setChecked(false);
        binding.checkGaruda.setChecked(false);
        binding.checkGolf.setChecked(false);
        binding.checkMerputResto.setChecked(false);
        binding.checkNusaDua.setChecked(false);
        binding.checkPadang.setChecked(false);
        binding.checkPandawa.setChecked(false);
        binding.checkTanjungBenoa.setChecked(false);

        kruskalMinimumTotalTimeSpend = 0;
        bruteMinimumTotalTimeSpend = 0;
        bruteExeTime = 0;
        kruskalExeTime = 0;
        userPlaceList.clear();
        kruskalBestRoute.clear();
        bruteBestRoute.clear();
    }

    void checkResult(){
        long start, end;

        start = System.currentTimeMillis()/1000;
        bruteForceMST(userPlaceList, 0);
        end = System.currentTimeMillis()/1000;
        bruteExeTime = start - end;

        start = System.currentTimeMillis()/1000;
        kruskalMST(userTravelGraph);
        end = System.currentTimeMillis()/1000;
        kruskalExeTime = start - end;
    }

    List<Edge> createTravelGraph(List<String> placeList) {
        List<Edge> travelGraph = new ArrayList<>();
        for (int i = 0; i < placeList.size() - 1; i++) {
            for (int j = i + 1; j < placeList.size(); j++) {
                travelGraph.add(searchEdgeData(placeList.get(i), placeList.get(j)));
            }
        }

        return travelGraph;
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
        kruskalMinimumTotalTimeSpend = 0;

        E.sort((o1, o2) -> o1.getBobot() - o2.getBobot());

        for (Edge edge: E) {
            if (!kruskalBestRoute.contains(edge)) {
                kruskalBestRoute.add(edge);
                kruskalMinimumTotalTimeSpend += edge.getBobot();
            }

            if (kruskalBestRoute.size() == userPlaceList.size() - 1) {
                break;
            }
        }

    }

    void bruteForceMST(List<String> placeList, int k) {

        bruteMinimumTotalTimeSpend = 0;

        for (int i = k; i < placeList.size(); i++) {
            java.util.Collections.swap(placeList, i, k);
            bruteForceMST(placeList, k + 1);
            java.util.Collections.swap(placeList, k, i);
        }
        if (k == placeList.size() - 1) {
            int t = computeTotalTimeSpend(placeList);
            if (t < bruteMinimumTotalTimeSpend) {
                bruteBestRoute = createTravelGraph(placeList);
                bruteMinimumTotalTimeSpend = t;
            }
        }
    }

    int computeTotalTimeSpend(List<String> E) {
        int total = 0;

        for (int i = 0; i < E.size() - 1; i++) {
            total +=  searchEdgeData(E.get(i), E.get(i + 1)).getBobot();
        }

        return total;
    }
}