package com.cse110team14.placeitserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class PlaceItsServerServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json; charset=utf-8");
		resp.setHeader("Cache-Control", "no-cache");
	}
}
