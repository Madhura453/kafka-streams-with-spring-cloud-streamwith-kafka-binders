- For only where we wrote Materialized.<String, TransactionTotal>as(storeSupplier)
state store . for fetching data from that state stores we need InteractiveQueryService

- http://localhost:8080/transactions/all

````
{
    "count": 5,
    "productCount": 500,
    "prize": 510000
}
````

http://localhost:8080/transactions/product/1

````
{
    "count": 5,
    "productCount": 500,
    "prize": 510000
}
````

http://localhost:8080/transactions/product/latest/1

````
[
    {
        "count": 5,
        "productCount": 500,
        "prize": 510000
    }
]
````

http://localhost:8080/transactions/product

````
{
    "1": {
        "count": 5,
        "productCount": 500,
        "prize": 510000
    }
}
````
http://localhost:8080/transactions/latest/product
````
{
    "1": {
        "count": 5,
        "productCount": 500,
        "prize": 510000
    }
}
````
