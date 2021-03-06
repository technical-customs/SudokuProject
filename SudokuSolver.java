
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SudokuSolver {
    private int SNUMBER = 3;
    final public int col = SNUMBER, row = SNUMBER;
    private SudokuGrid mainGrid;
    
    public class Pair<T,V>{
        protected T t;
        protected V v;
        
        public Pair(T t, V v){
            this.t = t;
            this.v = v;
        }
        public T getT(){
            return t;
        }
        public void setT(T t){
            this.t = t;
        }
        public V getV(){
            return v;
        }
        public void setV(V v){
            this.v = v;
        }
        
        @Override
        public boolean equals(Object obj){
            if (obj == null) {
                return false;
            }
            if (!Pair.class.isAssignableFrom(obj.getClass())) {
                return false;
            }
            final Pair p = (Pair) obj;
           
            if(!t.equals(p.getT())){
                return false;
            }
            return v.equals(p.getV());
        }
        
        @Override
        public String toString(){
            return t + "," + v;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + Objects.hashCode(this.t);
            hash = 53 * hash + Objects.hashCode(this.v);
            return hash;
        }
    }
    
    public class SudokuGrid{
        final private SudokuCell[][] grid = new SudokuCell[row][col];
        
        public SudokuGrid(){
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    grid[x][y] = new SudokuCell();
                }
            }
        }
        
        public boolean smartFill(){
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    smartFillCell(x, y);
                }
            }
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(!grid[x][y].checkCell()){
                        return false;
                    }
                }
            }
            return true;
        }
        
        private boolean genRandAmounts(){
            
            
            
            return true;
        }
        
        public void randomFill(int num){
            //create fill list of random ints from rl
            LinkedList<Integer> rl = new LinkedList<>();
            
            //get average num
            int nd = num;
            int av = num/(SNUMBER*SNUMBER);
            
            //create random amounts for random fills
            boolean ok = false;
            
            do{
                //creates list for fill numbers
                for(int x = 0; x < row; x++){
                    for(int y = 0; y < col; y++){
                        grid[x][y].emptyCell();

                        int rn = 0;
                        boolean pass = false;
                        do{
                            //gen random amount for cell
                            rn = new Random().nextInt(av+3)+1;

                            //if the random amount - remaining amount 
                            // equals 0,
                            if(nd-rn >= 0){
                                pass = true;
                            }
                        }while(!pass);

                        rl.add(rn);
                        nd -= rn;
                        
                        
                        if(nd == 0 && rl.size() <= (SNUMBER*SNUMBER)){
                            ok = true;
                            break;
                        }
                    }
                }
            }while(!ok);
            
            //creates list of grid places
            LinkedList<Pair> pl = new LinkedList<>();
            for(int x = 0; x < rl.size(); x++){
                boolean pass = false;
                do{
                    int rr = new Random().nextInt(row);
                    int rc = new Random().nextInt(col);
                    Pair<Integer, Integer> p = new Pair<>(rr,rc);

                    if(!pl.contains(p)){
                        pl.add(p);
                        pass = true;
                    }
                }while(!pass);
            }
            
            //for each place, assign number and fill the cell
            int n = pl.size();
            for(int x = 0; x < n ; x++){
                //random number
                int ri = new Random().nextInt(rl.size());
                int i = rl.get(ri);
                
                //random place/cell
                int pi = new Random().nextInt(pl.size());
                Pair p = pl.get(pi);
                SudokuCell cell = getCell((int)p.getT(),(int)p.getV());
                
                //System.out.println("Fill - " + i + " Cell(s)");
                //System.out.println("P - " + p.toString());
                //System.out.println(cell.toString());
                
                fillCell( (int)p.getT(),(int)p.getV() , i);
                
                //System.out.println(cell.toString());

                rl.remove(ri);
                pl.remove(pi);
            }
        }
        
        public void smartFillRow(int r1, int r2){
            HashSet<Integer> ul = new HashSet<>();
            ul.clear();
            
            LinkedList<Integer> ll = new LinkedList<>();
            for(int x = 1; x <= 9; x++){
                ll.add(x);
            }
            
            LinkedList<Integer> rl = ll;
            
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    
                    if(x == r1){
                        //in the cell in the grid
                        SudokuCell cell = grid[x][y];
                        
                        for(int xx = 0; xx < row; xx++){
                            for(int yy = 0; yy < col; yy++){
                                if(xx == r2){
                                    //in the row in the cell
                                    
                                    for(int num: cell.getRow(xx)){
                                        if(num != 0){
                                            ul.add(num);
                                            if(rl.contains(num)){
                                                int i = rl.indexOf(num);
                                                rl.remove(i);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //System.out.println("USED - " + ul.toString());
            //System.out.println("REMAINING - " + rl.toString());
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    
                    if(x == r1){
                        //in the cell in the grid
                        SudokuCell cell = grid[x][y];
                        
                        for(int xx = 0; xx < row; xx++){
                            for(int yy = 0; yy < col; yy++){
                                if(xx == r2){
                                    
                                    if(cell.getCell()[xx][yy] == 0){
                                        int ri = new Random().nextInt(rl.size());
                                        //System.out.println("SELECTED - " + ri + " Value: " + rl.get(ri));

                                        cell.getCell()[xx][yy] = rl.get(ri);
                                        ul.add(rl.get(ri));
                                        rl.remove(ri);

                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            //System.out.println("USED - " + ul.toString());
            //System.out.println("REMAINING - " + rl.toString());
        }
        public void smartFillCol(int c1, int c2){
            HashSet<Integer> ul = new HashSet<>();
            ul.clear();
            
            LinkedList<Integer> ll = new LinkedList<>();
            for(int x = 1; x <= 9; x++){
                ll.add(x);
            }
            
            LinkedList<Integer> rl = ll;
            
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    
                    if(y == c1){
                        //in the cell in the grid
                        SudokuCell cell = grid[x][y];
                        
                        for(int xx = 0; xx < row; xx++){
                            for(int yy = 0; yy < col; yy++){
                                if(yy == c2){
                                    //in the row in the cell
                                    
                                    for(int num: cell.getCol(yy)){
                                        if(num != 0){
                                            ul.add(num);
                                            if(rl.contains(num)){
                                                int i = rl.indexOf(num);
                                                rl.remove(i);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //System.out.println("USED - " + ul.toString());
            //System.out.println("REMAINING - " + rl.toString());
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    
                    if(y == c1){
                        //in the cell in the grid
                        SudokuCell cell = grid[x][y];
                        
                        for(int xx = 0; xx < row; xx++){
                            for(int yy = 0; yy < col; yy++){
                                if(yy == c2){
                                    
                                    if(cell.getCell()[xx][yy] == 0){
                                        int ri = new Random().nextInt(rl.size());
                                        //System.out.println("SELECTED - " + ri + " Value: " + rl.get(ri));

                                        cell.getCell()[xx][yy] = rl.get(ri);
                                        ul.add(rl.get(ri));
                                        rl.remove(ri);

                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            //System.out.println("USED - " + ul.toString());
            //System.out.println("REMAINING - " + rl.toString());
        }
        
        public void smartFillCell(int r, int c){
            SudokuCell cell = getCell(r,c);
            
            LinkedList<Integer> ll = new LinkedList<>();
            for(int x = 1; x <= 9; x++){
                ll.add(x);
            }
            //create fill list of random ints in cell
            LinkedList<Pair> pl = new LinkedList<>();
            LinkedList<Integer> fl = new LinkedList<>();
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(cell.getCell()[x][y] == 0){
                        pl.add(new Pair<>(x,y));
                    }else{
                        fl.add(cell.getCell()[x][y]);
                    }
                    
                }
            }
            ll.removeAll(fl);
            
            int n = pl.size();
            for(int x = 0; x < n; x++){
                boolean pass = false;
                int checks = 0;
                do{
                    checks++;
                    int li = new Random().nextInt(ll.size());
                    int i = ll.get(li);
                    int rp = new Random().nextInt(pl.size());
                    Pair<Integer,Integer> p = pl.get(rp);
                    
                    boolean rt = checkRowForNum(r,p.getT(),i);
                    boolean ct = checkColForNum(c,p.getV(),i);
                    
                    if(!rt && !ct){
                        //System.out.println("Add - " + fl.get(ri) + " to " + p.getT() + "," + p.getV());
                        cell.addNum(p.getT(), p.getV(), i);
                        ll.remove(li);
                        pl.remove(rp);
                        pass = true;
                    }
                    if(checks == 10){
                        //smartFillCell(r,c);
                        break;
                    }
                }while(!pass);
                
            }
        }
        public void fillCell(int r, int c, int num){
            SudokuCell cell = getCell(r,c);
            if(cell == null || num == 0){
                return;
            }
            cell.emptyCell();
            
            LinkedList<Integer> ll = new LinkedList<>();
            for(int x = 1; x <= 9; x++){
                ll.add(x);
            }
            
            //create fill list of random ints from ll
            LinkedList<Integer> fl = new LinkedList<>();
            
            for(int x = 0; x < num; x++){
                int ri = new Random().nextInt(ll.size());
                fl.add(ll.get(ri));
                ll.remove(ri);
            }
            
            //get random pairs
            
            LinkedList<Pair> pl = new LinkedList<>();
            for(int x = 0; x < num; x++){
                boolean pass = false;
                do{
                    int rr = new Random().nextInt(row);
                    int rc = new Random().nextInt(col);
                    
                    Pair<Integer, Integer> p = new Pair<>(rr,rc);
                    if(!pl.contains(p)){
                        pl.add(p);
                        pass = true;
                    }
                }while(!pass);
            }
            //System.out.println("PAIRS - " + pl.toString());
            
            //assign random values to positions
            for(int x = 0; x < num; x++){
                
                boolean pass = false;
                int checks = 0;
                
                do{
                    checks++;
                    int ri = new Random().nextInt(fl.size());
                    int rp = new Random().nextInt(pl.size());
                    Pair p = pl.get(rp);
                
                    boolean rt = checkRowForNum(r,(int)p.getT(),fl.get(ri));
                    
                    boolean ct = checkColForNum(c,(int)p.getV(), fl.get(ri));
                    
                    if(!rt){
                        //System.out.println("Num not in row");
                    }else if(rt){
                        //System.out.println("Num in row");
                    }
                    if(!ct){
                        //System.out.println("Num not in col");
                    }else if(ct){
                        //System.out.println("Num in col");
                    }
                    
                    if(!rt && !ct){
                        //System.out.println("Add - " + fl.get(ri) + " to " + p.getT() + "," + p.getV());
                        cell.addNum((int)p.getT(),(int)p.getV(),fl.get(ri));
                        fl.remove(ri);
                        pl.remove(rp);
                        pass = true;
                    
                    }
                    if(checks == 10){
                        //fillCell(r,c,num);
                        break;
                    }
                }while(!pass);
                
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
        
        public boolean checkRowForNum(int r1, int r2, int num){
            //check if row contain num
            LinkedList<Integer> rl = new LinkedList<>();
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(x == r1){
                        for(int i: grid[x][y].getRow(r2)){
                            rl.add(i);
                        }
                        if(rl.contains(num)){
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        public boolean checkColForNum(int c1, int c2, int num){
            //check if col contains num
            LinkedList<Integer> cl = new LinkedList<>();
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(y == c1){
                        for(int i: grid[x][y].getCol(c2)){
                            cl.add(i);
                        }
                        if(cl.contains(num)){
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        
        public boolean checkGrid(){
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(!grid[x][y].checkCell()){
                        return false;
                    }
                }
            }
            
            return true;
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
            for(int a = 1; a <= 9; a++){
                if(!rl.contains(a)){
                    return false;
                }
            }
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
            for(int a = 1; a <= 9; a++){
                if(!cl.contains(a)){
                    return false;
                }
            }
            return true;
        }
        
        public boolean checkRow(int r){
            for(int y = 0; y < col; y++){
                if(!checkRow(r,y)){
                    return false;
                }
            }
            return true;
        }
        public boolean checkCol(int c){
            for(int x = 0; x < row; x++){
                if(!checkCol(x,c)){
                    return false;
                }
            }
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
        
        
        public void smartFillCell(){
            LinkedList<Integer> ul = new LinkedList<>();
            ul.clear();
            
            LinkedList<Integer> ll = new LinkedList<>();
            for(int x = 1; x <= 9; x++){
                ll.add(x);
            }
            
            LinkedList<Integer> rl = ll;
            
            //go through all cells and get the number and add to ul
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(cell[x][y] != 0){
                        ul.add(cell[x][y]);
                        if(rl.contains(cell[x][y])){
                            int i = rl.indexOf(cell[x][y]);
                            rl.remove(i);
                        }
                    }
                }
            }
            
            //System.out.println("USED - " + ul.toString());
            //System.out.println("REMAINING - " + rl.toString());
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(cell[x][y] == 0){
                        int ri = new Random().nextInt(rl.size());
                        //System.out.println("SELECTED - " + ri + " Value: " + rl.get(ri));
                        
                        cell[x][y] = rl.get(ri);
                        ul.add(rl.get(ri));
                        rl.remove(ri);
                       
                    }
                }
            }
            
            //System.out.println("USED - " + ul.toString());
            //System.out.println("REMAINING - " + rl.toString());
        }
        
        public void emptyCell(){
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    cell[x][y] = 0;
                }
            }
        }
        public int[][] getCell(){
            return cell;
        }
        public void fillCell(){
            LinkedList<Integer> ll = new LinkedList<>();
            for(int x = 1; x <= 9; x++){
                ll.add(x);
            }
            
            for(int x = 0; x < row; x++){
                for(int y = 0; y < col; y++){
                    if(ll.size() == 1){
                        cell[x][y] = (int) ll.get(0);
                        ll.remove(0);
                    }else{
                        int num = new Random().nextInt(ll.size());
                        cell[x][y] = (int) ll.get(num);
                        ll.remove(num);
                    }
                }
            }
        }
        public void randomFill(int num){
            emptyCell();
            
            LinkedList<Integer> ll = new LinkedList<>();
            for(int x = 1; x <= 9; x++){
                ll.add(x);
            }
            //create fill list of random ints from ll
            LinkedList<Integer> fl = new LinkedList<>();
            
            for(int x = 0; x < num; x++){
                int ri = new Random().nextInt(ll.size());
                fl.add(ll.get(ri));
                ll.remove(ri);
            }
            
            //get random pairs
            
            LinkedList<Pair> pl = new LinkedList<>();
            for(int x = 0; x < num; x++){
                boolean pass = false;
                do{
                    int rr = new Random().nextInt(row);
                    int rc = new Random().nextInt(col);
                    
                    Pair<Integer, Integer> p = new Pair<>(rr,rc);
                    if(!pl.contains(p)){
                        pl.add(p);
                        pass = true;
                    }
                }while(!pass);
            }
            //System.out.println("PAIRS - " + pl.toString());
            
            //assign random values to positions
            for(int x = 0; x < num; x++){
                int ri = new Random().nextInt(fl.size());
                int rp = new Random().nextInt(pl.size());
                Pair p = pl.get(rp);
                
                
                addNum((int)p.getT(),(int)p.getV(),fl.get(ri));
                
                fl.remove(ri);
                pl.remove(rp);
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
                if(s == 0){
                    sb.append(' ');
                }else{
                    sb.append(s);
                }
                
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
                    if(cell[x][y] == 0){
                        sb.append(" ");
                    }else{
                        sb.append(cell[x][y]);
                    }
                    
                    
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
        grid.randomFill(17);
        
        SudokuCell sCell = sSolver.new SudokuCell();
        //grid.setCell(0, 0, sCell);
        
        System.out.println(grid.toString());
        
    }
}