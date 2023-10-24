# REST API
## MongoDB REST API 

### POST /api/v1/mongodb/create-customer
  
Request:   
```json
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String"
```  
Response:  
```json
{
    "customerID": "UUIDStr"
}
```  
### POST /api/v1/mongodb/delete-customer-by-id
  
Request:   
```json
{
    "customerID": "UUIDStr"
}
```
  
Response:   
**200**

### POST /api/v1/mongodb/delete-customer-by-phone
  
Request:   
```json
{
    "phone": "String"
}
```
  
Response:   
**200**

### POST /api/v1/mongodb/get-customer-by-id
  
Request:  
```json
{
    "customerID": "UUIDStr"
}
```  
Response:   
```json
{
    "brandID": "UUIDStr",
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String",
    "createdAt": "String"
}
```

### POST /api/v1/mongodb/get-customer-by-phone
  
Request:  
```json
{
    "phone": "String"
}
```  
Response:   
```json
{
    "brandID": "UUIDStr",
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String",
    "createdAt": "String"
}
```

### POST /api/v1/mongodb/get-customer-with-filter
  
Request:  
```json
{
    "limit": 10,
    "offset": 0,
    "id": "String",
    "phone": "String"
}
```  
Response:   
```json
{
    "brandID": "UUIDStr",
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String",
    "createdAt": "String"
}
```

### POST /api/v1/mongodb/update-customer-by-id
  
Request:  
```json
{
    "id": "UUIDStr",
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String",
    "createdAt": "String"
}
```  
Response:   
**200**

### POST /api/v1/mongodb/update-customer-by-phone
  
Request:  
```json
{
    "id": "UUIDStr",
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String",
    "createdAt": "String"
}
```  
Response:   
**200**

## PosgreSQL REST API 

### POST /api/v1/postgresql/create-customer
  
Request:   
```json
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String"
```  
Response:  
```json
{
    "customerID": "UUIDStr"
}
```  
### POST /api/v1/postgresql/delete-customer-by-id
  
Request:   
```json
{
    "customerID": "UUIDStr"
}
```
  
Response:   
**200**

### POST /api/v1/postgresql/delete-customer-by-phone
  
Request:   
```json
{
    "phone": "String"
}
```
  
Response:   
**200**

### POST /api/v1/postgresql/get-customer-by-id
  
Request:  
```json
{
    "customerID": "UUIDStr"
}
```  
Response:   
```json
{
    "brandID": "UUIDStr",
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String",
    "createdAt": "String"
}
```

### POST /api/v1/postgresql/get-customer-by-phone
  
Request:  
```json
{
    "phone": "String"
}
```  
Response:   
```json
{
    "brandID": "UUIDStr",
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String",
    "createdAt": "String"
}
```

### POST /api/v1/postgresql/get-customer-with-filter
  
Request:  
```json
{
    "limit": 10,
    "offset": 0,
    "id": "String",
    "phone": "String"
}
```  
Response:   
```json
{
    "brandID": "UUIDStr",
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String",
    "createdAt": "String"
}
```

### POST /api/v1/postgresql/update-customer-by-id
  
Request:  
```json
{
    "id": "UUIDStr",
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String",
    "createdAt": "String"
}
```  
Response:   
**200**

### POST /api/v1/postgresql/update-customer-by-phone
  
Request:  
```json
{
    "id": "UUIDStr",
    "name": "String",
    "yearOfBirth": "String",
    "firstPhone": "String",
    "secondPhone": "String",
    "createdAt": "String"
}
```  
Response:   
**200**

