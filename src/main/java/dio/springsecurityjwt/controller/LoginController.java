package dio.springsecurityjwt.controller;

import dio.springsecurityjwt.dto.Login;
import dio.springsecurityjwt.dto.Sessao;
import dio.springsecurityjwt.handler.UnauthorizedException;
import dio.springsecurityjwt.model.User;
import dio.springsecurityjwt.repository.UserRepository;
import dio.springsecurityjwt.security.JWTCreator;
import dio.springsecurityjwt.security.JWTObject;
import dio.springsecurityjwt.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
public class LoginController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public Sessao logar(@RequestBody Login login){
        User user = repository.findByUsername(login.getUsername());

        if(user!=null) {
            boolean passwordOk =  encoder.matches(login.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new UnauthorizedException("Senha inválida para o login: " + login.getUsername());
            }
            //Estamos enviando um objeto Sessão para retornar mais informações do usuário
            Sessao sessao = new Sessao();
            sessao.setLogin(user.getUsername());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(user.getRoles());
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
            sessao.setTokenCreated(jwtObject.getIssuedAt());
            sessao.setTokenExpired(jwtObject.getExpiration());
            sessao.setExpiration(SecurityConfig.EXPIRATION);
            return sessao;
        }else {
            throw new UnauthorizedException("O Usuario Não Cadastrado!");
        }
    }
}
