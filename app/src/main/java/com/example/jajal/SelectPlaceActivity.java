package com.example.jajal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

    boolean checkResultState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySelectPlaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListener();
    }

    void setListener(){
        binding.checkResultButton.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Here", Toast.LENGTH_LONG).show();
            if (!checkResultState) {
                inputUserPlaceList();
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
            checkResultState = !checkResultState;
        });
        binding.buttonBruteforce.setOnClickListener(v -> {

            binding.buttonBruteforce.setBackgroundColor(getColor(R.color.primary));
            binding.buttonKruskal.setBackgroundColor(getColor(R.color.secondary));
        });

        binding.buttonKruskal.setOnClickListener(v -> {
            binding.buttonKruskal.setBackgroundColor(getColor(R.color.primary));
            binding.buttonBruteforce.setBackgroundColor(getColor(R.color.secondary));
        });

        binding.padang.setOnClickListener(v -> binding.checkPadang.setChecked(
                !binding.checkPadang.isChecked()
        ));

        binding.dreamland.setOnClickListener(v -> {
            binding.checkDreamland.setChecked(
                    !binding.checkDreamland.isChecked()
            );
        });

        binding.beachwalk.setOnClickListener(v -> {
            binding.checkBeachwalk.setChecked(
                    !binding.checkBeachwalk.isChecked()
            );
        });

        binding.merputResto.setOnClickListener(v -> {
            binding.checkMerputResto.setChecked(
                    !binding.checkMerputResto.isChecked()
            );
        });

        binding.baliGolf.setOnClickListener(v -> {
            binding.checkGolf.setChecked(
                    !binding.checkGolf.isChecked()
            );
        });

        binding.garudaWisnu.setOnClickListener(v -> {
            binding.checkGaruda.setChecked(
                    !binding.checkGaruda.isChecked()
            );
        });

        binding.tanjungBenoa.setOnClickListener(v -> {
            binding.checkTanjungBenoa.setChecked(
                    !binding.checkTanjungBenoa.isChecked()
            );
        });

        binding.nusaDua.setOnClickListener(v -> {
            binding.checkNusaDua.setChecked(
                    !binding.checkNusaDua.isChecked()
            );
        });

        binding.apurvaKempinski.setOnClickListener(v -> {
            binding.checkApurva.setChecked(
                    !binding.checkApurva.isChecked()
            );
        });

        binding.pandawa.setOnClickListener(v -> {
            binding.checkPandawa.setChecked(
                    !binding.checkPandawa.isChecked()
            );
        });
    }

    void inputUserPlaceList(){
        if (binding.checkPadang.isChecked()){
            userPlaceList.add("Pantai Padang Padang");
        }
        if (binding.checkDreamland.isChecked()){
            userPlaceList.add("Pantai Dreamland");
        }
        if (binding.checkBeachwalk.isChecked()){
            userPlaceList.add("Beachwalk Walking Centre");
        }
        if (binding.checkMerputResto.isChecked()){
            userPlaceList.add("Merah Putih Restaurant");
        }
        if (binding.checkGaruda.isChecked()){
            userPlaceList.add("Garuda Wisnu Kencana");
        }
        if (binding.checkTanjungBenoa.isChecked()){
            userPlaceList.add("Pantai Tanjung Benoa");
        }
        if (binding.checkNusaDua.isChecked()){
            userPlaceList.add("Pantai Nusa Dua");
        }
        if (binding.checkApurva.isChecked()){
            userPlaceList.add("Apurva Kempinski Hotel");
        }
        if (binding.checkPandawa.isChecked()){
            userPlaceList.add("Pantai Pandawa");
        }
        if (binding.checkGolf.isChecked()){
            userPlaceList.add("Bali National Golf Club");
        }
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
        userTravelGraph = createTravelGraph(userPlaceList);
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
                Arrays.asList(new Edge("Bali National Golf Club", "Garuda Wisnu Kencana", 25),
                        new Edge("Bali National Golf Club", "Pantai Tanjung Benoa", 12),
                        new Edge("Bali National Golf Club", "Pantai Nusa Dua", 6),
                        new Edge("Bali National Golf Club", "Apurva Kempinski Hotel", 5),
                        new Edge("Bali National Golf Club", "Pantai Pandawa", 14),
                        new Edge("Bali National Golf Club", "Pantai Padang Padang", 36),
                        new Edge("Bali National Golf Club", "Pantai Dreamland", 36),
                        new Edge("Bali National Golf Club", "Beachwalk Walking Centre", 29),
                        new Edge("Bali National Golf Club", "Merah Putih Restaurant", 40),
                        new Edge("Garuda Wisnu Kencana", "Pantai Tanjung Benoa", 29),
                        new Edge("Garuda Wisnu Kencana", "Pantai Nusa Dua", 26),
                        new Edge("Garuda Wisnu Kencana", "Apurva Kempinski Hotel", 29),
                        new Edge("Garuda Wisnu Kencana", "Pantai Pandawa", 18),
                        new Edge("Garuda Wisnu Kencana", "Pantai Padang Padang", 23),
                        new Edge("Garuda Wisnu Kencana", "Pantai Dreamland", 25),
                        new Edge("Garuda Wisnu Kencana", "Beachwalk Walking Centre", 32),
                        new Edge("Garuda Wisnu Kencana", "Merah Putih Restaurant", 44),

                        new Edge("Bali Naional", "", 5),
                        new Edge("", "", 15),
                        new Edge("", "", 5),
                        new Edge("", "", 5),
                        new Edge("", "", 5),
                        new Edge("", "", 5),
                        new Edge("", "", 5),
                        new Edge("", "", 5))
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

        E.sort(Comparator.comparingInt(Edge::getBobot));

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