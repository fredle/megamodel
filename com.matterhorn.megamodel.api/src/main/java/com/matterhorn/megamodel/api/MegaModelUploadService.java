package com.matterhorn.megamodel.api;

import com.matterhorn.megamodel.api.transport.UploadCargo;
import com.matterhorn.megamodel.api.transport.UploadResp;


public interface MegaModelUploadService {

	UploadResp upload(UploadCargo cargo);

}