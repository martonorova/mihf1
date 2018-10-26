package logic;

public class SearchGraphNode {
    private static int idCounter = 0;
    private int id;

    private Field previousField;
    private Field actualField;
    private int distanceToHere;
    private int approxDistanceToNextObjective;

    public SearchGraphNode(Field previousField, Field actualField, int distanceToHere, int approxDistanceToNextObjective) {
        this.previousField = previousField;
        this.actualField = actualField;
        this.distanceToHere = distanceToHere;
        this.approxDistanceToNextObjective = approxDistanceToNextObjective;

        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public Field getPreviousField() {
        return previousField;
    }

    public void setPreviousField(Field previousField) {
        this.previousField = previousField;
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
}
