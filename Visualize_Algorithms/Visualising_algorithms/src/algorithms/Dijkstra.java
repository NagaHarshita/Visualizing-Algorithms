package algorithms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import app.Nodes;

public class Dijkstra {
    int mx;
    int my;
    Nodes[][] grid;
    int size;
    int rowCount;
    int colCount;/**Adds or updates the nodes in the heap. 
    * Here neighbours are 4 sides of the given block in a grid 
    * Update the distance if the distance from current node is less than present distance
    */
   public static void addNeighbours(Nodes top,PriorityQueue<Nodes> heap,Nodes[][] grid){
       if(top.row>0){
           if(!grid[top.row-1][top.col].isVisited && !grid[top.row-1][top.col].isWall){
               if( grid[top.row-1][top.col].distance > top.distance+1){
                   grid[top.row-1][top.col].distance = top.distance+1;
                   grid[top.row-1][top.col].parent = top;
               }
               if(!grid[top.row-1][top.col].inDatastructure){
                   heap.add(grid[top.row-1][top.col]);
                   grid[top.row-1][top.col].inDatastructure =  true;
               }
               
           }
       }
       if(top.col>0){
           if(!grid[top.row][top.col-1].isVisited && !grid[top.row][top.col-1].isWall){
               if( grid[top.row][top.col-1].distance > top.distance+1){
                   grid[top.row][top.col-1].distance = top.distance+1;
                   grid[top.row][top.col-1].parent = top;
               }
               if(!grid[top.row][top.col-1].inDatastructure){
                   heap.add(grid[top.row][top.col-1]);
                   grid[top.row][top.col-1].inDatastructure =  true;
               }
           }
       }
       if(top.row < grid.length-1){
           if(!grid[top.row+1][top.col].isVisited && !grid[top.row+1][top.col].isWall){
               if( grid[top.row+1][top.col].distance > top.distance+1){
                   grid[top.row+1][top.col].distance = top.distance+1;
                   grid[top.row+1][top.col].parent = top;
               }
               if(!grid[top.row+1][top.col].inDatastructure){
                   heap.add(grid[top.row+1][top.col]);
                   grid[top.row+1][top.col].inDatastructure =  true;
               }
           }
       }
       if(top.col < grid[0].length-1){
           if(!grid[top.row][top.col+1].isVisited && !grid[top.row][top.col+1].isWall){
               if( grid[top.row][top.col+1].distance > top.distance+1){
                   grid[top.row][top.col+1].distance = top.distance+1;
                   grid[top.row][top.col+1].parent = top;
               }
               if(!grid[top.row][top.col+1].inDatastructure){
                   heap.add(grid[top.row][top.col+1]);
                   grid[top.row][top.col+1].inDatastructure =  true;
               }
           }
       }
   }



    
    /**Implenting dijkstra using min-heap */
    static List<Nodes> implementDijkstra(Nodes[][] grid,Nodes source,Nodes dest){

        PriorityQueue<Nodes> heap = new PriorityQueue<>((a,b)->a.distance-b.distance);
        Nodes head = Nodes.checkInGrid(grid, source);
        if(head==null){
            System.out.println("No source found");
            return null;
        }

        List<Nodes> visitedInOrder = new ArrayList<>();

        /**Initialize distance to source from source as 0 and its parent as itself*/
        head.distance=0;
        head.parent=head;
        
        /**adding source pointer to the heap*/
        heap.add(head);
        head.inDatastructure = true;

        /**performing dijkstra */
        while(!heap.isEmpty()){
            Nodes temp = heap.poll();
            temp.isVisited = true;
            visitedInOrder.add(temp);

            if(dest.row==temp.row && dest.col==temp.col){
                return Nodes.printAllVisited(dest);
            }
            
            addNeighbours(temp, heap, grid);
        }
        System.out.print("Returning visited nodes");
        return visitedInOrder;
    }

