package Exercises;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Time {
    int hours;
    int minutes;
    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public String to24Hour(){
        return String.format("%02d:%02d", hours, minutes);
    }

    public String toAMPM(){

        int h = hours % 12;
        if(h == 0){
            h = 12;
        }

        String suffix = (hours < 12) ? "AM" : "PM";

        return String.format("%02d:%02d %s", h, minutes, suffix);
    }

}

class UnsupportedFormatException extends Exception{
    public UnsupportedFormatException(String message) {
        super(message);
    }
}

class InvalidTimeException extends Exception{
    public InvalidTimeException(String message) {
        super(message);
    }
}

class TimeTable{
    List<Time> times;

    public TimeTable() {
        this.times = new ArrayList<>();
    }

    public void readTimes(InputStream inputStream) throws IOException, UnsupportedFormatException, InvalidTimeException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while((line = br.readLine()) != null){

            if(!(line.contains(":") || line.contains("."))){
                throw new UnsupportedFormatException(line);
            }

            String [] parts = line.split("[: .]");

            if(parts.length != 2){
                throw new UnsupportedFormatException(line);
            }

            int hours, minutes;
            try {
                hours = Integer.parseInt(parts[0]);
                minutes = Integer.parseInt(parts[1]);
            }catch (NumberFormatException e){
                throw new UnsupportedFormatException(line);
            }

            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
                throw new InvalidTimeException(line);
            }

            times.add(new Time(hours, minutes));
        }
    }

    public void writeTimes(OutputStream outputStream, TimeFormat format){
        PrintWriter pw = new PrintWriter(outputStream);

        times.sort((t1,t2) -> {
            if(t1.hours != t2.hours) return Integer.compare(t2.hours, t1.hours);
            return Integer.compare(t2.minutes, t1.minutes);
        });

        for(Time t : times){
            String output = "";

            if(format == TimeFormat.FORMAT_24){
                output = t.to24Hour();
            }else if(format == TimeFormat.FORMAT_AMPM){
                output = t.toAMPM();
            }
            pw.println(output);
        }
        pw.flush();
    }
}

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}