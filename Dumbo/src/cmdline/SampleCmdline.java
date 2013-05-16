package cmdline;

import java.util.Hashtable;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@SuppressWarnings("static-access")
public class SampleCmdline {

	static HelpFormatter formatter = new HelpFormatter();
	static Hashtable<String,Options> HT = new Hashtable<String,Options>();
	//Common options
	static Option p = null;
	static Option s = OptionBuilder.withArgName( "Segment_Release" ).hasArg().withDescription(  "O (SAP Release (3 alhpanum. chars, e.g. 40B) )" ).create( "s" );
	static Option oList = OptionBuilder.withArgName( "outputfilename" ).hasArg().withDescription(  "O (name of listfile that keeps the found elements)" ).create( "o" );
	public static void main(String[] args) throws ParseException {
//		Options opt = PatternOptionBuilder.parsePattern("!l!t:!f:!p>");
//		CommandLineParser parser = new GnuParser();
//		parser.parse(opt, args);
		// TODO Auto-generated method stub
		if (args.length == 0) {
			System.exit(-1);
			return;
		}
		p = OptionBuilder.withArgName( "propertyfilename" ).hasArg().withDescription(  "M (file with SAP specific properties)" ).create( "p" );
		p.setRequired(true);
		CommandLineParser parser = new GnuParser();
		loadDDFCreate();
		loadDDFList();
		loadXSDCreate();
		loadXSDList();
			try
			{
				parser.parse(HT.get(args[0]),args );
			}
			catch(ParseException e1){
				handleException(args[0]+" -options - M)andatory or O)ptional are:",(Options)HT.get(args[0]));
			}
		System.out.println("Parsing completed successfuly........");
	}
	//load DDF Create
	private static void loadDDFCreate()
	{
		Options optionsDDFCreate = new Options();
		Option e = OptionBuilder.withArgName( "Extension" ).hasArg().withDescription(  "O (CIMtype,  e.g. ZORDERS01)" ).create( "e" );
		Option i = OptionBuilder.withArgName( "IDOCType" ).hasArg().withDescription(  "M (Basictype e.g. ORDERS01)" ).create( "i" );
		i.setRequired(true);
		
		Option n = OptionBuilder.withArgName( "" ).hasArg().withDescription(  "O (no IDoc version in records will be generated)" ).create( "n" );
		Option o = OptionBuilder.withArgName( "outputfilename" ).hasArg().withDescription(  "O (name of (DDF)-file to be generated)" ).create( "o" );
		Option v = OptionBuilder.withArgName( "Record Type Vers" ).hasArg().withDescription(  "O (allowed values: 2 or 3 (default) )" ).create( "v" );
		
		optionsDDFCreate.addOption(e).addOption(i).addOption(n).addOption(o)
			.addOption(p).addOption(s).addOption(v);
		HT.put("DDFCreate", optionsDDFCreate);
	}
	//load DDF List
	private static void loadDDFList()
	{
		Options optionsDDFList = new Options();
		Option f = OptionBuilder.withArgName( "Filterargument" ).hasArg().withDescription(  "M (search for a list of IDCOS e.g. ORDERS*)" ).create( "f" );
		f.setRequired(true);
		optionsDDFList.addOption(f).addOption(oList).addOption(p).addOption(s);
		HT.put("DDFList", optionsDDFList);
	}
	//load XSD Create
	private static void loadXSDCreate()
	{
		Options optionsXSDCreate = new Options();
		Option oXSD = OptionBuilder.withArgName( "outputfilename" ).hasArg().withDescription(  "O (name of (XSD)-file to be generated)" ).create( "o" );
		Option r = OptionBuilder.withArgName( "RFCname" ).hasArg().withDescription(  "M (RFCname whose Schema will be extracted)" ).create( "r" );
		r.setRequired(true);
		optionsXSDCreate.addOption(oXSD).addOption(p).addOption(r);
		HT.put("XSDCreate", optionsXSDCreate);
	}
	//load XSD List
	private static void loadXSDList()
	{
		Options optionsXSDList = new Options();
		Option t = OptionBuilder.withArgName( "searchtype" ).hasArg().withDescription(  "M (allowed values: BO or BAPI or RFC)" ).create( "t" );
		Option a = OptionBuilder.withArgName( "additional arg" ).hasArg().withDescription(  "O (used in case of BAPI (M) or RFC (O) )" ).create( "a" );
		Option fXSD = OptionBuilder.withArgName( "Filterargument" ).hasArg().withDescription(  "M (search for a list of BO's, BAPI's or RFC's depending on searchtype)" ).create( "f" );
		fXSD.setRequired(true);
		t.setRequired(true);
		optionsXSDList.addOption(p).addOption(fXSD).addOption(oList).addOption(t).addOption(a);
		HT.put("XSDList", optionsXSDList);
	}
	
	public static void handleException(String Msg, Options a)
	{
		formatter.printHelp( Msg, a );
		System.exit(-1);
	}
}
