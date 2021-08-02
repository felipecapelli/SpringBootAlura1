package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{
	
	//o underline não eh obrigatório, mas deixa claro que eh um relacionamento, mas se tiver um parametro com esse nome devemos usar pra saber se se trata do parametro ou de um atributo de um relacionamento
	List<Topico> findByCurso_Nome(String nomeCurso);
	
	//O mesmo metodo acima pode ser feito de outra maneira para não usar o padrão do spring data
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
	
}
