package br.com.fernanda.springbootinterview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{name.not.blank}")
    private String name;
    @NotBlank(message = "{state.not.blank}")
    private String state;

    public City(String name, String state) {
        this.name = name;
        this.state = state;
    }

}
