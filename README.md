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
**Endpoint**: `/api/receptionists/patients/register`  
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

- **URL**: `/patients/{id}/send-to-nurse`
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

### **Get Patients Assigned to Logged-in Nurse**  
**Endpoint**: `/nurse/patients`  
**Method**: `GET`

Retrieves the list of patients assigned to the currently logged-in nurse.

**Request**:  
- No parameters are required in the request as it uses the current logged-in nurse's credentials.

**Response**:  
- `200 OK`: Returns a list of patients assigned to the logged-in nurse.

---


### 1. **Record Patient Vitals**

**Method:** `POST`

**URL:** `/api/nurses/patients/{patientId}/vitals`

**Description:** Records vitals for a patient.

**Request Parameters:**
- `patientId` (Path Variable): ID of the patient.

**Request Body (JSON):**


**Response:**
- **200 OK**: Vitals recorded successfully.
- **400 Bad Request**: Error message if the operation fails.

---

### 2. **Update Patient Vitals**

**Method:** `PUT`

**URL:** `/api/nurses/patients/vitals/{vitalsId}`

**Description:** Updates the vitals for a patient.

**Request Parameters:**
- `vitalsId` (Path Variable): ID of the vitals record to update.

**Request Body (JSON):**

**Response:**
- **200 OK**: Vitals updated successfully.
- **400 Bad Request**: Error message if the operation fails.

---

### 3. **Delete Patient Vitals**

**Method:** `DELETE`

**URL:** `/api/nurses/patients/vitals/{vitalsId}`

**Description:** Deletes a vitals record for a patient.

**Request Parameters:**
- `vitalsId` (Path Variable): ID of the vitals record to delete.

**Response:**
- **200 OK**: Vitals deleted successfully.
- **400 Bad Request**: Error message if the operation fails.

---

### 4. **Send Vitals to Doctor**

**Method:** `POST`

**URL:** `/api/nurses/patients/vitals/{vitalsId}/send-to-doctor/{doctorId}`

**Description:** Sends a vitals record to a doctor.

**Request Parameters:**
- `vitalsId` (Path Variable): ID of the vitals record to send.
- `doctorId` (Path Variable): ID of the doctor to receive the vitals.

**Response:**
- **200 OK**: Vitals sent successfully to the doctor.
- **400 Bad Request**: Error message if the operation fails.

---
## 1. Create a Doctor
**Description:** Adds a new doctor to the system.
- **Method:** `POST`
- **URL:** `/api/doctors`
- **Headers:**
  - `Content-Type: application/json`
- **Response Codes:**
  - `200 OK`: Doctor created successfully.
  - `400 Bad Request`: Validation error or other issues.

---

### 2. Get All Doctors
**Description:** Retrieves a list of all doctors.
- **Method:** `GET`
- **URL:** `/api/doctors`
- **Response Codes:**
  - `200 OK`: Returns the list of doctors.

---

### 3. Get Doctor by ID
**Description:** Fetches details of a specific doctor by their ID.
- **Method:** `GET`
- **URL:** `/api/doctors/{id}`
  - Replace `{id}` with the ID of the doctor.
- **Response Codes:**
  - `200 OK`: Doctor details retrieved successfully.
  - `404 Not Found`: Doctor with the specified ID does not exist.

---

### 4. Update a Doctor
**Description:** Updates the details of a specific doctor.
- **Method:** `PUT`
- **URL:** `/api/doctors/{id}`
  - Replace `{id}` with the ID of the doctor.
- **Headers:**
  - `Content-Type: application/json`
- **Response Codes:**
  - `200 OK`: Doctor details updated successfully.
  - `400 Bad Request`: Validation error or other issues.

---

### 5. Delete a Doctor
**Description:** Deletes a specific doctor from the system.
- **Method:** `DELETE`
- **URL:** `/api/doctors/{id}`
  - Replace `{id}` with the ID of the doctor.
- **Response Codes:**
  - `200 OK`: Doctor deleted successfully.
  - `400 Bad Request`: Error during deletion.

---


## 1. Create Diagnosis
**Method**: `POST`  
**URL**: `/api/doctors/patients/{patientId}/diagnoses`

### Path Variables:
- `patientId`: Replace with the actual patient ID.

### Query Parameter:
- `doctorId`: The ID of the doctor creating the diagnosis.

### Request Body (JSON):

### Expected Response:
- **200 OK**: Returns the created diagnosis.

- **400 Bad Request**: If an error occurs.

---

## 2. Update Diagnosis
**Method**: `PUT`  
**URL**: `/api/doctors/diagnoses/{diagnosisId}`

### Path Variables:
- `diagnosisId`: Replace with the actual diagnosis ID.

### Request Body (JSON):


### Expected Response:
- **200 OK**: Returns the updated diagnosis.
- **400 Bad Request**: If an error occurs.

---

## 3. Delete Diagnosis
**Method**: `DELETE`  
**URL**: `/api/doctors/diagnoses/{diagnosisId}`

### Path Variables:
- `diagnosisId`: Replace with the ID of the diagnosis to delete.

### Expected Response:
- **200 OK**: Success message.

- **400 Bad Request**: If an error occurs.

---

## 4. Send Prescription to Receptionist
**Method**: `POST`  
**URL**: `/api/doctors/patients/{patientId}/prescriptions`

### Path Variables:
- `patientId`: Replace with the actual patient ID.

### Query Parameter:
- `receptionistId`: The ID of the receptionist.

### Request Body (JSON):


### Expected Response:
- **200 OK**: Returns the created prescription.

- **400 Bad Request**: If an error occurs.
---


### 3. **Refresh Token**
- **URL**: `/refresh-token`
- **Method**: `POST`
- **Description**: Refreshes the access token if the token is expired. The expired token must be provided in the `Authorization` header.
- **Request Header**:
  - `Authorization: Bearer JWT_TOKEN_HERE`
- **Response**:
  - **Status**: `200 OK`
  

  - **Status**: `403 Forbidden`
  - **Body**:
   

---

### 4. **Logout**
- **URL**: `/logout`
- **Method**: `POST`
- **Description**: Logs out the user by invalidating the access token and clearing it from storage.
- **Request Header**:
  - `Authorization: Bearer JWT_TOKEN_HERE`
- **Response**:
  - **Status**: `200 OK`
  - **Body**:
    


**END OF DOCUMENTATION**
