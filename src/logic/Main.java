package logic;


public class Main {
    public static void main(String[] args) {

        Labyrinth labyrinth = new Labyrinth();
        labyrinth.loadFieldMtx();

        labyrinth.start();
    }
}
