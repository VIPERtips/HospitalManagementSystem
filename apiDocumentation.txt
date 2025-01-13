Sign Up
Endpoint: api/sign-up
Method : POST
    --registers a patient
    takes : firstname (required),
            lastname  (required),
            email      (required,unique),
            address  (required),
            contact (required),
            dob    (required, format: yyyy-MM-dd),
            gender (required),
            password (required),
            confirmPassword (required)

Respone: 
  201 Created: "Sign up successfull"
  400 Bad Request: "Failed to sign up check input fields"

Login 
Endpoint: api/login
Method : POST
   takes: username  (required)
          password  (required)
          role.role  (fetches the role associated with user, required for role based access)

Respone: 
  201 Created: "Sign up successfull"
  400 Bad Request: "Failed to sign up check input fields"



Login 
Endpoint: api/roles
Method : GET

Respone: 
  available roles 
