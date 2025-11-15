package Exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here

class Match {
    String homeTeam;
    String awayTeam;
    int homeGoals;
    int awayGoals;

    public Match(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

}

class TeamStats implements Comparable<TeamStats>{
    String name;
    int playedGames;
    int wins;
    int draws;
    int losses;
    int goalsFor;
    int goalsAgainst;
    int points;

    public TeamStats(String name) {
        this.name = name;
    }

    public int goalDifference(){
        return goalsFor - goalsAgainst;
    }

    @Override
    public int compareTo(TeamStats other) {
        if(this.points != other.points){
            return Integer.compare(other.points, this.points);
        }
        if(this.goalDifference() != other.goalDifference()){
            return Integer.compare(other.goalDifference(), this.goalDifference());
        }

        return this.name.compareTo(other.name);
    }

    // Team                   P    W    D    L  PTS
    // 1. Liverpool          9    8    0    1   24
    @Override
    public String toString() {
        return String.format("%-15s %4d %4d %4d %4d %4d", name, playedGames, wins, draws, losses, points);
    }
}

class FootballTable {
    List<Match> matches;

    public FootballTable() {
        this.matches = new ArrayList<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals){
        matches.add(new Match(homeTeam,awayTeam,homeGoals,awayGoals));
    }

    public void printTable(){
        Map<String, TeamStats> table = new HashMap<>();

        for(Match m : matches){
            table.putIfAbsent(m.homeTeam, new TeamStats(m.homeTeam));
            table.putIfAbsent(m.awayTeam, new TeamStats(m.awayTeam));

            TeamStats home = table.get(m.homeTeam);
            TeamStats away = table.get(m.awayTeam);

            home.goalsFor += m.homeGoals;
            home.goalsAgainst += m.awayGoals;

            away.goalsFor += m.awayGoals;
            away.goalsAgainst += m.homeGoals;

            home.playedGames++;
            away.playedGames++;

            if(m.homeGoals > m.awayGoals){
                home.wins++;
                home.points += 3;
                away.losses++;
            }else if(m.homeGoals < m.awayGoals){
                away.wins++;
                away.points += 3;
                home.losses++;
            }else{
                home.draws++;
                home.points++;
                away.draws++;
                away.points++;
            }
        }

        List<TeamStats> sortedTeams = new ArrayList<>(table.values());
        Collections.sort(sortedTeams);

        int position = 1;
        for(TeamStats t : sortedTeams){
            System.out.printf("%2d. %s%n", position++, t);
        }

    }


}