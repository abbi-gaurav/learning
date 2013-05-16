package com.test.servlet;

/*
 * (C) Copyright 2001 - 2008 Sterling Commerce, Inc. ALL RIGHTS RESERVED
 *
 * ** Trade Secret Notice **
 *
 * This software, and the information and know-how it contains, is
 * proprietary and confidential and constitutes valuable trade secrets
 * of Sterling Commerce, Inc., its affiliated companies or its or
 * their licensors, and may not be used for any unauthorized purpose
 * or disclosed to others without the prior written permission of the
 * applicable Sterling Commerce entity. This software and the
 * information and know-how it contains have been provided
 * pursuant to a license agreement which contains prohibitions
 * against and/or restrictions on its copying, modification and use.
 * Duplication, in whole or in part, if and when permitted, shall
 * bear this notice and the Sterling Commerce, Inc. copyright
 * legend. As and when provided to any governmental entity,
 * government contractor or subcontractor subject to the FARs,
 * this software is provided with RESTRICTED RIGHTS under
 * Title 48 CFR 52.227-19.
 * Further, as and when provided to any governmental entity,
 * government contractor or subcontractor subject to DFARs,
 * this software is provided pursuant to the customary
 * Sterling Commerce license, as described in Title 48
 * CFR 227-7202 with respect to commercial software and commercial
 * software documentation.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author    kwilliams
 * @created   October 21, 2004
 * @since     Woodstock 3.2
 */
public class SpitServlet extends HttpServlet {

    /** Initialize global variables */
    private final static String CONTENT_TYPE = "text/ascii; charset=iso-8859-1";
    private final static String NUM_MBYTES = "num_mbytes";
    private final static long NUM_MBYTES_DEFAULT = 1;
    private final static String MODE = "mode";
    private final static String MODE_UP = "up";
    private final static int UPLOAD_BUFFER_SIZE = 65536;

    private final static byte[] TEST_BYTES = {'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 't', 'e', 's', 't', '.', ' ', 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 't', 'e', 's', 't', '.', ' ', 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 't', 'e', 's', 't', '.', ' ', 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 't', 'e', 's', 't', '.', '\n'};
    private final static byte[] TEST_BYTES_1KB = getTestBytes(1); // 16 TEST_BYTES
    private final static byte[] TEST_BYTES_1MB = getTestBytes(1024); // 1024 TEST_BYTES_1KB
    private final static byte[] TEST_BYTES_70KB = getTestBytes(70);
    /**
     * Initialize the servlet instance
     *
     * @param config                servlet instance configuration
     * @exception ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * Process the HTTP Get request
     *
     * @param request               originating request
     * @param response              response container
     * @exception ServletException
     * @exception IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("inside do get from : "+request.getRemoteHost());
        String mode = request.getParameter(MODE);
        if (MODE_UP.equals(mode))
            handleUpload(request, response);
        else
            handleDownload(request, response);

    }
    
     /**
     * Process the HTTP Post request
     *
     * @param request               originating request
     * @param response              response container
     * @exception ServletException
     * @exception IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void handleUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader in = request.getReader();
        char[] buffer = new char[UPLOAD_BUFFER_SIZE];
        int i = in.read(buffer, 0, UPLOAD_BUFFER_SIZE);
        int total = 0;
        while (i != -1) {
            total += i;
            i = in.read(buffer, 0, UPLOAD_BUFFER_SIZE);
        }

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.print("total = " + total);
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
            //out.write(TEST_BYTES_1MB);
        	out.write(TEST_BYTES_70KB);
    }

    private static byte[] getTestBytes(int numKBytes) {
        byte[] b = new byte[numKBytes * 1024];
        for (int i=0; i<numKBytes*16; i++) // 16 TEST_BYTES per KB
            System.arraycopy(TEST_BYTES, 0, b, i*TEST_BYTES.length, TEST_BYTES.length);
        return b;
    }

    /** Clean up resources */
    public void destroy() {
    }
}


