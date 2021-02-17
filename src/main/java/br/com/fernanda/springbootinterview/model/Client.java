package br.com.fernanda.springbootinterview.model;

import br.com.fernanda.springbootinterview.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private GenderEnum gender;
    private LocalDate birthDate;
    private Integer age;
    @ManyToOne
    @Transient
    private City city;

    public Client(@NotBlank String name, @NotNull GenderEnum gender,
                  @NotNull LocalDate birthday, @NotNull Integer age, City city) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthday;
        this.age = age;
        this.city = city;
    }

}
