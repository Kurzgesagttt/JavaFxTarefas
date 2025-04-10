package com.kurz.javafxtarefas;

import com.kurz.javafxtarefas.model.Tarefa;
import com.kurz.javafxtarefas.model.dao.TarefaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.time.LocalDateTime;

public class JanelaPrincipal {

    @FXML
    private TableView<Tarefa> tableView;

    @FXML
    private TableColumn<Tarefa, Boolean> colunaFeito;

    @FXML
    private TableColumn<Tarefa, String> colunaDescricao;

    @FXML
    private TableColumn<Tarefa, LocalDateTime> colunaDataCriacao;

    private final ObservableList<Tarefa> tarefas = FXCollections.observableArrayList();

    private final TarefaDAO tarefaDAO = new TarefaDAO();

    @FXML
    public void initialize() {
        colunaFeito.setCellValueFactory(cellData -> cellData.getValue().realizadoProperty());
        colunaFeito.setCellFactory(CheckBoxTableCell.forTableColumn(colunaFeito));
        colunaFeito.setEditable(true);

        colunaFeito.setOnEditCommit(event -> {
            Tarefa tarefa = event.getRowValue();
            tarefa.setRealizado(event.getNewValue());
            tarefaDAO.atualizar(tarefa);
        });

        colunaDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
        colunaDataCriacao.setCellValueFactory(cellData -> cellData.getValue().dataCriacaoProperty());

        tableView.setItems(tarefas);
        tableView.setEditable(true);

        carregarTarefasDoBanco();
    }

    private void carregarTarefasDoBanco() {
        tarefas.clear();
        tarefas.addAll(tarefaDAO.listarTodas());
    }
}
