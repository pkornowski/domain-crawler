# domain-crawler
Bot for crawling particular domain. Gives us e.g. possibility to see nested URLs as structured site-map.

## Environment requirements
* JDK 8+
* Maven 3.3.9+
* Apache Tomcat

### Build
Clone repository, open terminal in the main crawl directory `~/domain-crawlerafter` and execute the following command:
```
mvn clean install
```
### Running rest application
In the same directory:
```
mvn clean spring-boot:run
```
The server should be started up on 8080 port. 

Use Swagger UI for testing requests `http://localhost:8080/swagger-ui.html#/domain-controller`

The current version has only one request. There is possible to send the post request with domain URL as a body. As a response, we should receive a JSON tree with pages and theirs child-pages. At the end of the response will be placed links for resource e.g. images, videos.

CURL example:
`curl -d 'http://example.com: application/json' http://localhost:8080/urls
`

What next.:
* Separate frontend for current RestAPI
* Statistics, amount of internal, external links, resources etc.
* Database, the most frequent ones links.
* Add another implementation of CrawlerManager, e.g some of those algorithms: `https://pdfs.semanticscholar.org/c2d1/df9d8818907f174d446c60fc7b7908123fc5.pdf` and comparing execution time.
