package br.edu.ifpe.gestaocliente.controller;

import java.sql.SQLException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpe.gestaocliente.dao.ClienteDAO;
import br.edu.ifpe.gestaocliente.model.Cliente;
import br.edu.ifpe.gestaocliente.model.UnidadeFederacao;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	ClienteDAO clienteDAO; 
	
	@PostMapping("/{id}/delete")
	public ModelAndView deleteCliente(@PathVariable Long id) {
		int codigo = (int) id.intValue();
		  try {
			clienteDAO.deletarClienteDAO(codigo);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/clientes");
	}
	
	@PostMapping("/{id}")
	public ModelAndView update(@PathVariable Long id,@Valid Cliente cliente , BindingResult bindingResults){

		if(bindingResults.hasErrors()) {
			ModelAndView mv = new ModelAndView("clientes/edit");
			mv.addObject("UnidadeFederacao", UnidadeFederacao.values());
			return mv;
		}else {
			int codigo = (int) id.intValue();

			try {
				cliente.setCodigo(codigo);
				clienteDAO.alterarClienteDAO(cliente);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/clientes");
		}
	}
	
	@GetMapping("/{id}/edit")
	public ModelAndView edit(@PathVariable Long id, Cliente cliente) {
		ModelAndView mv = new ModelAndView("clientes/edit");
		int codigo = (int) id.intValue();
				
		try {
			cliente = clienteDAO.consultarCliente(codigo);
			
			if(cliente != null) {
				mv.addObject("cliente", cliente);
				mv.addObject("UnidadeFederacao", UnidadeFederacao.values());
				mv.addObject("clienteID", id);
				return mv;	
			}else {
				return new ModelAndView("redirect:/clientes"); //caso não encontre o objeto no banco.
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;		
		
	}
	
	@PostMapping("")
	public ModelAndView createCliente(@Valid Cliente cliente , BindingResult bindingResults) {
		
		if(bindingResults.hasErrors()) {
			ModelAndView mv = new ModelAndView("clientes/new");
			mv.addObject("UnidadeFederacao", UnidadeFederacao.values());
			return mv;
		}

		 try {
			clienteDAO.adiciona(cliente);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/clientes");
	}
	
	
	@GetMapping("/new")
	public ModelAndView novoCliente() {
		ModelAndView mv = new ModelAndView("clientes/new");
		mv.addObject("cliente", new Cliente());		
		mv.addObject("UnidadeFederacao", UnidadeFederacao.values());
		return mv;
	}
	
	
	@GetMapping("")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("clientes/index");
		try {
			mv.addObject("clientes", clienteDAO.consultarTodosClientes());
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	


}
