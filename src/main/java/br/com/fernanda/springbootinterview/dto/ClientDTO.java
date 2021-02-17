package br.com.fernanda.springbootinterview.dto;

import br.com.fernanda.springbootinterview.model.City;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO implements Serializable {

    private Long id;
    @NotBlank(message = "{name.not.blank}")
    private String name;
    @NotNull(message = "{gender.not.null}")
    private char gender;
    @NotBlank(message = "{birthDate.not.blank}")
    private String birthDate;
    @NotNull(message = "{age.not.null}")
    private Integer age;
    @NotNull(message = "{city.not.null}")
    private City city;

    public ClientDTO(String name, Character gender, String birthday, Integer age, City city) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthday;
        this.age = age;
        this.city = city;
    }
}
