package com.example.jajal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jajal.databinding.ActivitySelectPlaceBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SelectPlaceActivity extends AppCompatActivity {

    int bruteMinimumTotalTimeSpend, greedyMinimumTotalTimeSpend;
    long bruteExeTime, greedyExeTime;
    List<String> bruteBestRoute;
    List<String> greedyBestRoute = new ArrayList<>();
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
//            Toast.makeText(getApplicationContext(), "Here", Toast.LENGTH_LONG).show();
            if (!checkResultState) {
                inputUserPlaceList();
                binding.checkResultButton.setText("Back");
                binding.result.setVisibility(View.VISIBLE);
                binding.selectPlaceList.setVisibility(View.GONE);
                calculateResult();
                checkResult("brute");
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
            binding.buttonGreedy.setBackgroundColor(getColor(R.color.secondary));

//            clearResult();
            binding.resultContent.setVisibility(View.INVISIBLE);
            checkResult("brute");
            binding.resultContent.setVisibility(View.VISIBLE);
        });

        binding.buttonGreedy.setOnClickListener(v -> {
            binding.buttonGreedy.setBackgroundColor(getColor(R.color.primary));
            binding.buttonBruteforce.setBackgroundColor(getColor(R.color.secondary));

//            clearResult();
            binding.resultContent.setVisibility(View.INVISIBLE);
            checkResult("greedy");
            binding.resultContent.setVisibility(View.VISIBLE);
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
            userPlaceList.add("Beachwalk Shopping Center");
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

//    void clearResult() {
//        greedyMinimumTotalTimeSpend = 0;
//        bruteMinimumTotalTimeSpend = 0;
//        bruteExeTime = 0;
//        greedyExeTime = 0;
//        greedyBestRoute.clear();
//        bruteBestRoute.clear();
//    }

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

        greedyMinimumTotalTimeSpend = 0;
        bruteMinimumTotalTimeSpend = 0;
        bruteExeTime = 0;
        greedyExeTime = 0;
        userPlaceList.clear();
        greedyBestRoute.clear();
        bruteBestRoute.clear();
    }

    void calculateResult() {
        long start, end;

        bruteMinimumTotalTimeSpend = 999;
        List<String> tempPlaceList = new ArrayList<>(userPlaceList);
        start = System.currentTimeMillis();
        bruteForceEulerPath(tempPlaceList, 0);
        end = System.currentTimeMillis();
        bruteExeTime = end - start;

        start = System.currentTimeMillis();
        greedyEulerPath(userPlaceList);
        end = System.currentTimeMillis();
        greedyExeTime = end - start;
    }

    void checkResult(String algorithm){
        long start, end;

        if (algorithm.equals("brute")) {
//            start = System.currentTimeMillis();
//            List<String> tempPlaceList = new ArrayList<>(userPlaceList);
//            bruteForceEulerPath(tempPlaceList, 0);
//            end = System.currentTimeMillis();
//            bruteExeTime = end - start;
            binding.waktuEksekusi.setText(String.format("Waktu eksekusi: %d ms", bruteExeTime));

            String teksRute;
            teksRute = "Pilihan Rute:";
            for (int i = 0; i < bruteBestRoute.size(); i++) {
                teksRute += "\n";
                teksRute += String.valueOf(i + 1);
                teksRute += ". ";
                teksRute += bruteBestRoute.get(i);
            }
            teksRute += "\n";
            binding.rute.setText(teksRute);

            String teksWaktuRute = "Total Waktu Rute: ";
            teksWaktuRute += String.valueOf(bruteMinimumTotalTimeSpend);
            teksWaktuRute += " menit";
            binding.waktuRute.setText(teksWaktuRute);

            binding.hasilOptimal.setText("");
        }
        else {
//            start = System.currentTimeMillis();
//            greedyEulerPath(userPlaceList);
//            end = System.currentTimeMillis();
//            greedyExeTime = end - start;
            binding.waktuEksekusi.setText(String.format("Waktu eksekusi: %d ms", greedyExeTime));

            String teksRute;
            teksRute = "Pilihan Rute:";
            for (int i = 0; i < greedyBestRoute.size(); i++) {
                teksRute += "\n";
                teksRute += String.valueOf(i + 1);
                teksRute += ". ";
                teksRute += greedyBestRoute.get(i);
            }
            teksRute += "\n";
            binding.rute.setText(teksRute);

            String teksWaktuRute = "Total Waktu Rute: ";
            teksWaktuRute += String.valueOf(greedyMinimumTotalTimeSpend);
            teksWaktuRute += " menit";
            binding.waktuRute.setText(teksWaktuRute);

            if (bruteMinimumTotalTimeSpend == greedyMinimumTotalTimeSpend) {
                binding.hasilOptimal.setText("Optimal: Ya");
            } else {
                binding.hasilOptimal.setText("Optimal: Tidak");
            }

        }



    }

//    List<Edge> createTravelGraph(List<String> placeList) {
//        List<Edge> travelGraph = new ArrayList<>();
//        for (int i = 0; i < placeList.size() - 1; i++) {
//            for (int j = i + 1; j < placeList.size(); j++) {
//                travelGraph.add(searchEdgeData(placeList.get(i), placeList.get(j)));
//            }
//        }
//
//        return travelGraph;
//    }

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

    void greedyEulerPath(List<String> userPlaceList) {

        List<String> unvisitPlaceList = new ArrayList<>(userPlaceList);

        int numOfEdgesVisited = 1;
        greedyBestRoute.add(unvisitPlaceList.get(0));
        unvisitPlaceList.remove(unvisitPlaceList.get(0));

        while (numOfEdgesVisited < userPlaceList.size()) {
            int min = 999;
            String minPlace = "";
            String minPosition = "";
            for (String place: unvisitPlaceList) {
                if (searchEdgeData(greedyBestRoute.get(0), place).getBobot() < min) {
                    minPlace = place;
                    min = searchEdgeData(greedyBestRoute.get(0), place).getBobot();
                    minPosition = "left";
                }

                if(searchEdgeData(greedyBestRoute.get(numOfEdgesVisited - 1), place).getBobot() < min) {
                    minPlace = place;
                    min = searchEdgeData(greedyBestRoute.get(numOfEdgesVisited - 1), place).getBobot();
                    minPosition = "right";
                }
            }

            if (minPosition.equals("left")) {
                greedyBestRoute.add(0, minPlace);
            } else {
                greedyBestRoute.add(minPlace);
            }

            greedyMinimumTotalTimeSpend += min;
            unvisitPlaceList.remove(minPlace);
            numOfEdgesVisited++;
        }


    }

    void bruteForceEulerPath(List<String> placeList, int k) {

        for (int i = k; i < placeList.size(); i++) {
            java.util.Collections.swap(placeList, i, k);
            bruteForceEulerPath(placeList, k + 1);
            java.util.Collections.swap(placeList, k, i);
        }
        if (k == placeList.size() - 1) {
            int t = computeTotalTimeSpend(placeList);
            if (t < bruteMinimumTotalTimeSpend) {
                bruteBestRoute = new ArrayList<>(placeList);
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