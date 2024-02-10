package edu.brown.cs.student.main;

import edu.brown.cs.student.main.Creators.CreatorFromRow;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser<T> {
    private Reader reader;
    private CreatorFromRow<T> creator;
    private boolean hasHeader;
    private List<String> header;

    public Parser(Reader inputReader, CreatorFromRow inputCreator, boolean headerBool){
        this.reader = inputReader;
        this.creator = inputCreator;
        this.hasHeader = headerBool;
    }

    //Parsing the data
    public List<T> read() throws FactoryFailureException {
        List<T> readList = new ArrayList<>();
        BufferedReader bf = new BufferedReader(this.reader);
        try {
            String next = bf.readLine();
            if(this.hasHeader) {
                this.header = List.of(next.split(","));
                next = bf.readLine();
            }
            while (next != null) {
                String[] currentRow = next.split(",");
                T toAdd = creator.create(List.of(currentRow));
                readList.add(toAdd);
                next = bf.readLine();
            }
            bf.close();
        } catch (IOException e) {
            System.err.println("Error found: " + e.getMessage());
            System.exit(0);
        }
        return readList;
    }

    //Returning the header
    public List<String> getHeader(){
        return header;
    }
}
