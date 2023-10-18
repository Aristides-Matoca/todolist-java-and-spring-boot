package ao.com.aristides.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //Cria getter e setter dos atributos da classe
@Entity(name = "tb_tasks") //Cria a tabela tb_tasks usando os atributos da classe TaskModel como os campos da tabela
public class TaskModel {

    @Id //Define o atributo ID como um ID
    @GeneratedValue(generator =  "UUID")//Gera um id automático do tipo UUID
    private UUID id;

    private String description;

    @Column(length = 50) //Define apenas 50 caracteres no atributo title
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    //Função responsável em enviar uma exception para o caso do valor do title exceder os 50 caracteres
    public void setTitle(String title) throws Exception{ 
        if(title.length() > 50){
            throw new Exception("Nao pode exceder os 50 caracteres");
        }
        this.title = title;
    }
}
