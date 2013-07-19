package com.matterhorn.megamodel.service.api;

import com.matterhorn.megamodel.domain.transport.UploadCargo;
import com.matterhorn.megamodel.domain.transport.UploadResp;


public interface MegaModelUploadService {

	UploadResp upload(UploadCargo cargo);

}