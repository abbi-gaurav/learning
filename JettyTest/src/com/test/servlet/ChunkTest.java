package com.test.servlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.io.ChunkedOutputStream;

public class ChunkTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String NUM_MBYTES = "num_mbytes";
	private final static long NUM_MBYTES_DEFAULT = 1;
	private final static String CONTENT_TYPE = "text/ascii; charset=iso-8859-1";
	private final static String MODE = "mode";
    private final static String MODE_UP = "up";
    private final static int UPLOAD_BUFFER_SIZE = 65536;

    private final static byte[] TEST_BYTES = {'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 't', 'e', 's', 't', '.', ' ', 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 't', 'e', 's', 't', '.', ' ', 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 't', 'e', 's', 't', '.', ' ', 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 't', 'e', 's', 't', '.', '\n'};
    private final static byte[] TEST_BYTES_1KB = getTestBytes(1); // 16 TEST_BYTES
    private final static byte[] TEST_BYTES_1MB = getTestBytes(1024); // 1024 TEST_BYTES_1KB
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Date date = new Date();
		//not chunked
		System.out.println("inside post");
		/*PrintWriter out = response.getWriter();
		 * out.println("<html>"
				+"<body>" +
				"<h1 align=center> Gaurav\'s First chunked response, burrrrrrrrrrrah </h1>"
				+"<br>" +date+"</body></html>");*/
		/*chunked response starts here*/
		//response.setContentType("text/html");
		//response.setHeader("Transfer-Encoding", "chunked");
		
		String str = new String("sending a chunked response, burrrrrrrah");
		OutputStream outS = response.getOutputStream();
	//	SessionOutputBuffer sb = 
//		ChunkedOutputStream chunkOut = new ChunkedOutputStream(new PrintStream(outS));
//		chunkOut.write(read2List());
		//chunkOut.write(str.getBytes());
//		chunkOut.flush();
		//chunkOut.close();
	}
	//@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		PrintWriter out = response.getWriter();
		Date date = new Date();
		/*response.setContentType("text/html");*/
		/*response.setHeader("Transfer-Encoding", "chunked");
		out.println("<html>"
				+"<body>" +
				"<h1 align=center> Gaurav\'s First chunked response, burrrrrrrrrrrah </h1>"
				+"<br>"+"</body></html>");*/
		out.write("Sending sample response for Get");
		out.flush();
		/*String str = new String("sending a chunked response, burrrrrrrah");
		OutputStream outS = response.getOutputStream();
		//ChunkedOutputStream chunkOut = new ChunkedOutputStream(new PrintStream(outS));
		//TODO:fix this
		ChunkedOutputStream chunkOut = null;
		chunkOut.write(str.getBytes());
		//chunkOut.write(read2List());
		System.out.println("after write");
		//write2OPStream(chunkOut);
		chunkOut.flush();
		handleDownload(request, response);
		System.out.println("after flush");*/
		//out.println("hi lets see if this comes");
	}
	
	private void handleDownload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long numMBytes = 0;
        try {
            numMBytes = Integer.parseInt(request.getParameter(NUM_MBYTES));
        } catch (Exception e) {
            numMBytes = NUM_MBYTES_DEFAULT;
        }

        response.setContentType(CONTENT_TYPE);
        OutputStream out = response.getOutputStream();
        for (long i = 0; i < numMBytes; i++)
            out.write(TEST_BYTES_1MB);
        out.flush();
    }
	/**
	 * @return byte array
	 */
	private static byte[] read2List(){
		System.out.println("inside read2list");
		byte[] buff = null;
		int bufLen = 25000*1024;
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream("E:\\gaurav\\fun\\songs\\videos\\movies\\test.mpg"));
			//in = new BufferedInputStream(new FileInputStream("E:\\gaurav\\official\\issues\\above2GB\\commands\\file1.mpg"));
			buff = new byte[bufLen];
			byte[] tmp = null;
			int len = 0;
			List<byte[]> list = new ArrayList<byte[]>(24);
			while((len = in.read(buff,0,bufLen)) != -1){
				tmp	= new byte[len];
				System.arraycopy(buff, 0, tmp, 0, len);
				list.add(tmp);
			}
			len = 0;
			if(list.size() == 1) return list.get(0);

			for(int i=0;i<list.size();i++){
				len += list.get(i).length;
			}

			buff = new byte[len]; //final op buffer

			len =0;

			for(int i=0;i<list.size();i++){	//put data
				tmp = list.get(i);
				System.arraycopy(tmp, 0, buff, len, tmp.length);
				len += tmp.length;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in != null)
				try{
					in.close();
				}catch(Exception e){
					System.err.println("error while closing");
				}
		}
		System.out.println("retuning from read2list");
		return buff;
	}
	
	/**
	 * @return byte array
	 */
	private static void write2OPStream(OutputStream op){
		System.out.println("inside write2ops");
		byte[] buff = null;
		int bufLen = 5*1024*1024;
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream("E:\\gaurav\\official\\issues\\above2GB\\commands\\file150m.DAT"));
			buff = new byte[bufLen];
			byte[] tmp = null;
			int len = 0;
			/*List<byte[]> list = new ArrayList<byte[]>(24);*/
			while((len = in.read(buff,0,bufLen)) != -1){
				/*tmp	= new byte[len];
				System.arraycopy(buff, 0, tmp, 0, len);
				list.add(tmp);*/
				op.write(buff, 0, len);
			}
			/*len = 0;
			if(list.size() == 1) return;

			for(int i=0;i<list.size();i++){
				len += list.get(i).length;
			}

			buff = new byte[len]; //final op buffer

			len =0;

			for(int i=0;i<list.size();i++){	//put data
				tmp = list.get(i);
				System.arraycopy(tmp, 0, buff, len, tmp.length);
				len += tmp.length;
			}*/
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in != null)
				try{
					in.close();
				}catch(Exception e){
					System.err.println("error while closing");
				}
		}
		System.out.println("retuning from write2ops");
		/*return buff;*/
	}
	
	private static byte[] getTestBytes(int numKBytes) {
        byte[] b = new byte[numKBytes*1024];
        for (int i=0; i<numKBytes*16; i++) // 16 TEST_BYTES per KB
            System.arraycopy(TEST_BYTES, 0, b, i*TEST_BYTES.length, TEST_BYTES.length);
        return b;
    }
}
