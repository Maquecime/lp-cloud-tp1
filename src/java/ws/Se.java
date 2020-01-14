/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author madatin
 */
@Path("ws")
public class Se {

    @Context
    private UriInfo context;
    
    private ArrayList<String> books;


    /**
     * Creates a new instance of Se
     */
    public Se() {
        this.books = new ArrayList<>();
        books.add("Livre1");
        books.add("Livre2");
        books.add("Livre3");
        books.add("Livre4");
        books.add("Livre5");
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getAll() {
        return books;
    }
   
    
    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_HTML)
    public String getOneBook(@PathParam("name") String name) {
        
        try{
        String book = "";
        if(books.contains(name)){
            book = name;
        }
        
        return "<html><body>"+book+"</body></html>";
        }
        catch (Exception e) {throw new WebApplicationException(500);}
    }   
    
   
    
    @PUT
    @Path("/update/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooks(@PathParam("name") String name, String nameUpdated){
        if(nameUpdated == null){
            return Response.status(400).entity("Veuillez renseigner le nouveau nom du livre").build();
        }
        int index = books.indexOf(name);
        books.set(index, nameUpdated);
        return Response.ok().entity(books).build();
        
    }
    
    @DELETE
    @Path("/delete/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("name") String name) {
        if(books.contains(name)) {
           int index = books.indexOf(name);
           books.remove(index);
           return Response.ok().entity(books).build();
        }
        return Response.status(400).entity("Le livre demandé n'existe pas").build();
    }
    
    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(String name){
        if(name == null) {
            return Response.status(400).entity("veuillez renseigner un nom de livre").build();
        }
        books.add(name);
        return Response.ok().entity(books).build();       
    }
    
    
}
