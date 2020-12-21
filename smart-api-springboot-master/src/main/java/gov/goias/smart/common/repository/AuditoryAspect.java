package gov.goias.smart.common.repository;

import gov.goias.smart.common.service.AlgumNegocioException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@Aspect
@Profile({"!localh2"})
@Component
public class AuditoryAspect {

    @PersistenceContext
    private EntityManager em;

    @Auditory
    @Before("@annotation(gov.goias.smart.common.repository.Auditory)")
    public void logExecution(JoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //FIXME TODO RESOLVER A QUESTAO DA SEGURANCA PARA PODER PEGAR USUARIO DA CAMADA DE SEGURANCA
        setUsuarioSessao("HENRIQUEROCHA");

        Optional<Principal> principal = Optional.ofNullable(request.getUserPrincipal());
        principal.ifPresent(p -> {
            String user = p.getName();
            if (!ObjectUtils.isEmpty(user)) {
                setUsuarioSessao(user);
            }
        });
    }

    /**
     * Executa a procedure {call GEN.PKGGEN_INICIALIZA_USER.SPGEN_INICIALIZA_USER(:user)}
     * @param user Usuario a qual esta realizando operacao no banco de dados.
     */
    private void setUsuarioSessao(String user){
        try{
            log.trace("Auditing.. " + user);
            final Query q = em.createNativeQuery("{call GEN.PKGGEN_INICIALIZA_USER.SPGEN_INICIALIZA_USER(:user)}");
            q.setParameter("user", user);
            q.executeUpdate();
        }catch(Exception e){
            log.error(e.getMessage(), e);
            throw new AlgumNegocioException("Houve um erro ao tentar atribuir o usuário na sessão de auditoria",e);
        }
    }
}
