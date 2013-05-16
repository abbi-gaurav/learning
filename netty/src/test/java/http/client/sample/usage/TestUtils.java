package http.client.sample.usage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class TestUtils {
	private static AtomicInteger counter = new AtomicInteger();
	
	public static String addUriParams(String uri,Map<String, Object> uriParams){
		if(uriParams != null && uriParams.size() > 0){
			uri = uri+"?";
			Iterator<Entry<String, Object>> iterator = uriParams.entrySet().iterator();
			do{
				Entry<String, Object> entry = iterator.next();
				uri = uri+entry.getKey()+"="+entry.getValue();
			}while(iterator.hasNext() && ((uri = uri+"&") != null));
		}
		return uri;
	}
	
	public static String addFileId(String uri){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", counter.incrementAndGet());
		return addUriParams(uri, map);
	}
}
