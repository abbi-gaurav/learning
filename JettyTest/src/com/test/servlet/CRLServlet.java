package com.test.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.util.Helper;

public class CRLServlet extends HttpServlet{
	private static final String CRL_FILE = "/home/gaurav_abbi/official/meg/identity/certs/data/crl/identity.crl";
	private static final long serialVersionUID = 2974410259088873359L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		ServletOutputStream respStream = resp.getOutputStream();
		Helper.readFileToOS(new File(CRL_FILE), respStream);
		respStream.flush();
	}
}
