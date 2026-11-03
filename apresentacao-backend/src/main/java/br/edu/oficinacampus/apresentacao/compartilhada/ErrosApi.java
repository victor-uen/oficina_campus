package br.edu.oficinacampus.apresentacao.compartilhada;
import br.edu.oficinacampus.dominio.compartilhado.ErroDominio;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.Map;
@RestControllerAdvice
public class ErrosApi {
 @ExceptionHandler(ErroDominio.class) ResponseEntity<?> dominio(ErroDominio e){return ResponseEntity.status(switch(e.motivo()){case VALIDACAO -> 400;case AUSENTE -> 404;case CONFLITO -> 409;}).body(Map.of("mensagem",e.getMessage()));}
 @ExceptionHandler({OptimisticLockingFailureException.class}) ResponseEntity<?> conflito(Exception e){return ResponseEntity.status(409).body(Map.of("mensagem","Este registro mudou. Recarregue e revise antes de salvar."));}
 @ExceptionHandler({HttpMessageNotReadableException.class,MethodArgumentTypeMismatchException.class}) ResponseEntity<?> dados(Exception e){return ResponseEntity.badRequest().body(Map.of("mensagem","Dados inválidos. Confira os campos e os tipos informados."));}
}
