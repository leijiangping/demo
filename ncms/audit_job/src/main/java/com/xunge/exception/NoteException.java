package com.xunge.exception;

import org.apache.log4j.Logger;


/**  
 * @author SongJL
 * @Description 系统业务异常  
 */    
public class NoteException extends RuntimeException {  

	/** serialVersionUID */   
	private static final long serialVersionUID = -1604063715884516113L;

	private static final Logger LOGGER = Logger.getLogger(NoteException.class);  
    
    private String code;    
    
    public NoteException() {    
        super();    
    }    
    
    public NoteException(String message) { 
    	super(message);
    	LOGGER.info(message);
    }    
    
    public NoteException(String code, String message) {    
        super(message);    
        this.code = code;    
    }    
    
    public NoteException(Throwable cause) {    
        super(cause);    
    }    
    
    public NoteException(String message, Throwable cause) {    
        super(message, cause);    
    }    
    
    public NoteException(String code, String message, Throwable cause) {    
        super(message, cause);    
        this.code = code;    
    }    
    
    public String getCode() {    
        return code;    
    }    
    
    public void setCode(String code) {    
        this.code = code;    
    }    
    
}