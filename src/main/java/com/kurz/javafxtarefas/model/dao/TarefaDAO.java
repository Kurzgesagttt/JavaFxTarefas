package com.kurz.javafxtarefas.model.dao;

import com.kurz.javafxtarefas.config.DatabaseConfiguration;
import com.kurz.javafxtarefas.model.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public List<Tarefa> listarTodas() {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM tarefas ORDER BY data_criacao DESC";

        try (Connection conn = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(rs.getLong("id"));
                tarefa.setRealizado(rs.getBoolean("realizado"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());

                tarefas.add(tarefa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarefas;
    }

    public void inserir(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (realizado, descricao) VALUES (?, ?)";

        try (Connection conn = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, tarefa.isRealizado());
            stmt.setString(2, tarefa.getDescricao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Tarefa tarefa) {
        String sql = "UPDATE tarefas SET realizado = ?, descricao = ? WHERE id = ?";

        try (Connection conn = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, tarefa.isRealizado());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setLong(3, tarefa.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(long id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";

        try (Connection conn = DatabaseConfiguration.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
