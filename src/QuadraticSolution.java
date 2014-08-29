import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class QuadraticSolution {
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		while (true) {
			int n = sc.nextInt();
			if (n == 0) break;
			
			Point[] points = new Point[n];
			
			for (int i = 0; i < n; i++) {
				points[i] = new Point(sc.nextInt(), sc.nextInt());
			}
			
			solve(points);
		}
		
		sc.close();
		
	}
	
	public static int crossProduct(Point a, Point b, Point c) {
		return a.x * b.y + b.x * c.y + c.x * a.y - a.y * b.x - b.y * c.x - c.y * a.x;
	}
	
	public static void solve(Point[] points) {
		Arrays.sort(points,
				new Comparator<Point>() {
		            public int compare(Point p1, Point p2) {
		            	if (p1.x == p2.x && p1.y == p2.y)
		                	return 0;
		            	
		                if (p1.x < p2.x || p1.x == p2.x && p1.y < p2.y)
		                	return -1;
		                else
		                	return 1;
		            }
        		});
		
		boolean[] mark = new boolean[points.length];
		int sol = 0;
		while (points.length > 0) {
			sol++;
			ArrayList<Integer> upper = getChain(points, true);
			ArrayList<Integer> lower = getChain(points, false);
			
			Arrays.fill(mark, 0, points.length, false);
			for (int i = 0; i < upper.size(); i++) {
				mark[ upper.get(i) ] = true;
			}
			for (int i = 0; i < lower.size(); i++) {
				mark[ lower.get(i) ] = true;
			}
			
			int total = 0;
			for (int i = 0; i < points.length; i++) {
				if (!mark[i]) total++;
			}
			
			Point[] tmp = new Point[total];
			int j = 0;
			for (int i = 0; i < points.length; i++){
				if (!mark[i]) tmp[j++] = points[i];
			}
			
			points = tmp;
		}
		System.out.println(sol % 2 == 0 ? 
				"Do not take this onion to the lab!" :
				"Take this onion to the lab!");
	}
	
	public static ArrayList<Integer> getChain(Point[] points, boolean up) {
		ArrayList<Integer> chain = new ArrayList<Integer>();
		
		for (int i = 0; i < points.length; i++){
			chain.add(i);
			int size = chain.size();
			while (size >= 3) {
				Point a = points[chain.get(size - 3)];
				Point b = points[chain.get(size - 2)];
				Point c = points[chain.get(size - 1)];
				
				int cross = crossProduct(a, b, c);
				
				if (up) {
					if (cross <= 0) break;
				} else {
					if (cross >= 0) break;
				}
				
				chain.remove(size - 2);
				size--;
			}
		}
		
		return chain;
	}

}
