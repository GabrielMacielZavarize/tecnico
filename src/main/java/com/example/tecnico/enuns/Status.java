package com.example.tecnico.enuns;

public enum Status {

    ABERTO(0),
    ANDAMENTO(1),
    ENCERRADO(2);

    private final int codigo;

    Status(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Status toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (Status s : Status.values()) {
            if (codigo.equals(s.getCodigo())) {
                return s;
            }
        }

        throw new IllegalArgumentException("Código inválido para Status: " + codigo);
    }
}

