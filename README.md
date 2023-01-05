# Code Challenge

## Tech stack:
- kotlin
- Spring boot
- H2 in memory data base
- Junit library for unit testing

## How to test

### Create Policy
In order to create a policy call the ``POST`` endpoint with following payload
``http://SERVER:PORT/policyService/``
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
In order to get a policy call the ``GET`` endpoint with following payload. 

``http://SERVER:PORT/policyService/{requestDate}/requestDate/{policyId}/policyId``

or  ``http://SERVER:PORT/policyService/`` to retrieve all policies


### Update
In order to get a policy call the ``PATCH`` endpoint with following payload
``http://SERVER:PORT/policyService/policies``
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
         "firstName":"Roger",
         "secondName":"Water",
         "premium":15.90
      }
   ]
}
```
