package co.com.smartfit.web.service;



import co.com.smartfit.web.business.rs.CrearEmpresaRs;
import co.com.smartfit.web.business.rs.ObtenerConvenioAfiliadosRs;

import javax.servlet.http.HttpServletRequest;

public interface EmpresaService {
    CrearEmpresaRs crearEmpresa(HttpServletRequest rq);
    ObtenerConvenioAfiliadosRs vistaEmpresa(String username);

}
