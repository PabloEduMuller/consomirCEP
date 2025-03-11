package br.unipar.programacaoweb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsomeCEP {

    public static void main(String[] args) {
        try {
            // Solicita o CEP ao usuário
            String cep = JOptionPane.showInputDialog("Digite o CEP:");

            // Verifica se o CEP já existe no banco de dados
            EnderecoDAO dao = new EnderecoDAO();
            Endereco endereco = dao.buscarPorCep(cep);

            if (endereco != null) {
                // Se o CEP já existe, exibe as informações
                JOptionPane.showMessageDialog(null, "CEP já cadastrado:\n" + endereco.toString());
            } else {
                // Se o CEP não existe, consulta a API ViaCEP
                String url = "https://viacep.com.br/ws/" + cep + "/json/";
                URL urlViaCEP = new URL(url);
                HttpURLConnection conexao = (HttpURLConnection) urlViaCEP.openConnection();
                conexao.setRequestMethod("GET");

                BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                String linha;
                StringBuilder resposta = new StringBuilder();

                while ((linha = leitor.readLine()) != null) {
                    resposta.append(linha).append("\n");
                }

                // Converte o JSON para um objeto Endereco
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignora campos desconhecidos
                endereco = mapper.readValue(resposta.toString(), Endereco.class);

                // Adiciona a data e hora da consulta
                endereco.setDataConsulta(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

                // Salva o novo CEP no banco de dados
                dao.salvar(endereco);

                // Exibe as informações do CEP cadastrado
                JOptionPane.showMessageDialog(null, "CEP cadastrado com sucesso:\n" + endereco.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao consultar ou salvar o CEP: " + e.getMessage());
        }
    }
}