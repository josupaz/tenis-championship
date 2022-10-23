package com.init.championship.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.init.championship.dao.TorneoDAO;
import com.init.championship.entities.Jugador;
import com.init.championship.entities.Jugadores;
import com.init.championship.entities.Torneo;

public class TorneoService {

	private int cantMaxJugadores = 16;
	private String TORNEO_FEMENINO = "FEMENINO";
	private String TORNEO_MASCULINO = "MASCULINO";

	public Torneo play(ObjectMapper mapper, JsonNode rootNode) throws JsonProcessingException, JsonMappingException {

		Jugadores jugadores;
		Torneo torneo = new Torneo();

		// Start by checking if this is a list -> the order is important here:
		if (rootNode instanceof ArrayNode) {
			torneo = null;
			// Read the json as a list:
		} else if (rootNode instanceof JsonNode) {
			// Read the json as a single object:
			jugadores = mapper.readValue(rootNode.toString(), Jugadores.class);

			try {
				torneo = playMatch(jugadores);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return torneo;
	}

	private Torneo playMatch(Jugadores jugadores) throws ParseException {
		Torneo torneo = new Torneo();
		int cantidadJugadores;
		Jugador auxJugadores[];
		List<Jugador> arrJugadores = new ArrayList<Jugador>();
		List<Jugador> arrGanadores = new ArrayList<Jugador>();
		String auxSexo;
		boolean flagSiguenJugando = true;
		int playerOne;
		int playerTwo;

		if (ValidaJugadores(jugadores)) {
			auxJugadores = jugadores.getJugadores();
			cantidadJugadores = auxJugadores.length;
			torneo.setCant_jugadores(cantidadJugadores);
			auxSexo = auxJugadores[0].getSexo();

			// fill array jugadores
			for (int i = 0; i < cantidadJugadores; i++) {
				arrJugadores.add(auxJugadores[i]);
			}

			// se juegan los partidos hasta generar ganador
			while (flagSiguenJugando) {
				if (auxSexo.contains(TORNEO_FEMENINO)) {

					for (int i = 0; i < arrJugadores.size() - 1; i = i + 2) {
						int j = i + 1;
						playerOne = ((arrJugadores.get(i).getHabilidad() * getSuerte()))
								- arrJugadores.get(i).getTiempo_reaccion();

						playerTwo = ((arrJugadores.get(j).getHabilidad() * getSuerte()))
								- arrJugadores.get(j).getTiempo_reaccion();
						if (playerOne > playerTwo) {
							arrGanadores.add(arrJugadores.get(i));
						} else {
							arrGanadores.add(arrJugadores.get(j));
						}
					}
					arrJugadores.clear();
					arrJugadores = new ArrayList<Jugador>(arrGanadores);
					arrGanadores.clear();
					
					if (arrJugadores.size() == 1) {
						flagSiguenJugando = false;
					}

				} else if (auxSexo.contains(TORNEO_MASCULINO)) {

					for (int i = 0; i < arrJugadores.size() - 1; i = i + 2) {
						int j = i + 1;
						playerOne = (arrJugadores.get(i).getHabilidad() + arrJugadores.get(i).getFuerza()
								+ arrJugadores.get(i).getVel_desplazamiento()) * getSuerte();
						playerTwo = (arrJugadores.get(j).getHabilidad() + arrJugadores.get(j).getFuerza()
								+ arrJugadores.get(j).getVel_desplazamiento()) * getSuerte();
						if (playerOne > playerTwo) {
							arrGanadores.add(arrJugadores.get(i));
						} else {
							arrGanadores.add(arrJugadores.get(j));
						}
					}
					arrJugadores.clear();
					arrJugadores = new ArrayList<Jugador>(arrGanadores);
					arrGanadores.clear();

					if (arrJugadores.size() == 1) {
						flagSiguenJugando = false;
					}

				}

			}
			// cargo ganador
			torneo.setGanador(arrJugadores.get(0).getNombre());
			torneo.setSexo(auxSexo);

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			Date today = new Date();

			Date todayWithZeroTime = formatter.parse(formatter.format(today));

			torneo.setFecha(todayWithZeroTime);
		}

		return torneo;
	}

	// valida cant de jugadores sea ^2 y tipo torneo F o M
	private boolean ValidaJugadores(Jugadores jugadores) {
		int cantidadJugadores;
		Jugador auxJugadores[];
		String auxSexo;

		boolean result = false;
		cantidadJugadores = jugadores.getJugadores().length;

		if (cantidadJugadores % 2 == 0) {
			if (cantidadJugadores <= cantMaxJugadores) {
				auxJugadores = jugadores.getJugadores();
				auxSexo = auxJugadores[0].getSexo();
				if (auxSexo.contains(TORNEO_FEMENINO) || auxSexo.contains(TORNEO_MASCULINO)) {
					for (int i = 0; i < cantidadJugadores; i++) {
						if (auxJugadores[i].getSexo() == auxSexo) {
							result = true;
						}
					}

				}
			}
		}
		return result;
	}

	private int getSuerte() {
		Random r = new Random();
		int result = r.nextInt(100) + 1;
		return result;
	}

	/*
	 * *@brief guarda torneo en db * @param TorneoDAO: coneccion db torneo
	 */
	public Torneo saveTorneo(TorneoDAO torneoDAO, Torneo torneo) {
		return torneoDAO.save(torneo);
	}

	public List<Torneo> fillTorneo(TorneoDAO torneoDAO) {
		List<Torneo> arrTorneo = new ArrayList<Torneo>();
		arrTorneo = torneoDAO.findAll();
		return arrTorneo;
	}

	public List<Torneo> fillTorneoByTipo(TorneoDAO torneoDAO, String tipoTorneo) {
		List<Torneo> arrTorneo = new ArrayList<Torneo>();
		List<Torneo> arrAuxTorneo = new ArrayList<Torneo>();
		arrTorneo = torneoDAO.findAll();
		if (!arrTorneo.isEmpty()) {
			for (int i = 0; i < arrTorneo.size(); i++) {
				if (arrTorneo.get(i).getSexo().contains(tipoTorneo)) {
					arrAuxTorneo.add(arrTorneo.get(i));
				}
			}
			arrTorneo = arrAuxTorneo;
		}

		return arrTorneo;
	}

	public Optional<Torneo> fillTorneoById(TorneoDAO torneoDAO, Long torneoId) {
		Optional<Torneo> optTorneo;
		optTorneo = torneoDAO.findById(torneoId);
		return optTorneo;
	}

	public List<Torneo> fillTorneoByCantidadJugadores(TorneoDAO torneoDAO, int cantidadJugadores) {
		List<Torneo> arrTorneo = new ArrayList<Torneo>();
		List<Torneo> arrAuxTorneo = new ArrayList<Torneo>();
		arrTorneo = torneoDAO.findAll();
		if (!arrTorneo.isEmpty()) {
			for (int i = 0; i < arrTorneo.size(); i++) {
				if (arrTorneo.get(i).getCant_jugadores() ==  cantidadJugadores) {
					arrAuxTorneo.add(arrTorneo.get(i));
				}
			}
			arrTorneo = arrAuxTorneo;
		}

		return arrTorneo;
	}

}
