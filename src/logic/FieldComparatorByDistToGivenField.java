package logic;

import java.util.Comparator;

public class FieldComparatorByDistToGivenField implements Comparator<Field> {

    private Field givenField;

    public FieldComparatorByDistToGivenField(Field givenField) {
        this.givenField = givenField;
    }

    /**
     * the closest field to the given field will be at the beginning
     * @param o1
     * @param o2
     * @return
     */

    @Override
    public int compare(Field o1, Field o2) {
        return givenField.getManhattanDistFrom(o1) - givenField.getManhattanDistFrom(o2);
    }
}
