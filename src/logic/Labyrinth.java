package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Labyrinth {

    private List<List<Field>> fieldMtx = new ArrayList<>();
    private int numTreasure;
    private LinkedList<Field> objectiveFields = new LinkedList<>();

    private HashMap<Field, SearchGraphNode> expandedNodes = new HashMap<>();
    private List<SearchGraphNode> frontierNodes = new ArrayList<>();

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
                if (numTreasure != objectiveFields.size()) {
                    throw new RuntimeException(String.format("Inconsistency between numTreasure: %d and size of objectiveFields: %d", numTreasure, objectiveFields.size()));
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
                        objectiveFields.add(field);
                        numTreasure++;
                    }

                }
            }
        }

        setOrderOfTreasures(getField(0, 0));
        createFieldGraph();

        System.out.println("LOAD DONE");

    }

    private void setOrderOfTreasures(Field entranceField) {
        objectiveFields.sort(new FieldComparatorByDistToGivenField(entranceField));
    }

    public void printFieldMtx() {
        fieldMtx.forEach(fields -> {
            fields.forEach(field -> {
                System.out.print(field + " ");
            });
            System.out.println();
        });
    }

    public Field getField(int rowIdx, int columnIdx) {
        return fieldMtx.get(rowIdx).get(columnIdx);
    }

    public void start() {
        Field exitField = getField(fieldMtx.size() - 1, fieldMtx.get(0).size() - 1);
        objectiveFields.add(exitField);
        runTreasureHunt(getField(0, 0), exitField);
    }

    public void runTreasureHunt(Field entranceField, Field exitField) {


        SearchGraphNode root = new SearchGraphNode(
                null,
                entranceField,
                0,
                approximateDistToNextObjective(entranceField));

        expandNode(root);

        SearchGraphNode actualNode = root;


        System.out.println("BEFORE WHILE IN RUN");
        int valami = 0;

        while (!actualNode.getActualField().equals(exitField)) {
            System.out.println(actualNode.getActualField());
            int minDist = Integer.MAX_VALUE;

            for (SearchGraphNode frontierNode : frontierNodes) {
                int approximateDistWithThisNode = frontierNode.getDistanceToHere() + frontierNode.getApproxDistanceToNextObjective();
                if (approximateDistWithThisNode < minDist) {
                    minDist = approximateDistWithThisNode;
                    actualNode = frontierNode;
                }
            }

            expandNode(actualNode);

        }

        System.out.println("AFTER WHILE IN RUN");

        // TODO vegigertunk
        printResult(actualNode);

    }

    private void printResult(SearchGraphNode endNode) {
        List<SearchGraphNode> result = new ArrayList<>();

        SearchGraphNode actualNode = endNode;
        System.out.println("BEFORE WHILE IN PRINT");

        while (actualNode.getParentNode() != null) {
            result.add(actualNode);
            actualNode = actualNode.getParentNode();

        }

        System.out.println("AFTER WHILE IN PRINT");

        for (int i = result.size() - 1; i >= 0 ; i--) {
            System.out.println(result.get(i).getActualField());
            if (result.get(i).isTreasureFound()) {
                System.out.println("felvesz");
            }

        }
    }

    private void expandNode(SearchGraphNode node) {

        if (node.getActualField().hasTresure()) {
            objectiveFields.removeFirst();
            node.setTreasureFound(true);
        }

        frontierNodes.remove(node);

        expandedNodes.put(node.getActualField(), node);

        node.getActualField().getNeighbours().forEach(neighbour -> {

            SearchGraphNode possibleFrontierNode = new SearchGraphNode(
                    node,
                    neighbour,
                    node.getDistanceToHere() + 1,
                    approximateDistToNextObjective(neighbour));

            if (!expandedNodes.containsKey(neighbour) && !frontierNodes.contains(possibleFrontierNode)) {
                frontierNodes.add(possibleFrontierNode);
            }
        });

    }

    private int approximateDistToNextObjective(Field fromField) {
        //TODO visszaallitani
        //getField(fieldMtx.size() - 1, fieldMtx.get(0).size() - 1)
        return fromField.getManhattanDistFrom(objectiveFields.get(0));
    }

    private void createFieldGraph() {
        for (int rowIdx = 0; rowIdx < fieldMtx.size(); rowIdx++) {
            List<Field> actualRow = fieldMtx.get(rowIdx);

            for (int columnIdx = 0; columnIdx < actualRow.size(); ++columnIdx) {
                Field actualField = actualRow.get(columnIdx);

                if (columnIdx < actualRow.size() - 1) {
                    actualField.addNeighbour(getField(rowIdx, columnIdx + 1));
                    getField(rowIdx, columnIdx + 1).addNeighbour(actualField);
                }

                if (rowIdx < fieldMtx.size() - 1) {
                    actualField.addNeighbour(getField(rowIdx + 1, columnIdx));
                    getField(rowIdx + 1, columnIdx).addNeighbour(actualField);
                }
            }
        }
    }
}
