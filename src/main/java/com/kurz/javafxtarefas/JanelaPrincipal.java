package com.kurz.javafxtarefas;

import com.kurz.javafxtarefas.model.Tarefa;
import com.kurz.javafxtarefas.model.dao.TarefaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @FXML
    private TextField campoDescricao;

    private final ObservableList<Tarefa> tarefas = FXCollections.observableArrayList();

    private final TarefaDAO tarefaDAO = new TarefaDAO();

    @FXML
    public void initialize() {
        // Configuração das colunas
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

        tableView.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DELETE:
                case BACK_SPACE: // <- adiciona isso se quiser aceitar backspace também
                    deletarTarefaSelecionada();
                    break;
            }
        });
    }


    private void carregarTarefasDoBanco() {
        tarefas.clear();
        tarefas.addAll(tarefaDAO.listarTodas());
    }

    @FXML
    private void criarTarefa() {
        String descricao = campoDescricao.getText().trim();

        if (!descricao.isEmpty()) {
            Tarefa nova = new Tarefa();
            nova.setDescricao(descricao);
            nova.setRealizado(false);

            tarefaDAO.inserir(nova);
            carregarTarefasDoBanco();
            campoDescricao.clear();
        }
    }

    @FXML
    private void deletarTarefaSelecionada() {
        Tarefa tarefaSelecionada = tableView.getSelectionModel().getSelectedItem();

        if (tarefaSelecionada != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmar Exclusão");
            confirmacao.setHeaderText("Deseja excluir a tarefa?");
            confirmacao.setContentText(tarefaSelecionada.getDescricao());

            confirmacao.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    tarefaDAO.deletar(tarefaSelecionada.getId());
                    tarefas.remove(tarefaSelecionada); // Remove da ObservableList
                }
            });
        }
    }

}
