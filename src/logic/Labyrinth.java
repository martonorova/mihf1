package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Labyrinth {

    private List<List<Field>> fieldMtx = new ArrayList<>();
    private int numTreasure;
    private List<Field> treasureFields = new ArrayList<>();

    private HashMap<Field, SearchGraphNode> expandedNodes = new HashMap<>();
    private HashMap<Field, SearchGraphNode> frontierNodes = new HashMap<>();

    public void loadFieldMtx() {
        Scanner scanner;
        try {

            scanner = new Scanner(new File("input1.txt"));

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return;
        }


        while (scanner.hasNext()) {
            String[] inputs = scanner.nextLine().split(" ");

            if (inputs.length == 1) {

                numTreasure = Integer.parseInt(inputs[0]);
                if (numTreasure != treasureFields.size()) {
                    throw new RuntimeException("Inconsistency between treasureNum and size of treasureFields");
                }

            } else {
                List<Field> row = new ArrayList<>();
                fieldMtx.add(row);

                int rowIdx = fieldMtx.size() - 1;

                for (String layoutCode : inputs) {
                    int columnIdx = row.size(); // nem kell =1, hisz meg nem adtuk hozza az elemet
                    Field field = new Field(rowIdx, columnIdx, Integer.parseInt(layoutCode));
                    row.add(field);

                    if (field.hasTresure()) {
                        treasureFields.add(field);
                    }

                }
            }
        }

        setOrderOfTreasures(getField(0, 0));

    }

    private void setOrderOfTreasures(Field entranceField) {
        treasureFields.sort(new FieldComparatorByDistToGivenField(entranceField));
    }

    public void printFieldMtx() {
        fieldMtx.forEach(fields -> {
            fields.forEach(field -> {
                System.out.print(field + " ");
            });
            System.out.println("");
        });
    }

    public Field getField(int rowIdx, int columnIdx) {
        return fieldMtx.get(rowIdx).get(columnIdx);
    }

    public void runTreasureHunt(Field entranceField, Field exitField) {

        setOrderOfTreasures(entranceField);

        int approximateDistToNextObjective = entranceField.getManhattanDistFrom(treasureFields.get(0));

        SearchGraphNode root = new SearchGraphNode(null, entranceField, 0, approximateDistToNextObjective);
        SearchGraphNode actualNode = root;



        while (!actualNode.getActualField().equals(exitField)) {





        }

    }

    private void expandNode(SearchGraphNode node) {
        expandedNodes.put(node.getActualField(), node);

    }

    private List<Field> getAccessableNeighbours(Field field) {

    }


}
