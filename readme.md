Stack:
- Java 11;
- Amazon Web Services SQS, RDS, SDK;
- Spring WEB, Data JPA, Scheduling, REST;
- MySQL;

Microservice for deploy microservices-application.   
Docker application, that is listening common AWS SQS queue,
takes messages from it, parse them to required transfer objects and
writes to MySQL DB on AWS. Client can add new queues for listening 
using REST controller. Application can listen multiple queues and 
change queues list on demand.
