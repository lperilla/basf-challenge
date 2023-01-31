# Basf Challenge

This is a project for BASF and the purpose is allows to showcase the technical skills and expertise as developer.

## TASK

This solution should extract names of chemicals (like 'isobutanol') from the text of patents using NPL methods.
This means, your pipeline should:

1. Read one or many file xml of patents.
2. From these files you must extract:
   - Year.
   - Title.
   - abstract.
3. Persist these data in database.
4. Run a Named Entity Recognition (NER) over the abstract text.
5. Persis the NER output in the database.
6. Should allow to delete whole database.

## Tech

This project was developed with follow technicals features:
- Apache Maven 3.8.7 
- Java 17
- Spring boot version 3.0.2
- Spring integration version 3.0.2
- Gaphrql (graphql-spring-boot-starter ) version 15.0.0
- Lombok ()
- MongoDB (Database)
- Stanford-corenlp version 4.5.2 (for process NER).

## Architecture

- Minikube to quickly sets up a local Kubernetes cluster on linux.
- Docker.
- Kubernetes.

# Solution Design.

## Graphql Service

This application have run as a service and is callable via GraphQL.

### Schema:

```
type Query{
   sayHello(name: String!): String!
}

type Mutation {
   upload(file: Upload): Boolean
   dropCollections: Boolean
}
```

### Query:

- **sayHello**: 
  - just a simple method that return "hello ${name}" 

### Mutation:

- **upload(file: Upload):** Boolean: 
  - This is the main method, that can receive as a parameter a xml file or a collection of files inside a zip.
  - This method only allow upload file xml and zip, otherwise it returns an error.

### Behavior for XML file

> It will move to the folder specified in the **bash.directory** property of the **application.yaml** to be processed :

### Behavior for zip file

> It will unzip and move all xml files to the folder specified in the **bash.directory** property of the **application.yaml** to be processed :
