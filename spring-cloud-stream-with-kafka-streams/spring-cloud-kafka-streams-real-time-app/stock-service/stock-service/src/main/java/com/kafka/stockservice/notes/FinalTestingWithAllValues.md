2024-02-18T16:59:02.597+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 59,Order{id=59, customerId=1, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:02.588618600, type=BUY, amount=1073}
2024-02-18T16:59:02.702+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=51, customerId=7, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:58:58.560732700, type=BUY, amount=1563},orderSell:Order{id=58, customerId=9, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:02.587714600, type=SELL, amount=1503},
2024-02-18T16:59:02.706+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=59, customerId=1, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:02.588618600, type=BUY, amount=1073},orderSell:Order{id=46, customerId=3, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T16:58:56.541986200, type=SELL, amount=947},
2024-02-18T16:59:02.714+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : translation key: 20 and value: Transaction{id=20, buyOrderId=59, sellOrderId=48, amount=100, price=1048, creationTime=2024-02-18T16:59:02.714355900, status='NEW'}
2024-02-18T16:59:02.715+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=59, customerId=1, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:02.588618600, type=BUY, amount=1073},orderSell:Order{id=52, customerId=8, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T16:58:59.569915500, type=SELL, amount=1082},
2024-02-18T16:59:02.810+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : aggregate transation:        ,value:Transaction{id=20, buyOrderId=59, sellOrderId=48, amount=100, price=1048, creationTime=2024-02-18T16:59:02.714355900, status='NEW'}
2024-02-18T16:59:03.597+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 60,Order{id=60, customerId=9, productId=3, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:03.594395800, type=SELL, amount=4982}
2024-02-18T16:59:03.604+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 61,Order{id=61, customerId=1, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:03.595396800, type=BUY, amount=1453}
2024-02-18T16:59:03.711+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=49, customerId=6, productId=3, productCount=100, realizedCount=0, creationDate=2024-02-18T16:58:57.552509400, type=BUY, amount=5076},orderSell:Order{id=60, customerId=9, productId=3, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:03.594395800, type=SELL, amount=4982},
2024-02-18T16:59:03.711+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=61, customerId=1, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:03.595396800, type=BUY, amount=1453},orderSell:Order{id=42, customerId=7, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:58:54.524678500, type=SELL, amount=1465},
2024-02-18T16:59:03.711+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=61, customerId=1, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:03.595396800, type=BUY, amount=1453},orderSell:Order{id=56, customerId=6, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:01.583487, type=SELL, amount=1463},
2024-02-18T16:59:03.711+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=61, customerId=1, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:03.595396800, type=BUY, amount=1453},orderSell:Order{id=58, customerId=9, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:02.587714600, type=SELL, amount=1503},
2024-02-18T16:59:04.339+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transactionTotal was key : NEW and value is : TransactionTotal(count=20, productCount=2000, prize=4105500)
2024-02-18T16:59:04.590+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product key:5, value:TransactionTotal(count=3, productCount=300, prize=746600)
2024-02-18T16:59:04.590+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product key:2, value:TransactionTotal(count=3, productCount=300, prize=587600)
2024-02-18T16:59:04.590+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product key:3, value:TransactionTotal(count=3, productCount=300, prize=1510400)
2024-02-18T16:59:04.590+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product key:4, value:TransactionTotal(count=3, productCount=300, prize=449300)
2024-02-18T16:59:04.590+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product key:1, value:TransactionTotal(count=8, productCount=800, prize=811600)
2024-02-18T16:59:04.608+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 63,Order{id=63, customerId=10, productId=5, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:04.605949300, type=BUY, amount=2407}
2024-02-18T16:59:04.614+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 62,Order{id=62, customerId=9, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:04.604949600, type=SELL, amount=1425}
2024-02-18T16:59:04.635+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[5@1708255710000/1708255740000], value:TransactionTotal(count=3, productCount=300, prize=746600)
2024-02-18T16:59:04.636+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[4@1708255710000/1708255740000], value:TransactionTotal(count=2, productCount=200, prize=298000)
2024-02-18T16:59:04.636+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[2@1708255710000/1708255740000], value:TransactionTotal(count=3, productCount=300, prize=587600)
2024-02-18T16:59:04.636+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[1@1708255710000/1708255740000], value:TransactionTotal(count=7, productCount=700, prize=706800)
2024-02-18T16:59:04.636+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[3@1708255710000/1708255740000], value:TransactionTotal(count=2, productCount=200, prize=1009900)
2024-02-18T16:59:04.662+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[3@1708255740000/1708255770000], value:TransactionTotal(count=1, productCount=100, prize=500500)
2024-02-18T16:59:04.662+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[4@1708255740000/1708255770000], value:TransactionTotal(count=1, productCount=100, prize=151300)
2024-02-18T16:59:04.662+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : Total per product last 30 seconds key:[1@1708255740000/1708255770000], value:TransactionTotal(count=1, productCount=100, prize=104800)
2024-02-18T16:59:04.716+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=63, customerId=10, productId=5, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:04.605949300, type=BUY, amount=2407},orderSell:Order{id=44, customerId=2, productId=5, productCount=100, realizedCount=0, creationDate=2024-02-18T16:58:55.531796100, type=SELL, amount=2576},
2024-02-18T16:59:04.720+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : The transaction failed for this records orderBuy:Order{id=51, customerId=7, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:58:58.560732700, type=BUY, amount=1563},orderSell:Order{id=62, customerId=9, productId=4, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:04.604949600, type=SELL, amount=1425},
2024-02-18T16:59:04.725+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : translation key: 21 and value: Transaction{id=21, buyOrderId=61, sellOrderId=62, amount=100, price=1439, creationTime=2024-02-18T16:59:04.725987700, status='NEW'}
2024-02-18T16:59:04.827+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : aggregate transation:        ,value:Transaction{id=21, buyOrderId=61, sellOrderId=62, amount=100, price=1439, creationTime=2024-02-18T16:59:04.725987700, status='NEW'}
2024-02-18T16:59:05.615+05:30  INFO 22292 --- [stock-service] [-StreamThread-1] c.k.stockservice.service.StockService    : merge order saved : 65,Order{id=65, customerId=3, productId=1, productCount=100, realizedCount=0, creationDate=2024-02-18T16:59:05.612947700, type=BUY, amount=982}
2024-02-18T1


