import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Point;
import java.awt.Dimension;
import java.util.PriorityQueue;
import java.util.*;
import javax.swing.*;
import javafx.scene.layout.Priority;

class Nodes{
    /** x and y are the top and left coordinates for GUI */
    int x; 
    int y;

    /**row and col are the indices of the 2-D array for easy access */
    int row;
    int col;

    /**the variable size is the size of the square*/
    int size;

    /**this distance is used in dijkstra algorithm to find inimum path*/
    int distance;

    /**this parent variable is used to print the path by backtracking from dest to source*/
    Nodes parent;

    /**isVisited==true if the node is reached with shortest distance possible*/
    boolean isVisited;

    /**isHeap==true if it is present in heap, helpful to update the already existing node or add new node to heap*/
    boolean inHeap;

    /**isWall==true if it is a obstacle */
    boolean isWall;

    /**Constructor that initializes variables */
    Nodes(int row,int col,int x,int y,int size){
        this.x = x;
        this.y = y;
        this.row = row;
        this.col = col;
        this.size = size;
        distance = Integer.MAX_VALUE;
        isVisited = false;
        inHeap= false;
        isWall = false;
    }

    /**Adds or updates the nodes in the heap. 
     * Here neighbours are 4 sides of the given block in a grid 
     * Update the distance if the distance from current node is less than present distance
     */
    static void addNeighbours(Nodes top,PriorityQueue<Nodes> heap,Nodes[][] grid){
        if(top.row>0){
            if(!grid[top.row-1][top.col].isVisited && !grid[top.row-1][top.col].isWall){
                if( grid[top.row-1][top.col].distance > top.distance+1){
                    grid[top.row-1][top.col].distance = top.distance+1;
                    grid[top.row-1][top.col].parent = top;
                }
                if(!grid[top.row-1][top.col].inHeap){
                    heap.add(grid[top.row-1][top.col]);
                    grid[top.row-1][top.col].inHeap =  true;
                }
                
            }
        }
        if(top.col>0){
            if(!grid[top.row][top.col-1].isVisited && !grid[top.row][top.col-1].isWall){
                if( grid[top.row][top.col-1].distance > top.distance+1){
                    grid[top.row][top.col-1].distance = top.distance+1;
                    grid[top.row][top.col-1].parent = top;
                }
                if(!grid[top.row][top.col-1].inHeap){
                    heap.add(grid[top.row][top.col-1]);
                    grid[top.row][top.col-1].inHeap =  true;
                }
            }
        }
        if(top.row < grid.length-1){
            if(!grid[top.row+1][top.col].isVisited && !grid[top.row+1][top.col].isWall){
                if( grid[top.row+1][top.col].distance > top.distance+1){
                    grid[top.row+1][top.col].distance = top.distance+1;
                    grid[top.row+1][top.col].parent = top;
                }
                if(!grid[top.row+1][top.col].inHeap){
                    heap.add(grid[top.row+1][top.col]);
                    grid[top.row+1][top.col].inHeap =  true;
                }
            }
        }
        if(top.col < grid[0].length-1){
            if(!grid[top.row][top.col+1].isVisited && !grid[top.row][top.col+1].isWall){
                if( grid[top.row][top.col+1].distance > top.distance+1){
                    grid[top.row][top.col+1].distance = top.distance+1;
                    grid[top.row][top.col+1].parent = top;
                }
                if(!grid[top.row][top.col+1].inHeap){
                    heap.add(grid[top.row][top.col+1]);
                    grid[top.row][top.col+1].inHeap =  true;
                }
            }
        }
    }

