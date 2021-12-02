package com.interventor.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "app-usuarios")
public interface UsersFeignClient {

	@DeleteMapping("/users/eliminar/{username}")
	public Boolean eliminarUsuario(@PathVariable("username") String username);

}
