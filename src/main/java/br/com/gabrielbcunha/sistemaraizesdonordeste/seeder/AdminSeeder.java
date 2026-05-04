package br.com.gabrielbcunha.sistemaraizesdonordeste.seeder;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Usuario;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Perfil;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${senha.admin.raiz}")
    String senhaAdmin;

    @Override
    public void run(String... args) throws Exception {

        String emailAdmin = "admin@raizesdonordeste.com.br";

        Optional<Usuario> adminExistente = usuarioRepository.findByUserName(emailAdmin);
        if (adminExistente.isEmpty()) {

            Usuario usuario = new Usuario();
            usuario.setUserName(emailAdmin);
            usuario.setSenha(passwordEncoder.encode(senhaAdmin));
            usuario.setPerfil(Perfil.ROLE_ADMIN);
            usuarioRepository.save(usuario);
        }
    }
}