//Ritu Kumari 2016078
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

class InReader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    
    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }
    
    static String nextLine() throws IOException
    {
    	return reader.readLine();
    }
    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
    
    static float nextFloat() throws IOException {
    	return Float.parseFloat( next() );
    }
}
public class DistVect_16078 {
	static int Vertices, nV;
	static int[][] routing;
	static int[][] via;
	
	public static void initialise(int[][] matrix)
	{
		for(int i = 0; i < Vertices; i++)
		{
			for(int j = 0; j < Vertices; j++)
			{
				routing[i][j] = matrix[i][j];
				via[i][j] = j;
			}
		 }
	 }
	
	 static void update_tables(int[][] matrix)
	 {
		 int ctr;
		 do 
		 {
			 ctr = 0;
			 for (int i = 0; i < Vertices; i++)
			 {
				 for (int j = 0; j < Vertices; j++)
				 {
					 for (int k = 0; k < Vertices; k++)
					 {
						 if (routing[i][j] > (matrix[i][k] + routing[k][j]))
						 {
							 routing[i][j] = routing[i][k] + routing[k][j];
							 via[i][j] = k;
							 System.out.println("Update...");
							 System.out.println("Src: " + i + " Dest: " + j + " New Cost: " + routing[i][j] + " Via node: " + k);
							 print_routing_table();
							 ctr += 1;
						 }
					 }
				}
			}
		 } while (ctr != 0);
	 }
	 
	 public static void print_routing_table()
	 {
		 for(int i = 1; i < Vertices; i++)
		 {
			 for(int j = 1; j < Vertices; j++)
			 {
				 System.out.print(routing[i][j] + "    ");
			 }
			 System.out.println();
		 }
		 System.out.println();
	}
	
	public static void print_via_table()
	{
		System.out.println();
		System.out.println("Via Table: ");
		for(int i = 1; i < Vertices; i++) 
		 {
			 for(int j = 1; j < Vertices; j++)
			 {
				 System.out.print(via[i][j] + "    ");
			 }
			 System.out.println();
		 }
	}
	 
	public static void main(String[] args) throws IOException 
	{
		InReader.init(System.in);
		System.out.print("Enter the network topology: ");
		String info = InReader.nextLine();
		String[] paths = info.split("; ");
		int[][] graph = new int[paths.length][3];
		List<Integer> vertices = new ArrayList<Integer>(); 
		
		for (int i = 0; i < paths.length; i++)
		{
			String[] path_info = paths[i].split(", ");
			String[] nodes = path_info[0].split(":");
			graph[i][0] = Integer.parseInt(nodes[0]);
			graph[i][1] = Integer.parseInt(nodes[1]);
			
			if (!vertices.contains(graph[i][0]))
				vertices.add(graph[i][0]);
			if (!vertices.contains(graph[i][1]))
				vertices.add(graph[i][1]);
			
			if (path_info[1].indexOf(';') != -1)
				graph[i][2] = Integer.parseInt(path_info[1].substring(0, path_info[1].indexOf(';')));
			else 
				graph[i][2] = Integer.parseInt(path_info[1]);
		}
		
		Collections.sort(vertices);
		Vertices = vertices.get(vertices.size() - 1) + 1;
		nV = vertices.size();
		int[][] am = new int[Vertices][Vertices];
		routing = new int[Vertices][Vertices];
		via = new int[Vertices][Vertices];
		
		for (int i = 0; i < Vertices; i++)
		{
			for (int j = 0; j < Vertices; j++)
			{
				if (i == j)
					am[i][j] = 0;
				else
					am[i][j] = 9999;
			}
		}
		for (int i = 0; i < graph.length; i++)
		{
			am[graph[i][0]][graph[i][1]] = graph[i][2];
			am[graph[i][1]][graph[i][0]] = graph[i][2];
		}
		
		System.out.println("Initialising Routing Table...");
		initialise(am);
		print_routing_table();
		update_tables(am);
		System.out.println();
		System.out.println("Updated Routing Tables are: ");
		print_routing_table();
		print_via_table();
		
		int choice;
		String ch;
		
		do {
			System.out.println();
			System.out.println("1. Change Link Cost");
			System.out.println("2. Exit");
			System.out.print("Enter your choice (1/2): ");
			choice = InReader.nextInt();
			
			if (choice == 2)
				break;
			System.out.print("Enter the SOURCE node for which to change the cost: ");
			int src = InReader.nextInt();
			System.out.print("Enter the DESTINATION node for which to change the cost: ");
			int dest = InReader.nextInt();
			System.out.print("Enter the new link COST: ");
			int cost = InReader.nextInt();
			
			am[src][dest] = cost;
			am[dest][src] = cost;
			
			System.out.println("Initialising Routing table after cost change...");
			initialise(am);
			print_routing_table();
			print_via_table();
			
			update_tables(am);
			
			System.out.println();
			System.out.println("New Routing Tables are: ");
			print_routing_table();
			print_via_table();
			
			System.out.println();
			System.out.print("Want to continue (y/n)? ");
			ch = InReader.next();
			
		} while (ch.equals("y") || ch.equals("Y"));
	}
}
