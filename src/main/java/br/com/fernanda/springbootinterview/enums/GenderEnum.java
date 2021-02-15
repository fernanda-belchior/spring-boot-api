package br.com.fernanda.springbootinterview.enums;

public enum GenderEnum {


    M('M'),
    F('F');

    private char value;

    GenderEnum(char value){
        this.value = value;
    }

    public char getValue(){return this.value;}

}

