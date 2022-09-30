package com.corporativos_smartfit.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.corporativos_smartfit.dto.*;
import com.corporativos_smartfit.services.AffiliateQueryService;
import com.corporativos_smartfit.services.AffiliateService;
import org.apache.log4j.Logger;


/**
 * 
 * @author Oscar Idrobo
 * Clase para hacer el routing de los servicios
 */

@Path("/afiliados")
public class AffiliateController {
	private static final Logger LOG = Logger.getLogger(AffiliateController.class);

	@POST
	@Path("/asignar-codigo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAssigCode(AffiliateRequest affiliateRequest) {
		LOG.info("Start Call service POST: /afiliados/asignar-codigo ");
		Response response ;
		ResponseCode responseCode = new ResponseCode();
		try {
			AffiliateService affiliateService = new AffiliateService(affiliateRequest);
			responseCode = affiliateService.assignCode();
			response = Response.status(200).entity(responseCode).build();
		} catch (ErrorGeneral e) {
			e.printStackTrace();
			responseCode.setMessage(e.getMessage());
			response = Response.status(e.getStatusCode())
					.entity(responseCode).build();
		}
		LOG.info("End Call service POST: /afiliados/asignar-codigo");
		return response;
	}

	@POST
	@Path("/codigo-asignado")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response postQueryAsignedCode(AffiliateDocumentRequest affiliateDocumentRequest) {
		LOG.info("Start Call service GET: /afiliados/codigo-asignado ");
		Response response ;
		ResponseCodeQuery responseCode = new ResponseCodeQuery();
		try {
			AffiliateQueryService affiliateService = new AffiliateQueryService(affiliateDocumentRequest);
			responseCode = affiliateService.getInfomationCodeAsigned();
			response = Response.status(200).entity(responseCode).build();
		} catch (ErrorGeneral e) {
			e.printStackTrace();
			responseCode.setMessage(e.getMessage());
			response = Response.status(e.getStatusCode())
					.entity(responseCode).build();
		}
		LOG.info("End Call service GET: /afiliados/codigo-asignado");
		return response;
	}
}
