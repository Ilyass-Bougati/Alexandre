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
