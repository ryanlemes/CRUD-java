# CRUD Java
Compilando e executando
Primeira forma:

1. Caso tenha o maven instalado na máquina:

- `$ mvn clear package` Obs: Esse comando já roda os testes uniários.
- `$ cd /target`
- `$ java -jar crud-java.jar`

2. Caso tenha docker instalado 

- `$ docker build -t crud-java/java .` Obs: existe um comando no Dockerfile que roda os testes uniários.
- `$ docker run -p 8080:8080 crud-java/java` Obs: Verificar se a porta 8080 não está sendo utilizada para que não haja conflito. Caso esteja mapear a primeira opção após o -p para uma porta a sua escolha.

irá começar a rodar no link http://localhost8080
 
---

link da documentação Swagger:
- http://localhost:8080/swagger-ui.html#/