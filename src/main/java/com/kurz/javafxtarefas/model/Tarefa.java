package com.kurz.javafxtarefas.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Tarefa {
    private final LongProperty id = new SimpleLongProperty();
    private final BooleanProperty realizado = new SimpleBooleanProperty();
    private final StringProperty descricao = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> dataCriacao = new SimpleObjectProperty<>();

    public Tarefa() {}

    public Tarefa(long id, boolean realizado, String descricao, LocalDateTime dataCriacao) {
        this.id.set(id);
        this.realizado.set(realizado);
        this.descricao.set(descricao);
        this.dataCriacao.set(dataCriacao);
    }

    public long getId() { return id.get(); }
    public void setId(long id) { this.id.set(id); }

    public boolean isRealizado() { return realizado.get(); }
    public void setRealizado(boolean realizado) { this.realizado.set(realizado); }

    public String getDescricao() { return descricao.get(); }
    public void setDescricao(String descricao) { this.descricao.set(descricao); }

    public LocalDateTime getDataCriacao() { return dataCriacao.get(); }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao.set(dataCriacao); }

    // propriedades javaFX
    public BooleanProperty realizadoProperty() { return realizado; }
    public StringProperty descricaoProperty() { return descricao; }
    public ObjectProperty<LocalDateTime> dataCriacaoProperty() { return dataCriacao; }
}
