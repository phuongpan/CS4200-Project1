import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class Project1 {
	public static int row[] = { 1, 0, -1, 0 };
	public static int col[] = { 0, -1, 0, 1 };
	public static int pos, step, nodeGenerated; // coordinate of blank position
	
	public static void main(String[] args) 
	{
		int goal[]= {0,1,2,3,4,5,6,7,8};
		boolean exit = false;
		Scanner sc = new Scanner(System.in);
		while(!exit)
		{
			System.out.println("Select:");
			System.out.println("[1] Generate a ramdom puzzle ");
			System.out.println("[2] Input a puzzle");
			System.out.println("[3] Generate puzzles in file");
			System.out.println("[4] Exit");
			int input;
			input = sc.nextInt();
			while (input < 1 || input >  4)
			{
				System.out.println("Please input from the range 1 to 4!");
				input = sc.nextInt();
			}
			
			if(input == 1) // generate a random matrix
			{
				int initial[] = {0,1,2,3,4,5,6,7,8 };
				Generate_Random_Matrix(initial);
				while(!isSolvable(initial))
				{
					System.out.println("Unsolvable");
					Generate_Random_Matrix(initial);
				}
				System.out.println("solvable");
				Functions();
				int input2 = sc.nextInt();
				while(input2 < 1 || input2 > 2)
				{
					System.out.println("Please input from the range 1 or 2!");
					input = sc.nextInt();
				}
				if(input2 == 1)
					Heuristic1(initial, pos, goal);
				else
					Heuristic2(initial, pos, goal);
				
			}
			else if (input == 2)
			{
				int [] initial= new int [9];
				System.out.println("Please input the value for puzzle (Example: 1 0 6 8 5 2 4 7 3): ");
				
				for(int i = 0;i < initial.length; i++)
				{
					if(sc.hasNextInt())
					{
						initial[i] = sc.nextInt();
						if(initial[i] == 0)
						{
							pos = i;
						}
						while(initial[i] > 8 || initial[i] < 0)
						{
							System.out.println("Must in the range [0, 8]! ");
							initial[i] = sc.nextInt();
							if(initial[i] == 0)
							{
								pos = i;
							}
						}
					}
					
				}
				System.out.println("The Input Puzzle:"); 
				
				if(isSolvable(initial))
        		{
					Functions();
					int input2 = sc.nextInt();
					while(input2 < 1 || input2 > 2)
					{
						System.out.println("Please input from the range 1 or 2!");
						input = sc.nextInt();
					}
					if(input2 == 1)
						Heuristic1(initial, pos, goal);
					else
						Heuristic2(initial, pos, goal);
        		}
        		else
        		{
        			System.out.println("Unsolvable");
        		}
			}
			else if( input == 3)
			{
				try {
					System.out.println("Please type the name of the file:");
					String filename = sc.next();
					File file = new File("C:/Users/Ellie/Documents/CS 4200/project1/" + filename);
					Scanner readFile = new Scanner(file);
					int[] initial = new int [9];
					int i = 0;
					while (readFile.hasNextLine() ) {
					        String data = readFile.nextLine();
					        if(Character.isDigit(data.charAt(0)))
					        {
					        	String[] number = data.trim().split(" ");
					        	if(i < initial.length)
					        	{
					        		for(int j = 0; j < number.length; j++)
					        		{
					        			initial[i] = Integer.parseInt(number[j]);
					        			if(initial[i] == 0)
					        			{
					        				pos = i;
					        			}
					        			i++;
					        		}
					        	}
					        	if(i == initial.length)
					        	{
					        		if(isSolvable(initial))
					        		{
					        			Functions();
					    				int input2 = sc.nextInt();
					    				while(input2 < 1 || input2 > 2)
					    				{
					    					System.out.println("Please input from the range 1 or 2!");
					    					input = sc.nextInt();
					    				}
					    				if(input2 == 1)
					    					Heuristic1(initial, pos, goal);
					    				else
					    					Heuristic2(initial, pos, goal);
						        		System.out.println("-------------------------");
					        		}
					        		else
					        		{
					        			System.out.println("Unsolvable");
					        		}
					        		
					        	}
					        		
					        }
					        else
					        {
					        	i = 0;
					        	
					        }
					        
					}
					readFile.close();
					}
					catch(FileNotFoundException e)
					{
						System.out.println("File not found.");
					      e.printStackTrace();
					}
			}
			else 
			{
				exit = true;
			}
		}
		
		System.out.println("Shutting down ...");
		sc.close();
	
		
		
	}
	public static void Functions()
	{
		System.out.println("Select:");
		System.out.println("[1] Heuristic 1 - Calculating Misplaced Title ");
		System.out.println("[2] Heuristic 2 - Using Mathanttan Distance");
	}
	public static void Generate_Random_Matrix(int [] initial)
	{
	    Random rand = new Random();
	    for (int i = initial.length - 1; i > 0; i--)
	    {
	      int index = rand.nextInt(initial.length);
	      // Simple swap
	      int temp = initial[index];
	      initial[index] = initial[i];
	      initial[i] = temp;
	      if(initial[index] == 0)
	      {
	    	  pos = index;
	      }
	      else if (initial[i] == 0)
	      {
	    	  pos = i;
	      }
	    }
	    
	}
	// Function to check if (x, y) is a valid matrix coordinate
	public static int[] ListOfChildrenPosition(int pos)
	{
		int [] childenPos = null;
		if(pos == 0 || pos == 2 || pos == 6 || pos == 8)
		{
			childenPos = new int[2];
			if(pos == 0)
			{
				childenPos[0] = 1;childenPos[1] = 3;
			}
			else if(pos == 2) 
			{
				childenPos[0] = 1; childenPos[1] = 5;
			}
			else if(pos == 6) 
			{
				childenPos[0] = 3;childenPos[1] = 7;
			}
			else if(pos == 8) 
			{
				childenPos[0] = 5;childenPos[1] = 7;
			}
		}
		else if (pos == 1 || pos == 3 || pos == 5 || pos == 7)
		{
			childenPos = new int[3];
			if(pos == 1)
			{
				childenPos[0] = 0;childenPos[1] = 2;childenPos[2] = 4;
			}
			else if(pos == 3) 
			{
				childenPos[0] = 0; childenPos[1] = 4;childenPos[2] = 6;
			}
			else if(pos == 5) 
			{
				childenPos[0] = 2;childenPos[1] = 4;childenPos[2] = 8;
			}
			else if(pos == 7) 
			{
				childenPos[0] = 4;childenPos[1] = 6;childenPos[2] = 8;
			}
		}
		else if (pos == 4)
		{
			childenPos = new int[4];
			childenPos[0] = 1;childenPos[1] = 3;childenPos[2] = 5;childenPos[2] = 7;
		}
		return childenPos;
	}
	
	static int getInvCount(int[]arr)
	{
	    int inv_count = 0;
	    for(int i = 0; i<arr.length; i++)
	    {
            for(int j = i+1; j< arr.length; j++)
            {
                if(arr[j]>arr[i] && arr[i] > 0 && arr[j] > 0)
                {
                    inv_count++;
                }
            }
        }
	    return inv_count;
	}
	
	static boolean isSolvable(int[] puzzle)
	{
	    // Count inversions in given 8 puzzle
	    int invCount = getInvCount(puzzle);
	    System.out.println(invCount);
	    if(invCount % 2 == 0)
	    	return true;
	    return false;
	}
	public static void Heuristic1(int[] initial, int pos, int[] goal) {
		PriorityQueue<Node> queue = new PriorityQueue<Node>((Comparator<? super Node>) new NodeComparator());
		Node root = new Node(initial, pos, pos, 0, null);
		root.sethVal(Calculate_hDisplaced(initial, goal));
		queue.add(root);
		nodeGenerated = 1;
		while(!queue.isEmpty())
		{
			Node min = queue.element();
			queue.remove();
			if(min.gethVal() == 0)
			{
				// print from root to destination
				step = 0;
				printPath(min);
				System.out.println("Search cost (H1): " + nodeGenerated);
				nodeGenerated = 0;
				step = 0;
				queue.clear();
				return;
			}
			
			int [] ChildrenPos = ListOfChildrenPosition(min.getPos()); 
			for(int i = 0; i < ChildrenPos.length; i++)
			{
				Node temp = min;
				Node child = new Node(temp.getMatrix(),temp.getPos(), ChildrenPos[i], temp.getgVal()+1,temp);
				child.sethVal(Calculate_hDisplaced(child.getMatrix(), goal));
				queue.add(child);
				nodeGenerated++;
				
			}
		}
	}
	
	//------------------------------Mathanttan Heuristic------------------------------------
	public static int Calculate_Manhattan(int initial[], int goal[])
	{
		int count = 0;
		if (initial[0] != goal[0])
		{
			if (initial[1] == goal[0] || initial[3] == goal[0])
				count++;
			else if (initial[2] == goal[0] || initial[6] == goal[0] || initial[1] == goal[0])
				count += 2;
			else if (initial[7] == goal[0] || initial[5] == goal[0])
				count += 3;
			else if (initial[8] == goal[0])
				count += 4;
		}
		if (initial[1] != goal[1])
		{
			if (initial[0] == goal[1] || initial[2] == goal[1] || initial[4] == goal[1])
				count++;
			else if (initial[3] == goal[1] || initial[5] == goal[1] || initial[7] == goal[1])
				count += 2;
			else if (initial[6] == goal[1] || initial[8] == goal[1])
				count += 3;
		}
		if (initial[2] != goal[2])
		{
			if (initial[1] == goal[2] || initial[5] == goal[2])
				count++;
			else if (initial[0] == goal[2] || initial[4] == goal[2] || initial[8] == goal[2])
				count += 2;
			else if (initial[3] == goal[2] || initial[7] == goal[2])
				count += 3;
			else if (initial[6] == goal[2])
				count += 4;
		}
		if (initial[3] != goal[3])
		{
			if (initial[0] == goal[3] || initial[4] == goal[3] || initial[6] == goal[3])
				count++;
			else if (initial[1] == goal[3] || initial[7] == goal[3] || initial[5] == goal[3])
				count += 2;
			else if (initial[2] == goal[3] || initial[8] == goal[3])
				count += 3;
		}
		if (initial[4] != goal[4])
		{
			if (initial[1] == goal[4] || initial[3] == goal[4] || initial[5] == goal[4] || initial[8] == goal[4])
				count++;
			else if (initial[0] == goal[4] || initial[2] == goal[4] || initial[6] == goal[4] || initial[8] == goal[4])
				count += 2;
		}
		if (initial[5] != goal[5])
		{
			if (initial[2] == goal[5] || initial[0] == goal[5] || initial[8] == goal[5])
				count++;
			else if (initial[1] == goal[5] || initial[3] == goal[5] || initial[7] == goal[5])
				count += 2;
			else if (initial[0] == goal[5] || initial[6] == goal[5])
				count += 3;
		}
		if (initial[6] != goal[6])
		{
			if (initial[3] == goal[6] || initial[7] == goal[6])
				count++;
			else if (initial[0] == goal[6] || initial[4] == goal[6] || initial[8] == goal[6])
				count += 2;
			else if (initial[0] == goal[6] || initial[5] == goal[6])
				count += 3;
			else if (initial[2] == goal[6])
				count += 4;
		}
		if (initial[7] != goal[7])
		{
			if (initial[4] == goal[7] || initial[6] == goal[7] || initial[8] == goal[7])
				count++;
			if (initial[1] == goal[7] || initial[3] == goal[7] || initial[5] == goal[7])
				count += 2;
			else if (initial[0] == goal[7] || initial[2] == goal[7])
				count += 3;
		}
		if (initial[8] != goal[8])
		{
			if (initial[5] == goal[8] || initial[7] == goal[8])
				count++;
			else if (initial[2] == goal[8] || initial[4] == goal[8] || initial[6] == goal[8])
				count += 2;
			else if (initial[1] == goal[8] || initial[3] == goal[8])
				count += 3;
			else if (initial[0] == goal[8])
				count += 4;
		}
		return count;

	}
	public static void Heuristic2(int[] initial, int pos, int[] goal) {
		PriorityQueue<Node> queue = new PriorityQueue<Node>((Comparator<? super Node>) new NodeComparator());
		Node root = new Node(initial, pos, pos, 0, null);
		root.sethVal(Calculate_Manhattan(initial, goal));
		nodeGenerated = 1;
		queue.add(root);
		while(!queue.isEmpty())
		{
			Node min = queue.element();
			queue.remove();
			if(min.gethVal() == 0)
			{
				// print from root to destination
				step = 0;
				printPath(min);
				System.out.println("Search Cost (H2): " + nodeGenerated);
				nodeGenerated = 0;
				step = 0;
				queue.clear();
				return;
			}
			
			int [] ChildrenPos = ListOfChildrenPosition(min.getPos()); 
			for(int i = 0; i < ChildrenPos.length; i++)
			{
				Node temp = min;
				Node child = new Node(temp.getMatrix(),temp.getPos(), ChildrenPos[i], temp.getgVal()+1,temp);
				child.sethVal(Calculate_Manhattan(child.getMatrix(), goal));
				queue.add(child);
				nodeGenerated++;
			}
		}
	}
	public static void printMatrix(int mat[])
	{
		for (int i = 0; i < mat.length; i++)
		{
			System.out.print(mat[i] + " ");
			if(i == 2 || i == 5 || i == 8)
				System.out.println();
		}
	}
	// print path from root node to destination node
	public static void printPath(Node root)
	{
		if (root == null)
			return;
		printPath(root.getParent());
		System.out.println("Step "+ step++ + ":");
//		System.out.println("hval: "+ root.gethVal());
//		System.out.println("gval: "+ root.getgVal());
		printMatrix(root.getMatrix());
		System.out.println();
	}
	public static int Calculate_hDisplaced(int [] initial, int [] goal)
	{
		int count = 0;
		for (int i = 0; i < initial.length; i++)
		{
			if(initial[i] != goal[i])
				count++;
		}
					
		return count;
	}
}
class NodeComparator implements Comparator<Node>
{
	public int compare(Node s1, Node s2)
	{
		if((s1.gethVal() + s1.getgVal()) > (s2.gethVal() + s2.getgVal()))
		{
			return 1;
		}
		else if ((s1.gethVal() + s1.getgVal()) <= (s2.gethVal() + s2.getgVal()))
		{
			return -1;
		}
		return 0;
	}
}
