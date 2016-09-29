package com.crivano.bluc.rest.server;

import com.crivano.bluc.rest.server.IBlueCrystal.CertDetails;
import com.crivano.bluc.rest.server.IBlueCrystal.IValidatePost;
import com.crivano.bluc.rest.server.IBlueCrystal.ValidatePostRequest;
import com.crivano.bluc.rest.server.IBlueCrystal.ValidatePostResponse;
import com.crivano.swaggerservlet.ISwaggerCacheableMethod;

public class ValidatePost implements IValidatePost, ISwaggerCacheableMethod {

	@Override
	public String getContext() {
		return "bluc-validate";
	}

	@Override
	public void run(ValidatePostRequest req, ValidatePostResponse resp)
			throws Exception {
		// Date dtSign = javax.xml.bind.DatatypeConverter.parseDateTime(time)
		// .getTime();

		// Produce response
		ValidateResponse validateresp = new ValidateResponse();
		Utils.getBlucutil().validateSign(req.envelope, req.sha1, req.sha256,
				req.time, req.crl, validateresp);

		resp.cn = validateresp.getCn();
		resp.policy = validateresp.getPolicy();
		resp.policyversion = validateresp.getPolicyversion();
		resp.policyoid = validateresp.getPolicyoid();
		resp.error = validateresp.getError();
		resp.status = validateresp.getStatus();
		resp.certdetails = new CertDetails();
		CertificatePost.fillCertificateDetails(resp.certdetails,
				validateresp.getCertdetails());
	}
}