- http://localhost:8080/transactions/all
````
{
    "count": 40,
    "productCount": 4000,
    "prize": 9396100
}
````
http://localhost:8080/transactions/product/5

````
{
    "count": 6,
    "productCount": 600,
    "prize": 1491100
}
````

http://localhost:8080/transactions/product/latest/5


````
[
    {
        "count": 1,
        "productCount": 100,
        "prize": 247800
    },
    {
        "count": 4,
        "productCount": 400,
        "prize": 1001400
    }
]
````

http://localhost:8080/transactions/product

````
{
    "1": {
        "count": 12,
        "productCount": 1200,
        "prize": 1210500
    },
    "2": {
        "count": 5,
        "productCount": 500,
        "prize": 986900
    },
    "3": {
        "count": 9,
        "productCount": 900,
        "prize": 4514100
    },
    "4": {
        "count": 8,
        "productCount": 800,
        "prize": 1193500
    },
    "5": {
        "count": 6,
        "productCount": 600,
        "prize": 1491100
    }
}
````
http://localhost:8080/transactions/latest/product
````
{
    "[4@1708320690000/1708320720000]": {
        "count": 4,
        "productCount": 400,
        "prize": 596100
    },
    "[2@1708320660000/1708320690000]": {
        "count": 1,
        "productCount": 100,
        "prize": 198800
    },
    "[5@1708320690000/1708320720000]": {
        "count": 2,
        "productCount": 200,
        "prize": 493100
    },
    "[5@1708320660000/1708320690000]": {
        "count": 1,
        "productCount": 100,
        "prize": 251600
    },
    "[3@1708320690000/1708320720000]": {
        "count": 2,
        "productCount": 200,
        "prize": 997600
    },
    "[1@1708320660000/1708320690000]": {
        "count": 6,
        "productCount": 600,
        "prize": 608200
    }
}
````
= above only one is not tested properly
= Due to some error finally converted to Map<Windowed<Integer>, TransactionTotal>
from Map<Integer, TransactionTotal>