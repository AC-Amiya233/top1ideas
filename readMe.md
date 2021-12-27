#### Project name: Top Ideas

A distributed paper search application with 4 components for local deploy:

1. Log-server (8079)
2. Login-server (8080)
3. Paper-server (8081)
4. User-server (8082)

#### Tech Stacks:

1. SSM( Springboot + SpringMVC + Mybatis)
2. Redis
3. RabbitMQ

##### High availability Designs:

*Mainly based on docker*, ports blow are functioned.

1. Read-wirte Asyn division database based on docker: Ports (from 3039 to 3041)
2. Redis Sentienal Framework based on docker: Ports (from 6300 to 6302 for Redis Servers, from 26379 to 26381 for Redis Sentienals)
3. RabbitMQ enabled durable based on docker: Port 6379

#### Servers:

* Login-server provides:
    * Distributed RateLimiter (Redis)
    * Defense IP Attack ability (with max 3 warning)
    * Defense Cache Penetration ability (Blooming Filter)

* Paper-server provides:
    * Distribued RateLimiter (Redis)
    * Defense Cache Penetration ability (Blooming Filter)
    * Dblp Spider (*Exper*)
* User-server provides:
    * Distribued RateLimiter (Redis)
    * Partially Defense Cache Penetration ability (Min-uid detect)

- Log-server provides:
    - Store log
    - Frequently MySQL write operations (i.e. paper view history)