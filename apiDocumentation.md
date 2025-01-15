
# API Documentation

## **Sign Up**  
**Endpoint**: `/api/sign-up`  
**Method**: `POST`

Registers a new patient.

**Takes**:  
- `firstname` (required)  
- `lastname` (required)  
- `email` (required, unique)  
- `address` (required)  
- `contact` (required)  
- `dateOfBirth` (required, format: `yyyy-MM-dd`)  
- `gender` (required)  
- `password` (required)  
- `confirmPassword` (required)

**Response**:  
- `201 Created`: `"Sign up successful"`  
- `400 Bad Request`: `"Failed to sign up, check input fields"`

---

## **Login**  
**Endpoint**: `/api/login`  
**Method**: `POST`

Logs in a user.

**Takes**:  
- `username` (required)  
- `password` (required)  
- `role.role` (fetches the role associated with the user, required for role-based access)

**Response**:  
- `201 Created`: `"Login successful"`  
- `400 Bad Request`: `"Invalid login credentials"`

---

## **Role**  
**Endpoint**: `/api/roles`  
**Method**: `GET`

Fetches all available roles.

**Response**:  
- `200 OK`: A list of available roles.

---

**Endpoint**: `/api/roles`  
**Method**: `POST`

Creates a new role.

**Takes**:  
- `rolename` (required)  
- `active` (required, boolean)

**Response**:  
- `201 Created`: `"Role created successfully"`  
- `400 Bad Request`: `"Invalid data"`

---

**Endpoint**: `/api/roles/{roleName}`  
**Method**: `GET`

Fetches details of a specified role.

**Response**:  
- `200 OK`: A detailed response with the specified role.  
- `400 Bad Request`: `"Role not found"`

---

**Endpoint**: `/api/roles/{id}`  
**Method**: `PUT`

Updates an existing role.

**Takes**:  
- `id` (required, path parameter)

**Response**:  
- `200 OK`: `"Role updated successfully"`  
- `404 Not Found`: `"Role not found"`  
- `400 Bad Request`: `"Invalid data"`

---

**Endpoint**: `/api/roles/{id}`  
**Method**: `DELETE`

Deletes an existing role.

**Takes**:  
- `id` (required, path parameter)

**Response**:  
- `200 OK`: `"Role deleted successfully"`  
- `404 Not Found`: `"Role not found"`  
- `400 Bad Request`: `"Invalid data"`

---

## **Receptionist**  

**Endpoint**: `/api/receptionist`  
**Method**: `POST`

Creates a new receptionist.  

**Takes**:  
- `firstname` (required)  
- `lastname` (required)  
- `email` (required, unique)  
- `address` (required)  
- `contact` (required)  
- `dob` (required, format: `yyyy-MM-dd`)  
- `gender` (required)  
- `password` (required)  
- `role` (required, e.g., "Receptionist")  

**Response**:  
- `201 Created`: `"Receptionist created successfully"`  
- `400 Bad Request`: `"Failed to create receptionist, check input fields"`

---

**Endpoint**: `/api/receptionist/{id}`  
**Method**: `GET`

Fetches details of a specified receptionist.

**Response**:  
- `200 OK`: A detailed response with the receptionist details.  
- `404 Not Found`: `"Receptionist not found"`


## **Register Patient**  
**Endpoint**: `/api/receptionists/patient/register`  
**Method**: `POST`

Registers a new patient by a receptionist.

**Takes**:  
- `firstname` (required)  
- `lastname` (required)  
- `email` (required, unique)  
- `address` (required)  
- `contact` (required)  
- `dateOfBirth` (required, format: `yyyy-MM-dd`)  
- `gender` (required)  
- `password` (required)  
- `confirmPassword` (required)

**Response**:  
- `200 OK`: `"Patient registration successful!"`  
- `400 Bad Request`: `"Error: Passwords do not match!"`  
- `400 Bad Request`: `"Error: Email already exists. Please use a different email address."`  
- `401 Unauthorized`: `"No authenticated user found!"`



## **Find Patient**  
**Endpoint**: `/api/receptionists/patients/{id}`  
**Method**: `GET`

Fetches details of a specified patient.

**Response**:  
- `200 OK`: A response with the patient details.
- `400 Bad Request`: `"Patient not found"`

---

## **Charge Consultation Fee**  
**Endpoint**: `/api/receptionists/patients/{id}/charge-consultation-fee`  
**Method**: `POST`

Charges the consultation fee to a patient.

**Takes**:  
- `id` (required) - Patient ID  
- `feeAmount` (required) - The amount to be charged for the consultation.

**Response**:  
- `200 OK`: `"Consultation fee charged: <feeAmount>"`  
- `400 Bad Request`: `"Error: Patient not found"`  
- `400 Bad Request`: `"Error: Unable to charge consultation fee"`


--- 
---
