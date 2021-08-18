# aura-datalog

## JSON Parsing 

JSON file containing endpoints is stored in the Assets Folder in Android Studio.


Parsing of JSON is done in ```getEndpointList()```.
Within this method,to read JSON from Assets folder call for the function ```readJSONFromAsset();```  and store it in a String jsonLoc. 

Further we are required to create two JSON Objects, jsonObj and endpoints. 
jsonObj is used to create an object out of the entire JSON file from jsonLoc : ```JSONObject jsonObj = new JSONObject(jsonLoc);```

endpoints collects the objects from jsonObj by using inbuilt getJSONObject method : ```JSONObject endpoints = jsonObj.getJSONObject("endpoints");```

Further, an Iterator is initialized to store the keys in the object-endpoints : ```Iterator<?> keys = endpoints.keys();```


```String keys1 = (String) keys.next();``` This String contains the keys in endpoints, and if keys has another object within it in endpoints create another object...

Hence, we use the if condition ```if (endpoints.get(keys1) instanceof JSONObject)
{
JSONObject jsonobj2 = endpoints.getJSONObject(keys1);
... } ``` 
Within this if condition, store all the keys in endpoints(JSON) into different variables. 

Since, we only want the Appliance ID(AID) to be displayed on the listview, add the variable "id" into the ArrayList al : ``` al.add(id);```

```int position = aa.getPosition(id);``` stores the position of AID in the ArrayAdapter.



## Data Logging: 
Once JSON parsing is complete, we need to display the AID and log data everytime that a user accesses an Appliance, therefore we would be required to store a particular Appliance ID aswell as the TimeStamp. 

In onItemClick call for the method ```logaid(i)```
When an AID is clicked on the application, the Appliance ID and the TimeStamp is written onto a textfile. 
            ```out.write( al.get(pos)+ "," + getCurrentTimeStamp() +  "\n");``` - this is responsible for getting the Appliance ID in a particular position that is passed into the method and stores the current timestamp alongside it. 
            
            
            
      






                    
               
                    






