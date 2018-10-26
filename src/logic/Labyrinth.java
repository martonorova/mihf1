package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Labyrinth {

    private List<List<Field>> fieldMtx = new ArrayList<>();
    private int numTreasure;
    private LinkedList<Field> objectiveFields = new LinkedList<>();

   // private HashMap<Integer, SearchGraphNode> expandedNodes = new HashMap<>();
    private HashMap<Field, SearchGraphNode> expandedNodes = new HashMap<>();


    private HashMap<Integer, SearchGraphNode> frontierNodes = new HashMap<>();


    public void loadFieldMtx() {
        Scanner scanner;
        //try {
            scanner = new Scanner(System.in);
            //scanner = new Scanner(new File("input3.txt"));

      //} catch (FileNotFoundException ex) {
      //      ex.printStackTrace();
      //      return;
      //  }


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
        //objectiveFields.addLast(exitField);
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

        while (!actualNode.getActualField().equals(exitField) || !objectiveFields.isEmpty()) {
            //System.out.println(actualNode.getActualField());
            //System.out.println(actualNode);
            int minDist = Integer.MAX_VALUE;

            for (Integer nodeId : frontierNodes.keySet()) {

                SearchGraphNode frontierNode = frontierNodes.get(nodeId);

                int approximateDistWithThisNode = frontierNode.getDistanceToHere() + frontierNode.getApproxDistanceToNextObjective();
                if (approximateDistWithThisNode < minDist) {
                    minDist = approximateDistWithThisNode;
                    actualNode = frontierNode;
                }
            }

            expandNode(actualNode);

        }

        // TODO vegigertunk
        printResult(actualNode);

    }

    private void printResult(SearchGraphNode endNode) {
        List<SearchGraphNode> result = new ArrayList<>();

        SearchGraphNode actualNode = endNode;

        while (actualNode.getParentNode() != null) {
            result.add(actualNode);
            actualNode = actualNode.getParentNode();

        }

        for (int i = result.size() - 1; i >= 0 ; i--) {
            System.out.println(result.get(i).getActualField());
            if (result.get(i).isTreasureFound()) {
                System.out.println("felvesz");
            }

        }
    }

    private void expandNode(SearchGraphNode node) {

        if (node.getActualField().hasTresure()) {
            node.getActualField().collectTreasure();
            objectiveFields.removeFirst();
            node.setTreasureFound(true);
            expandedNodes.clear();
            frontierNodes.clear();
        }

        frontierNodes.remove(node.getId());

       // expandedNodes.put(node.getId(), node);
        expandedNodes.put(node.getActualField(), node);

//        node.getActualField().getNeighbours().forEach(neighbour -> {
//
//            SearchGraphNode possibleFrontierNode = new SearchGraphNode(
//                    node,
//                    neighbour,
//                    node.getDistanceToHere() + 1,
//                    approximateDistToNextObjective(neighbour));
//
//            if (!expandedNodes.containsKey(possibleFrontierNode.getId()) && !frontierNodes.containsKey(possibleFrontierNode.getId())) {
//                frontierNodes.put(possibleFrontierNode.getId(), possibleFrontierNode);
//            }
//        });

        node.getActualField().getNeighbours().forEach(neighbour -> {
            if (!expandedNodes.containsKey(neighbour)) {

                SearchGraphNode possibleFrontierNode = new SearchGraphNode(
                    node,
                    neighbour,
                    node.getDistanceToHere() + 1,
                    approximateDistToNextObjective(neighbour));

                frontierNodes.put(possibleFrontierNode.getId(), possibleFrontierNode);
            }
        } );

    }

    private int approximateDistToNextObjective(Field fromField) {
        if (!objectiveFields.isEmpty()) {
            return fromField.getManhattanDistFrom(objectiveFields.getFirst());
        }
        return fromField.getManhattanDistFrom(getField(fieldMtx.size() - 1, fieldMtx.get(0).size() - 1));
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
