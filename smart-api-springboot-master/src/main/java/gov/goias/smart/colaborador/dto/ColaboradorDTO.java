package gov.goias.smart.colaborador.dto;

import gov.goias.smart.colaborador.entity.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Schema(name = "Colaborador", description = "Colaborador da Subsecretaria de Tecnologia da Informacao.")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColaboradorDTO implements Serializable {

    @Schema(description = "Identificador do Colaborador")
    private Long id;

    @Schema(description = "Nome do Colaborador")
    private String nome;

    @Schema(description = "Email do Colaborador")
    private String email;

    @Schema(description = "Cpf do Colaborador")
    private String cpf;

    @Schema(description = "Data de Nascimento do Colaborador")
    private LocalDate dataNascimento;

    @Schema(description = "Sexo do Colaborador")
    private Sexo sexo;
}