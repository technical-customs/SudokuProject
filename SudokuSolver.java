
import display.LoggerDisplay;
import java.util.LinkedList;
import java.util.Random;

public class SudokuSolver {
    final public LoggerDisplay ld = new LoggerDisplay("Sudoku Solver");
    
    private int SNUMBER = 3;
    final public int col = SNUMBER, row = SNUMBER;
    private SudokuGrid mainGrid;
    
    public class SudokuGrid{
        final private SudokuCell[][] grid = new SudokuCell[row][col];
        
        public SudokuGrid(){
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    grid[x][y] = new SudokuCell();
                }
            }
        }
        
        public void setCell(int r, int c, SudokuCell cell){
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(x == r && y == c){
                        grid[x][y] = cell;
                    }
                }
            }
        }
        public SudokuCell getCell(int r, int c){
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(x == r && y == c){
                        return grid[x][y];
                    }
                }
            }
            return null;
        }
        
        public LinkedList<Integer> getRow(int r1, int r2){
            LinkedList<Integer> rl = new LinkedList<>();
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(x == r1){
                        for(int i: grid[x][y].getRow(r2)){
                            rl.add(i);
                        }
                        
                    }
                }
            }
            return rl;
        }
        public LinkedList<Integer> getCol(int c1, int c2){
            LinkedList<Integer> cl = new LinkedList<>();
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(y == c1){
                        for(int i: grid[x][y].getCol(c2)){
                            cl.add(i);
                        }
                        
                    }
                }
            }
            return cl;
        }
        
        public boolean checkRow(int r1, int r2){
            //check if row of each cell has 1-9
            LinkedList<Integer> rl = new LinkedList<>();
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(x == r1){
                        for(int i: grid[x][y].getRow(r2)){
                            rl.add(i);
                        }
                    }
                }
            }
            
            ld.addToDisplay(rl.toString() + " - ");
            
            for(int a = 1; a <= 9; a++){
                if(!rl.contains(a)){
                    ld.writeToDisplay("FALSE" + "\n");
                    return false;
                }
            }
            ld.writeToDisplay("TRUE");
            return true;
            
        }
        public boolean checkCol(int c1, int c2){
            //check if col of each cell has 1-9
            LinkedList<Integer> cl = new LinkedList<>();
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(y == c1){
                        for(int i: grid[x][y].getCol(c2)){
                            cl.add(i);
                        }
                    }
                }
            }
            
            ld.addToDisplay(cl.toString() + " - ");
            
            for(int a = 1; a <= 9; a++){
                if(!cl.contains(a)){
                    ld.writeToDisplay("FALSE" + "\n");
                    return false;
                }
            }
            ld.writeToDisplay("TRUE");
            return true;
        }
        
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            
            for(int x = 0; x < row; x++){
                for(int w = 0; w < SNUMBER; w++){
                    for(int y = 0; y < col; y++){
                        sb.append(grid[x][y].getRowString(w));

                        if(y != col-1){
                            sb.append("|");
                        }
                        if(y == col-1){
                        }
                    }
                    sb.append("\n");
                }
                if(x != row-1){
                    sb.append("-----------").append("\n");
                }
                if(x == row-1){
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
    }
    
    public class SudokuCell{
        private int[][] cell = new int[row][col];
        
        public SudokuCell(){
            //initialize all squares to 0
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    cell[x][y] = 0;
                }
            }
        }
        
        public void fillGrid(){
            LinkedList<Integer> ll = new LinkedList<>();
            ll.add(1);ll.add(2);ll.add(3);ll.add(4);ll.add(5);ll.add(6);ll.add(7);ll.add(8);ll.add(9);
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(ll.size() == 1){
                        cell[x][y] = ll.get(0);
                        ll.remove(0);
                    }else{
                        int num = new Random().nextInt(ll.size());
                        cell[x][y] = ll.get(num);
                        ll.remove(num);
                    }
                }
            }
        }
        public void addNum(int r, int c, int num){
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(x == r && y == c){
                        cell[x][y] = num;
                    }
                }
            }
        }
        
        public LinkedList<Integer> getRow(int r){
            //returns all ints int row 
            LinkedList<Integer> rArray = new LinkedList<>();
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(x == r){
                        rArray.add(cell[x][y]);
                    }
                }
            }
            
            return rArray;
        }
        public LinkedList<Integer> getCol(int c){
            //returns all ints int col  0-2
            LinkedList<Integer> cArray = new LinkedList<>();
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(y == c){
                        cArray.add(cell[x][y]);
                    }
                }
            }
            
            return cArray;
        }
        
        
        public boolean checkCell(){
            //returns true if cell contains 1-9
            for(int a = 1; a <= 9; a++){
                for(int x = 0; x < row; x++){
                    for(int y = 0; x < col; x++){
                        if(!getRow(x).contains(a)){
                            return false;
                        }
                    }
                }
            }
            
            return true;
        }
        public String getRowString(int r){
            StringBuilder sb = new StringBuilder();
            
            for(int s: getRow(r)){
                sb.append(s);
            }
            return sb.toString();
        }
        public String getColString(int c){
            StringBuilder sb = new StringBuilder();
            
            for(int s: getCol(c)){
                sb.append(s);
            }
            return sb.toString();
        }
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    sb.append(cell[x][y]);
                    
                    if(y != col-1){
                        sb.append("|");
                    }
                    if(y == col-1){
                        sb.append("\n");
                    }
                }
                if(x != row-1){
                    sb.append("-----").append("\n");
                }
                if(x == row-1){
                    sb.append("\n");
                }
            }
            
            return sb.toString();
        }
    }
    
    public SudokuSolver(){
        //create main grid
        mainGrid = new SudokuGrid();
    }
    
    public void setSNumber(int sNumber){
        SNUMBER = sNumber;
    }
    public int getSNumber(){
        return SNUMBER;
    }
    public void setGrid(SudokuGrid mainGrid){
        this.mainGrid = mainGrid;
    }
    public SudokuGrid getGrid(){
        return mainGrid;
    }
    
    public static void main(String[] args){
        SudokuSolver sSolver = new SudokuSolver();
        SudokuGrid grid = sSolver.getGrid();
        int count = 0;
        boolean rt = false, ct = false;
        
        while(true){
        
        count++;
        for(int x = 0; x < sSolver.row ; x++){
            for(int y = 0; y < sSolver.col; y++){
                SudokuCell cell = sSolver.new SudokuCell();
                cell.fillGrid();
                grid.setCell(x, y,cell);
            }
        }
        sSolver.ld.writeToDisplay(grid.toString());
        rt = grid.checkRow(0, 0);
        ct = grid.checkCol(0, 0);
        if(!rt){
            continue;
        }
        if(!ct){
            continue;
        }
        break;
        }
        
        sSolver.ld.writeToDisplay("WE HAVE SUCCESS!!! COUNT-" + count);
    }
}