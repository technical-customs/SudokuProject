
import java.util.Iterator;
import java.util.LinkedList;

public class SudokuSolver {
    private int SNUMBER = 3;
    final private int col = SNUMBER, row = SNUMBER;
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
                    if(x == c1){
                        for(int i: grid[x][y].getCol(c2)){
                            cl.add(i);
                        }
                        
                    }
                }
            }
            return cl;
        }
        
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            
                for(int x = 0; x < row; x++){
                    
                    
                    for(int w = 0; w < SNUMBER; w++){
                        for(int y = 0; y < col; y++){
                            sb.append(grid[x][y].getRow(w));
                        }
                        sb.append("\n");
                    }
                    sb.append("\n");
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
            //returns true if cell contains all 1-9
            LinkedList<Integer> nl = new LinkedList<>();
            nl.add(1);nl.add(2);nl.add(3);nl.add(4);nl.add(5);nl.add(6);nl.add(7);nl.add(8);nl.add(9);   
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(cell[x][y] == 0){
                        return false;
                    }
                    Iterator<Integer> nli = nl.iterator();
                    while(nli.hasNext()){
                        int n = nli.next();
                        
                        if(n == cell[x][y]){
                            nli.remove();
                        }
                    }
                    if(nl.isEmpty()){
                        System.out.println("Can move on");
                    }
                }
            }
            return true;
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
        
        SudokuCell sCell = sSolver.new SudokuCell();
        sCell.addNum(0, 1, 5);
        sCell.addNum(1, 1, 6);
        sCell.addNum(2, 1, 4);
        sSolver.getGrid().setCell(2, 2, sCell);
        
        System.out.println(sSolver.getGrid().toString());
        
    }
}