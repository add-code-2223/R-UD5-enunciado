package modelo.exceptions;



@SuppressWarnings("serial")
public class DuplicateInstanceException extends Exception {
	private Object key;
    private String className;
    
    public DuplicateInstanceException(String specificMessage, Object key, 
        String className) {
        
        super(specificMessage + " (key = '" + key + "' - className = '" + 
            className + "')");
        this.key = key;
        this.className = className;
        
    }
    
    public Object getKey() {
        return key;
    }
    
    public String getClassName() {
        return className;
    }
  
    
}