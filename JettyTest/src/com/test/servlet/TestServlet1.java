package com.test.servlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;

public class TestServlet1 extends HttpServlet implements Servlet{
	private static final long serialVersionUID = -7266236634995825508L;
	private static int count;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Request base_request = request instanceof Request ? (Request)request:HttpConnection.getCurrentConnection().getRequest();
		System.out.println(request.getRequestURI());
		System.out.println(++count);
		/*PrintWriter writer = response.getWriter();
		writer.println("request handeled, no further handler");*/
		//response.sendError(102);
		try{
			Thread.sleep(10 * 1000);

		}catch (InterruptedException e) {
			// TODO: handle exception
		}
		PrintWriter writer = response.getWriter();
		writer.println("request handeled, no further handler");
		//response.setStatus(102);
		//response.sendError(500, "testing dummy error");
		/*response.flushBuffer();
		writer.flush();*/
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Request base_request = request instanceof Request ? (Request)request:HttpConnection.getCurrentConnection().getRequest();
		
		System.out.println(request.getRemoteAddr());
		System.out.println(++count);
		/*try{
			Thread.sleep(5 * 1000);
			
		}catch (InterruptedException e) {
			// TODO: handle exception
		}*/
		
		PrintWriter writer = response.getWriter();
		writer.println("request handeled, no further handler");
		
		//response.sendError(500, "testing dummy error");
		
		//tesing large file
		/*OutputStream os = response.getOutputStream();
		os.write(read2List());*/
	}
	

	/**
	 * @return byte array
	 */
	private static byte[] read2List(){
		byte[] buff = null;
		int bufLen = 25000*1024;
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream("E:\\gaurav\\fun\\songs\\videos\\movies\\test.mpg"));
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

		return buff;
	}
}
