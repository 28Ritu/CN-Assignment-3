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
public class LinkState_16078 {
	static int Vertices;
	static ArrayList<Integer> links; 
	public static void linkstate(int[][] matrix, List<Integer> vertices, int start)
	{ 
		int[] shortest = new int[Vertices];
		boolean[] via = new boolean[Vertices];
		
		for (int i = 0; i < Vertices; i++)
		{
			shortest[i] = Integer.MAX_VALUE;
			via[i] = false;
		}
		
		shortest[start] = 0;
		int[] tree = new int[Vertices];
		tree[start] = -1;
		
		for (int i = 1; i < Vertices; i++)
		{
			int next = -1;
			int shortestDist = Integer.MAX_VALUE;
			for (int j = 0; j < Vertices; j++)
			{
				if (!via[j] && shortest[j] < shortestDist)
				{
					next = j;
					shortestDist = shortest[j];
				}
			}
			via[next] = true;
			for (int j = 0; j < Vertices; j++)  
			{
				int edgeDist = matrix[next][j]; 
				if (edgeDist > 0 && ((shortestDist + edgeDist) < shortest[j]))
				{ 
					tree[j] = next; 
					shortest[j] = shortestDist + edgeDist; 
					System.out.println("Src: " + tree[j] + " Destination: " + j + " Shortest Distance from " + tree[j] + ": " + edgeDist);
				} 
           } 
		}
		System.out.println("Forwarding Table for Vertex " + start + " :");
		System.out.println("Destination\t Shortest Distance\t Link"); 

		for (int i = 0;  i < Vertices;  i++)  
		{ 
			links = new ArrayList<Integer>();
			if (vertices.contains(i))
			{
				if (i != start)  
				{ 
					System.out.print("    " + i + " \t\t\t "); 
					System.out.print(shortest[i] + "\t\t "); 
					printLink(i, tree);
					System.out.print(links.get(0) + " " + links.get(1));
					System.out.println();
				} 
			}
		} 
	}  
	private static void printLink(int curr, int[] tree) 
	{ 
		if (curr == -1) 
		{ 
			return; 
		} 
		printLink(tree[curr], tree); 
		links.add(curr);
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
		int[][] am = new int[vertices.get(vertices.size() - 1) + 1][vertices.get(vertices.size() - 1) + 1];
		for (int i = 0; i < graph.length; i++)
		{
			am[graph[i][0]][graph[i][1]] = graph[i][2];
			am[graph[i][1]][graph[i][0]] = graph[i][2];
		}
		
		System.out.print("Enter the vertex for which to print the forwarding table (" + vertices.get(0) + " - " + vertices.get(vertices.size() - 1) + "): ");
		int vertex = InReader.nextInt();
        linkstate(am, vertices, vertex);
	}
}