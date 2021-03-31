import java.awt.image.BufferedImage;

public class DataManager {

    BufferedImage img;

    int[][] pixelStart;
    int[][] pixelLast;

    Tree[][] treeLast;

    TreeState[][] treeCurrent;

    int width = 600;
    int height = 330;

    public DataManager() {
        pixelStart =new int [width][height];
        pixelLast = new int [width][height];

        treeLast=new Tree[width][height];
        treeCurrent=new TreeState[width][height];


    }
}