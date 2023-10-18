package ao.com.aristides.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ao.com.aristides.todolist.user.IUserRepository;
import ao.com.aristides.todolist.user.UserModel;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //Classe responsável em decriptar e validar o username e a password
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) { //entra se a URL tiver /tasks/
            // Pegar a autenticao(user e pass)
            String authorization = request.getHeader("Authorization");

            String authEncode = authorization.substring("basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncode);

            String authString = new String(authDecode);
            System.out.println("Authorization");
            System.out.println(authString);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];
            System.out.println("Username: " + username + " e password: " + password);

            // Validar user
            UserModel user = this.userRepository.findByUsername(username); //Verifica se o user existe
            if(user == null){
                response.sendError(401);
                System.out.println("username esta errado");
            }
            else{
                // Validar senha
                BCrypt.Result passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(passwordVerify.verified){
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                }
                else{
                    response.sendError(401);
                    System.out.println("pass esta errado");
                }
            }
            // segue viagem
        }
        else{
            filterChain.doFilter(request, response);
        }
    }
}
