package DataSciences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {
    private int k;
    private Node root;
    
    public Tree(int k) {
        this.k = k;
    }
    private class Node {
        double[] points;
        List<Resource> values; //optional
        int sum;
        Node left, right;

        public Node(List<Resource> list,double[] arr) {
            points = arr;
            values = list;
            sum = 0;
            for(Resource x : values) sum += x.value;
        }
        public double euclidDistance(Node temp) {
            double s = 0;
            for(int i = 0;i<temp.points.length;i++) {
                s += Math.pow(temp.points[i]-points[i],2);
            }
            return s;
        }

        @Override
        public String toString() {
            return Arrays.toString(points) + " " + values;
        }
    }
    private Node createNode(Coordinate temp) {
        return new Node(temp.list,temp.points);
    }
    void insert(Coordinate temp) {
        root = insertRec(root,temp,0);
    }
    private Node insertRec(Node root, Coordinate temp, int depth) {
        if(root == null) return createNode(temp);
        int cd = depth % k;
        if(temp.points[cd] < root.points[cd]) root.left = insertRec(root.left,temp,depth+1);
        else root.right = insertRec(root.right,temp,depth+1);
        return root;
    }
    private double distance;
    private Node mostBest;
    private double[] splitting;
    Node nearestNeighbor(double[] points) {
        distance = Integer.MAX_VALUE;
        splitting = new double[k];
        Node player = new Node(new ArrayList<>(),points);
        nearestNeighborRec(root,player,0);
        if(distance(root.points,player) < distance) {mostBest = root;}
        splitting = null;
        return mostBest;
    }
    private double distance(double[] points, Node points2) {
        double s = 0;
        for(int i = 0;i<points.length;i++) {
            s += Math.pow(points[i]-points2.points[i],2);
        }
        return s;
    }
    private Node nearestNeighborRec(Node root,Node temp, int depth) {
        if(root != null && root.right == null && root.left == null) return root;
        else if(root != null) {
            int cd = depth % k;
            splitting[cd] = root.points[cd];
            Node best;
            boolean b = temp.points[cd] < root.points[cd];
            if(b) best = nearestNeighborRec(root.left, temp, depth+1);
            else best = nearestNeighborRec(root.right, temp, depth+1);
            double tempDouble = best.euclidDistance(temp);
            if(tempDouble < distance) {
                distance = tempDouble;
                mostBest = best;
            }
            if(distance(splitting, root) > distance) { //approx
            //if(true) { //brute
                if(b && root.right != null) nearestNeighborRec(root.right, temp, depth+1);
                else if(root.left != null) nearestNeighborRec(root.left, temp, depth+1);
            } 
            return best;
        }
        Node temp1 = new Node(new ArrayList<>(),new double[]{-1000.0,-1000.0});
        temp1.values.add(new Resource("placeholder",1));
        return temp1;
    }

    private static String s;
    @Override
    public String toString() {
        s = "";
		inOrder(root);
        return s;
	}

	private void inOrder(Node current) {
		if(current != null) {
            inOrder(current.left);
            s += current.toString()+"\n";
            inOrder(current.right);
		}
	}
    public Node findMin(Node root, int d) {
        return findMinRec(root,d,0);
    }
    private Node findMinRec(Node root, int d, int depth) {
        if(root == null) return null;
        int cd = depth % k;
        if(cd == d) {
            if(root.left == null) return root;
            return findMinRec(root, d, depth+1);
        }
        return minNode(root,findMinRec(root.left,d,depth+1),findMinRec(root, d, depth+1),d);
    }
    private Node minNode(Node x, Node y, Node z, int d) {
        Node res = x;
        if(y != null && y.points[d] < res.points[d]) res = y;
        if(z != null && z.points[d] < res.points[d]) res = z;
        return res;
    }
    public Node deleteNode(Coordinate temp) {
        return deleteNodeRec(root, temp.points,0);
    }
    private Node deleteNodeRec(Node root, double[] points, int depth) {
        if(root == null) return null;
        int cd = depth % k;
        if(arePointsSame(root.points,points)) {
            if(root.right != null) {
                Node min = findMin(root.right,cd);
                copyPoint(root.points,min.points);
                root.right = deleteNodeRec(root.right, min.points, depth+1);
            }
            else if(root.left != null) {
                Node min = findMin(root.left,cd);
                copyPoint(root.points, min.points);
                root.left = deleteNodeRec(root.left,min.points,depth+1);
            }
            else {
                root = null;
                return null;
            }
            return root;
        }
        if(points[cd] < root.points[cd])
            root.left = deleteNodeRec(root.left, points, depth+1);
        else root.right = deleteNodeRec(root.right, points, depth+1);
        return root;
    }
    private void copyPoint(double[] p1, double[] p2) {
        for(int i = 0;i<k;i++) p1[i] = p2[i];
    }
    private boolean arePointsSame(double p1[], double p2[]) {
        for(int i = 0;i<k;i++) 
            if(p1[i] != p2[i])
                return false;
        return true;
    }
}