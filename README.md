# subscriber-demo

 In `application.properties` file has some properties using in application.
- `initial.data.path` is a file path where cache data is stored.
- `schedule.each.minute` is a cron schedule. 
- `logging.file` is a file path where logs are written in.
    

## 1. Step
In order to run application,
1. project should be downloaded to local pc.
2. Because of maven, `mvn clean install` terminal command should be execeuted.
3. If 8080 port is not busy, app should start to work.
  
 
## How it works?
When app start to run
1.  When application runs, a data file is created if not exist,
    1. File path is defined into `application.properties`.
2.  Static initial data is persisted into `cache`
3.  Quartz Job runs each minute to sync from `cache` to `file` data.

## In Order to Test
- Tester can find wsdl in [address](http://localhost:8080/ws/subscriberWsdl.wsdl).
    - In order to test SOAP services, WSDL file can be run in any SOAP client app.
    
- REST requests

|  Request Method | URL | Sample Data| Success Http Status Code | Error Http Status Code
|------|----|----|----|----|
| `POST`  | localhost:8080\subscriber | {"id":"4","name":"Stephany Kirk", "msisdn":"905558887965"} | `201` | `400` |
|  `PUT` |  localhost:8080\subscriber |  {"id":"4","name":"New Name Value", "msisdn":"905558887965"}  | `200`| `404` | 
| `DELETE` | localhost:8080\subscriber | {"id":"4"} | `200`| `404` |  


