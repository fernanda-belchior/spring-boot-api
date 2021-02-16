package br.com.fernanda.springbootinterview.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class ErrorObject  implements Serializable {

    private final String message;
    private final String field;
    private final Object parameter;
}
