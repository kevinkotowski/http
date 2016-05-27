# http

Application class responsibilities:
-----------------------------------

* Server
    * connects Network via port
    * runs listener
        * creates shared queue
        * runs fixed pool of Producer threads
        * runs fixed pool of Consumer threads

* Producer
    * gets Requests from Network and puts into shared queue

* Consumer
    * polls for Requests from shared queue
    * Handler converts Requests into Responses, which are run

* Handler
    * Router maps Requests to Controllers

* Router
    * routes are defined and associated to Controllers
    * accepts Controller registrations

* Controller
    * accepts Resolver registrations
    * executes Model operations as per Resolver
    * executes View operations as per Resolver

* Resolver
    * interrogates Request to determine Model and View operations needed
    * examples:
        * if PUT and etag match and content, then persist to file Model
        * if GET and file type is image, read from image Model
        * if GET and file type is directory, format directory list View

* Model
    * each class will execute a specific IO operation
    * examples:
        * read from directory
        * read from file
        * read from image
        * persist to file

* View
    * each class will format Response in some manner