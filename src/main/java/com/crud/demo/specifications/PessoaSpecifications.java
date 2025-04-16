package com.crud.demo.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.crud.demo.models.Pessoa;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;

public class PessoaSpecifications {

    public static Specification<Pessoa> comNomeContendo(String nome) {
        return (root, query, builder) -> nome == null ? null
                : builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Pessoa> comCpfContendo(String cpf) {
        return (root, query, builder) -> cpf == null ? null
                : builder.like(builder.lower(root.get("cpf")), "%" + cpf.toLowerCase() + "%");
    }

    public static Specification<Pessoa> comCidadeContendo(String cidade) {
        return (root, query, builder) -> cidade == null ? null
                : builder.like(builder.lower(root.join("enderecos", JoinType.LEFT).get("cidade")),
                        "%" + cidade.toLowerCase() + "%");
    }

    public static Specification<Pessoa> comEstadoContendo(String estado) {
        return (root, query, builder) -> estado == null ? null
                : builder.like(builder.lower(root.join("enderecos", JoinType.LEFT).get("estado")),
                        "%" + estado.toLowerCase() + "%");
    }

    public static Specification<Pessoa> comBairroContendo(String bairro) {
        return (root, query, builder) -> bairro == null ? null
                : builder.like(builder.lower(root.join("enderecos", JoinType.LEFT).get("bairro")),
                        "%" + bairro.toLowerCase() + "%");
    }

    public static Specification<Pessoa> comCepContendo(String cep) {
        return (root, query, builder) -> cep == null ? null
                : builder.like(builder.lower(root.join("enderecos", JoinType.LEFT).get("cep")),
                        "%" + cep.toLowerCase() + "%");
    }

    public static Specification<Pessoa> aniversariantesDeHoje() {
        return (root, query, builder) -> {
            LocalDate hoje = LocalDate.now();

            String dia = String.format("%02d", hoje.getDayOfMonth());
            String mes = String.format("%02d", hoje.getMonthValue());

            Expression<String> diaExpr = builder.function("to_char", String.class, root.get("dataNascimento"),
                    builder.literal("DD"));
            Expression<String> mesExpr = builder.function("to_char", String.class, root.get("dataNascimento"),
                    builder.literal("MM"));

            return builder.and(
                    builder.equal(diaExpr, dia),
                    builder.equal(mesExpr, mes));
        };
    }

    public static Specification<Pessoa> aniversariantesDoMes(int mes) {
        return (root, query, builder) -> {
            if (mes < 1 || mes > 12) return null;

            String mesFormatado = String.format("%02d", mes);
            Expression<String> mesExpr = builder.function("to_char", String.class, root.get("dataNascimento"), builder.literal("MM"));

            return builder.equal(mesExpr, mesFormatado);
        };
}
}