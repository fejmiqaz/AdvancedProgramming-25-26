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
        this.ctr = ctr * 100;
        this.content = content;
    }

    public String getId() { return id; }
    public String getCategory() { return category; }
    public double getBidValue() { return bidValue; }
    public double getCtr() { return ctr; }
    public String getContent() { return content; }

    @Override
    public int compareTo(Ad o) {
        int cmp = Double.compare(o.bidValue, this.bidValue);
        if (cmp != 0) return cmp;
        return this.id.compareTo(o.id);
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

    public String getId() { return id; }
    public String getCategory() { return category; }
    public double getFloorBid() { return floorBid; }
    public String getKeywords() { return keywords; }

    @Override
    public String toString() {
        return String.format("%s [%s] (%.2f): %s", id, category, floorBid, keywords);
    }
}

class AdNetwork {
    private List<Ad> ads = new ArrayList<>();

    public AdNetwork() {}

    public void readAds(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length < 5) continue;
            String id = parts[0];
            String category = parts[1];
            double bidValue = Double.parseDouble(parts[2]);
            double ctr = Double.parseDouble(parts[3]);
            String content = String.join(" ", Arrays.copyOfRange(parts, 4, parts.length));

            ads.add(new Ad(id, category, bidValue, ctr, content));
        }
    }

    public List<Ad> placeAds(BufferedReader br, int k, PrintWriter pw) throws IOException {
        final double X = 5.0;
        final double Y = 100.0;

        String line = br.readLine();
        if (line == null || line.isEmpty()) return Collections.emptyList();

        String[] parts = line.split("\\s+");
        String id = parts[0];
        String category = parts[1];
        double floorBid = Double.parseDouble(parts[2]);
        String keywords = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));

        AdRequest request = new AdRequest(id, category, floorBid, keywords);

        List<Ad> eligibleAds = ads.stream()
                .filter(ad -> ad.getBidValue() >= request.getFloorBid())
                .collect(Collectors.toList());

        List<ScoredAd> scoredAds = new ArrayList<>();
        for (Ad ad : eligibleAds) {
            double totalScore = relevanceScore(ad, request)
                    + X * ad.getBidValue()
                    + Y * ad.getCtr();
            scoredAds.add(new ScoredAd(ad, totalScore));
        }

        scoredAds.sort((a, b) -> Double.compare(b.score, a.score));

        List<Ad> topK = scoredAds.stream()
                .limit(k)
                .map(ScoredAd::getAd)
                .collect(Collectors.toList());

        Collections.sort(topK);

        pw.printf("Top ads for request %s:%n", request.getId());
        for (Ad ad : topK) {
            pw.println(ad);
        }
        pw.flush();

        return topK;
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
class ScoredAd {
    Ad ad;
    double score;

    ScoredAd(Ad ad, double score) {
        this.ad = ad;
        this.score = score;
    }

    public Ad getAd() {
        return ad;
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
