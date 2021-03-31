public class Tree {

    boolean isTree;
    int moisture;
    TreeState state;

    public Tree(TreeState state) {
        this.state = state;
        this.isTree=true;
        this.moisture=-10000;
    }

    public Tree(boolean check, int wilgotnoscMin, int wilgotnoscMax, TreeState stan) {
        this.isTree = check;
        this.moisture=-1;//woda
        this.state=TreeState.DEAD;

        if (this.isTree == true) {
            this.moisture = (int) ((Math.random() * (wilgotnoscMax - wilgotnoscMin)) + wilgotnoscMin);
            this.state=stan;
        }
    }
}
