package gov.goias.smart.colaborador.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "DOT_COLABORADOR")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Colaborador implements Serializable {

    @Id
    @Column(name = "ID_COLABORA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME_COLABORA", length = 100)
    private String nome;

    @Column(name = "INFO_EMAIL", length = 80)
    private String email;

    @Column(name = "NUMR_CPF", length = 11)
    private String cpf;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "INDI_SEXO", length = 20)
    private Sexo sexo;
}