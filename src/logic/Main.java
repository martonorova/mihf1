package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Labyrinth labyrinth = new Labyrinth();
        labyrinth.loadFieldMtx();
//        labyrinth.printFieldMtx();
//
//        System.out.println(labyrinth.getField(0, 0).getManhattanDistFrom(labyrinth.getField(2, 2)));
//
//        Field end = labyrinth.getField(2, 2);
//        ArrayList<Field> firstRow = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            firstRow.add(labyrinth.getField(0, i));
//        }
//
//        firstRow.forEach(System.out::print);
//        System.out.println();
//
//        firstRow.sort(new FieldComparatorByDistToGivenField(labyrinth.getField(0, 0)));
//
//        firstRow.forEach(System.out::print);

        labyrinth.start();


    }
}
