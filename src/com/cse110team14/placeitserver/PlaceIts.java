package com.cse110team14.placeitserver;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import java.util.List;

/**
 * This class handles all the CRUD operations related to
 * Product entity.
 *
 */
public class PlaceIts {

  /**
   * Update the product
   * @param name: name of the product
   * @param description : description
   * @return  updated product
   */
  public static void createRegularPlaceIts(String title, String user, String description, String postDate, 
		  String dateToBeReminded, String color, String type, String location, String placeitType, String sneezeType,
		  String listType) {
    Entity placeit = getPlaceIts(title);
  	if (placeit == null) {
  	  //The entity type is User
  	  placeit = new Entity("PlaceIts", title);
  	  placeit.setProperty("title", title);
  	  placeit.setProperty("user", user);
  	  placeit.setProperty("description", description);
  	  placeit.setProperty("postDate", postDate);
  	  placeit.setProperty("dateToBeReminded", dateToBeReminded);
  	  placeit.setProperty("color", color);
  	  //type is for regular place or categorical placeit
  	  placeit.setProperty("type", type);
  	  placeit.setProperty("location", location);
  	  placeit.setProperty("placeitType", placeitType);
  	  placeit.setProperty("sneezeType", sneezeType);
  	  placeit.setProperty("listType", listType);
  	} else {
  	  if(placeit.getProperty("user").equals(user)){
  		 placeit.setProperty("listType", listType);
  		 placeit.setProperty("postDate", postDate);
  		 placeit.setProperty("dateToBeReminded", dateToBeReminded);
  	  }
  	}
    Key key = placeit.getKey();
  	Util.persistEntity(placeit);
  }
  
  public static void createCategoryPlaceIts(String title, String user, String description, String postDate, 
		  String dateToBeReminded, String type, String categories, String listType) {
    Entity placeit = getPlaceIts(title);
  	if (placeit == null) {
  	  //The entity type is User
  	  placeit = new Entity("PlaceIts", title);
  	  placeit.setProperty("title", title);
  	  placeit.setProperty("user", user);
  	  placeit.setProperty("description", description);
  	  placeit.setProperty("postDate", postDate);
  	  placeit.setProperty("dateToBeReminded", dateToBeReminded);
  	  //type is for regular place or categorical placeit
  	  placeit.setProperty("type", type);
  	  placeit.setProperty("listType", listType);
  	  placeit.setProperty("categories", categories);
  	} else {
  	  if(placeit.getProperty("user").equals(user)){
  		 placeit.setProperty("listType", listType);
  	  }
  	}
    Key key = placeit.getKey();
  	Util.persistEntity(placeit);
  }

  /**
   * Retrun all the products
   * @param kind : of kind product
   * @return  products
   */
  public static Iterable<Entity> getAllUsers(String kind) {
    return Util.listEntities(kind, null, null);
  }

  /**
   * Get product entity
   * @param name : name of the product
   * @return: product entity
   */
  public static Entity getPlaceIts(String name) {
  	Key key = KeyFactory.createKey("PlaceIts",name);
  	return Util.findEntity(key);
  }

  /**
   * Get all items for a product
   * @param name: name of the product
   * @return list of items
   */
  
  @SuppressWarnings("deprecation")
  public static List<Entity> getItems(String name) {
  	Query query = new Query();
  	Key parentKey = KeyFactory.createKey("PlaceIts", name);
  	query.setAncestor(parentKey);
  	query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN, parentKey);
  		List<Entity> results = Util.getDatastoreServiceInstance()
  				.prepare(query).asList(FetchOptions.Builder.withDefaults());
  	return results;
  }
  
  /**
   * Delete product entity
   * @param productKey: product to be deleted
   * @return status string
   */
  public static String deletePlaceIts(String id)
  {
	  Key key = KeyFactory.createKey("PlaceIts",id);
	  Util.deleteEntity(key);
	  List<Entity> items = getItems(id);	  
	  if (!items.isEmpty()){
	      return "Cannot delete, as there are items associated with this product.";	      
	    }	    
	 
	  return "PlaceIts deleted successfully";
	  
  }
}