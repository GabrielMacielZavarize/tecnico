package com.example.tecnico.enums;

public enum Prioridade {

    BAIXA(0),
    MEDIA(1),
    ALTA(2);

    private final int codigo;

    Prioridade(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Prioridade toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (Prioridade p : Prioridade.values()) {
            if (codigo.equals(p.getCodigo())) {
                return p;
            }
        }

        throw new IllegalArgumentException("Código inválido para Status: " + codigo);
    }
}
