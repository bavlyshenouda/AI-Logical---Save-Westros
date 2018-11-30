package saveWestros;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class generateGrid {

	public static int n,m;
	
	public char[][] grid;
	
	public static final char WHITE_WALKER = 'w', EMPTY_CELL = '.',
			OBSTACLE = 'o', JON_SNOW = 'j', DRAGONGLASS = 'd',
			JON_ON_DRAGONGLASS = '@';
	
	public static char[]grid_elements = {WHITE_WALKER, OBSTACLE, EMPTY_CELL, EMPTY_CELL, EMPTY_CELL};
	

	
	private static void genGrid() throws FileNotFoundException {
		char[][] newGrid;
		boolean addDragonGlass = true;
		Random randomIndexGenerator = new Random();
		
		PrintWriter out = new PrintWriter("kb.pl");
		n = randomIndexGenerator.nextInt(5);
		m = randomIndexGenerator.nextInt(5);
		if( n < 2 ) n = 2;
		if( m < 2 ) m = 2;
		
		newGrid = new char[n][m];	
		Random randomDragonGlass = new Random();
		int white_walker_count = 0;
		for (int i = 0; i < newGrid.length; i++) {
			for (int j = 0; j < newGrid[i].length; j++) {
				
				if(i == newGrid.length-1 && j == newGrid[i].length-1){
					newGrid[i][j] = JON_SNOW;
					out.println("jonLoc(" + i +","+j+",s0).");
					continue;
				}
				char cell = grid_elements[randomIndexGenerator.nextInt(grid_elements.length)];
				if(cell == EMPTY_CELL && addDragonGlass){
					out.println("dgLoc(" + i +","+j+").");
					addDragonGlass = false;
					
				}
				while(cell == WHITE_WALKER && white_walker_count == 2){
					cell = grid_elements[randomIndexGenerator.nextInt(grid_elements.length)];
				}
				newGrid[i][j] = cell;
				if(newGrid[i][j] == WHITE_WALKER) {
					out.println("wwLoc(" + i +","+j+").");
					white_walker_count++;
				}
				if(newGrid[i][j] == OBSTACLE) {
					out.println("obstLoc(" + i +","+j+").");					
				}
				
			}
		}
		int dragonGlass = randomDragonGlass.nextInt(white_walker_count+1) + 1;
		out.println("maxDG(" +dragonGlass+").");	
		out.println("currentDG(" +0+",s0).");
		out.println("gridSize(" +n +","+m+").");	
		out.close();
		out.flush();
	}
	public static void main(String[] args) throws FileNotFoundException {
		//Scanner sc = new Scanner(System.in);
		//System.out.println("Enter Width:");
		//n = sc.nextInt();
		//System.out.println("Enter Height:");
		//m = sc.nextInt();
		
		genGrid();
	}
}
