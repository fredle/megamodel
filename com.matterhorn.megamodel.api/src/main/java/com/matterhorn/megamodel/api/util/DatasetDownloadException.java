package com.matterhorn.megamodel.api.util;
public class DatasetDownloadException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1154930218170312470L;
	public DatasetDownloadException(String s) {
		super(s);
	}
	public DatasetDownloadException(String s, Throwable t) {
		super(s, t);
	}

}
