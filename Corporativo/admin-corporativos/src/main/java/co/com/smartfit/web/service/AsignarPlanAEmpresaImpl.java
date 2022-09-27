package co.com.smartfit.web.service;

import co.com.smartfit.web.dao.EmpresaEmpleadorXPlanDao;
import co.com.smartfit.web.dao.PlanesDao;
import co.com.smartfit.web.dao.PlanesEmpresaDao;
import co.com.smartfit.web.entities.EmpresaEmpleadorXPlan;
import co.com.smartfit.web.entities.ErrorGeneral;
import co.com.smartfit.web.entities.Planes;
import co.com.smartfit.web.entities.PlanesEmpresa;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service("asignarPlanAEmpresaService")
@Transactional
public class AsignarPlanAEmpresaImpl implements AsignarPlanAEmpresaService {

	private static final Logger LOG = Logger.getLogger(AsignarPlanAEmpresaImpl.class);
	private static final EmpresaEmpleadorXPlanDao empresaEmpleadorXPlanDao = new EmpresaEmpleadorXPlanDao();
	private static final PlanesEmpresaDao planesEmpresaDao = new PlanesEmpresaDao();

	public AsignarPlanAEmpresaImpl() {
	}

	@Override
	public List<PlanesEmpresa>  asignarPlanAEmpresa(Integer idPlan, Integer IdEmpresa) throws ErrorGeneral {
		LOG.info("INIT asignarPlanAEmpresa" );
		List<EmpresaEmpleadorXPlan> listRelacionPlanEmpresa = empresaEmpleadorXPlanDao.obtenerTodosEntidades();
		EmpresaEmpleadorXPlan nuevaRelacionPlanEmpresa = new EmpresaEmpleadorXPlan();
		nuevaRelacionPlanEmpresa.setIdPlan(idPlan);
		nuevaRelacionPlanEmpresa.setIdEmpresaEmpleador(IdEmpresa);
		this.isvalidInsert(listRelacionPlanEmpresa,nuevaRelacionPlanEmpresa);
		this.trySave(nuevaRelacionPlanEmpresa);
		LOG.info("ENDING asignarPlanAEmpresa" );
		return planesEmpresaDao.obtenerTodosEntidades();
	}

	private void isvalidInsert(List<EmpresaEmpleadorXPlan> listRelacionPlanEmpresa, EmpresaEmpleadorXPlan nuevaRelacionPlanEmpresa) throws ErrorGeneral {
		LOG.info("INIT isvalidInsert" );
		Predicate<EmpresaEmpleadorXPlan> isSamePlan= relation -> relation.getIdPlan().equals(nuevaRelacionPlanEmpresa.getIdPlan());
		Predicate<EmpresaEmpleadorXPlan> isSameCorporative = relation -> relation.getIdEmpresaEmpleador().equals(nuevaRelacionPlanEmpresa.getIdEmpresaEmpleador());
		Predicate <EmpresaEmpleadorXPlan > repets = isSamePlan.and(isSameCorporative);
		List<EmpresaEmpleadorXPlan>  listPlanesRepetidos = listRelacionPlanEmpresa.stream().filter(repets).collect(Collectors.toList());
		if (listPlanesRepetidos.size() > 0){
			LOG.info("FAIL isvalidInsert" );
			throw new ErrorGeneral(400,"La Empresa Seleccionada ya cuenta con este plan");
		}
		LOG.info("END isvalidInsert" );
	}

	private void trySave(EmpresaEmpleadorXPlan nuevaRelacionPlanEmpresa)throws ErrorGeneral{
		LOG.info("INIT trySave" );
		try {
			empresaEmpleadorXPlanDao.guardarEntidad(nuevaRelacionPlanEmpresa);
		} catch (ConstraintViolationException e){
			LOG.info("FAIL trySave : " +e.getConstraintName());
			throw new ErrorGeneral(400, "Verifica los valores seleccionados: "+e.getConstraintName());
		}
		LOG.info("END trySave" );
	}
}

