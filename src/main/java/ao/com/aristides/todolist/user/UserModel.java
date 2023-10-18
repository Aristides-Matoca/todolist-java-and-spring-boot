package ao.com.aristides.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //Cria getter e setter automaticamente
@Entity(name = "tb_users") //Cria a tabela tb_users usando os atributos da classe UserModel como os campos da tabela
public class UserModel {

    @Id //Define o atributo id com um ID
    @GeneratedValue(generator = "UUID") //Gera IDs automático
    private UUID id;

    @Column(unique = true) //Define o username como único
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
