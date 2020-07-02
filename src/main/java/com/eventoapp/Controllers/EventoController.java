package com.eventoapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;
	
	//renderizando e mostrando formulario
    @RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
    
    // Salvando dados no banco de dados 
    @RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
	public String form(Evento evento) {
    	er.save(evento);
		return "redirect:/eventos";
	}
    
    // mostrando os dados na tela index -  mostrandos dados de convidado
    @RequestMapping("/eventos")
    public ModelAndView listaEventos() {
    	ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos", eventos); 
        return mv;
    }
    
    // definido link de detalhes para cada evento com o id do evento
    @RequestMapping(value="/{codigo}", method=RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
    	Evento evento = er.findByCodigo(codigo);
    	ModelAndView mv = new ModelAndView("evento/detalhesEvento");
    	mv.addObject("evento",evento);
    	
        Iterable<Convidado> convidados = cr.findByEvento(evento);
        mv.addObject("convidado", convidados);
    	return mv;	
    }
    
    // deletando arquivo eventos - index
    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo) {
    	Evento evento = er.findByCodigo(codigo);
    	er.delete(evento);
    	return "redirect:/eventos";
    }
    
    // inserido no banco de dados (rg e nome 
    @RequestMapping(value="/{codigo}", method=RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, Convidado convidado) {
    	Evento evento = er.findByCodigo(codigo);
    	convidado.setEvento(evento);
    	cr.save(convidado);
    	return "redirect:/{codigo}";	
    }
    
    // deletando dados da lista de convidados
    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg) {
    	Convidado convidado = cr.findByRg(rg);
    	cr.delete(convidado);
    	
    	Evento evento = convidado.getEvento();
    	long codigoLong = evento.getCodigo();
    	String codigo = "" + codigoLong;
    	return "redirect:/" + codigo;
    }
}














