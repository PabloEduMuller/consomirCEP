package br.unipar.programacaoweb;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "endereco")
public class Endereco {
    @Id
    private String cep; // CEP é a chave primária
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String siafi;
    private String unidade; // Adicione este campo
    private String dataConsulta; // Data e hora da consulta

    @Override
    public String toString() {
        return "CEP: " + cep + "\n" +
                "Logradouro: " + logradouro + "\n" +
                "Complemento: " + complemento + "\n" +
                "Bairro: " + bairro + "\n" +
                "Localidade: " + localidade + "\n" +
                "UF: " + uf + "\n" +
                "IBGE: " + ibge + "\n" +
                "SIAFI: " + siafi + "\n" +
                "Unidade: " + unidade + "\n" +
                "Data da Consulta: " + dataConsulta;
    }
}