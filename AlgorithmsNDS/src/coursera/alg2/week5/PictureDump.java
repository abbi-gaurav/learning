package coursera.alg2.week5;

import java.awt.Color;

import edu.princeton.cs.introcs.BinaryStdIn;
import edu.princeton.cs.introcs.Picture;
import edu.princeton.cs.introcs.StdOut;

public class PictureDump {
	public static void main(String[] args) {
		int w = Integer.parseInt(args[0]);
		int h = Integer.parseInt(args[1]);
		
		Picture pic = new Picture(w, h);
		int count = 0;
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				pic.set(j, i, Color.RED);
				
				if(!BinaryStdIn.isEmpty()){
					boolean b = BinaryStdIn.readBoolean();
					if(b){
						pic.set(j, i, Color.BLACK);
					}else{
						pic.set(j, i, Color.WHITE);
					}
					count++;
				}
			}
		}
		
		pic.show();
		StdOut.println(count+ " bits");
	}
}
