package com.init.championship.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.init.championship.dao.TorneoDAO;
import com.init.championship.entities.Torneo;
import com.init.championship.service.TorneoService;




@RestController
@RequestMapping("/")
public class ChampionshipController {
	
	//separar capa datos
	@Autowired
	private TorneoDAO torneoDAO;    
	
	

	private TorneoService torneoService = new TorneoService();
	
	ResponseEntity<String> badRequest = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	
	
	@RequestMapping(value="play", method=RequestMethod.POST)
	public ResponseEntity<String> playchampionship(@RequestBody String jsonAsString){
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(jsonAsString);
			Torneo torneo = torneoService.play(mapper, rootNode);
			if (torneo != null) {	
				torneo = torneoService.saveTorneo(torneoDAO, torneo);
				return ResponseEntity.ok(torneo.toString());
				
			};
			
			//error en parametros json de entrada
			return badRequest;
		
		} catch (Exception e) {
			System.out.println("****LOG****: ERROR EXCEPCION PARSE JSON: "+ e);
			return badRequest;
		}
	}
	
	
	@RequestMapping(value="torneo", method=RequestMethod.GET)
	public List<Torneo> torneo() {
		List<Torneo> arrTorneo = new ArrayList<Torneo>();
		arrTorneo = torneoService.fillTorneo(torneoDAO);
		return arrTorneo;
	}
	
	@RequestMapping(value="torneo/{torneoId}", method=RequestMethod.GET)
	public ResponseEntity<Torneo> torneoById(@PathVariable("torneoId")Long torneoId) {
		Optional<Torneo> optTorneo;
		optTorneo = torneoService.fillTorneoById(torneoDAO ,torneoId);
		if(optTorneo.isPresent()) {
			return ResponseEntity.ok(optTorneo.get());
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="torneo/tipo/{tipoTorneo}", method=RequestMethod.GET)
	public List<Torneo> torneoByTipo(@PathVariable("tipoTorneo")String tipoTorneo) {
		List<Torneo> arrTorneo = new ArrayList<Torneo>();
		arrTorneo = torneoService.fillTorneoByTipo(torneoDAO ,tipoTorneo.toUpperCase());
		return arrTorneo;
	}
	
	@RequestMapping(value="torneo/cantidad/{cantidadJugadores}", method=RequestMethod.GET)
	public List<Torneo> torneoByCantidadJugadores(@PathVariable("cantidadJugadores")int cantidadJugadores) {
		List<Torneo> arrTorneo = new ArrayList<Torneo>();
		arrTorneo = torneoService.fillTorneoByCantidadJugadores(torneoDAO ,cantidadJugadores);
		return arrTorneo;
	}
	
	

}
