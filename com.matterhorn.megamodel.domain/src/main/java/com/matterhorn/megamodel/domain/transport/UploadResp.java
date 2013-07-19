package com.matterhorn.megamodel.domain.transport;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "UploadResp")
public class UploadResp {

	public boolean uploadSucceeded;
	
	public boolean downloadRefreshRequired;
	
	
	public UploadResp()
	{
		this(false);
	}
	
	public UploadResp(boolean downloadRefreshRequired)
	{
		uploadSucceeded = true;
		this.downloadRefreshRequired = downloadRefreshRequired;
	}
}