    /**Checks and returns the source node present in grid*/
    static Nodes checkInGrid(Nodes[][] grid,Nodes temp){
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
    static List<Nodes> printAllVisited(Nodes dest){
        List<Nodes> res = new ArrayList<>();
        if(dest==null){
            return null;
        }
        while(dest!=dest.parent){
            //System.out.println(dest.row+" "+dest.col);
            res.add(dest);
            dest = dest.parent;
        } 
        res.add(dest);
        return res;
    }

    /**Implenting dijkstra using min-heap */
    static List<Nodes> implementDijkstra(Nodes[][] grid,Nodes source,Nodes dest){
        PriorityQueue<Nodes> heap = new PriorityQueue<>((a,b)->a.distance-b.distance);
        Nodes head = checkInGrid(grid, source);
        if(head==null){
            System.out.println("No source found");
            return null;
        }

        /**Initialize distance to source from source as 0 and its parent as itself*/
        head.distance=0;
        head.parent=head;
        
        /**adding source pointer to the heap*/
        heap.add(head);
        head.inHeap = true;

        /**performing dijkstra */
        while(!heap.isEmpty()){
            Nodes temp = heap.poll();
            temp.isVisited = true;

            if(dest.row==temp.row && dest.col==temp.col){
                return printAllVisited(temp);
            }

            addNeighbours(temp, heap, grid);
        }
        
        return null;
    }
}

public class GUI  {
    int mx;
    int my;
    Nodes[][] grid;
    int size;
    int rowCount;
    int colCount;
    public static void main(String[] args) {
        GUI paintGUI = new GUI();
    }

    
    public GUI() {
        mx = -100;
        my = -100;
        

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                TestPane board = new TestPane();
                frame.setContentPane(board);;
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
                Move move = new Move();
                frame.addMouseMotionListener(move);

                Click click = new Click();
                frame.addMouseListener(click);
            }
        });
    }

    public class Move implements MouseMotionListener{
        @Override
        public void mouseMoved(MouseEvent e){

        }
        public void mouseDragged(MouseEvent e){

        }
    }

    public class Click implements MouseListener{
        @Override
        public void mousePressed(MouseEvent e){

        }
        
        public void mouseClicked(MouseEvent e){
            System.out.print(e.getX());
            mx = e.getX();
            my = e.getY();

            int row = (my-20)/ rowCount;
            int col = (mx-20) / colCount;
            grid[row][col].isWall = true;

            // repaint();
        }
        public void mouseReleased(MouseEvent e){
            
        }
        public void mouseExited(MouseEvent e){
            
        }
        public void mouseEntered(MouseEvent e){

        }
    }
    
    public class TestPane extends JPanel {
       

        public TestPane() {
            
        }
        // public void mousePressed(MouseEvent e){

        // }
        // public void mouseReleased(MouseEvent e){
            
        // }
        // public void mouseExited(MouseEvent e){
            
        // }
        // public void mouseEntered(MouseEvent e){

        // }

        // public void mouseClicked(MouseEvent e){
        //     System.out.print(e.getX());
        //     mx = e.getX();
        //     my = e.getY();

        //     int row = (my-20)/ rowCount;
        //     int col = (mx-20) / colCount;
        //     grid[row][col].isWall = true;

        //     repaint();
        // }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }
        

        void updateCellType(int x,int y){
            int row = (y-20)/ rowCount;
            int col = (x-20) / colCount;
            grid[row][col].isWall = true;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            
            /**Setting up no. of squares in a grid should be*/
            size = Math.min(getWidth() - 20, getHeight() - 20) / 20;
            
            if(getWidth() - 20 == Math.min(getWidth() - 20, getHeight() - 20)){
                colCount = 20;
                rowCount = (getHeight() - 20) / size;
            }else{
                rowCount= 20;
                colCount = (getWidth() - 20)/size;
            }

            /**Creating nodes */
            grid = new Nodes[rowCount][colCount];

            int y = 10;
            for (int horz = 0; horz < rowCount; horz++) {
                
                int x = 10;
                for (int vert = 0; vert < colCount; vert++) {
                    
                    grid[horz][vert] = new Nodes(horz,vert,x,y,size);
                    // g.setColor(Color.white);
                    // System.out.println(mx);
                    if(grid[horz][vert].isWall == true){
                        g.setColor(Color.RED);
                    }
                    g.drawRect(x, y, size, size);
                    x += size;
                }
                y += size;
            }
            
            System.out.println("*"+mx);
            /**setting source and dest*/
            Nodes source,dest;
            source = grid[0][0];
            dest = grid[rowCount-1][colCount-1];

            List<Nodes> res = Nodes.implementDijkstra(grid, source, dest);
            if(res==null){
                return ;
            }

            /**Filling path color with blue*/
            for(Nodes i: res){
                g.setColor(new Color(42, 179, 231));
                g.fillRect(i.x, i.y, size, size);
            }

            /**Filling source color with green */
            g.setColor(new Color(125, 167, 116));
            g.fillRect(source.x, source.y, size, size);

            /**Filling dest color with red*/
            g.setColor(new Color(241, 98, 69));
            g.fillRect(dest.x, dest.y, size, size);

            g.dispose();
        }
    } 
}