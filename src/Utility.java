import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Utility {
    DataManager dm;

    Color mojSpalony = new Color(50,50,50);
    Color mojOgien = new Color(200,50,0);


    public Utility(DataManager dm){
        this.dm = dm;
    }

    void binarization(int i){
        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){

                dm.pixelLast[wi][hi]= dm.img.getRaster().getSample(wi,hi,0);

                if(dm.img.getRaster().getSample(wi,hi,0)<=i){dm.img.getRaster().setSample(wi,hi,0,0);}
                else{dm.img.getRaster().setSample(wi,hi,0,255);}
            }
        }
    }


    void makeBrighter(int i)
    {
        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){

                int newSample=dm.img.getRaster().getSample(wi,hi,0)+i;
                dm.pixelLast[wi][hi]= dm.img.getRaster().getSample(wi,hi,0);

                if(newSample>=255){dm.img.getRaster().setSample(wi,hi,0,255);}
                else {dm.img.getRaster().setSample(wi,hi,0,newSample);}


            }
        }
    }

    void makeDarker(int i)
    {
        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){

                int newSample=dm.img.getRaster().getSample(wi,hi,0)-i;
                dm.pixelLast[wi][hi]= dm.img.getRaster().getSample(wi,hi,0);

                if(newSample<0){dm.img.getRaster().setSample(wi,hi,0,0);}
                else {dm.img.getRaster().setSample(wi,hi,0,newSample);}
            }
        }
    }

    int[][] getMatrix(int Width, int Height) {

        int[][] neighbors = new int[3][3];
        int licznik=0;
        int licznik2=0;

        for (int wi = Width-1; wi <= Width+1; wi++) {
            for (int hi = Height-1; hi <=Height+1; hi++) {
                neighbors[licznik][licznik2]=dm.pixelLast[wi][hi];licznik2++;
            }
            licznik2=0;
            licznik++;
        }
        return neighbors;
    }

    void lowPass(){
        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){
                dm.pixelLast[wi][hi]= dm.img.getRaster().getSample(wi,hi,0);
            }}

        for(int wi = 1; wi < dm.width-1; wi++){
            for(int hi = 1; hi < dm.height-1; hi++){

                int [][] localMatrix={{1,1,1},{1,1,1},{1,1,1}};
                int[][] localNeighbor= getMatrix(wi,hi);
                int value=0;


                for(int i=0;i<3;i++) {
                    for (int j = 0; j < 3; j++) {
                        //  System.out.println(localNeighbor[i][j]);
                        value = value + ((localNeighbor[i][j])*localMatrix[i][j]); // tez
                    }
                }
                value=value/9;

                if(value<0){dm.img.getRaster().setSample(wi,hi,0,0);}
                else if(value>255){dm.img.getRaster().setSample(wi,hi,0,255);}
                else {dm.img.getRaster().setSample(wi,hi,0,value);}

            }
        }
    }

    void highPass(){
        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){
                dm.pixelLast[wi][hi]= dm.img.getRaster().getSample(wi,hi,0);
            }}

        for(int wi = 1; wi < dm.width-1; wi++){
            for(int hi = 1; hi < dm.height-1; hi++){

                int [][] localMatrix={{-1,-1,-1},{-1,9,-1},{-1,-1,-1}};
                int[][] localNeighbor= getMatrix(wi,hi);
                int value=0;


                for(int i=0;i<3;i++) {
                    for (int j = 0; j < 3; j++) {
                        value = value + ((localNeighbor[i][j])*localMatrix[i][j]); // tez
                    }
                }

                if(value<0){dm.img.getRaster().setSample(wi,hi,0,0);}
                else if(value>255){dm.img.getRaster().setSample(wi,hi,0,255);}
                else {dm.img.getRaster().setSample(wi,hi,0,value);}

            }
        }
    }

    void gaussianFilter(){
        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){
                dm.pixelLast[wi][hi]= dm.img.getRaster().getSample(wi,hi,0);
            }}

        for(int wi = 1; wi < dm.width-1; wi++){
            for(int hi = 1; hi < dm.height-1; hi++){

                int [][] localMatrix={{1,4,1},
                                      {4,32,4},
                                      {1,4,1}};
                int[][] localNeighbor= getMatrix(wi,hi);
                int value=0;


                for(int i=0;i<3;i++) {
                    for (int j = 0; j < 3; j++) {
                        value = value + ((localNeighbor[i][j])*localMatrix[i][j]); // tez
                    }
                }

                value=value/52;

                if(value<0){dm.img.getRaster().setSample(wi,hi,0,0);}
                else if(value>255){dm.img.getRaster().setSample(wi,hi,0,255);}
                else {dm.img.getRaster().setSample(wi,hi,0,value);}

            }
        }
    }

    void dilatation()
    {
        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){
                dm.pixelLast[wi][hi]= dm.img.getRaster().getSample(wi,hi,0);
            }
        }


        for(int wi = 1; wi < dm.width-1; wi++){
            for(int hi = 1; hi < dm.height-1; hi++){

                int[][] localNeighbor= getMatrix(wi,hi);

                outer:
                for(int i=0;i<3;i++) {
                    for (int j = 0; j < 3; j++) {
                        if(i==1&&j==1) {}
                        else {
                            if (localNeighbor[i][j] == 0) {
                                dm.img.getRaster().setSample(wi, hi, 0, 0);
                                break outer;
                            } else {
                                dm.img.getRaster().setSample(wi, hi, 0, 255);
                            }
                        }
                    }
                }
            }
        }
    }

    void erosion()
    {
        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){
                dm.pixelLast[wi][hi]= dm.img.getRaster().getSample(wi,hi,0);
            }
        }


        for(int wi = 1; wi < dm.width-1; wi++){
            for(int hi = 1; hi < dm.height-1; hi++){

                int[][] localNeighbor = getMatrix(wi, hi);

                outer:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (i == 1 && j == 1) { }
                        else
                        {
                            if (localNeighbor[i][j] == 255) {
                                dm.img.getRaster().setSample(wi, hi, 0, 255);
                                break outer;
                            }
                            else {}
                        }
                    }
                }
            }
        }
    }


    void saveRGB(){
        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){
                dm.pixelStart[wi][hi]=dm.img.getRaster().getSample(wi,hi,0);
                dm.pixelLast[wi][hi]=dm.img.getRaster().getSample(wi,hi,0);
                //System.out.println(dm.img.getData().getSample(wi,hi,0));
            }
        }
    }

    void reverse(){
        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){

                dm.img.getRaster().setSample(wi,hi,0,dm.pixelStart[wi][hi]);

            }
        }
    }

    void Initial(){
        for(int wi = 0; wi < dm.width; wi++){
        for(int hi = 0; hi < dm.height; hi++){
            dm.pixelStart[wi][hi]=dm.img.getRaster().getSample(wi,hi,0);
            dm.pixelLast[wi][hi]=dm.img.getRaster().getSample(wi,hi,0);
            //System.out.println(dm.img.getData().getSample(wi,hi,0));
        }
    } }

    ///////////////////////////////////POZAR LASU

    void setFire(int x, int y){
        dm.img.setRGB(x,y,Color.ORANGE.darker().getRGB());
        dm.treeLast[x][y].state=TreeState.ONFIRE;
        //dm.treeCurrent[x][y].state=TreeState.ONFIRE;

    }

    void generateForest(int min,int max)
    {
        BufferedImage convertedImg = new BufferedImage(dm.img.getWidth(), dm.img.getHeight(), BufferedImage.TYPE_INT_RGB);
        convertedImg.getGraphics().drawImage(dm.img, 0, 0, null);
        dm.img=convertedImg;

        for(int wi = 0; wi < dm.width; wi++){
            for(int hi = 0; hi < dm.height; hi++){


                if(dm.img.getRGB(wi,hi)==Color.WHITE.getRGB())
                {
                    dm.img.setRGB(wi,hi,Color.GREEN.getRGB());
                    dm.treeLast[wi][hi]=new Tree(true,min,max,TreeState.ALIVE);
                }

                else
                {
                    dm.img.setRGB(wi,hi,Color.BLUE.getRGB());
                    dm.treeLast[wi][hi]=new Tree(false,-10000,-9000,TreeState.DEAD);

                }
                //System.out.println(dm.treeLast[wi][hi].moisture);
            }
        }
        for (int x = 0; x < dm.width; x++) {
            for (int z = 0; z < dm.height; z++) {
                dm.treeCurrent[x][z]=dm.treeLast[x][z].state;
                //System.out.println(dm.treeLast[x][z].moisture);
            }
        }
    }

    Tree[] getNeighbors(int Width, int Height) {

        Tree[] neighbors = new Tree[8];
        int licznik = 0;

        try{
            for (int wi = Width - 1; wi <= Width + 1; wi++) {
                for (int hi = Height - 1; hi <= Height + 1; hi++) {

                    if (wi == Width && hi == Height) {
                    } else {
                        neighbors[licznik] = dm.treeLast[wi][hi];
                        licznik++;
                    }
                }
            }
        }
        catch (Exception e) {
            if(Width==0&&Height==0)
            {
                neighbors[0]=new Tree(TreeState.DEAD);
                neighbors[1]=new Tree(TreeState.DEAD);
                neighbors[2]=new Tree(TreeState.DEAD);
                neighbors[3]=new Tree(TreeState.DEAD);
                neighbors[4]=dm.treeLast[Width][Height+1];
                neighbors[5]=new Tree(TreeState.DEAD);
                neighbors[6]=dm.treeLast[Width+1][Height];
                neighbors[7]=dm.treeLast[Width+1][Height+1];
            }

            else if(Width==dm.width-1&&Height==0)
            {
                neighbors[0]=new Tree(TreeState.DEAD);
                neighbors[1]=dm.treeLast[Width-1][Height];
                neighbors[2]=dm.treeLast[Width-1][Height+1];
                neighbors[3]=new Tree(TreeState.DEAD);
                neighbors[4]=dm.treeLast[Width][Height+1];
                neighbors[5]=new Tree(TreeState.DEAD);
                neighbors[6]=new Tree(TreeState.DEAD);
                neighbors[7]=new Tree(TreeState.DEAD);
            }

            else if(Width==0&&Height==dm.height-1)
            {
                neighbors[0]=new Tree(TreeState.DEAD);
                neighbors[1]=new Tree(TreeState.DEAD);
                neighbors[2]=new Tree(TreeState.DEAD);
                neighbors[3]=dm.treeLast[Width][Height-1];
                neighbors[4]=new Tree(TreeState.DEAD);
                neighbors[5]=dm.treeLast[Width+1][Height-1];
                neighbors[6]=dm.treeLast[Width+1][Height];
                neighbors[7]=new Tree(TreeState.DEAD);
            }

            else if(Width==dm.width-1&&Height== dm.height-1)
            {
                neighbors[0]=dm.treeLast[Width-1][Height-1];
                neighbors[1]=dm.treeLast[Width-1][Height];
                neighbors[2]=new Tree(TreeState.DEAD);
                neighbors[3]=dm.treeLast[Width][Height-1];
                neighbors[4]=new Tree(TreeState.DEAD);;
                neighbors[5]=new Tree(TreeState.DEAD);
                neighbors[6]=new Tree(TreeState.DEAD);
                neighbors[7]=new Tree(TreeState.DEAD);
            }


            else if(Height== 0)
            {
                neighbors[0]=new Tree(TreeState.DEAD);
                neighbors[1]=dm.treeLast[Width-1][Height];
                neighbors[2]=dm.treeLast[Width-1][Height+1];
                neighbors[3]=new Tree(TreeState.DEAD);
                neighbors[4]=dm.treeLast[Width][Height+1];
                neighbors[5]=new Tree(TreeState.DEAD);
                neighbors[6]=dm.treeLast[Width+1][Height];
                neighbors[7]=dm.treeLast[Width+1][Height+1];
            }

            else if(Height== dm.height-1)
            {
                neighbors[0]=dm.treeLast[Width-1][Height-1];
                neighbors[1]=dm.treeLast[Width-1][Height];
                neighbors[2]=new Tree(TreeState.DEAD);
                neighbors[3]=dm.treeLast[Width][Height-1];
                neighbors[4]=new Tree(TreeState.DEAD);
                neighbors[5]=dm.treeLast[Width+1][Height-1];
                neighbors[6]=dm.treeLast[Width+1][Height];
                neighbors[7]=new Tree(TreeState.DEAD);
            }

            else if(Width== 0)
            {
                neighbors[0]=new Tree(TreeState.DEAD);
                neighbors[1]=new Tree(TreeState.DEAD);
                neighbors[2]=new Tree(TreeState.DEAD);
                neighbors[3]=dm.treeLast[Width][Height-1];
                neighbors[4]=dm.treeLast[Width][Height+1];
                neighbors[5]=dm.treeLast[Width+1][Height-1];
                neighbors[6]=dm.treeLast[Width+1][Height];
                neighbors[7]=dm.treeLast[Width+1][Height+1];
            }

            else if(Width== dm.width-1)
            {
                neighbors[0]=dm.treeLast[Width-1][Height-1];
                neighbors[1]=dm.treeLast[Width-1][Height];
                neighbors[2]=dm.treeLast[Width-1][Height+1];
                neighbors[3]=dm.treeLast[Width][Height-1];
                neighbors[4]=dm.treeLast[Width][Height+1];
                neighbors[5]=new Tree(TreeState.DEAD);
                neighbors[6]=new Tree(TreeState.DEAD);
                neighbors[7]=new Tree(TreeState.DEAD);
            }
        }

        finally{ return neighbors;}
    }

    void animateFire(){

        do {
            for (int hi = 0; hi < dm.height ; hi++) {
                for (int wi = 0; wi < dm.width; wi++) {
                    Random x=new Random();

                    Tree[] localNeighbor = getNeighbors(wi, hi);

                    Tree localMiddle = dm.treeLast[wi][hi];

                    int licznik = 0;
                    int licznikWilgoci = 0;
                    int licznikWag=0;

                    if (localMiddle.state == TreeState.ALIVE)
                    {
                        if(x.nextInt(3000000)==12345){dm.treeCurrent[wi][hi]=TreeState.ONFIRE;}
                    }

                    if (localMiddle.state == TreeState.DEAD)
                    {
                        if( x.nextInt(2500)==555){dm.treeCurrent[wi][hi]=TreeState.ALIVE;}
                    }

                        if (localMiddle.state == TreeState.ALIVE) {
                        for (int j=0;j< localNeighbor.length;j++) {
                            if (localNeighbor[j].state == TreeState.ONFIRE) {
                                licznik++;
                            }
                        }

                        for (int j=0;j< localNeighbor.length;j++) {
                            int y=x.nextInt(1000)%100;
                            licznikWag+=y;
                                licznikWilgoci+=localNeighbor[j].moisture*y;
                        }

                        licznikWilgoci/=8+licznikWag;

                        if (licznik>=1&&licznik<3&&licznikWilgoci>=0&&licznikWilgoci<= x.nextInt(75)) {
                            dm.treeCurrent[wi][hi] = TreeState.ONFIRE;
                        }
                    }

                    else if (localMiddle.state == TreeState.ONFIRE) {
                        dm.treeCurrent[wi][hi] = TreeState.DEAD;
                    }

                   // else if(localMiddle.state==TreeState.DEAD){dm.treeCurrent[wi][hi].state = TreeState.DEAD;}
                }
            }
            for (int x = 0; x < dm.width; x++) {
                for (int z = 0; z < dm.height; z++) {
                    dm.treeLast[x][z].state=dm.treeCurrent[x][z];
                    if(dm.treeLast[x][z].isTree==true) {
                        if (dm.treeLast[x][z].state == TreeState.ALIVE) {
                            dm.img.setRGB(x, z, Color.green.getRGB());
                        } else if (dm.treeLast[x][z].state == TreeState.DEAD) {
                            dm.img.setRGB(x, z, mojSpalony.getRGB());
                        } else if (dm.treeLast[x][z].state == TreeState.ONFIRE) {
                            dm.img.setRGB(x, z, mojOgien.getRGB());
                        }
                    }
                }
            }


        } while (true);
    }

}