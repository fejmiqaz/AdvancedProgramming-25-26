package Labs.Lab5.Task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.MemoryManagerMXBean;
import java.util.*;
import java.util.stream.Collectors;

// todo: implement the necessary classes

class Book {
    String isbn;
    String title;
    Integer year;
    String author;
    int numCopies;
    int totalBorrows;
    Queue<Member> waitingList;

    public Book(String isbn, String title, String author, Integer year) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.author = author;
        numCopies = 1;
        totalBorrows = 0;
        this.waitingList = new LinkedList<>();
    }

    public void increaseNumCopies() {
        this.numCopies += 1;
    }

    public void decreaseNumCopies(){
        this.numCopies -= 1;
    }

    public void increaseNumBorrows(){
        this.totalBorrows++;
    }
    public void decreaseNumBorrows(){
        this.totalBorrows--;
    }

    public int numBorrows(){
         return totalBorrows;
    }

    public Integer getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%s - \"%s\" by %s (%d), available: %d, total borrows: %d",isbn, title,author,year,numCopies, totalBorrows);
    }
}

class Member {
    String id;
    String name;
    int totalBorrows;
    List<Book> books;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.totalBorrows = 0;
        books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int numBorrowedBooks(){
        return books.size();
    }

    public int getTotalBorrows(){
        return totalBorrows;
    }

    public void increaseMemberBookBorrows(){
        this.totalBorrows++;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - borrowed now: %d, total borrows: %d", name, id, books.size(), getTotalBorrows());
    }
}


class LibrarySystem {
    String name;
    Map<String, Member> members;
    Map<String, Book> books;

    public LibrarySystem(String name) {
        this.name = name;
        members = new HashMap<>();
        books = new HashMap<>();
    }

    public void registerMember(String id, String fullName){
        members.putIfAbsent(id, new Member(id,fullName));
    }

    public void addBook(String isbn, String title, String author, int year){
        Book book = books.get(isbn);
        if(book != null){
            book.increaseNumCopies();
        }else{
            books.put(isbn, new Book(isbn,title,author,year));
        }
    }

    public void borrowBook(String memberId, String isbn){
        Book book = books.get(isbn);
        Member member = members.get(memberId);
        if(book == null || member == null) return;

        if(book.numCopies == 0){
            book.waitingList.add(member);
            return;
        }

        member.books.add(book);
        book.decreaseNumCopies();
        book.increaseNumBorrows();
        member.increaseMemberBookBorrows();
    }
    public void returnBook(String memberId, String isbn){
        Book book = books.get(isbn);
        Member member = members.get(memberId);

        member.books.remove(book);

        book.increaseNumCopies();

        if(!book.waitingList.isEmpty()){
            Member waitingMember = book.waitingList.poll();
            waitingMember.books.add(book);
            waitingMember.increaseMemberBookBorrows();
            book.decreaseNumCopies();
            book.increaseNumBorrows();
        }

    }

    public void printMembers(){
        members.values().stream().sorted(
                Comparator.comparingInt(Member::numBorrowedBooks).reversed()
                        .thenComparing(Member::getName)
        ).forEach(System.out::println);
    }

    public void printBooks(){
        books.values().stream().sorted(Comparator.comparingInt(Book::numBorrows).reversed()
                .thenComparingInt(Book::getYear))
                .forEach(System.out::println);
    }

    public void printBookCurrentBorrowers(String isbn){
        Book book = books.get(isbn);

        List<String> borrowerIds = members.values().stream().filter(m->m.books.contains(book))
                .map(m -> m.id)
                .sorted()
                .collect(Collectors.toList());

        System.out.println(String.join(", ", borrowerIds));

    }

    public void printTopAuthors(){
        books.values().stream()
                .collect(Collectors.groupingBy(
                        b -> b.author, Collectors.summingInt(b->b.totalBorrows)
                )).entrySet()
                .stream()
                .sorted(
                        Map.Entry.<String,Integer>comparingByValue().reversed()
                                .thenComparing(Map.Entry::getKey)
                )
                .forEach((Map.Entry<String,Integer> e) ->
                        System.out.println(e.getKey() + " - " + e.getValue()));
    }
}

public class LibraryTester {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String libraryName = br.readLine();
            //   System.out.println(libraryName); //test
            if (libraryName == null) return;

            libraryName = libraryName.trim();
            LibrarySystem lib = new LibrarySystem(libraryName);

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.equals("END")) break;
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");

                switch (parts[0]) {

                    case "registerMember": {
                        lib.registerMember(parts[1], parts[2]);
                        break;
                    }

                    case "addBook": {
                        String isbn = parts[1];
                        String title = parts[2];
                        String author = parts[3];
                        int year = Integer.parseInt(parts[4]);
                        lib.addBook(isbn, title, author, year);
                        break;
                    }

                    case "borrowBook": {
                        lib.borrowBook(parts[1], parts[2]);
                        break;
                    }

                    case "returnBook": {
                        lib.returnBook(parts[1], parts[2]);
                        break;
                    }

                    case "printMembers": {
                        lib.printMembers();
                        break;
                    }

                    case "printBooks": {
                        lib.printBooks();
                        break;
                    }

                    case "printBookCurrentBorrowers": {
                        lib.printBookCurrentBorrowers(parts[1]);
                        break;
                    }

                    case "printTopAuthors": {
                        lib.printTopAuthors();
                        break;
                    }

                    default:
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