    public void runNow() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        
        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TestPane board = new TestPane();
        frame.setContentPane(board);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public Dijkstra() {
        mx = -100;
        my = -100;
        grid = new Nodes[20][20];
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                grid[i][j] = new Nodes();
            }
        }
        this.runNow();
    }

    public class TestPane extends JPanel implements ActionListener {
        private static final long serialVersionUID = 1L;
        List<Nodes> res;
        Nodes source;
        Nodes dest;
    
        @Override
        public void actionPerformed(ActionEvent e) {
            for(int i=0;i<rowCount;i++){
                for(int j=0;j<colCount;j++){
                    grid[i][j].isVisited=false;
                    grid[i][j].inDatastructure = false;
                }
            }
            // if(e.getActionCommand()=="Source"){
            
            // }else
            if(e.getActionCommand()=="Dijkstra"){
                res = implementDijkstra(grid, source, dest);
                this.repaint();
            }
            
        }
    
        public TestPane() {
            res = new ArrayList<>();
            source = grid[0][0];
            dest = grid[5][9];
            // JButton src = new JButton("Source");
            // src.addActionListener(this);
            // this.add(src);
    
            // JButton Dest = new JButton("Destination");
            // Dest.addActionListener(this);
            // this.add(Dest);
    
            JButton button = new JButton("Dijkstra");
            button.addActionListener(this);
            this.add(button);
    
            MouseMotionListener drag =new MouseMotionListener(){
            
                @Override
                public void mouseMoved(MouseEvent e) {
                    
                }
            
                @Override
                public void mouseDragged(MouseEvent e) {
                    mx = e.getX();
                    my = e.getY();
                    
                    int row = (my-20)/ rowCount ;
                    int col = (mx-20) / colCount;
                    if(row>=0 && row<rowCount && col>=0 && col<colCount){
                        grid[row][col].isWall = true;
                        TestPane.this.repaint();
                    }
                    
                }
            };
            MouseListener move = new MouseListener(){
            
                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }
            
                @Override
                public void mousePressed(MouseEvent e) {
                    
                }
            
                @Override
                public void mouseExited(MouseEvent e) {
                    
                }
            
                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }
            
                @Override
                public void mouseClicked(MouseEvent e) {
                    mx = e.getX();
                    my = e.getY();
                    
                    int row = (my-20)/ rowCount ;
                    int col = (mx-20) / colCount;
                    if(row>=0 && row<rowCount && col>=0 && col<colCount){
                        grid[row][col].isWall = true;
                        TestPane.this.repaint();
                    }
    
                }
            };
            this.addMouseListener(move);
            this.addMouseMotionListener(drag);
        }
    
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 600);
        }
        
    
        void updateCellType(int x,int y){
            int row = (y-20)/ rowCount;
            int col = (x-20) / colCount;
            grid[row][col].isWall = true;
        }
    
        protected void paintComponent(Graphics g)  {
            super.paintComponent(g);
            /** Setting up no. of squares in a grid should be */
            size = Math.min(getWidth() - 20, getHeight() - 20) / 20;
            
            if(getWidth() - 20 == Math.min(getWidth() - 20, getHeight() - 20)){
                colCount = 20;
                rowCount = (getHeight() - 20) / size;
            }else{
                rowCount= 20;
                colCount = (getWidth() - 20)/size;
            }
           
            int y = 10;
            for (int horz = 0; horz < rowCount; horz++) {
                
                int x = 10;
                for (int vert = 0; vert < colCount; vert++) {
                    
                    grid[horz][vert].x = x;
                    grid[horz][vert].y = y;
                    grid[horz][vert].row = horz;
                    grid[horz][vert].col = vert;
                    grid[horz][vert].size = size;
                    g.drawRect(x, y, size, size);
                    
                    if(grid[horz][vert].isWall == true){
                        g.setColor(Color.black);
                        g.fillRect(x, y, size, size);
                    }
                    x += size;
                }
                y += size;
            }
    
            /**setting source and dest*/
            if(res==null){
                System.out.println("Hello");
                g.dispose();
                return;
            }else{
                printPath(g);
            }
            g.dispose();
        }
       
        
        public void printPath(Graphics g) {
            try{
            /**Filling path color with blue*/
                
                for(Nodes i:res){
                    g.setColor(new Color(42, 179, 231));
                    g.fillRect(i.x, i.y, size, size);
                }
    
                /**Filling source color with green */
                g.setColor(new Color(125, 167, 116));
                g.fillRect(source.x, source.y, size, size);
    
                /**Filling dest color with red*/
                g.setColor(new Color(241, 98, 69));
                g.fillRect(dest.x, dest.y, size, size);
    
            }catch(Exception e){
    
            }
            
        }
    } 
    
   
}
