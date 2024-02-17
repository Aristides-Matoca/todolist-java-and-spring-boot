package ao.com.aristides.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users") //URL para a users
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @SuppressWarnings("rawtypes")
    @PostMapping("/")//Metódo responsávem em criar users
    public ResponseEntity create(@RequestBody UserModel userModel){
        UserModel user = this.userRepository.findByUsername(userModel.getUsername()); //Verifica se o username da existe

        if(user != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilizador inexistente");
        }

        //Criptografa a password antes de mandar para a Base de Dados
        String passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred);

        UserModel userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
}