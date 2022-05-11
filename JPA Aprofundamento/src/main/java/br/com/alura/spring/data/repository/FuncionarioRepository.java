package br.com.alura.spring.data.repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer> ,
        JpaSpecificationExecutor<Funcionario> {

    // JPA NORMAL
    List<Funcionario> findByNome(String nome);

    /*List<Funcionario> findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, Double salario, LocalDate data);*/

    @Query("SELECT f FROM Funcionario f WHERE f.nome = :nome " +
            "AND f.salario >= :salario AND f.dataContratacao = :data")
    List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);

    @Query("SELECT f FROM Funcionario f JOIN f.unidadeTrabalhos u WHERE u.descricao = :descricao")
    List<Funcionario> findByUnidadeTrabalhos_Descricao(String descricao);


    List<Funcionario> findByNomeAndDataContratacaoGreaterThan(String nome, LocalDate data);

    // JQPL
    @Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.dataContratacao >= :data")
    List<Funcionario> findByNomeDataContratacaoMaiorQue(String nome, LocalDate data);

    // QUERY NATIVA ESTILO BANCO DE DADOS
    @Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data",
                  nativeQuery = true)
    List<Funcionario> findDataContratacaoMaior(LocalDate data);

    // PROJECAO
    @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f",
             nativeQuery = true)
    List<FuncionarioProjecao> findNomeESalarioFuncionarios();
}
