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
                        new Edge("Garuda Wisnu Kencana", "Pantai Tanjung Benoa", 29),
                        new Edge("Bali National Golf Club", "Pantai Nusa Dua", 6),
                        new Edge("Garuda Wisnu Kencana", "Pantai Nusa Dua", 26),
                        new Edge("Pantai Tanjung Benoa", "Pantai Nusa Dua", 13),
                        new Edge("Apurva Kempinski Hotel", "Pantai Nusa Dua", 12),
                        new Edge("Apurva Kempinski Hotel", "Pantai Tanjung Benoa", 17),
                        new Edge("Apurva Kempinski Hotel", "Garuda Wisnu Kencana", 20),
                        new Edge("Apurva Kempinski Hotel", "Bali National Golf Club", 5),
                        new Edge("Pantai Pandawa", "Apurva Kempinski Hotel", 11),
                        new Edge("Pantai Pandawa", "Pantai Nusa Dua", 21),
                        new Edge("Pantai Pandawa", "Pantai Tanjung Benoa", 26),
                        new Edge("Pantai Pandawa", "Garuda Wisnu Kencana", 18),
                        new Edge("Pantai Pandawa", "Bali National Golf Club", 14),
                        new Edge("Pantai Padang Padang", "Pantai Pandawa", 29),
                        new Edge("Pantai Padang Padang", "Apurva Kempinski Hotel", 31),
                        new Edge("Pantai Padang Padang", "Pantai Nusa Dua", 38),
                        new Edge("Pantai Padang Padang", "Pantai Tanjung Benoa", 42),
                        new Edge("Pantai Padang Padang", "Garuda Wisnu Kencana", 23),
                        new Edge("Pantai Padang Padang", "Bali National Golf Club", 36),
                        new Edge("Pantai Dreamland", "Pantai Padang Padang", 11),
                        new Edge("Pantai Dreamland", "Pantai Pandawa", 31),
                        new Edge("Pantai Dreamland", "Apurva Kempinski Hotel", 32),
                        new Edge("Pantai Dreamland", "Pantai Nusa Dua", 41),
                        new Edge("Pantai Dreamland", "Pantai Tanjung Benoa", 45),
                        new Edge("Pantai Dreamland", "Garuda Wisnu Kencana", 25),
                        new Edge("Pantai Dreamland", "Bali National Golf Club", 36),
                        new Edge("Beachwalk Shopping Center", "Pantai Dreamland", 58),
                        new Edge("Beachwalk Shopping Center", "Pantai Padang Padang", 57),
                        new Edge("Beachwalk Shopping Center", "Pantai Pandawa", 45),
                        new Edge("Beachwalk Shopping Center", "Apurva Kempinski Hotel", 34),
                        new Edge("Beachwalk Shopping Center", "Pantai Nusa Dua", 31),
                        new Edge("Beachwalk Shopping Center", "Pantai Tanjung Benoa", 33),
                        new Edge("Beachwalk Shopping Center", "Garuda Wisnu Kencana", 32),
                        new Edge("Beachwalk Shopping Center", "Bali National Golf Club", 29),
                        new Edge("Merah Putih Restaurant", "Beachwalk Shopping Center", 23),
                        new Edge("Merah Putih Restaurant", "Pantai Dreamland", 65),
                        new Edge("Merah Putih Restaurant", "Pantai Padang Padang", 68),
                        new Edge("Merah Putih Restaurant", "Pantai Pandawa", 52),
                        new Edge("Merah Putih Restaurant", "Apurva Kempinski Hotel", 52),
                        new Edge("Merah Putih Restaurant", "Pantai Nusa Dua", 44),
                        new Edge("Merah Putih Restaurant", "Pantai Tanjung Benoa", 42),
                        new Edge("Merah Putih Restaurant", "Garuda Wisnu Kencana", 44),
                        new Edge("Merah Putih Restaurant", "Bali National Golf Club", 40))
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

    void kruskalMST(List<Edge> travelGraph) {
        kruskalMinimumTotalTimeSpend = 0;

        travelGraph.sort(Comparator.comparingInt(Edge::getBobot));

        int numberOfEdgeVisited = 0;
        int numberOfNewPlaceVisited;
        List<String> visited = new ArrayList<>();
        for (Edge edge: travelGraph) {
            numberOfNewPlaceVisited = 0;
            if (!visited.contains(edge.getAsal())) {
                numberOfNewPlaceVisited++;
            }

            if (!visited.contains(edge.getTujuan())) {
                numberOfNewPlaceVisited++;
            }

            if (numberOfEdgeVisited + 1 < visited.size() + numberOfNewPlaceVisited) {
                kruskalBestRoute.add(edge);
                kruskalMinimumTotalTimeSpend += edge.getBobot();
                numberOfEdgeVisited++;
                if (!visited.contains(edge.getAsal())) {
                    visited.add(edge.getAsal());
                }

                if (!visited.contains(edge.getTujuan())) {
                    visited.add(edge.getTujuan());
                }
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