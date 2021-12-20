package com.daviholanda.cursomc.resources;

import com.daviholanda.cursomc.domain.Cliente;
import com.daviholanda.cursomc.dto.ClienteDTO;
import com.daviholanda.cursomc.dto.ClienteNewDTO;
import com.daviholanda.cursomc.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		Cliente cliente = clienteService.find(id);
		return ResponseEntity.ok(cliente);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> delete(@PathVariable Long id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO) {

		Cliente obj = clienteService.fromDTO(objDTO);
		obj = clienteService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id,@Valid @RequestBody ClienteDTO clienteDTO) {
		Cliente obj = clienteService.fromDTO(clienteDTO);
		obj.setId(id);
		clienteService.update(obj);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value="page",defaultValue="0") Integer page,
													   @RequestParam(value="linesPerPage",defaultValue="2") Integer linesPerPage,
													   @RequestParam(value="orderBy",defaultValue="id") String orderBy,
													   @RequestParam(value="direction",defaultValue="ASC") String direction) {
		Page<ClienteDTO> clientesPageDTO = clienteService.findPage(page,linesPerPage,orderBy,direction);
		return ResponseEntity.ok().body(clientesPageDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> findAll() {
		var clientes = clienteService.findAll();
		return ResponseEntity.ok().body(clientes);
	}

	@PostMapping("/picture")
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file")  MultipartFile file) {
		URI uri = clienteService.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}

}
