import java.awt.Color;
import java.util.Arrays;


public class SeamCarver {
	private double[][] ea;
	private boolean isVertical;
	private int[][] colorArrays;
	
	public SeamCarver(Picture picture){
		createEAs(picture);
	}

	private void createEAs(Picture picture) {
		this.ea = calculateEnergy(picture);
		this.isVertical= true;
	}
	
	public Picture picture(){
		if(!isVertical){
			transpose();
		}
		Picture pic = new Picture(colorArrays[0].length, colorArrays.length);
		for(int i=0;i<colorArrays.length;i++){
			for(int j=0;j<colorArrays[0].length;j++){
				int rgb = colorArrays[i][j];
				pic.set(j, i, new Color(rgb));
			}
		}
		return pic;
	}

	public int width(){
		return isVertical ? this.ea[0].length:this.ea.length;
	}
	
	public int height(){
		return isVertical ? ea.length:ea[0].length;
	}
	
	public double energy(int x, int y){
		validate(x,y);
		return isVertical ? ea[y][x] : ea[x][y];
	}

	private void validate(int x, int y) {
		if(x < 0 || x > width()-1){
			throw new IndexOutOfBoundsException();
		}
		
		if(y < 0 || y > height()-1){
			throw new IndexOutOfBoundsException();
		}
	}

	public int[] findVerticalSeam(){
		if(!isVertical){
			transpose();
		}
		return findVerticalSeam(ea);
	}
	
	public int[] findHorizontalSeam(){
		if(isVertical){
			transpose();
		}
		return findVerticalSeam(ea);
	}
	
	private void transpose() {
		isVertical = !isVertical;
		double[][] transposed = new double[ea[0].length][ea.length];
		int[][] transposedColor = new int[colorArrays[0].length][colorArrays.length];
		for (int j = 0; j < ea[0].length; j++) {
			transposedColor[j] = new int[ea.length];
			for (int i = 0; i < ea.length; i++) {
				transposed[j][i] = ea[i][j];
				transposedColor[j][i] = colorArrays[i][j];
			}
		}
		this.ea = transposed;
		this.colorArrays = transposedColor;
	}

	public void removeHorizontalSeam(int[] a){
		validateHS(a);
		if(isVertical){
			transpose();
		}
		removeSeam(a);
	}
	
	public void removeVerticalSeam(int[] a){
		validateVS(a);
		if(!isVertical){
			transpose();
		}
		removeSeam(a);
	}
	
	private static Picture removeVerticalSeam(Picture pic, int[] a) {
		Picture afterRemoval = new Picture(pic.width()-1, pic.height());
		int seamPoint = 0;
		for(int j = 0; j < pic.height();j++){
			for(int i = 0; i<pic.width();i++){
				if(i < a[seamPoint]){
					afterRemoval.set(i, j, pic.get(i, j));
				}else if(i > a[seamPoint]){
					afterRemoval.set(i-1, j, pic.get(i, j));
				}
			}
			seamPoint++;
		}
		return afterRemoval;
	}
	
	private void validateHS(int[] a) {
		if(a.length != width() || width() <= 1){
			throw new IndexOutOfBoundsException();
		}
		validateSeamCorrectness(a);
	}

	private void validateVS(int[] a) {
		if(a.length != height() || height() <= 1){
			throw new IndexOutOfBoundsException();
		}
		validateSeamCorrectness(a);
	}

	private void validateSeamCorrectness(int[] a) {
		int previous = a[0];
		for(int i =1; i < a.length;i++){
			if(previous-a[i] > 1){
				throw new IndexOutOfBoundsException();
			}
			previous = a[i];
		}
	}

	private static int[] findVerticalSeam(double[][] energyArray) {
		return new VSP32(energyArray).seam();
	}
	
	private static Picture transpose(Picture originalPic){
		Picture transposed = new Picture(originalPic.height(),originalPic.width());
		for(int j = 0; j < originalPic.height();j++){
			for(int i = 0; i<originalPic.width();i++){
				transposed.set(j,i, originalPic.get(i, j));
			}
		}
		
		return transposed;
	}
	
	private double[][] calculateEnergy(Picture pic) {
		int width = pic.width();
		int height = pic.height();
		colorArrays = new int[height][];
		
		double [][] energyArray = new double[height][width];
		for(int j = 0; j < height;j++){
			colorArrays[j] = new int[width];
			for(int i = 0; i<width;i++){
				energyArray[j][i] = Utils.energy(i, j,pic);
				colorArrays[j][i] = pic.get(i, j).getRGB();
			}
		}
		return energyArray;
	}
	
	private void removeSeam( int[] a) {
		double[][] afterRemoval = new double[ea.length][ ea[0].length-1];
		int[][] afterRemColor = new int[colorArrays.length][colorArrays[0].length-1];
		
		int seamPoint = 0;
		for(int j = 0; j < ea.length;j++){
			for(int i = 0; i<ea[0].length;i++){
				if(i < a[seamPoint]){
					afterRemoval[j] [i] =  ea[j][ i];
					afterRemColor[j][i] = colorArrays[j][i];
				}else if(i > a[seamPoint]){
					afterRemoval[j][ i-1] = ea[j][ i];
					afterRemColor[j][i-1] = colorArrays[j][i];
				}
			}
			seamPoint++;
		}
		this.colorArrays = afterRemColor;
		this.ea = afterRemoval;
	}
	public static void main(String[] args) {
		SeamCarver sc = new SeamCarver(new Picture(args[0]));
		System.out.println(Arrays.toString(sc.findVerticalSeam()));
		printEA(sc.calculateEnergy(sc.picture()));
		
		SeamCarver sc2 = new SeamCarver(transpose(sc.picture()));
		printEA(sc2.calculateEnergy(sc2.picture()));
		
		int[] vs = sc.findVerticalSeam();
		int[] hs = sc.findHorizontalSeam();
		
		System.out.println(Arrays.toString(vs));
		System.out.println(Arrays.toString(hs));
		sc.removeVerticalSeam(vs);
		
		printEA(sc.calculateEnergy(sc.picture()));
	}

	private static void printEA(double[][] ea) {
		for(int i = 0; i < ea.length;i++){
			System.out.println(Arrays.toString(ea[i]));
		}
	}
}
