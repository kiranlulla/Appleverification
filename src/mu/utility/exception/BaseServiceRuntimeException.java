////-----------------------------------------------------------------------
//
///*
//* Copyright (c) 2015, 2022, HCL Technologies Ltd. All rights reserved.
//* Material published by HCL Technologies on these web
//* pages/mobile app may not be reproduced without permission.
//*/
//
////-----------------------------------------------------------------------
//
//package mu.utility.exception;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.util.concurrent.TimeoutException;
//
//import javax.naming.AuthenticationException;
//
//import org.bson.BSONException;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.ParseException;
//import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
//
//import mu.utility.token.Constants;
//
///**
// * Base RuntimeException for all MU AWS Lambda exception.
// * 
// * @author krishna.ga
// */
//public class BaseServiceRuntimeException extends RuntimeException {
//
//	/**
//	 * serialVersionUID serialVersionUID.
//	 */
//	private static final long serialVersionUID = 6328788869820683197L;
//
//	/**
//	 * status code for the Exception as Integer type.
//	 */
//	private Integer statusCode;
//	
//	/**
//	 * ErrorCode for the Exception.
//	 */
//	private String errorCode = "GENERIC";
//
//	/**
//	 * ErrorDescription for the Exception.
//	 */
//	private String errorDescription = "GENERIC";
//
//	/**
//	 * errorMessage for the Exception.
//	 */
//	private String errorMessage = "GENERIC";
//	
//	/**
//	 * key name of the exception for the client.
//	*/
//	private String keyName;
//	
//	/**
//	 * fix error messages (default message) for particular exception.
//	*/
//	private String errorType;
//	
//	/**
//	 * custom description/message for the exception, may vary for every exception.
//	*/
//	private String customMessage;
//	
//	/**
//	 * error information in Json format.
//	 */
//	private JSONObject errorJson;
//
//	/**
//	 * Empty Constructor creates instance without any parameter.
//	 */
//	public BaseServiceRuntimeException() {
//		super();
//	}
//
//	/**
//	 * Constructor with message as parameter.
//	 * 
//	 * @param message
//	 *            text value
//	 */
//	public BaseServiceRuntimeException(final String message) {
//		super(message);
//		this.errorMessage = message;
//	}
//	
//	/**
//	 * Constructor with JSON object as parameter.
//	 * 
//	 * @param errorJson object contains details of exception in json format. This is passed in the message
//	 */
//	public BaseServiceRuntimeException(final JSONObject errorJson) {
//		super();
//		this.errorJson = errorJson;
//	}
//
//	/**
//	 * Constructor with message and errorCode as parameter.
//	 * 
//	 * @param message
//	 *            text value
//	 * @param errorCode
//	 *            unique error code
//	 */
//	public BaseServiceRuntimeException(final String message,
//			final String errorCode) {
//		super(message);
//		this.errorMessage = message;
//		this.errorCode = errorCode;
//	}
//
//	/**
//	 * Constructor with message and errorCode as parameter.
//	 * 
//	 * @param message
//	 *            text value
//	 * @param errorCode
//	 *            unique error code
//	 * @param errorDescription
//	 *            unique error code
//	 */
//	public BaseServiceRuntimeException(final String message,
//			final String errorCode, final String errorDescription) {
//		super(message);
//		this.errorMessage = message;
//		this.errorCode = errorCode;
//		this.errorDescription = errorDescription;
//	}
//
//	/**
//	 * Constructor with Throwable cause as parameter.
//	 * 
//	 * @param cause
//	 *            Throwable cause
//	 */
//	public BaseServiceRuntimeException(final Throwable cause) {
//		super(cause);
//		validateException(cause);
//	}
//
//	/**
//	 * Constructor with Exception cause as parameter.
//	 * 
//	 * @param cause
//	 *            Exception cause
//	 */
//	public BaseServiceRuntimeException(final Exception cause) {
//		super(cause);
//		validateException(cause);
//	}
//
//	/**
//	 * Constructor with RuntimeException cause as parameter.
//	 * 
//	 * @param cause
//	 *            RuntimeException cause
//	 */
//	public BaseServiceRuntimeException(final RuntimeException cause) {
//		super(cause);
//		validateException(cause);
//	}
//
//	/**
//	 * Constructor with Throwable cause and errorCode as parameter.
//	 * 
//	 * @param cause
//	 *            Throwable cause
//	 * @param errorCode
//	 *            unique error code
//	 */
//	public BaseServiceRuntimeException(final Throwable cause,
//			final String errorCode) {
//		super(cause);
//		this.errorCode = errorCode;
//	}
//
//	/**
//	 * Constructor with message and Throwable cause as parameter.
//	 * 
//	 * @param message
//	 *            text value
//	 * @param cause
//	 *            Throwable cause
//	 */
//	public BaseServiceRuntimeException(final String message,
//			final Throwable cause) {
//		super(message, cause);
//		this.errorMessage = message;
//	}
//
//	/**
//	 * Constructor with message, Throwable cause and errorCode as parameter.
//	 * 
//	 * @param message
//	 *            text value
//	 * @param cause
//	 *            Throwable cause
//	 * @param errorCode
//	 *            unique error code
//	 */
//	public BaseServiceRuntimeException(final String message,
//			final Throwable cause, final String errorCode) {
//		super(message, cause);
//		this.errorMessage = message;
//		this.errorCode = errorCode;
//	}
//
//	/**
//	 * @param message
//	 *            instance of message
//	 * @param cause
//	 *            instance of Throwable
//	 * @param enableSuppression
//	 *            instance of boolean
//	 * @param writableStackTrace
//	 *            instance of boolean
//	 */
//	public BaseServiceRuntimeException(final String message,
//			final Throwable cause, final boolean enableSuppression,
//			final boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//		this.errorMessage = message;
//	}
//
//	/**
//	 * @param message
//	 *            instance of message
//	 * @param cause
//	 *            instance of Throwable
//	 * @param enableSuppression
//	 *            instance of boolean
//	 * @param writableStackTrace
//	 *            instance of boolean
//	 * @param errorCode
//	 *            errorCode of Exception
//	 */
//	public BaseServiceRuntimeException(final String message,
//			final Throwable cause, final boolean enableSuppression,
//			final boolean writableStackTrace, final String errorCode) {
//		super(message, cause, enableSuppression, writableStackTrace);
//		this.errorMessage = message;
//		this.errorCode = errorCode;
//	}
//
//	/**
//	 * @param statusCode
//	 *            instance of int
//	 * @param keyName
//	 *            instance of String
//	 * @param errorType
//	 *            instance of String
//	 * @param customMessage
//	 *            instance of String
//	 */
//	@SuppressWarnings("unchecked")
//	public BaseServiceRuntimeException(final int statusCode, final String keyName, final String errorType,
//			final String customMessage) {
//		
//		this.statusCode = statusCode;
//		this.keyName = keyName;
//		this.errorType = errorType;
//		this.customMessage = customMessage;
//		//initialize errorJson field of BaseServiceRuntimeException
//		//initializeExceptionJSON();
//		JSONObject exceptionInfo = new JSONObject();
//		
//		this.errorJson = exceptionInfo;
//	}
//
//	/**
//	 * route related exception.
//	 * @param exception instance of Throwable.
//	 */
//	public static void validateException(final Throwable exception) {
//		if (exception instanceof BSONException) {
//			throw new BaseServiceRuntimeException("BadRequest", "400",
//					exception.getMessage());
//		} else if (exception instanceof NotFoundException) {
//			throw new BaseServiceRuntimeException("Unauthorized", "401",
//					exception.getMessage());
//		} else if (exception instanceof AuthenticationException) {
//			throw new BaseServiceRuntimeException("Unauthenticated", "403",
//					exception.getMessage());
//		} else if (exception instanceof MalformedURLException) {
//			throw new BaseServiceRuntimeException("InvalidResource", "404",
//					exception.getMessage());
//		} else if (exception instanceof IOException) {
//			throw new BaseServiceRuntimeException("TooManyRequests", "429",
//					exception.getMessage());
//		} else if (exception instanceof  ParseException) {
//			throw new BaseServiceRuntimeException("DependentServiceError", "502",
//					exception.getMessage());
//		} else if (exception instanceof TimeoutException) {
//			throw new BaseServiceRuntimeException("DependentService timed out",
//					"504", exception.getMessage());
//		} else {
//			throw new BaseServiceRuntimeException("UnexpectedError", "500",
//					exception.getMessage());
//		}
//	}
//
//
//	/**
//	 * Error code for this Exception.
//	 * 
//	 * @return the errorCode
//	 */
//	public Integer getStatusCode() {
//		return statusCode;
//	}
//
//
//	/**
//	 * Error code for this Exception.
//	 * 
//	 * @param statusCode status code of the exception
//	 */
//	public void setStatusCode(final Integer statusCode) {
//		this.statusCode = statusCode;
//	}
//
//	/**
//	 * Error code for this Exception.
//	 * 
//	 * @return the errorCode
//	 */
//	public String getErrorCode() {
//		return errorCode;
//	}
//
//	/**
//	 * Error code for this Exception.
//	 * 
//	 * @param errorCode
//	 *            the errorCode to set
//	 */
//	public void setErrorCode(final String errorCode) {
//		this.errorCode = errorCode;
//	}
//
//	/**
//	 * @return the errorDescription
//	 */
//	public String getErrorDescription() {
//		return errorDescription;
//	}
//
//	/**
//	 * @param errorDescription
//	 *            the errorDescription to set
//	 */
//	public void setErrorDescription(final String errorDescription) {
//		this.errorDescription = errorDescription;
//	}
//
//	/**
//	 * @return the errorMessage
//	 */
//
//	public String getErrorMessage() {
//		return errorMessage;
//	}
//
//	/**
//	 * @param errorMessage
//	 *            the errorDescription to set
//	 */
//	public void setErrorMessage(final String errorMessage) {
//		this.errorMessage = errorMessage;
//	}
//	
//	public String getKeyName() {
//		return keyName;
//	}
//
//	public void setKeyName(final String keyName) {
//		this.keyName = keyName;
//	}
//	
//	public String getCustomMessage() {
//		return customMessage;
//	}
//
//	public void setCustomMessage(final String customMessage) {
//		this.customMessage = customMessage;
//	}
//
//	public String getErrorType() {
//		return errorType;
//	}
//
//	public void setErrorType(final String errorType) {
//		this.errorType = errorType;
//	}
//
//	/**
//	 * error message in json format.
//	 * @return jsonobject
//	*/
//	public JSONObject getErrorJson() {
//		return errorJson;
//	}
//
//	public void setErrorJson(final JSONObject errorJson) {
//		this.errorJson = errorJson;
//	}
//	
//	/*
//	 * Initialize errorJson field from existing fields 
//	*/
////	public void initializeExceptionJSON(){
////		JSONObject exceptionInfo = new JSONObject();
////		exceptionInfo.put(MUConstants.EXCEPTION_KEYNAME,keyName);
////		exceptionInfo.put(MUConstants.ERROR_TYPE, errorType);
////		exceptionInfo.put(MUConstants.HTTP_STATUS, statusCode);
////		exceptionInfo.put(MUConstants.MESSAGE,(customMessage!=null)?customMessage:"");
////		this.errorJson = exceptionInfo;
////	}
//
//	@Override
//	public String getMessage() {
//		if (errorJson != null) {
//			return JSONObject.toJSONString(errorJson);
//		}
//		return super.getMessage();
//	}
//
//	@Override
//	public String toString() {
//		return errorDescription + ":" + errorCode + ":" + errorMessage + ":" + getMessage();
//	}
//
//}
