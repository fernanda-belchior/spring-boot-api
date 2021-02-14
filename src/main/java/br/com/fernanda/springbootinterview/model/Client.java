package br.com.fernanda.springbootinterview.model;

import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Character gender;
    private Date birthday;
    private Integer age;
    @ManyToOne
    private City city;
}
