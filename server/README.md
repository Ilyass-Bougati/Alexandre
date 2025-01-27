# Server doc
### The authentication
There are only 3 routes responsible for authentication

- `/v1/auth/register` : to register a user
- `/v1/auth/login` : to login
- `/v1/logout` : to logout

### The user controller
The creation of users can only be done through the register route, the other operations can be done through the `/v1/user`

- `GET` : fetch the details of the user (`UserDTO`)
- `PUT` : modifies the user details (except the role and the password)
- `DELETE` : to delete ther user
- `PUT /v1/user/password` : to change the password of the user

### The product controller
The CRUD api responsible for handling the operations linked to creating, reading, updating and deleting the products. Has one entrypoint which is `/v1/product`

- `GET /v1/product/{id}` : fetche the details of the product
- `POST /v1/product` : creates a new product (Recieves a `ProductCreationRequest` json object)
- `PUT /v1/product/{id}` : updates a product details (cannot update the user_id, id or creation date)
- `DELETE /v1/product/{id}` : deletes the product

updating and deleting the product can only be done if you're the creator of the product. And you can only create a product if you have the role of a `SELLER`
