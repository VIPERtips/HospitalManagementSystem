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

**Endpoint**: `/api/receptionists`  
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

**Endpoint**: `/api/receptionists/{id}`  
**Method**: `GET`

Fetches details of a specified receptionist.

**Response**:  
- `200 OK`: A detailed response with the receptionist details.  
- `404 Not Found`: `"Receptionist not found"`

---

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

---

**Send Patient Details to Nurse**  
**Description**:  
Allows the receptionist to send patient details to the nurse. The action is permitted only if the consultation fee has been paid.

- **URL**: `/patient/{id}/send-to-nurse`
- **Method**: `POST`
- **Path Parameters**:  
  - `id` (integer): The unique identifier of the patient.
  
- **Response**:  
  - `200 OK`:  
    Patient details successfully sent to the nurse.
  
  - `400 Bad Request`:  
    Consultation fee not paid or patient not found.

---

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

## **Send Patient Details to Nurse**  
**Endpoint**: `/patient/{id}/send-to-nurse`  
**Method**: `POST`

Sends patient details to the nurse by the nurse's ID.

**Takes**:  
- `id` (required) - Patient ID (integer)  
- `nurseIdentifier` (required) - Nurse ID (integer)

---

## **Nurse**  

### **Create Nurse**  
**Endpoint**: `/api/nurses`  
**Method**: `POST`

Creates a new nurse.

**Takes**:  
- `firstname` (required)  
- `lastname` (required)  
- `email` (required, unique)  
- `address` (required)  
- `contact` (required)  
- `dob` (required, format: `yyyy-MM-dd`)  
- `gender` (required)  
- `password` (required)  
- `role` (required, e.g., "Nurse")  

**Response**:  
- `201 Created`: `"Nurse created successfully"`  
- `400 Bad Request`: `"Failed to create nurse, check input fields"`

---

### **Get All Nurses**  
**Endpoint**: `/api/nurses`  
**Method**: `GET`

Fetches a list of all nurses.

**Response**:  
- `200 OK`: A list of all nurses.

---

### **Get Nurse by ID**  
**Endpoint**: `/api/nurses/{id}`  
**Method**: `GET`

Fetches details of a specified nurse.

**Response**:  
- `200 OK`: A detailed response with the nurse details.  
- `404 Not Found`: `"Nurse not found"`

---

### **Update Nurse**  
**Endpoint**: `/api/nurses/{id}`  
**Method**: `PUT`

Updates an existing nurse.

**Takes**:  
- `id` (required, path parameter)  
- `firstname` (optional)  
- `lastname` (optional)  
- `email` (optional)  
- `address` (optional)  
- `contact` (optional)  
- `dob` (optional, format: `yyyy-MM-dd`)  
- `gender` (optional)  
- `password` (optional)  

**Response**:  
- `200 OK`: `"Nurse updated successfully"`  
- `404 Not Found`: `"Nurse not found"`  
- `400 Bad Request`: `"Invalid data"`

---

### **Delete Nurse**  
**Endpoint**: `/api/nurses/{id}`  
**Method**: `DELETE`

Deletes an existing nurse.

**Takes**:  
- `id` (required, path parameter)

**Response**:  
- `200 OK`: `"Nurse deleted successfully"`  
- `404 Not Found`: `"Nurse not found"`  
- `400 Bad Request`: `"Invalid data"`

---

## **Get Patients Assigned to Logged-in Nurse**  
**Endpoint**: `/nurse/patients`  
**Method**: `GET`

Retrieves the list of patients assigned to the currently logged-in nurse.

**Request**:  
- No parameters are required in the request as it uses the current logged-in nurse's credentials.

**Response**:  
- `200 OK`: Returns a list of patients assigned to the logged-in nurse.

---

## **Record Patient Vitals**

**Endpoint**: `/api/nurses/patients/{patientId}/vitals`  
**Method**: `POST`

Allows the nurse to record the patient's vitals.

**Takes**:  
- `temperature` (required)  
- `bloodPressure` (required)  
- `heartRate` (required)  
- `respirationRate` (required)

**Response**:  
- `200 OK`: `"Vitals recorded successfully"`  
- `400 Bad Request`: `"Invalid patient or vitals data"`

---

**END OF DOCUMENTATION**
