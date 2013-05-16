package kata.karateChop_2;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BS1 {
	private static int[] INPUT_ARRAY
//	= {0,7,16,25,34,43,52,61,70,79}
	;
	static{
		INPUT_ARRAY = new int[24576];
		INPUT_ARRAY[0] = 0;
		INPUT_ARRAY[1] = 7;
		for(int i = 2;i<INPUT_ARRAY.length;i++){
			INPUT_ARRAY[i] = INPUT_ARRAY[i-1]+9; 
		}
	}
	public static void main(String[] args) {
		int parseInt = Integer.parseInt(args[0]);
		System.out.println(INPUT_ARRAY[INPUT_ARRAY.length-1]);
		System.out.println(INPUT_ARRAY[recursiveBS(parseInt,INPUT_ARRAY)]);
		System.out.println(INPUT_ARRAY[iterativeBS(parseInt,INPUT_ARRAY)]);
	}

	private static int recursiveBS(int target, int[] searchArray) {
		int index = searchArray.length/2;
		int find = searchArray[index];
		if(target == find){
			return index;
		}else if(searchArray[0] > target || 
				searchArray[searchArray.length-1] < target 
				|| searchArray.length == 1){
			return -1;
		}else if(target < find){
			int[] leftArray = new int[index];
			System.arraycopy(searchArray, 0, leftArray, 0, leftArray.length);
			return recursiveBS(target, leftArray);
		}else{
			int[] rightArray = new int[searchArray.length - (index+1)];
			System.arraycopy(searchArray, index+1, rightArray, 0, rightArray.length);
			return (index+1) + recursiveBS(target, rightArray);
		}
	}
	
	private static int iterativeBS(int target, int[] searchArray){
		int start = 0;
		int end = searchArray.length;
		while(start <= end){
			int index = (start+end)/2;
			int find = searchArray[index];
			if(target == find){
				return index;
			}else if(target < find){
				end = index-1;
			}else if(target > find){
				start = index+1;
			}
		}
		return -1;
	}
	
	@Test(testName="TestBS",dataProvider="input")
	public void testBS(int testValue){
		assertResult(recursiveBS(testValue, INPUT_ARRAY),testValue);
		assertResult(iterativeBS(testValue, INPUT_ARRAY),testValue);
	}

	protected void assertResult(int resultIndex, int actualValue) {
		if(actualValue == -1){
			Assert.assertEquals(actualValue, resultIndex);
		}else{
			Assert.assertEquals(INPUT_ARRAY[resultIndex],actualValue);
		}
	}
	
	@DataProvider(name="input")
	public Object[][] createInput(){
		Random random = new Random();
		return new Object[][]{
			new Object[]{INPUT_ARRAY[random.nextInt(INPUT_ARRAY.length)]}	
		};
	}
}
