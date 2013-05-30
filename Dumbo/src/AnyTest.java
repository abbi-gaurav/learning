import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class AnyTest {
	@SuppressWarnings("unused")
	private static String createStringForServerAliases( List<String> serverKeyAliases ) {
        if(serverKeyAliases != null && serverKeyAliases.size() > 0){
            StringBuilder sb = new StringBuilder();
            Iterator<String> iterator = serverKeyAliases.iterator();
            while ( true ) {
				String alias = iterator.next();
                sb.append( alias );
                if(iterator.hasNext()){
                    sb.append( "|" );
                }else{
                    return sb.toString();
                }
            }
        }
        return "";
    }
    
    public static void main( String[] args ) {
        testNullString();
    }

	private static void testNullString() {
		StringBuilder sb = new StringBuilder();
		List<String> list = null;
		sb.append(list);
		System.out.println(sb.toString());
	}
	
	
	private void exceptionCheck(){
		try{
			throw new IOException(new IOException("dummy"));
		}catch(IOException e){
			if(e.getCause() instanceof IOException){
				System.out.println("right");
			}
		}
	}
}
