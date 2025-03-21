/**
 * Creates a new user role with the specified name and description.
 *
 * @param {string} roleName - The name of the new role to be created.
 * @param {string} description - A brief description of the role.
 *
 * Sends a POST request to the API to create the user role and logs the response status.
 */
async function createUserRole(roleName, description) {
    let url = `http://localhost/api/v1/userRole/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        roleName,
        description,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};
/**
 * Grants a specified role access permissions on a given model.
 *
 * @param {number} modelID - The ID of the model to which access is being granted.
 * @param {number} roleID - The ID of the role receiving the access.
 * @param {Object} accessOperation - An object defining access permissions for different operations.
 *
 * Example:
 * ```json
 * {
 *   "modelID": 1,
 *   "roleID": 1,
 *   "accessOperation": {
 *     "read": 0,
 *     "write": 1,
 *     "delete": 1
 *   }
 * }
 * ```
 *
 * In the `accessOperation` object:
 * - `0` means no access to the operation.
 * - `1` grants access to the operation.
 */

async function grantAccessRight(modelID, roleID, accessOperation) {
    let url = `http://localhost/api/v1/users/access/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        modelID,
        roleID,
        accessOperation,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
}

/**
 * Retrieves access rights for a specific user role.
 *
 * @param {string} userRoleName - The name of the user role to fetch access rights for.
 *
 * Sends a GET request to the API and logs the response status.
 */
