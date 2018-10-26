package logic;

public class SearchGraphNode {
    private static int idCounter = 0;
    private int id;

    private SearchGraphNode parentNode;
    private Field actualField;
    private int distanceToHere;
    private int approxDistanceToNextObjective;

    private boolean treasureFound;

    public SearchGraphNode(SearchGraphNode parentNode, Field actualField, int distanceToHere, int approxDistanceToNextObjective) {
        this.parentNode= parentNode;
        this.actualField = actualField;
        this.distanceToHere = distanceToHere;
        this.approxDistanceToNextObjective = approxDistanceToNextObjective;

        this.id = idCounter++;
    }

    public boolean isTreasureFound() {
        return treasureFound;
    }

    public void setTreasureFound(boolean treasureFound) {
        this.treasureFound = treasureFound;
    }

    public int getId() {
        return id;
    }

    public SearchGraphNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(SearchGraphNode parentNode) {
        this.parentNode = parentNode;
    }

    public Field getActualField() {
        return actualField;
    }

    public void setActualField(Field actualField) {
        this.actualField = actualField;
    }

    public int getDistanceToHere() {
        return distanceToHere;
    }

    public void setDistanceToHere(int distanceToHere) {
        this.distanceToHere = distanceToHere;
    }

    public int getApproxDistanceToNextObjective() {
        return approxDistanceToNextObjective;
    }

    public void setApproxDistanceToNextObjective(int approxDistanceToNextObjective) {
        this.approxDistanceToNextObjective = approxDistanceToNextObjective;
    }

    @Override
    public String toString() {

        return String.format("ID: %d, parentID: %d, actualField: %s, distToHere: %d, distToObj: %d",
                getId(),
                parentNode != null ? parentNode.getId() : -1,
                getActualField(),
                getDistanceToHere(),
                getApproxDistanceToNextObjective());
    }
}
