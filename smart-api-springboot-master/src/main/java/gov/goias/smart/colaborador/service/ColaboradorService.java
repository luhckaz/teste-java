package gov.goias.smart.colaborador.service;

import gov.goias.smart.colaborador.dto.ColaboradorDTO;
import gov.goias.smart.colaborador.entity.Colaborador;
import gov.goias.smart.colaborador.repository.ColaboradorRepository;
import gov.goias.smart.common.service.AlgumNegocioException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository repository;

    @Transactional(rollbackFor = Throwable.class)
    public ColaboradorDTO saveCreate(ColaboradorDTO colaboradorDTO) {

        colaboradorDTO.setId(null);
        return save(colaboradorDTO);
    }

    @Transactional(rollbackFor = Throwable.class)
    public ColaboradorDTO saveUpdate(ColaboradorDTO colaboradorDTO) {

        repository.findById(colaboradorDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Colaborador : " + colaboradorDTO.getId()));

        return save(colaboradorDTO);
    }


    public ColaboradorDTO save(ColaboradorDTO colaboradorDTO) {

        if (isMenorDeIdade(colaboradorDTO.getDataNascimento())) {
            throw new AlgumNegocioException("Não permitido Colaborador menor de idade!");
        }

        Colaborador colaborador = new ModelMapper().map(colaboradorDTO, Colaborador.class);
        Colaborador save = repository.save(colaborador);

        return new ModelMapper().map(save, ColaboradorDTO.class);
    }

    /**
     * Regra de Negocio se Colaborador eh menor de idade no Brasil.
     *
     * @param dataNascimento Data de Nascimento do Colaborador
     * @return validacao
     */
    private boolean isMenorDeIdade(LocalDate dataNascimento) {
        final int idadeMaioridadeNoBrasil = 18;
        return Period.between(dataNascimento, LocalDate.now()).getYears() < idadeMaioridadeNoBrasil;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteById(Long idColaborador) {

        repository.deleteById(idColaborador);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void delete(Colaborador colaborador) {

        repository.delete(colaborador);
    }
}