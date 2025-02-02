# Server doc
### The authentication
There are only 3 routes responsible for authentication

- `/v1/auth/register` : to register a user
- `/v1/auth/login` : to login
A request JSON example is
```json
{
	"email": "userEmail",
	"password": "userpassword"
}
```

- `/v1/logout` : to logout

### The user controller
The creation of users can only be done through the register route, the other operations can be done through the `/v1/user`

- `GET` : fetch the details of the user (`UserDTO`)
- `PUT` : modifies the user details (except the role and the password)
- `DELETE` : to delete ther user
- `PUT /v1/user/password` : to change the password of the user

### The product controller
The CRUD api responsible for handling the operations linked to creating, reading, updating and deleting the products. Has one entrypoint which is `/v1/id`

- `GET /v1/product/{id}` : fetche the details of the product
- `POST /v1/product` : creates a new id (Recieves a `ProductCreationRequest` json object)
- `PUT /v1/product/{id}` : updates a id details (cannot update the user_id, id or creation date)
- `DELETE /v1/product/{id}` : deletes the id

updating and deleting the id can only be done if you're the creator of the id. And you can only create a id if you have the role of a `SELLER`

### The cart controller
- `GET /v1/cart` : returns all the elements in the user car
- `POST /v1/cart` : adds a product to the cart
```json
{
	"productId": "[id]",
	"quantity": "[qtt]"
}
```
- `PUT /v1/cart` ; modifies the quantity or `isOrdered` property of a cart, and takes a `CartDTO` object
- `DELETE /v1/cart/productId` : Deltes a certain cart element

### The card controller
- `GET /v1/card` : return all of the cards of the user
- `POST /v1/card` : add a card to the users list of cards
```json
{
	"holdersFullName": "[...]",
	"cardNumber":  "[...]",
	"expiringDate":  "[...]",
	"cvv":  "[...]"
}
```
- `PUT /v1/card` : modifies a card informations.
- `DELETE /v1/card/cardId` : deletes a card by its id

### The seller controller
- `GET /v1/seller` : returns the sellers informations if the user is a seller
- `POST /v1/seller` : to become a seller
```json
{
	"companyName":  "[...]",
	"identifier":  "[...]"
}
```
