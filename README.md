# http

Application class responsibilities:
-----------------------------------

* Server
    * initializes Network by connecting to port
    * initializes Router
    * runs listener
        * creates shared queue
        * runs fixed pool of Producer threads
        * runs fixed pool of Consumer threads

* Producer
    * runnable class gets Requests from Network and puts into shared queue

* Request
    * implements HTTP protocol reading data stream for Request from socket and organizaing data for Response

* Consumer
    * runnable class polls for Requests from shared queue
    * routes Requests into Controller, which converts into Responses
    * runs Responses
    
* Response
    * implements HTTP protocol sending to socket a properly formed data stream

* Handler
    * initializes Router
    * uses Router to route Requests to Controllers

* Router
    * all Routes are defined and associated to Controllers
    * accepts Route registrations by path
    * returns Options list for a given path

* Controller
    <!--* accepts Resolver registrations-->
    * executes Model operations as per Resolver
    * executes View operations as per Resolver

<!--* Resolver-->
    <!--* interrogates Request to determine Model and View operations needed-->
    <!--* examples:-->
        <!--* if PUT and etag match and content, then persist to file Model-->
        <!--* if GET and file type is image, read from image Model-->
        <!--* if GET and file type is directory, format directory list View-->

* Model
    * each class will execute a specific IO operation
    * examples:
        * read from directory
        * read from file
        * read from image
        * persist to file

* View
    * each class will format Response in some manner