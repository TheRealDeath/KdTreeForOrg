package DataSciences;

import java.util.*;


public class Coordinate {

    double[] points;
    List<Resource> list;
    public Coordinate(double... points) {
        this.points = points;
        list = new ArrayList<>();
    }
    /*public Coordinate(Tree.Node temp) {
        points = temp.points;
        list = temp.values;
    }*/
    public int sum() {
        int sum = 0;
        for(Resource x : list) sum += x.value;
        return sum;
    }
    @Override
    public String toString() {
        return points[0] + " " + points[1] + " " + list.toString();
    }
}