async function getAccessRightsByUSerRoleName(userRoleName) {
    let url = `http://localhost/api/v1/users/access/accessRights/${userRoleName}`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

/**
 * Retrieves all access rights for all roles and models.
 *
 * Sends a GET request to the API and logs the response status.
 */
async function getAllAccessRights() {
    let url = `http://localhost/api/v1/users/access/allAccess`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

/**
 * Updates access rights for a specific access entry.
 *
 * @param {number} accessID - The ID of the access entry to update.
 * @param {number} modelID - The ID of the model associated with the access entry.
 * @param {number} roleID - The ID of the role associated with the access entry.
 * @param {Object} accessOperation - An object defining access permissions for different operations.
 *
 * Example:
 * ```json
 * {
 *   "modelID": 1,
 *   "roleID": 1,
 *   "accessOperation": {
 *     "read": 1,
 *     "write": 0,
 *     "delete": 1
 *   }
 * }
 * ```
 *
 * In the `accessOperation` object:
 * - `0` means no access to the operation.
 * - `1` grants access to the operation.
 *
 * Sends a PUT request to the API to update the access rights and logs the response status.
 */
async function updateAccessRight(accessID, modelID, roleID, accessOperation) {
    let url = `http://localhost/api/v1/users/access/updateAccess/${accessID}`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        modelID,
        roleID,
        accessOperation,
    };

    let response = await fetch(url, {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

/**
 * Retrieves access rights for a specific access ID.
 *
 * @param {number} accessID - The ID of the access entry to retrieve.
 *
 * Sends a GET request to the API and logs the response status.
 */
async function getAccessRight(accessID) {
    let url = `http://localhost/api/v1/users/access/${accessID}`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

/**
 * Registers a new external user.
 *
 * @param {string} firstName - The first name of the user.
 * @param {string} lastName - The last name of the user.
 * @param {string} email - The email address of the user.
 * @param {string} password - The password for the user account.
 * @param {number} rolesId - The ID of the role assigned to the user.
 * @param {string} phoneNumber - The phone number of the user.
 * @param {string} dateOfBirth - The user's date of birth (format: YYYY-MM-DD).
 * @param {string} gender - The gender of the user.
 *
 * Sends a POST request to register the external user and logs the response status.
 */
async function registerExternalUser(firstName, lastName, email, password, rolesId, phoneNumber, dateOfBirth, gender) {
    let url = `http://localhost/api/v1/users/external_user`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        firstName,
        lastName,
        email,
        password,
        rolesId,
        phoneNumber,
        dateOfBirth,
        gender,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

/**
 * Registers a new internal user account with an assigned service.
 *
 * @param {string} firstName - The first name of the user.
 * @param {string} lastName - The last name of the user.
 * @param {string} email - The email address of the user.
 * @param {string} password - The password for the user account.
 * @param {number} rolesId - The ID of the role assigned to the user.
 * @param {string} phoneNumber - The phone number of the user.
 * @param {string} dateOfBirth - The user's date of birth (format: YYYY-MM-DD).
 * @param {string} gender - The gender of the user.
 * @param {string} service - The service or department the user belongs to.
 *
 * Sends a POST request to register the internal user and logs the response status.
 */
async function registerNewInAccount(firstName, lastName, email, password, rolesId, phoneNumber, dateOfBirth, gender, service) {
    let url = `http://localhost/api/v1/users/in_user`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        firstName,
        lastName,
        email,
        password,
        rolesId,
        phoneNumber,
        dateOfBirth,
        gender,
        service,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

/**
 * Authenticates a user with email and password.
 *
 * @param {string} email - The user's email address.
 * @param {string} password - The user's password.
 *
 * Sends a POST request to authenticate the user and logs the response status.
 */
async function authenticate(email, password) {
    let url = `http://localhost/api/v1/auth/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        email,
        password,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function test1(body) {
    let url = `http://localhost/api/v1/auth/test`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function getAllBoats() {
    let url = `http://localhost/api/v1/boat/`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

async function registerBoat(name, numMatriculation, motorType, fabricationDate, boatCategory, boatWeight, supportedWeight, boatClassList) {
    let url = `http://localhost/api/v1/boat/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        name,
        numMatriculation,
        motorType,
        fabricationDate,
        boatCategory,
        boatWeight,
        supportedWeight,
        boatClassList,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function updateBoat(boatID, name, numMatriculation, motorType, fabricationDate, boatCategory, boatWeight, supportedWeight, boatClassList) {
    let url = `http://localhost/api/v1/boat/${boatID}`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        name,
        numMatriculation,
        motorType,
        fabricationDate,
        boatCategory,
        boatWeight,
        supportedWeight,
        boatClassList,
    };

    let response = await fetch(url, {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function getBoatById(boatID) {
    let url = `http://localhost/api/v1/boat/${boatID}/`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

async function updateBoatClass(boatClassID, name, placesNumber, boatID, priceListID, productID) {
    let url = `http://localhost/api/v1/boatClass/${boatClassID}`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        name,
        placesNumber,
        boatID,
        priceListID,
        productID,
    };

    let response = await fetch(url, {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function getBoatClasses(boatID) {
    let url = `http://localhost/api/v1/boatClass/${boatID}`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

async function newClass(boatID, name, placesNumber, priceListID, productID) {
    let url = `http://localhost/api/v1/boatClass/${boatID}/newClass`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        name,
        placesNumber,
        boatID,
        priceListID,
        productID,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function createProduct(productName, productDescription, productType, productCategoryId, priceId) {
    let url = `http://localhost/api/av1/products/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        productName,
        productDescription,
        productType,
        productCategoryId,
        priceId,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function createProductCategory(productTypeName, productTypeDescription) {
    let url = `http://localhost/api/av1/productCategory/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        productTypeName,
        productTypeDescription,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function createEmployee(firstName, lastName, email, phone, specialization, degree, departmentID, positionID) {
    let url = `http://localhost/api/v1/employee/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        firstName,
        lastName,
        email,
        phone,
        specialization,
        degree,
        departmentID,
        positionID,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function createUserFromEmployee(body) {
    let url = `http://localhost/api/v1/employee/createUser`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function getEmployee(email) {
    let url = `http://localhost/api/v1/employee/${email}`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

async function getPosition() {
    let url = `http://localhost/api/v1/position/`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

async function createPosition(positionName, description, departmentID) {
    let url = `http://localhost/api/v1/position/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        positionName,
        description,
        departmentID,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function getAllDepartments() {
    let url = `http://localhost/api/v1/department/`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

async function createDepartment(departmentName, managerID, parentDepartmentID, childrenDepartmentIDs) {
    let url = `http://localhost/api/v1/department/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        departmentName,
        managerID,
        parentDepartmentID,
        childrenDepartmentIDs,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function getDepartmentById(departmentID) {
    let url = `http://localhost/api/v1/department/${departmentID}`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

async function reserveTicket(email, firstName, lastName, telephone, operator, tripID, classID, note) {
    let url = `http://localhost/api/v1/ticket/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        email,
        firstName,
        lastName,
        telephone,
        operator,
        tripID,
        classID,
        note,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function getTicketsByTeller(tellerUserName) {
    let url = `http://localhost/api/v1/ticket/${tellerUserName}`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

async function assignUserRoleToRole(userName, userRoleIds) {
    let url = `http://localhost/api/v1/userRole/assignUserToRole`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        userName,
        userRoleIds,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function getUserRoleByUserName(userName) {
    let url = `http://localhost/api/v1/userRole/userRoles/${userName}`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

async function grantUserRoleAccessRights(roleID, body) {
    let url = `http://localhost/api/v1/userRole/${roleID}/grantAccessRights`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function createPrice(amount, startDate, endDate, deviseName, isDefault) {
    let url = `http://localhost/api/v1/price/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        amount,
        startDate,
        endDate,
        deviseName,
        isDefault,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function registerRoute(routeName, origin, destination) {
    let url = `http://localhost/api/v1/routes/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        routeName,
        origin,
        destination,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};

async function getTrips() {
    let url = `http://localhost/api/v1/trips/`;

    let response = await fetch(url, {
        method: 'GET',
    });
    console.log(response.status)
};

async function createTrip(departureDate, arrivalDate, days, boatID, classIDs, tripType, routeID, expectedComeBackInHours, durationInWeeks, tag) {
    let url = `http://localhost/api/v1/trips/`;

    let headers = {
        'Content-Type': 'application/json',
    };

    let body = {
        departureDate,
        arrivalDate,
        days,
        boatID,
        classIDs,
        tripType,
        routeID,
        expectedComeBackInHours,
        durationInWeeks,
        tag,
    };

    let response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body),
    });
    console.log(response.status)
};