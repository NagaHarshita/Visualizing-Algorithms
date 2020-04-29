package app;

import java.util.*;

public class Nodes{
    /** x and y are the top and left coordinates for GUI */
    public int x; 
    public int y;

    /**row and col are the indices of the 2-D array for easy access */
    public int row;
    public int col;

    /**the variable size is the size of the square*/
    public int size;

    /**this distance is used in dijkstra algorithm to find inimum path*/
    public int distance;

    /**this parent variable is used to print the path by backtracking from dest to source*/
    public Nodes parent;

    /**isVisited==true if the node is reached with shortest distance possible*/
    public boolean isVisited;

    /**inDatastructure==true if it is present in heap, helpful to update the already existing node or add new node to heap*/
    public boolean inDatastructure;

    /**isWall==true if it is a obstacle */
    public boolean isWall;

    /**Constructor that initializes variables */

    public Nodes(){
        distance = Integer.MAX_VALUE;
        isVisited = false;
        inDatastructure= false;
        isWall = false;
    }   

    Nodes(int row,int col,int x,int y,int size){
        this.x = x;
        this.y = y;
        this.row = row;
        this.col = col;
        this.size = size;
        distance = Integer.MAX_VALUE;
        isVisited = false;
        inDatastructure= false;
        isWall = false;
    }

    /**Checks and returns the source node present in grid*/
    public static Nodes checkInGrid(Nodes[][] grid,Nodes temp){
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j].row == temp.row && grid[i][j].col == temp.col){
                    return grid[i][j];
                }
            }
        }
        return null;
    }

    /**returns the path of the nodes with the shortest path by backtracking from dest to source*/
    public static List<Nodes> printAllVisited(Nodes dest){
        List<Nodes> res = new ArrayList<>();
        if(dest==null){
            return null;
        }
        while(dest!=dest.parent){
            // System.out.println(dest.row+" "+dest.col);
            res.add(dest);
            if(dest.parent!=null)
                dest = dest.parent;
            else{
                return res;
            }
        } 
        res.add(dest);
        return res;
    }
}
