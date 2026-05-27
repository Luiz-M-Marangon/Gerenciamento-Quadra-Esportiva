package gerenciamento.quadra.frameworks.service;

import gerenciamento.quadra.frameworks.model.Usuario;
import gerenciamento.quadra.frameworks.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Primary
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void salvar(Usuario usuario){
        if (usuario.getId() == null || !usuario.getSenha().startsWith("{bcrypt}")){
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(usuario.getUsername())
                .password(usuario.getSenha())
                .roles(usuario.getRole())
                .build();
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID:" + id));
    }

    public void excluir(Long id){
        usuarioRepository.deleteById(id);
    }
}
