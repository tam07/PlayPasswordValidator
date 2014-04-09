package controllers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.*;
import play.mvc.*;
import play.mvc.BodyParser.TolerantJson;
import views.html.*;
import play.libs.Json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import play.Logger;



public class Application extends Controller {
  
	public static final int MIN_LENGTH = 5;
	public static final int MAX_LENGTH = 12;
	public static final String VALID_CHAR = "[a-z0-9]+";
	public static final String AT_LEAST = "((?=.*[a-z])(?=.*\\d))";
	public static final String CONSEC_SEQ = "([a-z0-9]+?)\\1";
	
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    
    @BodyParser.Of(TolerantJson.class)
    public static Result passwordChecker() {
    	JsonNode root = request().body().asJson();
    	
    	if(root == null) {
    		return badRequest();
    	}
    	ObjectMapper mapper = new ObjectMapper();
    	String password = null;
    	Boolean result = false;
		try {
				password = mapper.readValue(root, String.class);
				result = isValidPwd(password);
				Logger.info(result.toString());
				
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ok(Json.toJson(result));

    }
    
    public static boolean isValidPwd(String pwd) {
    	
    	int pwdLen = pwd.length();
    	// must be between 5 and 12 chars in length
    	if(pwdLen < MIN_LENGTH || pwdLen > MAX_LENGTH) {
    		return false;
    	}
    	
    	// consist of only lowercase letters and numbers
    	Pattern pattern = Pattern.compile(VALID_CHAR);
    	Matcher matcher = pattern.matcher(pwd);
    	if(!matcher.matches())
    		return false;
    	
    	// at least 1 letter and 1 number
        pattern = Pattern.compile(AT_LEAST);
		matcher = pattern.matcher(pwd);
		if(!matcher.find()) 
		    return false;
		
		// NO consecutive sequences 
		pattern = Pattern.compile(CONSEC_SEQ);
		matcher = pattern.matcher(pwd);
		if(matcher.find()) 
			return false;
		
		return true;
    }
  
}
