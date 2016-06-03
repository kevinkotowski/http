# http

Application class responsibilities:
-----------------------------------

* Server
    * initializes Network by connecting to port
    * initializes Router by registering Routes
    * runs listener
        * creates shared queue
        * runs fixed pool of Producer threads
        * runs fixed pool of Consumer threads

* Network
    * opens a ServerSocket on a port
    * iterates through inbound Requests using the RequestParser
    
* Router
    * registers Routes
    * Requests are routed to Controller via Route
    * returns valid options list for a given path
    * logs access

* Route
    * simple data structure storing Method, path, and Controller combination
    
* RequestParser
    * implements HTTP protocol reading data stream from Socket and puts into Request

* Request
    * holds accessible version of raw Request (method, path, query, headers, etc.)

* Producer
    * runnable class puts Requests from Network onto shared queue

* Consumer
    * runnable class polls for Requests from shared queue
    * routes Requests to a Controller, which converts into Responses
    * runs Responses
    
* Controller
    * executes specific operations for a Request, returning a Response

* Response
    * implements HTTP protocol sending to Socket a properly formed data stream
    
* Socket
    * simple wrapper for java sockets
    
* Method
    * enumerated type for valid Request methods
