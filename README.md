# subscriber-demo
## 1. Step
In application.properties file has a property which is called `initial.data.path` has initial file address to sync cache and file data.

- In order to run application, tester has to add `${home}/app.json` file. Tester can add
  ######   {"subscribers":[{"id":1,"name":"Stephan King","msisdn":905552551122},{"id":2,"name":" Alice Gracy","msisdn":905552551133},{"id":3,"name":"Glory Wisdom","msisdn":905552551144}]}
  
 
## 2. Step
When app start to run
1.  App read json file from path and insert to app's cache.
2.  Quartz Job runs each minute to sync `file` and `cache` data.

## 3. Step
- Tester can find wsdl in [address](http://localhost:8080/ws/subscriberWsdl.wsdl).
    - In order to test SOAP services, WSDL file can be run in any SOAP client app.
    
- REST request examples

|  Request Method | URL | Sample Data| Success Http Status Code | Error Http Status Code
|------|----|----|----|----|
| `POST`  | localhost:8080\subscriber | {"id":"4","name":"Stephany Kirk", "msisdn":"905558887965"} | `201` | `400` |
|  `PUT` |  localhost:8080\subscriber |  {"id":"4","name":"New Name Value", "msisdn":"905558887965"}  | `200`| `404` | 
| `DELETE` | localhost:8080\subscriber | {"id":"4"} | `200`| `404` |  


