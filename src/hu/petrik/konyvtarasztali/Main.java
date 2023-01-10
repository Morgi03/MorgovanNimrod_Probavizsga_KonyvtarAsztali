package hu.petrik.konyvtarasztali;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Statisztika st = new Statisztika();
            System.out.println("500 oldalnál hosszabb könyvek száma: "+st.pageCountHigherThanFiveThousand());
            System.out.println(st.olderThan1950() ? "Van 1950-nél régebbi könyv" : "Nincs 1950-nél régebbi könyv");
            System.out.println("A leghosszabb Könyv:\n"+st.longestBook());
            System.out.println("A legtöbb könyvvel rendelkező szerző: "+st.authorWithMostBooks());
            st.whoIsTheAuthor();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
