package Labs.Lab4.Task3;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

class TermFrequency {
    private final InputStream inputStream;
    private final String[] stopWords;
    private final List<String> input = new ArrayList<>();

    public TermFrequency(InputStream inputStream, String[] stopWords) throws IOException {
        this.stopWords = stopWords;
        this.inputStream = inputStream;

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = br.readLine()) != null){
            input.add(line);
        }
    }

    public int countTotal() throws IOException {
        int cnt = 0;

        for(String line : input){
            String [] parts = line.split("\\s+");

            for(String part : parts){
                part = part.replaceAll("[.,]", "");
                if(part.isEmpty()) continue;

                boolean isStopWord = false;
                for(String stop : stopWords){
                    if(stop.equalsIgnoreCase(part)){
                        isStopWord = true;
                        break;
                    }
                }
                if(!isStopWord){
                    cnt++;
                }

            }
        }
        return cnt;
    }

    public int countDistinct() throws IOException {
        Set<String> set = new HashSet<>();

        for(String line : input){
            String [] parts = line.split("\\s+");

            for(String part : parts){
                part = part.replaceAll("[,.]", "");
                if(part.isEmpty()) continue;

                boolean isStopWord = false;
                for(String stop : stopWords){
                    if(stop.equalsIgnoreCase(part)){
                        isStopWord = true;
                        break;
                    }
                }
                if(!isStopWord){
                    set.add(part.toLowerCase());
                }
            }
        }
        return set.size();
    }

    public List<String> mostOften(int k){
        Map<String, Integer> freq = new HashMap<>();

        for(String line : input){
            String [] parts = line.split("\\s+");

            for(String part : parts){
                part = part.replaceAll("[,.]", "");
                if(part.isEmpty()) continue;

                boolean isStopWord = false;
                for(String stop : stopWords){
                    if(stop.equalsIgnoreCase(part)){
                        isStopWord = true;
                        break;
                    }
                }
                if(!isStopWord){
                    String key = part.toLowerCase();
                    freq.put(key, freq.getOrDefault(key,0)+1);
                }
            }
        }

        List<Map.Entry<String,Integer>> sorted = new ArrayList<>(freq.entrySet());

        sorted.sort((a,b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue());
            if(cmp !=0) return cmp;
            return a.getKey().compareTo(b.getKey());
        });

        List<String> rez = new ArrayList<>();

        for(int i = 0; i < Math.min(k, sorted.size()); i++){
            rez.add(sorted.get(i).getKey());
        }
        return rez;
    }

}

public class TermFrequencyTest {
    public static void main(String[] args) throws IOException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde
