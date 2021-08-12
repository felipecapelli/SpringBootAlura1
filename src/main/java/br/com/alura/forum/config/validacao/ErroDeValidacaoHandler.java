package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //eh um interceptador que faz o tratamento de qq exception em qq método de qq controller
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource; //vai ser usado pra pegar o erro no idioma que o usuario requisitar
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) //nesse caso vai continuar a devolver um 400 caso existe esse tipo de exceção
	@ExceptionHandler(MethodArgumentNotValidException.class) //quando houver uma exceção de um certo tipo dispara um argumento de um tipo
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
		List<ErroDeFormularioDto> dto = new ArrayList<>(); 
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors(); //pega os erros que aconteceram
		
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale()); //se quiser mudar pra inglês eh só mandar no header da requisição a chave: Accept-Language = en-US
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}
}
