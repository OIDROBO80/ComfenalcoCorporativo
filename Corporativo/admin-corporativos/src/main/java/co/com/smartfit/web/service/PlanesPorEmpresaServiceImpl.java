package co.com.smartfit.web.service;
import java.util.ArrayList;
import java.util.function.Predicate;
import co.com.smartfit.web.dao.*;
import co.com.smartfit.web.entities.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("planesPorEmpresaService")
@Transactional
public class PlanesPorEmpresaServiceImpl implements PlanesPorEmpresaService {

	private static final Logger LOG = Logger.getLogger(PlanesPorEmpresaServiceImpl.class);
	private static  final int DIAS_DE_VALIDACION = 2;
	public PlanesPorEmpresaServiceImpl() {
	}

	@Override
	public List<Planes>  createPlan(String nombrePlan,Integer dias) throws ErrorGeneral {
		LOG.info("INIT createPlan" );
		PlanesDao planesDao = new PlanesDao();
		Planes nuevoPlan = new Planes();
		nuevoPlan.setPeriocidad(dias);
		nuevoPlan.setNombre(nombrePlan.toUpperCase());
		nuevoPlan.setDiasValidacion(DIAS_DE_VALIDACION);
		this.isvalidInsert(planesDao.obtenerTodosEntidades(),
				nuevoPlan );
		this.savePlan(planesDao.guardarOActualizar(nuevoPlan));
		LOG.info("ENDING createPlan" );
		return planesDao.obtenerTodosEntidades();
	}

	private void isvalidInsert(List<Planes> listPlanes, Planes nuevoPlan) throws ErrorGeneral {
		LOG.info("ENDING isvalidInsert" );
		Predicate<Planes> isSameName = plan -> plan.getNombre().equals(nuevoPlan.getNombre());
		Predicate<Planes> isSamePeriocidad = plan -> plan.getPeriocidad().equals(nuevoPlan.getPeriocidad());
		Predicate <Planes > repets = isSameName.and(isSameName);
		List<Planes>  listPlanesRepetidos = listPlanes.stream().filter(repets).collect(Collectors.toList());
		if (listPlanesRepetidos.size() > 0){
			LOG.info("FAIL isvalidInsert" );
			throw new ErrorGeneral(400,"Plan ya existe por favor verificar.");
		}
		LOG.info("END isvalidInsert" );
	}

	private void savePlan(boolean planWasSave) throws ErrorGeneral {
		LOG.info("INIT savePlan" );
		if (!planWasSave) {
			LOG.info("FAIL savePlan" );
			throw new ErrorGeneral(400,"Error guardando el nuevo plan");
		}
		LOG.info("END savePlan" );
	}

	@Override
	public List<CantidadCodigosPorPlan>  getlistCodeByPlan(String documentoEmpresa) throws ErrorGeneral {
		LOG.info("INIT getlistCodeByPlan" );
		CantidadCodigosPorPlanDao dao = new CantidadCodigosPorPlanDao();
		LOG.info("ENDING getlistCodeByPlan" );
		return dao.getListCode(false,documentoEmpresa);
	}



}

