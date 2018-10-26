package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Field {
    private int i, j;

    private boolean hasTresure;

    private List<Field> neighbours;
    private int layoutCode;

    public Field(int i, int j, int layoutCode) {
        this.i = i;
        this.j = j;
        this.layoutCode = layoutCode;

        if ((layoutCode >> 4 & 1) == 1) {
            hasTresure = true;
        }

    }

    public void addNeighbour(Field neighbour) {

        //ha korbe fal vagy nem egymas melletti mezok
//        if ((layoutCode & 1111) == 1 || this.getManhattanDistFrom(neighbour) != 1) {
//            return;
//        }

        boolean hasAllWalls = true;
        for (int i = 0; i < 4; i++) {
            if ((layoutCode >> i & 1) == 0) {
                hasAllWalls = false;
            }
        }
        if (hasAllWalls) {
            return;
        }

        if (neighbours == null) {
            neighbours = new ArrayList<>();
        }

        //ha eszaki szomszed
        if (this.getI() > neighbour.getI() && this.getJ() == neighbour.getJ()) {

            //ha nincs ennek a mezonek eszaki fala
            if ((this.layoutCode & 1) != 1) {
                neighbours.add(neighbour);
            }
            //ha keleti szomszed
        } else if (this.getI() == neighbour.getI() && this.getJ() < neighbour.getJ()) {

            // nincs ennek a mezonek keleti fala
            if ((this.layoutCode >> 1 & 1) != 1) {
                neighbours.add(neighbour);
            }
            //ha deli szomszed
        } else if (this.getI() < neighbour.getI() && this.getJ() == neighbour.getJ()) {

            // nincs ennek a mezonek deli fala
            if ((this.layoutCode >> 2 & 1) != 1) {
                neighbours.add(neighbour);
            }
            //ha nyugati szomszed
        } else if (this.getI() == neighbour.getI() && this.getJ() > neighbour.getJ()) {

            // nincs ennek a mezonek nyugati fala
            if ((this.layoutCode >> 3 & 1) != 1) {
                neighbours.add(neighbour);
            }
        }
    }

    public int getManhattanDistFrom(Field other) {
        return Math.abs(this.getI() - other.getI()) + Math.abs(this.getJ() - other.getJ());
    }

    public boolean hasTresure() {
        return hasTresure;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public List<Field> getNeighbours() {
        if (neighbours == null) {
            throw new RuntimeException("Neighbours of this field are not set " + this);
        }
        return neighbours;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", i, j);
    }
}
