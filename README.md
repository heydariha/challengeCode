# Code Challenge

## Tech stack:
- kotlin
- Spring boot
- H2 in memory data base
- Junit library for unit testing

## How to test

### Create Policy
In order to create a policy call the ``POST`` endpoint with following payload
``http://localhost:9090/policyService/policies``
payload:
```
{
   "startDate":"2020-12-31",
   "insuredPersons":[
      {
         "firstName":"Jane",
         "secondName":"Johnson",
         "premium":12.90
      },
      {
         "firstName":"Jack",
         "secondName":"Doe",
         "premium":15.90
      }
   ]
}
```


### get created policy
In order to get a policy call the ``GET`` endpoint with following payload
``http://localhost:9090/policyService/policies``
```
{
   "requestDate":"2021-06-13",
   "policyId":"f6eb99a2-c54e-4e3c-8ae4-75212f19ba01"
}
```

#### Note
In my point of view, it would be better if we would call an ``GET`` endpoint with query parameters and not Json payload as it is not very Restfull


### Update
In order to get a policy call the ``PATCH`` endpoint with following payload
``http://localhost:9090/policyService/policies``
```
{
   "startDate":"2021-06-13",
	 "policyId":"f6eb99a2-c54e-4e3c-8ae4-75212f19ba01",
	 "totalPremium": 28.80,
   "insuredPersons":[
      {
         "firstName":"Jane",
         "secondName":"Johnson",
         "premium":6.00
      },
      {
         "firstName":"Hadi",
         "secondName":"Heydari",
         "premium":20.00
      },
      {
         "firstName":"Jack",
         "secondName":"Doe",
         "premium":15.90
      }
   ]
}
```
