package co.com.smartfit.web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author alejandro.areiza
 * @since 19/05/2017
 * @version 1.0
 */

@Service("gestionadorService")
@Transactional
public interface GestionadorService {
	
	boolean esRolAdministrativo(String username);
	boolean esRolCorporativo(String username);
	boolean esRolCorporativo2(String username);
}
