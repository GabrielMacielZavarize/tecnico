package com.example.tecnico.enums;

public enum Perfil {

    ADIMIN(0),
    CLIENTE(1),
    TECNICO(2);

    private final  int codigo;

    Perfil(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Perfil toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (Perfil p : Perfil.values()) {
            if (codigo.equals(p.getCodigo())) {
                return p;
            }
        }

        throw new IllegalArgumentException("Código inválido para Perfil: " + codigo);
    }
}
