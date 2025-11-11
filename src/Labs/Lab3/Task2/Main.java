package Labs.Lab3.Task2;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// todo: complete the implementation of the Ad, AdRequest, and AdNetwork classes

class Ad implements Comparable<Ad> {
    private String id;
    private String category;
    private double bidValue;
    private double ctr;
    private String content;

    public Ad(String id, String category, double bidValue, double ctr, String content) {
        this.id = id;
        this.category = category;
        this.bidValue = bidValue;
        this.ctr = ctr;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public double getBidValue() {
        return bidValue;
    }

    public double getCtr() {
        return ctr;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int compareTo(Ad o) {
        int bidValueCompare = Double.compare(o.getBidValue(), getBidValue());
        if (bidValueCompare == 0) {
            return getId().compareToIgnoreCase(o.getId());
        }
        return bidValueCompare;
    }

    @Override
    public String toString() {
        return String.format("%s %s (bid=%.2f, ctr=%.2f%%) %s", id, category, bidValue, ctr, content);
    }
}

class AdRequest {
    private String id;
    private String category;
    private double floorBid;
    private String keywords;

    public AdRequest(String id, String category, double floorBid, String keywords) {
        this.id = id;
        this.category = category;
        this.floorBid = floorBid;
        this.keywords = keywords;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] (floor=%.2f): %s", id, category, floorBid, keywords);
    }
}

class AdNetwork {

    private ArrayList<Ad> ads;
    private int size = 0;

    public AdNetwork() {
        ads = new ArrayList<>(size);
        size++;
    }

    public void readAds(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split("\\s");

            if(parts.length < 5) continue;
            if (!parts[0].startsWith("AD")) break;


            String id = parts[0];
            String category = parts[1];
            double bid_value = Double.parseDouble(parts[2]);
            double ctr = Double.parseDouble(parts[3]);
            String content = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));;

            ads.add(new Ad(id, category, bid_value, ctr, content));
        }
    }
    public List<Ad> placeAds(BufferedReader br, int k, PrintWriter pw) throws IOException {
        final double X = 5.0;
        final double Y = 100.0;
        List<Ad> selectedAds = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            String id = parts[0];
            String category = parts[1];
            double floorBid = Double.parseDouble(parts[2]);

            String keywords = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));

            AdRequest request = new AdRequest(id, category, floorBid, keywords);

            // 1. FILTER ADS WITH BID >= FLOORBID
            List<Ad> matchingAds = new ArrayList<>();
            for (Ad ad : this.ads) {
                if (ad.getBidValue() >= floorBid) {
                    matchingAds.add(ad);
                }
            }

            // 2. Compute totalScore for each and  store in a map
            Map<Ad, Double> scoreMap = new HashMap<>();
            for (Ad ad : matchingAds) {
                int relevance = relevanceScore(ad, request);
                double totalScore = relevance + X * ad.getBidValue() + Y * ad.getCtr();
                scoreMap.put(ad, totalScore);
            }

            // 3. Sort by totalScore Descending
            matchingAds.sort((a, b) -> Double.compare(scoreMap.get(b), scoreMap.get(a)));

            // 4. Take top K and sort by natural order
            List<Ad> topK = matchingAds.stream().limit(k).collect(Collectors.toList());
            List<Ad> sortedTopK = new ArrayList<>(topK);
            Collections.sort(sortedTopK);

            selectedAds.addAll(sortedTopK);

            pw.printf("Top ads for reques %s:%n", request.getId());
            for (Ad ad : sortedTopK) {
                pw.println(ad);
            }
            pw.println();
        }
        pw.flush();

        return selectedAds;
    }
    private int relevanceScore(Ad ad, AdRequest req) {
        int score = 0;
        if (ad.getCategory().equalsIgnoreCase(req.getCategory())) score += 10;
        String[] adWords = ad.getContent().toLowerCase().split("\\s+");
        String[] keywords = req.getKeywords().toLowerCase().split("\\s+");
        for (String kw : keywords) {
            for (String aw : adWords) {
                if (kw.equals(aw)) score++;
            }
        }
        return score;
    }
}


public class Main {
    public static void main(String[] args) throws IOException {
        AdNetwork network = new AdNetwork();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

        int k = Integer.parseInt(br.readLine().trim());

        if (k == 0) {
            network.readAds(br);
            network.placeAds(br, 1, pw);
        } else if (k == 1) {
            network.readAds(br);
            network.placeAds(br, 3, pw);
        } else {
            network.readAds(br);
            network.placeAds(br, 8, pw);
        }

        pw.flush();
    }
}