# Sales Management

## O que é?

É uma aplicação que tem por finalidade monitorar um diretório e identificar se existe um arquivo contendo informações de clientes, vendedores e vendas.  Caso esse arquivo exista, o sistema deve identificar tais informações e gerar um arquivo-resumo contendo o resumo do arquivo lido. Nesse resumo, devem conter as seguintes informações:

- Quantidade de clientes
- Quantidade de vendedores
- Id da maior venda
- O pior vendedor

## Como é esse arquivo?

O arquivo é formado por Strings na qual possui o caractere 'ç' como delimitador de campos. 

Veja abaixo um exemplo do arquivo e como deve ser feita a desestruturação da linha.

Exemplo de conteúdo total do arquivo:</br>
001ç1234567891234çPedroç50000</br>
001ç3245678865434çPauloç40000.99</br>
002ç2345675434544345çJose da SilvaçRural</br>
002ç2345675433444345çEduardo PereiraçRural</br>
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro</br>
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo</br>

### Dados do vendedor
Os dados do vendedor possuem o identificador 001 e seguem o seguinte formato:
001çCPFçNameçSalary</br>
### Dados do cliente
Os dados do cliente possuem o identificador 002 e seguem o seguinte formato:
002çCNPJçNameçBusiness Area</br>
### Dados de venda
Os dados de venda possuem o identificador 003 e seguem o seguinte formato:
003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name</br>

## O objetivo foi cumprido?

Sim, o sistema verifica se existe a pasta dentro do diretório home_path/data/in e começa a 'varrer' o diretório em busca de arquivos através de um schedule setado para realizar a rotina com um time determinado de 10 segundos , caso ele encontre, ele faz uma tentativa de leitura e em caso de sucesso, ele lê cada uma das linhas, ignorando e 'logando' as linhas que não atendem ao padrão estabelecido a cima.

Por fim, após ele guardar em memória todas as informações do arquivo ele realiza a análise das mesmas, identificando a maior venda, pior vendedor e quantidade de clientes e vendedores. Após essa análise o sistema escreve o arquivo de resumo e salva no diretório home_path/data/out.

Obs: esses diretório estão parametrizados dentro do arquivo application.properties, podendo serem alterados de forma simples.

## Além do objetivo

Além de atingir o objetivo principal da aplicação, a mesma utiliza algumas features que podem ser destacadas:

- Em algumas classes que poderiam ter somente uma instância durante o ciclo de vida da aplicação, foi aplicado o Pattern **singleton**
- Para gerenciar as vendas, foi utilizado o Pattern **builder,** visto que foi necessário armazenar o objeto itemsSale dentro do objeto Orders
- A aplicação consegue realizar a validação individual da linha, para que não invalide o arquivo inteiro caso somente algumas linhas não estejam de acordo com o padrão (essas linhas fora de padrão podem ser consultadas no arquivo de log).
- Todos os erros tratados da aplicação ficam salvas em arquivo de log na qual deve-se incluir o diretório escolhido no arquivo log4j.properties.
- Algumas variáveis estão definidas no arquivo de properties para ser fácil alteração caso seja necessário.

## Observações

- Não foi utilizado nenhum ORM para armazenar as informações porque dependeria de subir um container e configurar um ambiente para que a aplicação funcionasse. Não que seja difícil de fazer, mas acredito que não seja o intuito da aplicação, pois ao meu ver, seria mais fácil o desenvolvimento.
- Os arquivos utilizam a extensão .dat e os arquivos de saída são gerados com nomes distintos.
- A aplicação consolida os dados de todos os arquivos que estiverem no diretório em somente um arquivo de saída.

## Fluxo de funcionamento

A aplicação segue o seguinte fluxo de funcionamento:

1. Aplicação inicia juntamente com schedule
2. Quando o schedule localiza um arquivo, realiza a leitura
3. Passa a linha para outro método que quebra a mesma incluindo suas informações no seu respectivo repositório
4. Envia os 3 objetos para a classe responsável por analisar e escrever o arquivo de resumo.
5. A classe de escrita lê as informações  realizando as validações necessárias para escrever o arquivo de resumo.
6. Escreve e grava o arquivo se resumo.
