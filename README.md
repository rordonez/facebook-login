# facebook-login
This is a simple Rest API to connect to Facebook through social login and then it is posible to send request to an Facebook app. This project has a endpoint to get user photos.

This project has been configured using Spring Initializr. These are the included modules:

- Spring Boot
- Sprint HATEOAS
- Spring Social Facebook
- Spring Test
- Thymeleaf for rendering.

### Login url
http://localhost:8080 #This url renders a page to connect to Facebook.

### User photos
http://localhost:8080/api/facebook #list of user photos

This API manages content negotiation through Accept Header and URI suffix. It also uses Json and XML for representing output data.
