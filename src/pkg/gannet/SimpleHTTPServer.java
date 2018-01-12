//package pkg.gannet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/*
 * a simple static http server
*/
public class SimpleHTTPServer {

  private static String finalString="";	

  public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(8004), 0);
    server.createContext("/", new MyHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
  }

  static class MyHandler implements HttpHandler {
	  public void handle(HttpExchange exchange) throws IOException {
		  finalString="";
		  String stringFromURL = exchange.getRequestURI().getPath();
		  String inputString = stringFromURL.substring(1,stringFromURL.length());
		  processParameters(parseQueryString(exchange.getRequestURI().getQuery()),inputString);
		  String response = finalString;
	      exchange.sendResponseHeaders(200, response.length());
	      OutputStream os = exchange.getResponseBody();
	      os.write(response.getBytes());
	      os.close();
    }
  }
  
  static void processPath(String inputString){
	  if(inputString==""){
		 finalString+="Hello World" ;
	  }
	  else if(inputString.equals("hello")){
		  finalString+="Hello\n";
	  }
	  else if(inputString.equals("world")){
		  finalString+="World\n";
	  }
	  else{
		  finalString+="Invalid Request\n";
	  }
  }
  
  static String reverseString(String input){
	  return new StringBuilder(input).reverse().toString();
  }
  
  private static String uppercaseString(String input){
	  return input.toUpperCase();
  }
  
  static void processParameters(Map<String,String> paramsMap, String inputString){
	  processPath(inputString);
	  if(paramsMap.containsKey("uppercase")){
		  if(Boolean.valueOf(paramsMap.get("uppercase").toLowerCase())){
			  finalString+= "Converting "+inputString+" to Uppercase: "+uppercaseString(inputString)+"\n";
		  }
	  }
	  if(paramsMap.containsKey("reverse")){
		  if(Boolean.valueOf(paramsMap.get("reverse").toLowerCase())){
			  finalString+= "Reversing "+inputString+" :"+reverseString(inputString)+"\n";
		  }
	  }
	  if(paramsMap.isEmpty()){
		  finalString+="No Query Parameters Provided";
	  }
  }
  
  String getFinalString(){
	  return finalString;
  }
  
  public static Map<String, String> parseQueryString(String qs) {
	    Map<String, String> result = new HashMap<>();
	    if (qs == null)
	        return result;

	    int last = 0, next, l = qs.length();
	    while (last < l) {
	        next = qs.indexOf('&', last);
	        if (next == -1)
	            next = l;

	        if (next > last) {
	            int eqPos = qs.indexOf('=', last);
	            try {
	                if (eqPos < 0 || eqPos > next)
	                    result.put(URLDecoder.decode(qs.substring(last, next), "utf-8"), "");
	                else
	                    result.put(URLDecoder.decode(qs.substring(last, eqPos), "utf-8"), URLDecoder.decode(qs.substring(eqPos + 1, next), "utf-8"));
	            } catch (UnsupportedEncodingException e) {
	                throw new RuntimeException(e); // will never happen, utf-8 support is mandatory for java
	            }
	        }
	        last = next + 1;
	    }
	    return result;
	}

public static void setFinalString(String string) {
		finalString=string;
}
}
