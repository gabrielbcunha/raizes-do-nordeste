package br.com.gabrielbcunha.sistemaraizesdonordeste.security;

import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return usuarioRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

}
