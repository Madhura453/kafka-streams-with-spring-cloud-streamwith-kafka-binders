# step 1
- get data from https://api.domainsdb.info/v1/domains/search?domain=facebook&zone=com
  produce data to kafka topic processor-topic
- o/p Processor-topic
````
{"domain":"facebook-mobile.com","create_date":"2024-02-10T21:13:31.937685","update_date":"2024-02-10T21:13:31.937688","country":"US","a":null,"txt":null,"ns":null,"dead":false,"mx":null,"cname":null}
{"domain":"facebook-photo.com","create_date":"2024-02-09T14:07:40.557721","update_date":"2024-02-09T14:07:40.557723","country":"US","a":null,"txt":null,"ns":null,"dead":false,"mx":null,"cname":null}
{"domain":"facebook-order.com","create_date":"2024-02-06T21:31:01.233106","update_date":"2024-02-06T21:31:01.233108","country":null,"a":null,"txt":null,"ns":null,"dead":false,"mx":null,"cname":null}
{"domain":"facebook-giris.com","create_date":"2024-02-02T19:31:51.860600","update_date":"2024-02-02T19:31:51.860602","country":null,"a":null,"txt":null,"ns":null,"dead":false,"mx":null,"cname":null}
{"domain":"facebook-mex.com","create_date":"2024-02-01T10:39:27.821258","update_date":"2024-02-01T10:39:27.821260","country":null,"a":null,"txt":null,"ns":null,"dead":false,"mx":null,"cname":null}
{"domain":"facebook-adulto.com","create_date":"2024-02-01T10:39:27.821052","update_date":"2024-02-01T10:39:27.821054","country":"AU","a":null,"txt":null,"ns":null,"dead":false,"mx":null,"cname":null}
{"domain":"facebook-01.com","create_date":"2024-02-01T10:39:27.820872","update_date":"2024-02-01T10:39:27.820874","country":null,"a":null,"txt":null,"ns":null,"dead":false,"mx":null,"cname":null}
````