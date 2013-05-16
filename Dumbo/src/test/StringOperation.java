package test;

public class StringOperation {
	private static void getId( String str, String alias ) {
        int idIndex = str.indexOf( alias+"_id=" );
        int equasIndex = str.indexOf( "=", idIndex );
        int colonIndex = str.indexOf( ":", equasIndex );
        System.out.println(str.substring( equasIndex+1, colonIndex ));
    }
    
    public static void main( String[] args ) {
        getId( "xyz_id=tee:", "xyz" );
    }
}
