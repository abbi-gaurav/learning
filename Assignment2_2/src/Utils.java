import java.awt.Color;


public class Utils {

	public static boolean isBorder(int x, int y, int width, int height) {
		return x == 0|| y == 0 || 
				x == width-1 || y == height-1;
	}

	public static double delta(Color color1, Color color2) {
		
		double rd = Math.abs(color1.getRed()	-	color2.getRed());
		double gd = Math.abs(color1.getGreen()	-	color2.getGreen());
		double bd = Math.abs(color1.getBlue() 	- 	color2.getBlue());
		return rd*rd+gd*gd+bd*bd;
	}

	public static  double energy(int x, int y, Picture pic) {
		if(isBorder(x, y, pic.width(), pic.height())){
			return 195075.0;
		}
		
		return delta(pic.get(x-1, y),pic.get(x+1, y))+
								delta(pic.get(x, y-1),pic.get(x, y+1));
	}

	public static int[] getadj( int w, int p) {
		if(p == 0){
			return new int[]{p,p+1};
		}else if(p == w-1){
			return new int[]{p,p-1};
		}
		return new int[]{p,p+1,p-1};
	}
	
	public static int[] getadj2( int w, int h, int p) {
		if(p == w*h){
			return getFirstRow(w);
		}else if (p == w*h+1){
			return new int[]{};
		} else {
			int i = p/w;
			int j = p%w;
			if(i == h-1){
				return new int[]{w*h+1};
			}else if (j == 0) {
				return new int[] { getDown(w, i, j), getRD(w, i,j) };
			} else if (j == w - 1) {
				return new int[] { getDown(w, i, j), getLD(w, i, j) };
			}else{
				return new int[]{getDown(w, i, j), getLD(w, i, j), getRD(w, i,j)};
				
			}
		}
	}

	private static int getLD(int w, int i, int j) {
		return ((i+1)*w)+j - 1;
	}

	private static int getRD(int w, int i,int j) {
		return ((i+1)*w) +j+ 1;
	}

	private static int getDown(int w, int i, int j) {
		return (i+1)*w+j;
	}

	private static int[] getFirstRow(int w) {
		int [] firstRow = new int[w];
		for(int i = 0; i<w;i++){
			firstRow[i] = i;
		}
		return firstRow;
	}

	public static int findMinIndex(double... d) {
		int min = 0;
		
		for(int i = 1; i<d.length;i++){
			if(d[i] < d[min]){
				min = i;
			}
		}
		return min;
	}

}
