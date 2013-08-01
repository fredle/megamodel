package com.matterhorn.megamodel.webservice.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;


public final class WsContextLogTracer {
	
	private WsContextLogTracer(){}
	
	public static void traceWsContext(WebServiceContext wsContext, Logger logger)
	{
//		logFormattedKeyValue(logger, "UserPrincipal.name", 
//				(wsContext.getUserPrincipal() == null) ? null : wsContext.getUserPrincipal().getName());
//	    MessageContext context = wsContext.getMessageContext();
//	    logger.info("TRACING WS CONTEXT:");
//	    traceMessageContext(logger, context);
	}

	
	public static void traceMessageContext(Logger logger, MessageContext context)
	{
	    traceMessageRequest(logger, context);
	    traceMessageWsdl(context, logger);
	    traceMessageRequestHeaders(logger, context);
	}
	
	
	public static void traceMessageRequest(Logger logger, MessageContext context)
	{
		logFormattedKeyValue(logger, MessageContext.HTTP_REQUEST_METHOD, context.get(MessageContext.HTTP_REQUEST_METHOD));
		logFormattedKeyValue(logger, MessageContext.PATH_INFO, context.get(MessageContext.PATH_INFO));
		logFormattedKeyValue(logger, MessageContext.QUERY_STRING, context.get(MessageContext.QUERY_STRING));
		logFormattedKeyValue(logger, MessageContext.REFERENCE_PARAMETERS, 
				listToString((List<?>)context.get(MessageContext.REFERENCE_PARAMETERS)));
	}

	public static void traceMessageWsdl(MessageContext context, Logger logger)
	{
	    logFormattedKeyValue(logger, MessageContext.WSDL_INTERFACE, context.get(MessageContext.WSDL_INTERFACE));
	    logFormattedKeyValue(logger, MessageContext.WSDL_OPERATION, context.get(MessageContext.WSDL_OPERATION));
	    logFormattedKeyValue(logger, MessageContext.WSDL_PORT, context.get(MessageContext.WSDL_PORT));
	    logFormattedKeyValue(logger, MessageContext.WSDL_SERVICE, context.get(MessageContext.WSDL_SERVICE));
	}

	
	public static void traceMessageRequestHeaders(Logger logger, MessageContext context)
	{
		@SuppressWarnings("unchecked")
		Map<String, List<String>> requestHeaders = (Map<String, List<String>>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
		for(Map.Entry<String, List<String>> e : requestHeaders.entrySet()) {
			logFormattedKeyValue(logger, e.getKey(), listToString(e.getValue()));
		}
	}
	
	
	private static void logFormattedKeyValue(Logger logger, Object key, Object value)
	{
		logger.info(String.format("%35s  ->  %s", key.toString(), (value == null) ? "null" : value.toString()));
	}
	
	
	private static String listToString(List<?> list)
	{
		if(list == null) {
			return "null";
		}
		StringBuilder sb = new StringBuilder();
		
		for(Iterator<?> it = list.iterator(); it.hasNext();) {
			sb.append(it.next());
			if(it.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
}