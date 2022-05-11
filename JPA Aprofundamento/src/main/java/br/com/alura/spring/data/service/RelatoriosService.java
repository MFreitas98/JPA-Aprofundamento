package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatoriosService {

    private Boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;


    public RelatoriosService(FuncionarioRepository funcionarioRepository){

        this.funcionarioRepository = funcionarioRepository;
    }



    public void inicial(Scanner scanner) {
        while (system){
            System.out.println("Qual acao de cargo deseja executar");
            System.out.println("0 - Sair");
            System.out.println("1- Busca funcionario nome");
            System.out.println("2 - Busca funcionario nome, data contratacao e salario maior que");
            System.out.println("3 - Busca funcionario nome e data contratacao maior que");
            System.out.println("4 - Funcionarios Data Contratacao a partir de");
            System.out.println("5 - Pesquisa Funcionario Salario");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    buscaFuncionarioNome(scanner);
                    break;
                case 2:
                    buscaFuncionarioNomeSalarioMaiorData(scanner);
                    break;
                case 3:
                    buscaFuncionarioNomeEDataContratacaoApos(scanner);
                    break;
                case 4:
                    buscaFuncionarioDataContratacaoMaiorQue(scanner);
                    break;
                case 5:
                    pesquisaFuncionarioSalario();
                    break;
                default:
                    system=false;
                    break;
            }
        }

    }

     private void buscaFuncionarioNome(Scanner scanner) {
         System.out.println("Qual nome deseja pesquisar");
         String nome = scanner.next();
         List<Funcionario> list = funcionarioRepository.findByNome(nome);
         list.forEach(System.out::println);

    }

    private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
        System.out.println("Qual nome deseja pesquisar");
        String nome = scanner.next();

        System.out.println("Qual data contratacao deseja pesquisar");
        String data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);

        System.out.println("Qual salario deseja pesquisar");
        Double salario = scanner.nextDouble();

        List<Funcionario> list = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome,salario,localDate);
        list.forEach(System.out::println);
    }

    private void buscaFuncionarioNomeEDataContratacaoApos(Scanner scanner){
        System.out.println("Qual nome deseja pesquisar");
        String nome = scanner.next();

        System.out.println("Qual data contratacao deseja pesquisar");
        String data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);

        List<Funcionario> list = funcionarioRepository.findByNomeDataContratacaoMaiorQue(nome,localDate);
        list.forEach(System.out::println);
    }


    private void buscaFuncionarioDataContratacaoMaiorQue(Scanner scanner){
        System.out.println("Qual data contratacao deseja pesquisar");
        String data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);

        List<Funcionario> list = funcionarioRepository.findDataContratacaoMaior(localDate);
        list.forEach(System.out::println);

    }

   private void pesquisaFuncionarioSalario() {
        List<FuncionarioProjecao> list = funcionarioRepository.findNomeESalarioFuncionarios();
        list.forEach(f -> System.out.println("Funcionario: id: " + f.getId()
                + " | nome: " + f.getNome()
                + " | salario: " + f.getSalario()));
   }

}
