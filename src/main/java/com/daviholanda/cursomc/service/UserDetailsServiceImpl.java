package com.daviholanda.cursomc.service;

import com.daviholanda.cursomc.domain.Cliente;
import com.daviholanda.cursomc.repository.ClienteRepository;
import com.daviholanda.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Cliente cliente = clienteRepository.findByEmail(email);

        if(cliente == null) {
            throw new UsernameNotFoundException(email);
        }

        UserSS userSS = new UserSS(cliente.getId().intValue(),cliente.getEmail(),cliente.getSenha(),cliente.getPerfis());

        return userSS;
    }

}
