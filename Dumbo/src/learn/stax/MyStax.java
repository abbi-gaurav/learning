package learn.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class MyStax {
	private static int pointer;
	public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(args[0]));
		
		while(reader.hasNext()){
			process(reader);
		}
	}

	private static void process(XMLStreamReader reader) throws XMLStreamException {
		int currentPos = reader.next();
		switch (currentPos) {
		case XMLStreamReader.START_DOCUMENT:
			System.out.println("Document start");
			break;
		case XMLStreamReader.START_ELEMENT:
			System.out.println(printData(reader.getName().toString(),pointer));
			pointer++;
			break;
		case XMLStreamReader.END_ELEMENT:
			pointer--;
			break;
		case XMLStreamReader.ATTRIBUTE:
			String attribute = reader.getAttributeLocalName(currentPos)+"="+reader.getAttributeValue(currentPos);
			System.out.println(printData(attribute,pointer));
			break;
		case XMLStreamReader.END_DOCUMENT:
			System.out.println("Document end");
			break;
		case XMLStreamReader.CHARACTERS:
		case XMLStreamReader.COMMENT:
		case XMLStreamReader.CDATA:
			if(reader.getTextLength() > 0){
				System.out.println(printData(reader.getText(),pointer));
			}
			break;
		default:
			break;
		}
	}

	public static String printData(String name,int position) {
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<position;i++){
			sb.append("\t");
		}
		return sb.append(name).toString();
	}
}
