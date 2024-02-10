package edu.brown.cs.student;
import edu.brown.cs.student.main.*;
import edu.brown.cs.student.main.Creators.CreateEarningsDisparity;
import edu.brown.cs.student.main.Creators.CreateStringList;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

public class testSuite {

    //Testing using a StringReader
    @Test
    public void testStrings() throws FactoryFailureException {
        StringReader reader = new StringReader("Hello,my,name,is,Malin");
        Search s = new Search(reader, new CreateStringList(), false);
        Assert.assertEquals(s.search("name"), List.of("Hello", "my", "name", "is", "Malin"));
        Assert.assertEquals(s.search("food"), List.of(""));
        Assert.assertEquals(s.search(""), List.of(""));
    }

    //Testing using empty Strings with StringReader
    @Test
    public void testEmptyStrings() throws FactoryFailureException {
        StringReader reader = new StringReader("");
        Search s = new Search(reader, new CreateStringList(), false);
        Assert.assertEquals(s.search("name"), List.of(""));
        Assert.assertEquals(s.search(""), List.of(""));
    }

    //Testing my own CSV file
    @Test
    public void testMyCSV() throws FactoryFailureException, FileNotFoundException {
        FileReader reader = new FileReader("data/new/friends.csv");
        Search s = new Search(reader, new CreateStringList(), true);
        Assert.assertEquals(s.search("Malin", "Name"), List.of(List.of("Malin", "20", "F")));
        Assert.assertEquals(s.search("Sam", 0), List.of(List.of("Sam", "20", "M"), List.of("Sam", "30", "M")));
        Assert.assertEquals(s.search("Jack", "Name"), List.of());
        Assert.assertEquals(s.search("Jack", "Height"), List.of());
    }

    //Testing my own CSV file which does not have headers
    @Test
    public void testMyCSVNoHeaders() throws FactoryFailureException, FileNotFoundException {
        FileReader reader = new FileReader("data/new/friends_no_headers.csv");
        Search s = new Search(reader, new CreateStringList(), false);
        Assert.assertEquals(s.search("Malin"), List.of(List.of("Malin", "20", "F")));
        Assert.assertEquals(s.search("Sam"), List.of(List.of("Sam", "20", "M"), List.of("Sam", "30", "M")));
    }

    //Testing the earnings disparity CSV, which uses its own creator
    @Test
    public void testEarningsDisparity() throws FactoryFailureException, FileNotFoundException {
        FileReader reader = new FileReader("data/census/dol_ri_earnings_disparity.csv");
        Search s = new Search(reader, new CreateEarningsDisparity(), true);
        Assert.assertEquals(s.search("Black", "Data Type"), List.of(List.of("RI", "Black", "$770.26",
                "30424.80376", "$0.73", "6%")));
        Assert.assertEquals(s.search("Swedish", "Data Type"), List.of());
        Assert.assertEquals(s.search("Swedish", "Nationality"), List.of());
        Assert.assertEquals(s.search("6%", "Employed Percent"), List.of(List.of("RI", "Black", "$770.26",
                "30424.80376", "$0.73", "6%")));
        Assert.assertEquals(s.search("6%", 5), List.of(List.of("RI", "Black", "$770.26",
                "30424.80376", "$0.73", "6%")));
    }

    //Testing using the malformed data
    @Test
    public void testMalformed() throws FactoryFailureException, FileNotFoundException {
        FileReader reader = new FileReader("data/malformed/malformed_signs.csv");
        Search s = new Search(reader, new CreateStringList(), true);
        Assert.assertEquals(s.search("Leo"), List.of(List.of("Leo", "Gabi")));
        Assert.assertEquals(s.search("Virgo"), List.of(List.of("Virgo")));
    }

    //Testing using data with a lot of numbers
    @Test
    public void testStarData() throws FactoryFailureException, FileNotFoundException{
        FileReader reader = new FileReader("data/stars/stardata.csv");
        Search s = new Search(reader, new CreateStringList(), true);
        Assert.assertEquals(s.search("0", 0), List.of(List.of("0","Sol","0","0","0")));
        Assert.assertEquals(s.search("0", 0), List.of(List.of("0","Sol","0","0","0")));
        Assert.assertEquals(s.search("2.97088"), List.of(List.of("871" , "Ollie" , "63.51319" , "2.97088" , "-14.18887")));
    }
}
