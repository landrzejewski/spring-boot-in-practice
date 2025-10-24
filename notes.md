Exercise:
Implement a personal blog application. Apply the acquired knowledge of language and Spring framework.
Use the discussed payment example as a reference. Expose application logic using REST API.  
Store data in the H2 database.

UseCases for article authors:
- create article
- delete article
- update article
- publish article

UseCases for article readers:
- add article like/dislike
- add article comment
- find articles by category
- find articles by tags

### Queries

findByCategory:
select new pl.training.blog.application.ArticleView(a.id, a.title, a.author) from Articles a where a.category = :category

findByTags:
select new pl.training.blog.application.ArticleView(a.id, a.title, a.author) from Articles a join a.tags t where t.name in :tags group by a having count (a) = :count

findByCategoryAndTags:  
select new pl.training.blog.application.ArticleView(a.id, a.title, a.author) from Articles a join a.tags t where a.category = :category and t.name in :tags group by a having count (a) = :count


https://leanpub.com/maws
https://github.com/odrotbohm/spring-modulith-deep-dive
https://leanpub.com/get-your-hands-dirty-on-clean-architecture
https://reflectoring.io/book/


Proxy/AOP
https://refactoring.guru/design-patterns/proxy
https://www.baeldung.com/java-dynamic-proxies
https://docs.spring.io/spring-framework/reference/core/aop.html

Spring Security
https://www.manning.com/books/spring-security-in-action-second-edition
https://github.com/landrzejewski/spring-boot-in-practice
https://github.com/landrzejewski/spring-framework
https://github.com/landrzejewski/spring-cloud
https://github.com/landrzejewski/k8s

