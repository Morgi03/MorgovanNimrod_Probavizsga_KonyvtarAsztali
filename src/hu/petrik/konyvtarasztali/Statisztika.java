package hu.petrik.konyvtarasztali;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Statisztika {
    private List<Konyv> konyvek;
    private Connection conn;
    private String DB_DRIVER = "mysql";
    private String DB_HOST = "localhost";
    private String DB_PORT = "3306";
    private String DB_DBNAME = "books";
    private String DB_USER = "root";
    private String DB_PASS = "";

    public Statisztika() throws SQLException {
        konyvek = new ArrayList<>();
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_DBNAME);
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);
        String sql = "SELECT * FROM books";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String title = result.getString("title");
            String author = result.getString("author");
            int publish_year = result.getInt("publish_year");
            int page_count = result.getInt("page_count");
            Konyv konyv = new Konyv(id, title, author, publish_year, page_count);
            konyvek.add(konyv);
        }
    }

    public int pageCountHigherThanFiveThousand() {
        int counter = 0;
        for (Konyv book : konyvek) {
            if (book.getPage_count() > 500) {
                counter++;
            }
        }
        return counter;
    }

    public boolean olderThan1950() {
        for (Konyv book : konyvek) {
            if (book.getPublish_year() < 1950) {
                return true;
            }
        }
        return false;
    }

    public Konyv longestBook() {
        Konyv legnagyobb = konyvek.get(0);
        for (Konyv book : konyvek) {
            if (legnagyobb.getPage_count() < book.getPage_count()) {
                legnagyobb = book;
            }
        }
        return legnagyobb;
    }

    public String authorWithMostBooks() {
        List<String> szerzok = new ArrayList<>();
        List<Integer> szerzok_konyvei = new ArrayList<>();
        for (Konyv book : konyvek) {
            if (!szerzok.contains(book.getAuthor())) {
                szerzok.add(book.getAuthor());
            }
        }
        int counter = 0;
        for (String s : szerzok) {
            for (Konyv book : konyvek) {
                if (book.getAuthor().equals(s)) {
                    counter++;
                }
            }
            szerzok_konyvei.add(counter);
            counter = 0;
        }
        int max = szerzok_konyvei.get(0);
        for (Integer x : szerzok_konyvei) if (x > max) max = x;
        int index = szerzok_konyvei.indexOf(max);
        return szerzok.get(index);
    }

    public void whoIsTheAuthor() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Adjon meg egy könyv címet: ");
        String book = sc.nextLine().trim();
        String author = "";
        for (Konyv konyv : konyvek) {
            if (konyv.getTitle().equals(book)) {
                author = konyv.getAuthor();
            }
        }
        System.out.println("\n");
        if (author.isEmpty()) {
            System.out.println("Nincs ilyen könyv");
            return;
        }
        System.out.println("Az megadott könyv szerzője " + author);
    }


}
