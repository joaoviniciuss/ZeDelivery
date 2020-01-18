# Zé Delivery - Teste técnico

> Por João Vinícius S da Silva - joaoviniciuss@gmail.com

## **Pré-requisitos**

Nesta seção encontram-se os pré-requisitos necessários para executar a aplicação.

São eles:

- Docker
- Java 11
- IDE (recomendo o IntelliJ)
- Postgres 11

## **Executando a aplicação**

Para que a aplicação seja facilmente executa preparei um docker-compose que irá executar o postgres, pgadmin e a aplicação do teste técnico. 

Para isso abra o terminal de sua preferencia, navegue até o diretório raiz do projeto e execute o comando *docker-compose up*. Abaixo seguem informações dessas aplicações:

 > Postgres 

 Porta: 5432 <br>
 Banco de dados: zeDelivery <br>
 Usuário: postgres <br>
 Senha: Postgres

> PgAdmin

 Porta: 15433 <br>
 Usuário: joaoviniciuss@gmail.com <br>
 Senha: 123456

 > Aplicação (Zé Delivery)

  Porta: 8889 <br>
  Swagger: 8889/swagger-ui.html

## **Informações sobre a API**

### **POST**

URL: http://localhost:8889/v1/pdv

Body:
```json
{
	"tradingName": "Proximo a Algumas quadras",
	"ownerName": "Zé da Silva",
	"document": "1432132123891/0001",
	"coverageArea": {
		"type": "MultiPolygon",
		"coordinates": [
			[
				[
					[
						-50.42957425117493,
						-22.65172989233181
					],
					[
						-50.43116211891174,
						-22.65177939854546
					],
					[
						-50.4312264919281,
						-22.653908148844729
					],
					[
						-50.43026089668273,
						-22.653967555356389
					],
					[
						-50.43023943901062,
						-22.654809144843097
					],
					[
						-50.42753577232361,
						-22.65484874892712
					],
					[
						-50.427589416503909,
						-22.653185367560448
					],
					[
						-50.42947769165039,
						-22.653125960710314
					],
					[
						-50.42957425117493,
						-22.65172989233181
					]
				]
			]
		]
	},
	"address": {
		"type": "Point",
		"coordinates": [
			-50.42979955673218,
			-22.65450221280448
		]
	}
}
```

Algumas informações extras:

> Não permite a inclusão de mais de um registro com o mesmo CNPJ (*document*)
> Não permite a inserção de registro cuja sede (*addres*) não esteja dentro da área de cobertura (*coverageArea*)

### **GET ALL**

URL: http://localhost:8889/v1/pdv

Este *endpoint* retorna todos os estabelecimentos cadastrados.

>Caso nenhum registro seja encontrado o retorna o erro 400 (não encontrado)

### **GET BY ID**

URL: http://localhost:8889/v1/pdv/{id}

Este *endpoint* retorna o estabelecimento por ID.

>Caso nenhum registro seja encontrado o retorna o erro 400 (não encontrado)


### **GET BY POSITION**

URL: http://localhost:8889/v1/pdv/{longitude}/{latitude}

Este *endpoint* retorna o estabelecimento mais próximo da longitude e latitude informadas no momento da requisição.

>Caso nenhum registro seja encontrado o retorna o erro 400 (não encontrado)

## **Execunato sem o utilização do Docker**

Para executar a aplicação sem a utilização do Docker siga os passos abaixo:

*  Instale o Postgres 11 e crie um banco de dados.
* Abra a projeto da aplicação na sua IDE preferida. Aguarde que o Maven faça a construção do projeto.
* Abra o arquivo *application.yml* e coloque as informações de acesso ao banco de dados
* Execute como *Spring boot app* ou faça a compilação via *maven* e execute o jar diretamente pelo terminal.  
