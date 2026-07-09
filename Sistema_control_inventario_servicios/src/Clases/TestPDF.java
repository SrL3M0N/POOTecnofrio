package Clases;

import com.lowagie.text.Document;

public class TestPDF {
    public static void main(String[] args) {
        Document d = new Document();
        System.out.println("OK iText 2.0.8");
        System.out.println(System.getProperty("user.home"));
    }
}
