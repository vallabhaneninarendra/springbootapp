package parkinglotsystem.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
private String errorMessage;
private int errorCode;
private String documentation;

public ErrorMessage() {
	
}

public ErrorMessage(String string,int errorCode,String documentation) {
	super();
	this.errorMessage = string;
	this.errorCode = errorCode;
	this.documentation = documentation;
	
}


public String getErrorMessage() {
	return errorMessage;
}


public int getErrorCode() {
	return errorCode;
}


public void setErrorCode(int errorCode) {
	this.errorCode = errorCode;
}


public String getDocumentation() {
	return documentation;
}


public void setDocumentation(String documentation) {
	this.documentation = documentation;
}


public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
}

}
