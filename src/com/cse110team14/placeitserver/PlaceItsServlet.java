package com.cse110team14.placeitserver;
/**
 * Copyright 2011 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

/**
 * This servlet responds to the request corresponding to product entities. The servlet
 * manages the Product Entity
 * 
 * 
 */
@SuppressWarnings("serial")
public class PlaceItsServlet extends PlaceItsServerServlet {

  private static final Logger logger = Logger.getLogger(UserServlet.class.getCanonicalName());
  /**
   * Get the entities in JSON format.
   */

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	super.doGet(req, resp);
    logger.log(Level.INFO, "Obtaining placeits listing");
    String searchFor = req.getParameter("q");
    PrintWriter out = resp.getWriter();
    Iterable<Entity> entities = null;
    if (searchFor == null || searchFor.equals("") || searchFor == "*") {
      entities = PlaceIts.getAllUsers("PlaceIts");
      out.println(Util.writeJSON(entities));
    } else {
      Entity product = PlaceIts.getPlaceIts(searchFor);
      Set<Entity> result = new HashSet<Entity>();
      result.add(product);
      out.println(Util.writeJSON(result));
    }
  }

  /**
   * Create the entity and persist it.
   */
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    logger.log(Level.INFO, "Creating PlaceIts");
    PrintWriter out = resp.getWriter();
    //String id = req.getParameter("name");
    String title = req.getParameter("title");
    String user = req.getParameter("user");
    String description = req.getParameter("description");
    String postDate = req.getParameter("postDate");
	String dateToBeReminded = req.getParameter("dateToBeReminded"); 
	
	String type = req.getParameter("type"); 
	
	String listType = req.getParameter("listType");
    try {
      //If the type is 1, it's a regular placeit
      if(type.equals("1"))
      {
    	  String location = req.getParameter("location"); 
    	  String color = req.getParameter("color"); 
    	  String placeitType = req.getParameter("placeitType");
    	  String sneezeType = req.getParameter("sneezeType");
    	  PlaceIts.createRegularPlaceIts(title,user, description,postDate,dateToBeReminded, color,
    		  type, location, placeitType, sneezeType, listType);
      }
    } catch (Exception e) {
      String msg = Util.getErrorMessage(e);
      out.print(msg);
    }
  }

  /**
   * Delete the product entity
   */
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String id = req.getParameter("name");
    PrintWriter out = resp.getWriter();
    try{    	
    	out.println(PlaceIts.deletePlaceIts(id));
    } catch(Exception e) {
    	out.println(Util.getErrorMessage(e));
    } 
  }

  /**
   * Redirect the call to doDelete or doPut method
   */
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String action = req.getParameter("action");
    if (action.equalsIgnoreCase("delete")) {
      doDelete(req, resp);
      return;
    } else if (action.equalsIgnoreCase("put")) {
      doPut(req, resp);
      return;
    }
  }
}


