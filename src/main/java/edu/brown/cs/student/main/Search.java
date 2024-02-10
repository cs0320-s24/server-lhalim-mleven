package edu.brown.cs.student.main;

import edu.brown.cs.student.main.Creators.CreatorFromRow;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Search<T> {

    private List<List<String>> parsedData;
    private boolean hasHeaders;
    private MyParser parser;

    public Search(Reader inputReader, CreatorFromRow inputCreator, boolean headersBool) throws FactoryFailureException {
        this.parser = new MyParser<>(inputReader, inputCreator, headersBool);
        this.parsedData = parser.read();
        this.hasHeaders = headersBool;
    }

    //Search using a target and index
    public List<List<T>> search(String target, int index){
        if(index > this.parsedData.get(0).size()){
            System.err.println("Index out of bounds");
            System.exit(0);
        }
        List<List<T>> found = new ArrayList<>();
        for (List row : this.parsedData){
            if (row.get(index).equals(target)){
                System.out.println(row);
                found.add(row);
            }
        }
        if(found.isEmpty()){
            System.err.println("Target not found");
        }
        return found;
    }

    //Search using a target and column name
    public List<List<T>> search(String target, String column){
        List<List<T>> found = new ArrayList<>();
        try {
            int index = this.parser.getHeader().indexOf(column);
            for (List row : this.parsedData){
                if (row.get(index).equals(target)){
                    System.out.println(row);
                    found.add(row);
                }
            }

        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Column not found");
        }
        if(found.isEmpty()){
            System.err.println("Target not found");
        }
        return found;
    }

    //Search using just a target
    public List<List<T>> search(String target){
        List<List<T>> found = new ArrayList<>();
        for (int row = 0; row < this.parsedData.size(); row ++){
            for (int col = 0; col < this.parsedData.get(row).size(); col++){
                String current = this.parsedData.get(row).get(col);
                if (target.equals(current)){
                    List foundRow = this.parsedData.get(row);
                    System.out.println(foundRow);
                    found.add(foundRow);
                }
            }
        }
        if(found.isEmpty()){
            System.err.println("Target not found");
        }
        return found;
    };

}
